package xueqiao.trade.hosting.dealing.core;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TBase;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderContractSummary;
import xueqiao.trade.hosting.HostingExecOrderLegContractSummary;
import xueqiao.trade.hosting.HostingExecOrderStateValue;
import xueqiao.trade.hosting.HostingExecOrderTradeDirection;
import xueqiao.trade.hosting.HostingExecOrderTradeSummary;
import xueqiao.trade.hosting.HostingExecTrade;
import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.HostingExecTradeLeg;
import xueqiao.trade.hosting.HostingExecTradeLegInfo;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.dealing.storage.DealingDBV2;
import xueqiao.trade.hosting.dealing.storage.DealingStorageApiV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecOrderTableV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecTradeLegTableV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecTradeTableV2;
import xueqiao.trade.hosting.events.ExecOrderGuardEvent;
import xueqiao.trade.hosting.events.ExecOrderGuardType;
import xueqiao.trade.hosting.events.ExecTradeListChangedEvent;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.framework.utils.ErrorInfoCallHelper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class TradeListHandler {
    private static final SimpleDateFormat TRADE_DATETIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    
	private HostingExecOrder originOrder;
	private HostingExecOrder newOrder;

	private HostingExecOrderContractSummary contractSummary;
	private HostingExecOrderTradeDirection tradeDirection;

	private List<HostingExecTrade> dbTradeList;
	private List<HostingExecTradeLeg> dbTradeLegList;
	
	private List<HostingExecTrade> newTradeList = new ArrayList<HostingExecTrade>();
	private List<HostingExecTradeLeg> newTradeLegList = new ArrayList<HostingExecTradeLeg>();
	private List<HostingExecTradeLeg> updateTradeLegList = new ArrayList<HostingExecTradeLeg>();
	
	public TradeListHandler(HostingExecOrder execOrder) {
		this.originOrder = execOrder;
	}

	public HostingExecOrder getNewOrder() {
		return newOrder;
	}
	
	public void process(HostingExecOrderLegContractSummary legContractSummary
	        , List<HostingExecTradeLegInfo> tradeLegInfos) throws ErrorInfo {
		if (tradeLegInfos == null || tradeLegInfos.isEmpty()) {
			return ;
		}
		
		long startNano = System.nanoTime();
		try {
			HostingExecOrder operateOrder = prepare();
			if (operateOrder.getStateInfo().getCurrentState().getValue() == HostingExecOrderStateValue.ORDER_SLED_SEND_FAILED) {
				AppLog.w("[WARNING]send failed order but get trade, execOrderId=" + operateOrder.getExecOrderId());
				return ;
			}
			for (HostingExecTradeLegInfo tradeLegInfo : tradeLegInfos) {
				handleTradeInfo(legContractSummary, tradeLegInfo);
			}
			commit();
		} catch (ErrorInfo e) {
		    StringBuilder logBuilder = new StringBuilder(128);
		    logBuilder.append("process trade infos error ocuurs, execOrderId=").append(originOrder.getExecOrderId())
		              .append(", legContractSummary=").append(legContractSummary)
		              .append(", tradeLegInfos=[");
		    for (int index = 0; index < tradeLegInfos.size(); ++index) {
		        if (index > 0) {
		            logBuilder.append(",");
		        }
		        logBuilder.append(tradeLegInfos.get(index));
		    }
		    logBuilder.append("]").append(", errorCode=").append(e.getErrorCode())
		              .append(", errorMsg=").append(e.getErrorMsg());
		    AppLog.e(logBuilder.toString());
			if (e.getErrorCode() == TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_NOT_EXISTED.getValue()) {
				return ;
			}
			throw e;
		} finally {
		    if (AppLog.infoEnabled()) {
		        AppLog.i("TradeList process, tradeLegInfos size=" + tradeLegInfos.size() + ", time escaped="
		                + ((System.nanoTime() - startNano)/1000)  + "us");
		    }
		}
	}
	
	private HostingExecOrder prepare() throws ErrorInfo {
		return new DBQueryHelper<HostingExecOrder>(DealingDBV2.Global()) {
			@Override
			protected HostingExecOrder onQuery(Connection conn) throws Exception {
			    contractSummary = originOrder.getContractSummary();
			    dbTradeLegList = new HostingExecTradeLegTableV2(conn).getOrderTradeLegs(originOrder.getExecOrderId());
			    tradeDirection = originOrder.getOrderDetail().getTradeDirection();
			    
			    return originOrder;
			}
		}.query();
	}
	
	private void handleTradeInfo(HostingExecOrderLegContractSummary legContractSummary
	        , HostingExecTradeLegInfo tradeLegInfo) throws ErrorInfo {
		if (!tradeLegInfo.isSetLegUpsideTradeId()) {
			AppLog.w("handleTradeInfo tradeLegInfo no legUpsideTradeId for execOrderId=" + originOrder.getExecOrderId());
			return ;
		}
		if (!tradeLegInfo.isSetLegTradeVolume() || tradeLegInfo.getLegTradeVolume() <= 0) {
			AppLog.w("handleTradeInfo tradeLegInfo no legTradeVolume or legTradeVolumne <= 0 for execOrderId="
					+ originOrder.getExecOrderId());
			return ;
		}
		if (!tradeLegInfo.isSetLegTradePrice()) {
			AppLog.w("handleTradeInfo tradeLegInfo no legTradePrice for execOrderId=" + originOrder.getExecOrderId());
			return ;
		}
		if (!tradeLegInfo.isSetLegUpsideTradeDirection()) {
			AppLog.w("handleTradeInfo tradeLegInfo no legUpsideTradeDirection for execOrderId="
					+ originOrder.getExecOrderId());
			return ;
		}
		
		// 判断这条腿是否已经在DB记录中存在
		for (HostingExecTradeLeg dbTradeLeg : dbTradeLegList) {
			Preconditions.checkNotNull(dbTradeLeg.getTradeLegInfo());
			if (tradeLegInfo.getLegUpsideTradeId().equals(
			        dbTradeLeg.getTradeLegInfo().getLegUpsideTradeId())) {
				return ;
			}
		}
		
		HostingExecTradeLeg newTradeLeg = new HostingExecTradeLeg();
		
		// 查询合约腿索引
		if (contractSummary.isSetRelatedLegs() && !contractSummary.getRelatedLegs().isEmpty()) {
		    short matchIndex = -1;
		    for (short index = 0; index < contractSummary.getRelatedLegs().size(); ++index) {
		        HostingExecOrderLegContractSummary relatedLegContractSummary 
		            = contractSummary.getRelatedLegs().get(index);
		        if (relatedLegContractSummary.getLegSledContractCode().equals(legContractSummary.getLegSledContractCode())
		             && relatedLegContractSummary.getLegSledCommodityCode().equals(legContractSummary.getLegSledCommodityCode())
		             && relatedLegContractSummary.getLegSledCommodityType() == legContractSummary.getLegSledCommodityType()
		             && relatedLegContractSummary.getLegSledCommodityId() == legContractSummary.getLegSledCommodityId()
		             && relatedLegContractSummary.getLegSledExchangeMic().equals(legContractSummary.getLegSledExchangeMic())) {
		            matchIndex = index;
		            legContractSummary.setLegSledContractId(relatedLegContractSummary.getLegSledContractId());
		            break;
		        }
		    }
		    if (matchIndex == -1) {
		        AppLog.w("compose contract execOrderId=" + originOrder.getExecOrderId() + ", contractSummary="
		                + contractSummary + " does not match any leg for legContractSummary="
		                + legContractSummary);
		        return ;
		    }
		    
		    newTradeLeg.setLegIndex(matchIndex);
		} else {
		    if (!contractSummary.getSledContractCode().equals(legContractSummary.getLegSledContractCode())
		        || !contractSummary.getSledCommodityCode().equals(legContractSummary.getLegSledCommodityCode())
		        || contractSummary.getSledCommodityType() != legContractSummary.getLegSledCommodityType()
		        || contractSummary.getSledCommodityId() != legContractSummary.getLegSledCommodityId()
		        || !contractSummary.getSledExchangeMic().equals(legContractSummary.getLegSledExchangeMic())) {
		        AppLog.w("single leg contract execOrderId=" + originOrder.getExecOrderId() + ", contractSummary="
		                + contractSummary + " does not match legContractSummary="
		                + legContractSummary);
		        return ;
		    }
		    legContractSummary.setLegSledContractId(contractSummary.getSledContractId());
		    newTradeLeg.setLegIndex((short)0);
		}
		
        newTradeLeg.setExecOrderId(originOrder.getExecOrderId());
        newTradeLeg.setExecTradeLegId(ExecIDGenerator.Global().createExecTradeLegId());
        newTradeLeg.setTradeLegInfo(tradeLegInfo);
        newTradeLeg.setLegContractSummary(legContractSummary);
        newTradeLegList.add(newTradeLeg);
	}
	
	private static class UnMatchedTradeLeg {
        private boolean dbExist;
        private HostingExecTradeLeg tradeLeg;
        
        public UnMatchedTradeLeg(boolean dbExist, HostingExecTradeLeg tradeLeg) {
            this.dbExist = dbExist;
            this.tradeLeg = tradeLeg;
        }
        
        public boolean isDBExist() {
            return dbExist;
        }
        
        public HostingExecTradeLeg getTradeLeg() {
            return tradeLeg;
        }
    }
	
	private void addLegByTradeTime(UnMatchedTradeLeg addedLeg, List<UnMatchedTradeLeg> resultList) throws ParseException {
	    long addLegTimestamp = 0;
	    if (StringUtils.isNotEmpty(addedLeg.getTradeLeg().getTradeLegInfo().getLegTradeDateTime())) {
	        addLegTimestamp = TRADE_DATETIME_FORMATTER.parse(
	                addedLeg.getTradeLeg().getTradeLegInfo().getLegTradeDateTime()).getTime();
	    }
	    for (int index = 0; index < resultList.size(); ++index) {
	        HostingExecTradeLeg listTradeLeg = resultList.get(index).getTradeLeg();
	        long listLegTimestamp = 0;
	        if (StringUtils.isNotEmpty(listTradeLeg.getTradeLegInfo().getLegTradeDateTime())) {
	            listLegTimestamp = TRADE_DATETIME_FORMATTER.parse(
	                    listTradeLeg.getTradeLegInfo().getLegTradeDateTime()).getTime();
	        }
	        
	        if (listLegTimestamp > addLegTimestamp) {
	            resultList.add(index, addedLeg);
	            return ;
	        }
	    }
	    resultList.add(addedLeg);
	}
	
	private void addLegByTradeTimeThrowsErrorInfo(UnMatchedTradeLeg addedLeg, List<UnMatchedTradeLeg> resultList) throws ErrorInfo {
	    ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                addLegByTradeTime(addedLeg, resultList);
                return null;
            }
        });
	}
	
	private static boolean matchFully(int[] matchAfterTradeLegsIndex, int legCount) {
	    for (int index = 0; index < legCount; ++index) {
	        if (matchAfterTradeLegsIndex[index] < 0) {
	            return false;
	        }
	    }
	    return true;
	}

	private HostingExecTrade filterTrade(HostingExecTrade execTrade) {
		execTrade.setRelatedTradeLegCount(execTrade.getRelatedTradeLegIdsSize());
		if (execTrade.getRelatedTradeLegIdsSize() != 1) {
			execTrade.unsetRelatedTradeLegIds();
		}
		execTrade.unsetRelatedTradeLegContractSummaries();
		execTrade.unsetRelatedTradeLegTradeDirections();
		execTrade.unsetRelatedTradeLegPrices();
		execTrade.unsetRelatedTradeLegVolumes();
		return execTrade;
	}
	
	private void matchTradeLegs() throws ErrorInfo {
	    dbTradeList = DealingStorageApiV2.getOrderTrades(originOrder.getExecOrderId());
	    
	    int legCount = 1;
        if (contractSummary.isSetRelatedLegs() && !contractSummary.getRelatedLegs().isEmpty()) {
            legCount = contractSummary.getRelatedLegsSize();
        }
        
        //所有未匹配腿, 索引为legIndex
        Map<Short, List<UnMatchedTradeLeg>> unmatchedTradeLegs = new HashMap<>();
        for (short legIndex = 0; legIndex < legCount; ++legIndex) {
            unmatchedTradeLegs.put(legIndex, new ArrayList<>());
        }
        
        for (HostingExecTradeLeg dbTradeLeg : dbTradeLegList) {
            if (dbTradeLeg.getRelatedExecTradeId() <= 0) {
                List<UnMatchedTradeLeg> sameIndexLegList = unmatchedTradeLegs.get(dbTradeLeg.getLegIndex());
                Preconditions.checkNotNull(sameIndexLegList);
                addLegByTradeTimeThrowsErrorInfo(
                        new UnMatchedTradeLeg(true, dbTradeLeg), sameIndexLegList);
            }
        }
        for (HostingExecTradeLeg newTradeLeg : newTradeLegList) {
            List<UnMatchedTradeLeg> sameIndexLegList = unmatchedTradeLegs.get(newTradeLeg.getLegIndex());
            Preconditions.checkNotNull(sameIndexLegList);
            addLegByTradeTimeThrowsErrorInfo(
                    new UnMatchedTradeLeg(false, newTradeLeg), sameIndexLegList);
        }

        List<UnMatchedTradeLeg> firstUnMatchedTradeLegList = new ArrayList<>();

        // 匹配成交
        while(true) {
            List<UnMatchedTradeLeg> firstTradeLegList = unmatchedTradeLegs.get((short)0);
            if (firstTradeLegList.isEmpty()) {
                break;
            }
            
            UnMatchedTradeLeg matchFirstTradeLeg = firstTradeLegList.get(0);
            
            int[] matchTradeLegIndexs = new int[legCount];
            matchTradeLegIndexs[0] = 0;
            for (short legIndex = 1; legIndex < legCount; ++legIndex) {
                matchTradeLegIndexs[legIndex] = -1;
                
                List<UnMatchedTradeLeg> chooseLegs = unmatchedTradeLegs.get(legIndex);
                for (int chooseIndex = 0; chooseIndex < chooseLegs.size(); ++chooseIndex) {
                    UnMatchedTradeLeg chooseLeg = chooseLegs.get(chooseIndex);
                    if (chooseLeg.getTradeLeg().getTradeLegInfo().getLegTradeVolume() 
                            == matchFirstTradeLeg.getTradeLeg().getTradeLegInfo().getLegTradeVolume()) {
                        matchTradeLegIndexs[legIndex] = chooseIndex;
                    }
                }
            }
            
            // 具备完整匹配
            if (matchFully(matchTradeLegIndexs, legCount)) {
                UnMatchedTradeLeg[] matchTradeLegs = new UnMatchedTradeLeg[legCount];
                for (short legIndex = 0; legIndex < legCount; ++legIndex) {
                    List<UnMatchedTradeLeg> unmatchedTradeLegsList = unmatchedTradeLegs.get(legIndex);
                    matchTradeLegs[legIndex] 
                            = unmatchedTradeLegsList.get(matchTradeLegIndexs[legIndex]);
                    unmatchedTradeLegsList.remove(matchTradeLegIndexs[legIndex]);
                }
                
                HostingExecTrade newTrade = new HostingExecTrade();
                newTrade.setExecOrderId(originOrder.getExecOrderId());
                newTrade.setExecTradeId(ExecIDGenerator.Global().createExecTradeId());
                newTrade.setTradeVolume(matchTradeLegs[0].getTradeLeg().getTradeLegInfo().getLegTradeVolume());
                
                // TODO 按照公式计算价格，目前我们完全采用先手腿减去后手腿的价格计算
                BigDecimal tradePrice = new BigDecimal(
                        String.valueOf(matchTradeLegs[0].getTradeLeg().getTradeLegInfo().getLegTradePrice()));
                for(short afterLegIndex = 1; afterLegIndex < legCount; ++afterLegIndex) {
                    tradePrice = tradePrice.subtract(new BigDecimal(
                          String.valueOf(matchTradeLegs[afterLegIndex].getTradeLeg().getTradeLegInfo().getLegTradePrice())));
                }
                newTrade.setTradePrice(tradePrice.doubleValue());
                
                newTrade.setRelatedTradeLegIds(new ArrayList<>());
                newTrade.setRelatedTradeLegPrices(new ArrayList<>());
                newTrade.setRelatedTradeLegTradeDirections(new ArrayList<>());
                newTrade.setRelatedTradeLegContractSummaries(new ArrayList<>());
                newTrade.setRelatedTradeLegVolumes(new ArrayList<>());
                for (short legIndex = 0; legIndex < legCount; ++legIndex) {
                    UnMatchedTradeLeg operateTradeLeg = matchTradeLegs[legIndex];
                    newTrade.getRelatedTradeLegIds().add(operateTradeLeg.getTradeLeg().getExecTradeLegId());
                    newTrade.getRelatedTradeLegPrices().add(operateTradeLeg.getTradeLeg().getTradeLegInfo().getLegTradePrice());
                    newTrade.getRelatedTradeLegTradeDirections().add(operateTradeLeg.getTradeLeg().getTradeLegInfo().getLegUpsideTradeDirection());
                    newTrade.getRelatedTradeLegContractSummaries().add(operateTradeLeg.getTradeLeg().getLegContractSummary());
                    newTrade.getRelatedTradeLegVolumes().add(operateTradeLeg.getTradeLeg().getTradeLegInfo().getLegTradeVolume());
                    if (operateTradeLeg.isDBExist()) {
                        HostingExecTradeLeg updateTradeLeg = new HostingExecTradeLeg();
                        updateTradeLeg.setExecTradeLegId(operateTradeLeg.getTradeLeg().getExecTradeLegId());
                        updateTradeLeg.setRelatedExecTradeId(newTrade.getExecTradeId());
                        updateTradeLegList.add(updateTradeLeg);
                    } else {
                        int foundNewTradeIndex = -1;
                        for (int newTradeLegIndex = 0; newTradeLegIndex < newTradeLegList.size(); ++newTradeLegIndex) {
                            if (newTradeLegList.get(newTradeLegIndex).getExecTradeLegId()
                                    == operateTradeLeg.getTradeLeg().getExecTradeLegId()) {
                                foundNewTradeIndex = newTradeLegIndex;
                                break;
                            }
                        }
                        Preconditions.checkNotNull(foundNewTradeIndex >= 0, "match map may not right!");
                        newTradeLegList.get(foundNewTradeIndex).setRelatedExecTradeId(newTrade.getExecTradeId());
                    }
                }

                newTradeList.add(filterTrade(newTrade));
            } else {
				firstUnMatchedTradeLegList.add(firstTradeLegList.remove(0));
            }
        }


        // 对比所有未匹配的成交总量是否匹配
		unmatchedTradeLegs.put((short)0, firstUnMatchedTradeLegList);
        if (!isUnMatchLegsVolumeEquals(unmatchedTradeLegs)) {
        	return ;
		}

		// 未匹配的腿总量一致，则按照总和进行汇聚

		HostingExecTrade newTrade = new HostingExecTrade();
		newTrade.setExecOrderId(originOrder.getExecOrderId());
		newTrade.setExecTradeId(ExecIDGenerator.Global().createExecTradeId());
		newTrade.setTradeVolume(calUnMatchTradeLegVolume(unmatchedTradeLegs.get((short)0)));

		BigDecimal totalFee = getTotalFee(unmatchedTradeLegs.get((short)0));
		for(short afterLegIndex = 1; afterLegIndex < legCount; ++afterLegIndex) {
			totalFee = totalFee.subtract(getTotalFee(unmatchedTradeLegs.get(afterLegIndex)));
		}
		newTrade.setTradePrice(totalFee.divide(new BigDecimal(newTrade.getTradeVolume()), 16, BigDecimal.ROUND_HALF_UP).doubleValue());

        newTrade.setRelatedTradeLegIds(new ArrayList<>());
        newTrade.setRelatedTradeLegPrices(new ArrayList<>());
        newTrade.setRelatedTradeLegTradeDirections(new ArrayList<>());
        newTrade.setRelatedTradeLegContractSummaries(new ArrayList<>());
        newTrade.setRelatedTradeLegVolumes(new ArrayList<>());
        for (short legIndex = 0; legIndex < unmatchedTradeLegs.size(); ++legIndex) {
			for (UnMatchedTradeLeg operateTradeLeg : unmatchedTradeLegs.get(legIndex)) {
				newTrade.getRelatedTradeLegIds().add(operateTradeLeg.getTradeLeg().getExecTradeLegId());
				newTrade.getRelatedTradeLegPrices().add(operateTradeLeg.getTradeLeg().getTradeLegInfo().getLegTradePrice());
				newTrade.getRelatedTradeLegTradeDirections().add(operateTradeLeg.getTradeLeg().getTradeLegInfo().getLegUpsideTradeDirection());
				newTrade.getRelatedTradeLegContractSummaries().add(operateTradeLeg.getTradeLeg().getLegContractSummary());
				newTrade.getRelatedTradeLegVolumes().add(operateTradeLeg.getTradeLeg().getTradeLegInfo().getLegTradeVolume());
				if (operateTradeLeg.isDBExist()) {
					HostingExecTradeLeg updateTradeLeg = new HostingExecTradeLeg();
					updateTradeLeg.setExecTradeLegId(operateTradeLeg.getTradeLeg().getExecTradeLegId());
					updateTradeLeg.setRelatedExecTradeId(newTrade.getExecTradeId());
					updateTradeLegList.add(updateTradeLeg);
				} else {
					int foundNewTradeIndex = -1;
					for (int newTradeLegIndex = 0; newTradeLegIndex < newTradeLegList.size(); ++newTradeLegIndex) {
						if (newTradeLegList.get(newTradeLegIndex).getExecTradeLegId()
								== operateTradeLeg.getTradeLeg().getExecTradeLegId()) {
							foundNewTradeIndex = newTradeLegIndex;
							break;
						}
					}
					Preconditions.checkNotNull(foundNewTradeIndex >= 0, "match map may not right!");
					newTradeLegList.get(foundNewTradeIndex).setRelatedExecTradeId(newTrade.getExecTradeId());
				}
			}
		}

		newTradeList.add(filterTrade(newTrade));
	}

	private static int calUnMatchTradeLegVolume(List<UnMatchedTradeLeg> tradeLegList) {
		int totalVolume = 0;
		for (UnMatchedTradeLeg tradeLeg : tradeLegList) {
			totalVolume += tradeLeg.getTradeLeg().getTradeLegInfo().getLegTradeVolume();
		}
		return totalVolume;
	}

	private static BigDecimal getTotalFee(List<UnMatchedTradeLeg> tradeLegList) {
		BigDecimal totalFee = new BigDecimal(0);
		for (UnMatchedTradeLeg tradeLeg : tradeLegList) {
			double tradePrice = tradeLeg.getTradeLeg().getTradeLegInfo().getLegTradePrice();
			int tradeVolume = tradeLeg.getTradeLeg().getTradeLegInfo().getLegTradeVolume();
			totalFee = totalFee.add(
					new BigDecimal(String.valueOf(tradePrice)).multiply(new BigDecimal(tradeVolume)));
		}
		return totalFee;
	}

	private static boolean isUnMatchLegsVolumeEquals(Map<Short, List<UnMatchedTradeLeg>> unmatchedTradeLegs) {
		int firstLegVolume = calUnMatchTradeLegVolume(unmatchedTradeLegs.get((short)0));
		if (firstLegVolume <= 0) {
			return false;
		}

		for (short legIndex = 1; legIndex < unmatchedTradeLegs.size(); ++legIndex) {
			if (firstLegVolume != calUnMatchTradeLegVolume(unmatchedTradeLegs.get(legIndex))) {
				return false;
			}
		}

		return true;
	}

	private void commit() throws ErrorInfo {
		if (newTradeLegList.isEmpty()) {
			return ;
		}
		
		matchTradeLegs();
		
		boolean tradeListChanged = !newTradeList.isEmpty();
		
		new DBTransactionHelperWithMsg<Void>(DealingDBV2.Global()) {

			private int tradeListTotalVolume;
			private double tradeListAveragePrice;
			
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
			    if (tradeListChanged) {
			        BigDecimal totalFee = new BigDecimal(0);
			        for (HostingExecTrade dbTrade : dbTradeList) {
			            tradeListTotalVolume += dbTrade.getTradeVolume();
			            BigDecimal tradePrice = new BigDecimal(new Double(dbTrade.getTradePrice()).toString());
			            totalFee = totalFee.add(tradePrice.multiply(new BigDecimal(dbTrade.getTradeVolume())));
			        }
				
			        for (HostingExecTrade newTrade : newTradeList) {
			            tradeListTotalVolume += newTrade.getTradeVolume();
			            BigDecimal tradePrice = new BigDecimal(new Double(newTrade.getTradePrice()).toString());
			            totalFee = totalFee.add(tradePrice.multiply(new BigDecimal(newTrade.getTradeVolume())));
			        }
			        
			        tradeListAveragePrice = totalFee.divide(new BigDecimal(tradeListTotalVolume)
			                , 16, BigDecimal.ROUND_HALF_UP).doubleValue();
			    }
			}

			@Override
			public void onUpdate() throws ErrorInfo, Exception {
			    HostingExecTradeLegTableV2 tradeLegTable = new HostingExecTradeLegTableV2(getConnection());
			    for (HostingExecTradeLeg newTradeLeg : newTradeLegList) {
			        newTradeLeg.setSubUserId(originOrder.getSubUserId());
			        newTradeLeg.setSubAccountId(originOrder.getSubAccountId());
			        newTradeLeg.setAccountSummary(originOrder.getAccountSummary());
			        newTradeLeg.setCreateTimestampMs(System.currentTimeMillis());
			        tradeLegTable.addTradeLeg(newTradeLeg);
                }
			    
			    for (HostingExecTradeLeg updateTradeLeg : updateTradeLegList) {
			        tradeLegTable.updateTradeLeg(updateTradeLeg);
			    }
			    
			    if (!tradeListChanged) {
			        return ;
			    }
			    
				HostingExecTradeTableV2 tradeTable = new HostingExecTradeTableV2(getConnection());
				for (HostingExecTrade newTrade : newTradeList) {
					newTrade.setSubUserId(originOrder.getSubUserId());
					newTrade.setSubAccountId(originOrder.getSubAccountId());
					newTrade.setAccountSummary(originOrder.getAccountSummary());
					newTrade.setContractSummary(originOrder.getContractSummary());
					newTrade.setOrderTradeDirection(tradeDirection);
					newTrade.setCreateTimestampMs(System.currentTimeMillis());
					tradeTable.addTrade(newTrade);
				}

				newOrder = new HostingExecOrder(originOrder);

				HostingExecOrder operationOrder = new HostingExecOrder();
				operationOrder.setExecOrderId(originOrder.getExecOrderId());
				operationOrder.setTradeSummary(new HostingExecOrderTradeSummary());
				operationOrder.getTradeSummary().setTradeListTotalVolume(tradeListTotalVolume);
				operationOrder.getTradeSummary().setTradeListAveragePrice(tradeListAveragePrice);
				operationOrder.setVersion(originOrder.getVersion() + 1);

				newOrder.getTradeSummary().setTradeListTotalVolume(tradeListTotalVolume);
				newOrder.getTradeSummary().setTradeListAveragePrice(tradeListAveragePrice);
				newOrder.setVersion(operationOrder.getVersion());
				
				new HostingExecOrderTableV2(getConnection()).updateOrder(operationOrder);

			}

			@Override
			public Void getResult() {
				return null;
			}

			@SuppressWarnings("rawtypes")
			@Override
			protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
			    if (!tradeListChanged) {
			        return null;
			    }
			    
			    ExecOrderGuardEvent event = new ExecOrderGuardEvent();
			    event.setGuardExecOrderId(originOrder.getExecOrderId());
			    event.setGuardType(ExecOrderGuardType.GUARD_ORDER_TRADE_LIST_CHANGED);
	                
			    return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(2));
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			protected TBase prepareNotifyMessage() {
			    if (!tradeListChanged) {
			        return null;
			    }
			    
			    ExecTradeListChangedEvent event = new ExecTradeListChangedEvent();
			    event.setNewTradeList(new ArrayList<>());
			    for (HostingExecTrade newTrade : newTradeList) {
			        event.getNewTradeList().add(newTrade);
			    }

			    HostingExecOrder notifyOrder = new HostingExecOrder(newOrder);
				notifyOrder.getStateInfo().unsetHistoryStates();
				notifyOrder.unsetNotifyStateHandleInfos();
				event.setExecOrder(notifyOrder);
				return event;
			}

			@Override
			protected MessageAgent getMessageAgent() {
				return HostingMessageContext.Global().getSenderAgent();
			}

		}.execute();
	}


//	public static void main(String[] args) {
//	    int legCount = 2;
//
//		Map<Short, List<UnMatchedTradeLeg>> unmatchedTradeLegs = new HashMap<Short, List<UnMatchedTradeLeg>>();
//
//		List<UnMatchedTradeLeg> tradeLegList0 = new ArrayList<>();
//		tradeLegList0.add(new UnMatchedTradeLeg(
//				false
//				, new HostingExecTradeLeg().setExecTradeLegId(1)
//						.setLegIndex((short)0)
//						.setTradeLegInfo(new HostingExecTradeLegInfo()
//								.setLegTradePrice(100)
//								.setLegTradeDateTime("2019-03-19 10:08:03")
//								.setLegTradeVolume(2)
//								.setLegUpsideTradeDirection(HostingExecTradeDirection.TRADE_BUY))
//						.setLegContractSummary(new HostingExecOrderLegContractSummary()
//								.setLegSledExchangeMic("SHFE")
//								.setLegSledCommodityCode("cu")
//								.setLegSledContractCode("1906"))
//		));
//		tradeLegList0.add(new UnMatchedTradeLeg(false
//				,  new HostingExecTradeLeg().setExecTradeLegId(2)
//						.setLegIndex((short)0)
//						.setTradeLegInfo(new HostingExecTradeLegInfo()
//								.setLegTradePrice(105)
//								.setLegTradeDateTime("2019-03-19 10:08:03")
//								.setLegTradeVolume(1)
//								.setLegUpsideTradeDirection(HostingExecTradeDirection.TRADE_BUY))
//						.setLegContractSummary(new HostingExecOrderLegContractSummary()
//								.setLegSledExchangeMic("SHFE")
//								.setLegSledCommodityCode("cu")
//								.setLegSledContractCode("1906"))
//		));
//        tradeLegList0.add(new UnMatchedTradeLeg(false
//                ,  new HostingExecTradeLeg().setExecTradeLegId(20)
//                .setLegIndex((short)0)
//                .setTradeLegInfo(new HostingExecTradeLegInfo()
//                        .setLegTradePrice(103)
//                        .setLegTradeDateTime("2019-03-19 10:08:03")
//                        .setLegTradeVolume(3)
//                        .setLegUpsideTradeDirection(HostingExecTradeDirection.TRADE_BUY))
//                .setLegContractSummary(new HostingExecOrderLegContractSummary()
//                        .setLegSledExchangeMic("SHFE")
//                        .setLegSledCommodityCode("cu")
//                        .setLegSledContractCode("1906"))
//        ));
//		unmatchedTradeLegs.put((short)0, tradeLegList0);
//
//		List<UnMatchedTradeLeg> tradeLegList1 = new ArrayList<>();
//		tradeLegList1.add(new UnMatchedTradeLeg(
//				false
//				, new HostingExecTradeLeg().setExecTradeLegId(3)
//				.setLegIndex((short)1)
//				.setTradeLegInfo(new HostingExecTradeLegInfo()
//						.setLegTradePrice(100)
//						.setLegTradeDateTime("2019-03-19 10:08:03")
//						.setLegTradeVolume(2)
//						.setLegUpsideTradeDirection(HostingExecTradeDirection.TRADE_SELL))
//				.setLegContractSummary(new HostingExecOrderLegContractSummary()
//						.setLegSledExchangeMic("SHFE")
//						.setLegSledCommodityCode("cu")
//						.setLegSledContractCode("1907"))
//		));
//        tradeLegList1.add(new UnMatchedTradeLeg(
//                false
//                , new HostingExecTradeLeg().setExecTradeLegId(4)
//                .setLegIndex((short)1)
//                .setTradeLegInfo(new HostingExecTradeLegInfo()
//                        .setLegTradePrice(101)
//                        .setLegTradeDateTime("2019-03-19 10:08:03")
//                        .setLegTradeVolume(2)
//                        .setLegUpsideTradeDirection(HostingExecTradeDirection.TRADE_SELL))
//                .setLegContractSummary(new HostingExecOrderLegContractSummary()
//                        .setLegSledExchangeMic("SHFE")
//                        .setLegSledCommodityCode("cu")
//                        .setLegSledContractCode("1907"))
//        ));
//        tradeLegList1.add(new UnMatchedTradeLeg(
//                false
//                , new HostingExecTradeLeg().setExecTradeLegId(5)
//                .setLegIndex((short)1)
//                .setTradeLegInfo(new HostingExecTradeLegInfo()
//                        .setLegTradePrice(101)
//                        .setLegTradeDateTime("2019-03-19 10:08:03")
//                        .setLegTradeVolume(2)
//                        .setLegUpsideTradeDirection(HostingExecTradeDirection.TRADE_SELL))
//                .setLegContractSummary(new HostingExecOrderLegContractSummary()
//                        .setLegSledExchangeMic("SHFE")
//                        .setLegSledCommodityCode("cu")
//                        .setLegSledContractCode("1907"))
//        ));
//		unmatchedTradeLegs.put((short)1, tradeLegList1);
//
//        List<UnMatchedTradeLeg> firstUnMatchedTradeLegList = new ArrayList<>();
//
//        // 匹配成交
//        while(true) {
//            List<UnMatchedTradeLeg> firstTradeLegList = unmatchedTradeLegs.get((short)0);
//            if (firstTradeLegList.isEmpty()) {
//                break;
//            }
//
//            UnMatchedTradeLeg matchFirstTradeLeg = firstTradeLegList.get(0);
//
//            int[] matchTradeLegIndexs = new int[legCount];
//            matchTradeLegIndexs[0] = 0;
//            for (short legIndex = 1; legIndex < legCount; ++legIndex) {
//                matchTradeLegIndexs[legIndex] = -1;
//
//                List<UnMatchedTradeLeg> chooseLegs = unmatchedTradeLegs.get(legIndex);
//                for (int chooseIndex = 0; chooseIndex < chooseLegs.size(); ++chooseIndex) {
//                    UnMatchedTradeLeg chooseLeg = chooseLegs.get(chooseIndex);
//                    if (chooseLeg.getTradeLeg().getTradeLegInfo().getLegTradeVolume()
//                            == matchFirstTradeLeg.getTradeLeg().getTradeLegInfo().getLegTradeVolume()) {
//                        matchTradeLegIndexs[legIndex] = chooseIndex;
//                    }
//                }
//            }
//
//            // 具备完整匹配
//            if (matchFully(matchTradeLegIndexs, legCount)) {
//                UnMatchedTradeLeg[] matchTradeLegs = new UnMatchedTradeLeg[legCount];
//                for (short legIndex = 0; legIndex < legCount; ++legIndex) {
//                    List<UnMatchedTradeLeg> unmatchedTradeLegsList = unmatchedTradeLegs.get(legIndex);
//                    matchTradeLegs[legIndex]
//                            = unmatchedTradeLegsList.get(matchTradeLegIndexs[legIndex]);
//                    unmatchedTradeLegsList.remove(matchTradeLegIndexs[legIndex]);
//                }
//
//                HostingExecTrade newTrade = new HostingExecTrade();
//                newTrade.setExecOrderId(1);
//                newTrade.setExecTradeId(1);
//                newTrade.setTradeVolume(matchTradeLegs[0].getTradeLeg().getTradeLegInfo().getLegTradeVolume());
//
//                // TODO 按照公式计算价格，目前我们完全采用先手腿减去后手腿的价格计算
//                BigDecimal tradePrice = new BigDecimal(
//                        String.valueOf(matchTradeLegs[0].getTradeLeg().getTradeLegInfo().getLegTradePrice()));
//                for(short afterLegIndex = 1; afterLegIndex < legCount; ++afterLegIndex) {
//                    tradePrice = tradePrice.subtract(new BigDecimal(
//                            String.valueOf(matchTradeLegs[afterLegIndex].getTradeLeg().getTradeLegInfo().getLegTradePrice())));
//                }
//                newTrade.setTradePrice(tradePrice.doubleValue());
//
//                newTrade.setRelatedTradeLegIds(new ArrayList<>());
//                newTrade.setRelatedTradeLegPrices(new ArrayList<>());
//                newTrade.setRelatedTradeLegTradeDirections(new ArrayList<>());
//                newTrade.setRelatedTradeLegContractSummaries(new ArrayList<>());
//                newTrade.setRelatedTradeLegVolumes(new ArrayList<>());
//                for (short legIndex = 0; legIndex < legCount; ++legIndex) {
//                    UnMatchedTradeLeg operateTradeLeg = matchTradeLegs[legIndex];
//                    newTrade.getRelatedTradeLegIds().add(operateTradeLeg.getTradeLeg().getExecTradeLegId());
//                    newTrade.getRelatedTradeLegPrices().add(operateTradeLeg.getTradeLeg().getTradeLegInfo().getLegTradePrice());
//                    newTrade.getRelatedTradeLegTradeDirections().add(operateTradeLeg.getTradeLeg().getTradeLegInfo().getLegUpsideTradeDirection());
//                    newTrade.getRelatedTradeLegContractSummaries().add(operateTradeLeg.getTradeLeg().getLegContractSummary());
//                    newTrade.getRelatedTradeLegVolumes().add(operateTradeLeg.getTradeLeg().getTradeLegInfo().getLegTradeVolume());
////                    if (operateTradeLeg.isDBExist()) {
////                        HostingExecTradeLeg updateTradeLeg = new HostingExecTradeLeg();
////                        updateTradeLeg.setExecTradeLegId(operateTradeLeg.getTradeLeg().getExecTradeLegId());
////                        updateTradeLeg.setRelatedExecTradeId(newTrade.getExecTradeId());
////                        updateTradeLegList.add(updateTradeLeg);
////                    } else {
////                        int foundNewTradeIndex = -1;
////                        for (int newTradeLegIndex = 0; newTradeLegIndex < newTradeLegList.size(); ++newTradeLegIndex) {
////                            if (newTradeLegList.get(newTradeLegIndex).getExecTradeLegId()
////                                    == operateTradeLeg.getTradeLeg().getExecTradeLegId()) {
////                                foundNewTradeIndex = newTradeLegIndex;
////                                break;
////                            }
////                        }
////                        Preconditions.checkNotNull(foundNewTradeIndex >= 0, "match map may not right!");
////                        newTradeLegList.get(foundNewTradeIndex).setRelatedExecTradeId(newTrade.getExecTradeId());
////                    }
//                }
//
////                newTradeList.add(newTrade);
//                System.out.println("newTrade=" + newTrade);
//            } else {
//                firstUnMatchedTradeLegList.add(firstTradeLegList.remove(0));
//            }
//        }
//
//
//        // 对比所有未匹配的成交总量是否匹配
//        unmatchedTradeLegs.put((short)0, firstUnMatchedTradeLegList);
//        if (!isUnMatchLegsVolumeEquals(unmatchedTradeLegs)) {
//            System.out.println("unmatched");
//            return ;
//        }
//
//        // 未匹配的腿总量一致，则按照总和进行汇聚
//
//        HostingExecTrade newTrade = new HostingExecTrade();
//        newTrade.setExecOrderId(1);
//        newTrade.setExecTradeId(1);
//        newTrade.setTradeVolume(calUnMatchTradeLegVolume(unmatchedTradeLegs.get((short)0)));
//
//        BigDecimal totalFee = getTotalFee(unmatchedTradeLegs.get((short)0));
//        for(short afterLegIndex = 1; afterLegIndex < legCount; ++afterLegIndex) {
//            totalFee = totalFee.subtract(getTotalFee(unmatchedTradeLegs.get(afterLegIndex)));
//        }
//        newTrade.setTradePrice(totalFee.divide(new BigDecimal(newTrade.getTradeVolume()), 16, BigDecimal.ROUND_HALF_UP).doubleValue());
//
//        newTrade.setRelatedTradeLegIds(new ArrayList<>());
//        newTrade.setRelatedTradeLegPrices(new ArrayList<>());
//        newTrade.setRelatedTradeLegTradeDirections(new ArrayList<>());
//        newTrade.setRelatedTradeLegContractSummaries(new ArrayList<>());
//        newTrade.setRelatedTradeLegVolumes(new ArrayList<>());
//        for (short legIndex = 0; legIndex < unmatchedTradeLegs.size(); ++legIndex) {
//            for (UnMatchedTradeLeg operateTradeLeg : unmatchedTradeLegs.get(legIndex)) {
//                newTrade.getRelatedTradeLegIds().add(operateTradeLeg.getTradeLeg().getExecTradeLegId());
//                newTrade.getRelatedTradeLegPrices().add(operateTradeLeg.getTradeLeg().getTradeLegInfo().getLegTradePrice());
//                newTrade.getRelatedTradeLegTradeDirections().add(operateTradeLeg.getTradeLeg().getTradeLegInfo().getLegUpsideTradeDirection());
//                newTrade.getRelatedTradeLegContractSummaries().add(operateTradeLeg.getTradeLeg().getLegContractSummary());
//                newTrade.getRelatedTradeLegVolumes().add(operateTradeLeg.getTradeLeg().getTradeLegInfo().getLegTradeVolume());
////                if (operateTradeLeg.isDBExist()) {
////                    HostingExecTradeLeg updateTradeLeg = new HostingExecTradeLeg();
////                    updateTradeLeg.setExecTradeLegId(operateTradeLeg.getTradeLeg().getExecTradeLegId());
////                    updateTradeLeg.setRelatedExecTradeId(newTrade.getExecTradeId());
////                    updateTradeLegList.add(updateTradeLeg);
////                } else {
////                    int foundNewTradeIndex = -1;
////                    for (int newTradeLegIndex = 0; newTradeLegIndex < newTradeLegList.size(); ++newTradeLegIndex) {
////                        if (newTradeLegList.get(newTradeLegIndex).getExecTradeLegId()
////                                == operateTradeLeg.getTradeLeg().getExecTradeLegId()) {
////                            foundNewTradeIndex = newTradeLegIndex;
////                            break;
////                        }
////                    }
////                    Preconditions.checkNotNull(foundNewTradeIndex >= 0, "match map may not right!");
////                    newTradeLegList.get(foundNewTradeIndex).setRelatedExecTradeId(newTrade.getExecTradeId());
////                }
//            }
//        }
//
//
//		System.out.println("newTrade=" + newTrade.toString());
//	}
}
