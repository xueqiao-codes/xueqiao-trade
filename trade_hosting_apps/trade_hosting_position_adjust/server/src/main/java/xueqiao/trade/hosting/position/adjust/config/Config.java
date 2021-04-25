package xueqiao.trade.hosting.position.adjust.config;

import org.soldier.platform.id_maker.IdMaker;
import org.soldier.platform.id_maker.IdMakerFactory;

public class Config {
    private static Config ourInstance = new Config();

    private static final int MANUAL_INPUT_ID_MAKER_TYPE = 237;
    private static final int POSITION_ASSIGN_ID_MAKER_TYPE = 238;
    private static final int POSITION_VERIFY_ID_MAKER_TYPE = 239;

    public static Config getInstance() {
        return ourInstance;
    }

    private Config() {
        init();
    }

    private IdMaker manualInputIdMaker;

    private IdMaker positionAssignIdMaker;

    private IdMaker positionVerifyIdMaker;

    private void init(){
        manualInputIdMaker = IdMakerFactory.getInstance().getIdMaker(MANUAL_INPUT_ID_MAKER_TYPE);
        positionAssignIdMaker = IdMakerFactory.getInstance().getIdMaker(POSITION_ASSIGN_ID_MAKER_TYPE);
        positionVerifyIdMaker = IdMakerFactory.getInstance().getIdMaker(POSITION_VERIFY_ID_MAKER_TYPE);
    }

    public IdMaker getManualInputIdMaker() {
        return manualInputIdMaker;
    }

    public IdMaker getPositionAssignIdMaker() {
        return positionAssignIdMaker;
    }

    public IdMaker getPositionVerifyIdMaker() {
        return positionVerifyIdMaker;
    }
}
