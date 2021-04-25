package xueqiao.trade.hosting.quot.comb.core;

import org.soldier.base.logger.AppLog;
import xueqiao.trade.hosting.HostingComposeGraph;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class QuotCombCalculatorManager  {

    public static interface ITopicCallback {
        void addTopic(String platform, String contractSymbol);
        void removeTopic(String platform, String contractSymbol);
    }

    private ITopicCallback mTopicCallback;
    private Map<Long, QuotCombCalculator> mCalculators = new HashMap<>();
    private Map<String, Map<String, Set<Long>>>  mRelatedCalculators = new HashMap<>();

    public QuotCombCalculatorManager(ITopicCallback topicCallback) {
        mTopicCallback = topicCallback;
    }

    public boolean containsCalculator(long composeGraphId) {
        return mCalculators.containsKey(composeGraphId);
    }

    public QuotCombCalculator getCalculator(long composeGraphId) {
        return mCalculators.get(composeGraphId);
    }

    public Set<Long> getAllComposeGraphIds() {
        return mCalculators.keySet();
    }

    public void addCalculator(HostingComposeGraph composeGraph) throws UnsupportedEncodingException {
        QuotCombCalculator calculator = mCalculators.get(composeGraph.getComposeGraphId());
        if (calculator != null) {
            return ;
        }

        if (AppLog.infoEnabled()) {
            AppLog.i("create QuotCombCalculator, composeGraphId=" + composeGraph.getComposeGraphId());
        }
        calculator = new QuotCombCalculator(composeGraph);
        mCalculators.put(composeGraph.getComposeGraphId(), calculator);

        Map<String, Set<Long>> relatedContractSymbolsMap = mRelatedCalculators.get(calculator.getPlatform());
        if (relatedContractSymbolsMap == null) {
            relatedContractSymbolsMap = new HashMap<>();
            mRelatedCalculators.put(calculator.getPlatform(), relatedContractSymbolsMap);
        }

        Set<String> relatedContractSymbols = calculator.getRelatedContractSymbols();
        for (String contractSymbol : relatedContractSymbols) {
            Set<Long> relatedCalculatorSet = relatedContractSymbolsMap.get(contractSymbol);
            if (relatedCalculatorSet == null) {
                relatedCalculatorSet = new HashSet<>();
                relatedContractSymbolsMap.put(contractSymbol, relatedCalculatorSet);
            }
            if (relatedCalculatorSet.isEmpty()) {
                if (AppLog.infoEnabled()) {
                    AppLog.i("addTopic platform=" + calculator.getPlatform() + ", contractSymbol=" + contractSymbol);
                }
                mTopicCallback.addTopic(calculator.getPlatform(), contractSymbol);
            }
            relatedCalculatorSet.add(composeGraph.getComposeGraphId());
        }
    }

    public void rmCalculator(long composeGraphId) {
        // 移除计算资源
        QuotCombCalculator calculator = mCalculators.remove(composeGraphId);
        if (AppLog.infoEnabled()) {
            AppLog.i("remove QuotCombCalculator, composeGraphId=" + composeGraphId);
        }
        if (calculator == null) {
            return ;
        }

        // 释放关联的行情
        Map<String, Set<Long>> relatedContractSymbolsMap = mRelatedCalculators.get(calculator.getPlatform());
        if (relatedContractSymbolsMap == null) {
            AppLog.w("[WARNING]cant not found relatedContractSymbolsMap when removeSubscribeUserGraph, platform="
                    + calculator.getPlatform());
            return ;
        }

        Set<String> relatedContractSymbols = calculator.getRelatedContractSymbols();
        for (String contractSymbol : relatedContractSymbols) {
            Set<Long> relatedCalculatorSet = relatedContractSymbolsMap.get(contractSymbol);
            if (relatedCalculatorSet == null || relatedCalculatorSet.isEmpty()) {
                continue;
            }
            relatedCalculatorSet.remove(composeGraphId);
            if (relatedCalculatorSet.isEmpty()) {
                if (AppLog.infoEnabled()) {
                    AppLog.i("removeTopic platform=" + calculator.getPlatform() + ", contractSymbol=" + contractSymbol);
                }
                mTopicCallback.removeTopic(calculator.getPlatform(), contractSymbol);
            }
        }
    }

    public Set<Long> getRelatedCalculators(String platform, String contractSymbol) {
        Map<String, Set<Long>> relatedContractSymbolsMap = mRelatedCalculators.get(platform);
        if (relatedContractSymbolsMap == null || relatedContractSymbolsMap.isEmpty()) {
            if (AppLog.debugEnabled()) {
                AppLog.d("No relatedContractSymbolsMap for Platform=" + platform);
            }
            return null;
        }
        Set<Long> relatedCalculatorSet = relatedContractSymbolsMap.get(contractSymbol);
        if (relatedCalculatorSet == null || relatedCalculatorSet.isEmpty()) {
            if (AppLog.debugEnabled()) {
                AppLog.d("No relatedCalculatorSet for contractSymbol=" + contractSymbol);
            }
            return null;
        }

        return relatedCalculatorSet;
    }

}
