package xueqiao.trade.hosting.asset.config;

import org.soldier.platform.id_maker.IdMaker;
import org.soldier.platform.id_maker.IdMakerFactory;

public class Config {
    private static Config ourInstance = new Config();

    private static final int SETTLEMENT_ID_MAKER_TYPE = 215;
    private static final int TRADE_DETAIL_ID_MAKER_TYPE = 218;

    public static Config getInstance() {
        return ourInstance;
    }

    private Config() {
        init();
    }

    private IdMaker settlementIdMaker;

    private IdMaker tradeDetailIdMaker;

    private void init(){
        settlementIdMaker = IdMakerFactory.getInstance().getIdMaker(SETTLEMENT_ID_MAKER_TYPE);
        tradeDetailIdMaker = IdMakerFactory.getInstance().getIdMaker(TRADE_DETAIL_ID_MAKER_TYPE);
    }

    public IdMaker getSettlementIdMaker() {
        return settlementIdMaker;
    }


    public IdMaker getTradeDetailIdMaker() {
        return tradeDetailIdMaker;
    }
}
