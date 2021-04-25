package xueqiao.trade.hosting.arbitrage.core.composelimit.handicap;

import com.google.common.base.Preconditions;
import org.soldier.base.logger.AppLog;
import xueqiao.trade.hosting.HostingExecOrderTradeDirection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *  套利盘口管理器
 */
public class HandicapManager {
    private static HandicapManager sInstance;

    public static HandicapManager Global() {
        if (sInstance == null) {
            synchronized (HandicapManager.class) {
                if (sInstance == null) {
                    sInstance = new HandicapManager();
                }
            }
        }
        return sInstance;
    }

    private static class Requirement {
        private IHandicapRequester mRequester;
        private int mRequestBuyQty;
        private int mRequestSellQty;

        public Requirement(IHandicapRequester requester
                , int requestBuyQty
                , int requestSellQty) {
            this.mRequester = requester;
            this.mRequestBuyQty = requestBuyQty;
            this.mRequestSellQty = requestSellQty;
        }

        public Object getRequester() {
            return mRequester;
        }

        public int getRequestBuyQty() {
            return mRequestBuyQty;
        }
        public void setRequestBuyQty(int requestQty) {
            this.mRequestBuyQty = requestQty;
        }

        public int getRequestSellQty() {
            return mRequestSellQty;
        }

        public void setRequestSellQty(int requestQty) {
            this.mRequestSellQty = requestQty;
        }
    }

    private static class HandicapInfo {
        private Object mLockedRequester;  // 当前锁住
        private Map<Object, Requirement> mRequirments = new HashMap<Object, Requirement>();
        private int mTotalBuyCount;
        private int mTotalSellCount;

        public Object getLockedRequester() {
            return mLockedRequester;
        }

        public HandicapInfo setLockedRequester(Object lockedRequester) {
            this.mLockedRequester = lockedRequester;
            return this;
        }

        public Requirement getRequirment(IHandicapRequester requester) {
            Requirement req = mRequirments.get(requester);
            if (req == null) {
                req = new Requirement(requester, 0, 0);
                mRequirments.put(requester, req);
            }
            return req;
        }

        public void rmRequirement(Object requester) {
            mRequirments.remove(requester);
        }

        public int getTotalBuyCount() {
            return mTotalBuyCount;
        }

        public HandicapInfo setTotalBuyCount(int totalCount) {
            this.mTotalBuyCount = totalCount;
            return this;
        }

        public int getTotalSellCount() {
            return this.mTotalSellCount;
        }

        public HandicapInfo setTotalSellCount(int totalCount) {
            this.mTotalSellCount = totalCount;
            return this;
        }
    }

    /**
     *  盘口需求记录，以合约ID为Key
     */
    private Map<Long, HandicapInfo> mHandicapsIndex = new HashMap<>();

    /**
     * 记录需求列表
     */
    private Map<Object, Set<Long>> mRequesterIndex = new HashMap<>();

    private HandicapInfo getHandicap(long sledContractId) {
        HandicapInfo info = mHandicapsIndex.get(sledContractId);
        if (info == null) {
            info = new HandicapInfo();
            mHandicapsIndex.put(sledContractId, info);
        }
        return info;
    }

    /**
     *  获取盘后量操作锁
     */
    public void requireOpLock(IHandicapRequester requester, Set<Long> requireSledContractIds) {
        Preconditions.checkNotNull(requester);
        Preconditions.checkNotNull(requireSledContractIds);

        while(true) {
            synchronized (this) {
                boolean allEmpty = true;
                for (long sledContractId : requireSledContractIds) {
                    HandicapInfo info = getHandicap(sledContractId);
                    if (info.getLockedRequester() != null && info.getLockedRequester() != requester) {
                        allEmpty = false;
                        break;
                    }
                }

                if (allEmpty) {
                    // 将所有的资源一起锁住
                    for (long sledContractId : requireSledContractIds) {
                        getHandicap(sledContractId).setLockedRequester(requester);
                    }
                    mRequesterIndex.put(requester, requireSledContractIds);

                    break;
                }

            }

            // 无法获取锁时进行自旋
            try {
                Thread.sleep(0, 100);
            } catch (InterruptedException e) {
            }
        }
    }

    public void set(IHandicapRequester requester
            , long sledContractId
            , HostingExecOrderTradeDirection orderTradeDirection
            , int qty) {
        Preconditions.checkNotNull(requester);
        Preconditions.checkArgument(sledContractId > 0);
        Preconditions.checkArgument(qty >= 0);

        HandicapInfo info = getHandicap(sledContractId);
        Preconditions.checkState(info.getLockedRequester() == requester);

        Requirement requirement = info.getRequirment(requester);
        if (orderTradeDirection == HostingExecOrderTradeDirection.ORDER_BUY) {
            if (requirement.getRequestBuyQty() == qty) {
                return ;
            }

            int dis = qty - requirement.getRequestBuyQty();
            info.setTotalBuyCount(info.getTotalBuyCount() + dis);
            requirement.setRequestBuyQty(qty);
        } else {
            if(requirement.getRequestSellQty() == qty) {
                return ;
            }

            int dis = qty - requirement.getRequestSellQty();
            info.setTotalSellCount(info.getTotalSellCount() + dis);
            requirement.setRequestSellQty(qty);
        }

        Preconditions.checkState(info.getTotalBuyCount() >= 0);
        Preconditions.checkState(info.getTotalSellCount() >= 0);

        if (AppLog.infoEnabled()) {
            AppLog.i("set IHandicapRequester=" + requester.description()
                    + ", sledContractId=" + sledContractId
                    + ", orderTradeDirction=" + orderTradeDirection
                    + ", qty=" + qty
                    + ", totalBuyCount=" + info.getTotalBuyCount()
                    + ", totalSellCount=" + info.getTotalSellCount());
        }

    }

    public void clear(IHandicapRequester requester, long sledContractId) {
        Preconditions.checkNotNull(requester);
        Preconditions.checkArgument(sledContractId > 0);

        HandicapInfo info = getHandicap(sledContractId);
        Preconditions.checkState(info.getLockedRequester() == requester);

        Requirement requirement = info.getRequirment(requester);
        info.setTotalBuyCount(info.getTotalBuyCount() - requirement.getRequestBuyQty());
        info.setTotalSellCount(info.getTotalSellCount() - requirement.getRequestSellQty());
        info.rmRequirement(requester);

        Preconditions.checkState(info.getTotalBuyCount() >= 0);
        Preconditions.checkState(info.getTotalSellCount() >= 0);

        if (AppLog.infoEnabled()) {
            AppLog.i("clear IHandicapRequester=" + requester.description()
                    + ", sledContractId=" + sledContractId
                    + ", totalBuyCount=" + info.getTotalBuyCount()
                    + ", totalSellCount=" + info.getTotalSellCount());
        }
    }

    public int getTotal(IHandicapRequester requester
            , long sledContractId
            , HostingExecOrderTradeDirection orderTradeDirection) {
        Preconditions.checkNotNull(requester);
        Preconditions.checkArgument(sledContractId > 0);

        HandicapInfo info = getHandicap(sledContractId);
        Preconditions.checkState(info.getLockedRequester() == requester);

        if (orderTradeDirection == HostingExecOrderTradeDirection.ORDER_BUY) {
            return info.getTotalBuyCount();
        }
        return info.getTotalSellCount();
    }


    /**
     *  释放盘口量操作锁
     */
    public void releaseOpLock(Object requester) {
        synchronized (this) {
            Set<Long> requireSledContractIds = mRequesterIndex.get(requester);
            if (requireSledContractIds == null) {
                return ;
            }

            for (Long sledContractId : requireSledContractIds) {
                HandicapInfo info = getHandicap(sledContractId);
                if (info.getLockedRequester() == requester) {
                    info.setLockedRequester(null);
                }
            }

        }
    }

}
