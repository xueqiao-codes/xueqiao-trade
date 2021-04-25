package xueqiao.trade.hosting.position.statis.core.cache.compose;

import com.antiy.error_code.ErrorCodeInner;
import com.antiy.error_code.ErrorCodeOuter;
import com.google.gson.Gson;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.HostingComposeLegTradeDirection;
import xueqiao.trade.hosting.position.statis.StatDirection;
import xueqiao.trade.hosting.position.statis.core.cache.ICacheManager;
import xueqiao.trade.hosting.position.statis.service.handler.HostingComposeHandler;
import xueqiao.trade.hosting.position.statis.service.report.AppReport;
import xueqiao.trade.hosting.position.statis.service.report.StatErrorCode;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class StatComposeGraphManager implements ICacheManager {

    private static StatComposeGraphManager ourInstance = new StatComposeGraphManager();

    private Map<Long, SoftReference<StatComposeGraph>> softReferenceMap = new HashMap<>();

    public static StatComposeGraphManager getInstance() {
        return ourInstance;
    }

    private StatComposeGraphManager() {
    }

    /**
     * 获取组合信息
     * 优先从缓存中获取
     * 缓存中没命中再从接口获取
     */
    public StatComposeGraph getComposeGraph(String targetKey) throws ErrorInfo {
        long composeGraphId = 0;
        try {
            composeGraphId = Long.valueOf(targetKey);
        } catch (Throwable throwable) {
            throw new ErrorInfo(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "invalid targetKey");
        }
        Gson gson = new Gson();
        StatComposeGraph composeGraph;
        SoftReference<StatComposeGraph> statComposeGraphSoftReference = softReferenceMap.get(composeGraphId);
        if (statComposeGraphSoftReference != null) {
            composeGraph = statComposeGraphSoftReference.get();
            if (composeGraph != null) {
                StatComposeGraph composeGraphCopy = gson.fromJson(gson.toJson(composeGraph), StatComposeGraph.class);
                return composeGraphCopy;
            } else {
                AppLog.i("StatComposeGraphManager # getComposeGraph ---- composeGraph is null, targetKey : " + targetKey);
            }
        } else {
            AppLog.i("StatComposeGraphManager # statComposeGraphSoftReference is null, targetKey : " + targetKey);
        }

        composeGraph = getComposeGraphFromApi(targetKey, composeGraphId);
        if (composeGraph != null) {
            statComposeGraphSoftReference = new SoftReference(composeGraph);
            softReferenceMap.put(composeGraphId, statComposeGraphSoftReference);
        } else {
            AppLog.e("StatComposeGraphManager # getComposeGraphFromApi is null");
            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "getComposeGraphFromApi fail, targetKey : " + targetKey);
        }
        return gson.fromJson(gson.toJson(composeGraph), StatComposeGraph.class);
    }

    /**
     * 获取组合信息
     * 优先从缓存中获取
     * 缓存中没命中再从接口获取
     */
    public StatComposeGraph getComposeGraph(long composeGraphId) throws ErrorInfo {
        Gson gson = new Gson();
        SoftReference<StatComposeGraph> statComposeGraphSoftReference = softReferenceMap.get(composeGraphId);
        StatComposeGraph composeGraph;
        if (statComposeGraphSoftReference != null) {
            composeGraph = statComposeGraphSoftReference.get();
            if (composeGraph != null) {
                StatComposeGraph composeGraphCopy = gson.fromJson(gson.toJson(composeGraph), StatComposeGraph.class);
                return composeGraphCopy;
            } else {
                AppLog.i("StatComposeGraphManager # getComposeGraph ---- composeGraph is null");
            }
        } else {
            AppLog.i("StatComposeGraphManager # statComposeGraphSoftReference is null");
        }

        composeGraph = getComposeGraphFromApi(String.valueOf(composeGraphId), composeGraphId);
        if (composeGraph != null) {
            statComposeGraphSoftReference = new SoftReference(composeGraph);
            softReferenceMap.put(composeGraphId, statComposeGraphSoftReference);
        } else {
            AppLog.e("StatComposeGraphManager # getComposeGraphFromApi is null");
            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "getComposeGraphFromApi fail, composeGraphId : " + composeGraphId);
        }
        return gson.fromJson(gson.toJson(composeGraph), StatComposeGraph.class);
    }

    /**
     * 获取组合信息
     * 直接从接口获取
     */
    public StatComposeGraph getComposeGraphDirect(long composeGraphId) throws ErrorInfo {
        return getComposeGraphFromApi(String.valueOf(composeGraphId), composeGraphId);
    }

        /**
         * 清除缓存
         */
        @Override
        public void clear () {
            softReferenceMap.clear();
        }

        /**
         * （内部方法）从接口获取组合信息
         */
        private StatComposeGraph getComposeGraphFromApi (String targetKey,long composeGraphId) throws ErrorInfo {
            HostingComposeGraph hostingComposeGraph = new HostingComposeHandler().getComposeGraph(composeGraphId);
            if (hostingComposeGraph == null) {
                AppReport.reportErr("fail to find compose graph(composeGraphId: " + composeGraphId + ")");
                throw new ErrorInfo(StatErrorCode.innerError.errorCode, "fail to find compose graph(targetKey: " + composeGraphId + ")");
            }

            Map<Long, StatComposeLeg> statComposeLegMap = new HashMap<>();
            StatComposeLeg tempStatComposeLeg;
            for (HostingComposeLeg hostingComposeLeg : hostingComposeGraph.getLegs().values()) {
                tempStatComposeLeg = new StatComposeLeg();
                tempStatComposeLeg.setSledContractId(hostingComposeLeg.getSledContractId());
                tempStatComposeLeg.setContractSymbol(hostingComposeLeg);
                tempStatComposeLeg.setQuantity(hostingComposeLeg.getQuantity());
                tempStatComposeLeg.setVariableName(hostingComposeLeg.getVariableName());
                if (hostingComposeLeg.getLegTradeDirection() == HostingComposeLegTradeDirection.COMPOSE_LEG_BUY) {
                    tempStatComposeLeg.setDirection(StatDirection.STAT_BUY);
                } else {
                    tempStatComposeLeg.setDirection(StatDirection.STAT_SELL);
                }
                statComposeLegMap.put(hostingComposeLeg.getSledContractId(), tempStatComposeLeg);
            }

            StatComposeGraph statComposeGraph = new StatComposeGraph();
            statComposeGraph.setTargetKey(targetKey);
            statComposeGraph.setFormular(hostingComposeGraph.getFormular());
            statComposeGraph.setLegKeySet(hostingComposeGraph.getLegs().keySet());
            statComposeGraph.setComposeLegQuantityMap(statComposeLegMap);

            return statComposeGraph;
        }
    }
