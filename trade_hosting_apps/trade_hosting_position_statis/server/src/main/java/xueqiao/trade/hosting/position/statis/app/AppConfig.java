package xueqiao.trade.hosting.position.statis.app;

import org.soldier.platform.id_maker.IdException;
import org.soldier.platform.id_maker.IdMaker;
import org.soldier.platform.id_maker.IdMakerFactory;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.statis.service.report.AppReport;
import xueqiao.trade.hosting.position.statis.service.report.StatErrorCode;

public class AppConfig {

    /**
     * 本应用目录名
     */
    public static final String APP_NAME = "position_statis";

    /**
     * 托管机用到的IdMaker的类型统一由王立分配
     * */
    /**
     * 统计持仓数据IdMaker类型
     * ATTENTION: 这个IdMaker应该是用不上了，因为持仓汇总里直接使用 targetKey , targetType 和 subAccountId 做为索引了
     */
    private static final int ID_MAKER_TYPE_STAT_POSITION = 230;

    /**
     * 统计持仓明细数据IdMaker类型
     */
    private static final int ID_MAKER_TYPE_STAT_POSITION_ITEM = 231;

    /**
     * 统计持仓小单元数据IdMaker类型
     */
    private static final int ID_MAKER_TYPE_STAT_POSITION_UNIT = 232;

    /**
     * 平仓数据IdMaker类型
     */
    private static final int ID_MAKER_TYPE_STAT_CLOSED_POSITION = 233;

    /**
     * 平仓明细数据IdMaker类型
     */
    private static final int ID_MAKER_TYPE_STAT_CLOSED_POSITION_ITEM = 234;

    /**
     * 平仓天汇总数据IdMaker类型
     */
    private static final int ID_MAKER_TYPE_STAT_CLOSED_POSITION_DATE_SUMMARY = 235;

    /**
     * 平仓小单元数据IdMaker类型
     */
    private static final int ID_MAKER_TYPE_STAT_CLOSED_UNIT = 236;

    /**
     * 成交腿关联信息 IdMaker 类型
     */
    private static final int ID_MAKER_TYPE_SOURCE_HOSTING_XQ_TRADE_RELATED_ITEM = 245;

    private static IdMaker statPositionIdMaker = IdMakerFactory.getInstance().getIdMaker(ID_MAKER_TYPE_STAT_POSITION);
    private static IdMaker statPositionItemIdMaker = IdMakerFactory.getInstance().getIdMaker(ID_MAKER_TYPE_STAT_POSITION_ITEM);
    private static IdMaker statPositionUnitIdMaker = IdMakerFactory.getInstance().getIdMaker(ID_MAKER_TYPE_STAT_POSITION_UNIT);
    private static IdMaker statClosedPositionIdMaker = IdMakerFactory.getInstance().getIdMaker(ID_MAKER_TYPE_STAT_CLOSED_POSITION);
    private static IdMaker statClosedPositionItemIdMaker = IdMakerFactory.getInstance().getIdMaker(ID_MAKER_TYPE_STAT_CLOSED_POSITION_ITEM);
    private static IdMaker statClosedPositionDateSummaryIdMaker = IdMakerFactory.getInstance().getIdMaker(ID_MAKER_TYPE_STAT_CLOSED_POSITION_DATE_SUMMARY);
    private static IdMaker statClosedUnitIdMaker = IdMakerFactory.getInstance().getIdMaker(ID_MAKER_TYPE_STAT_CLOSED_UNIT);
    private static IdMaker sourceHostingXQTradeRelatedItemIdMakder = IdMakerFactory.getInstance().getIdMaker(ID_MAKER_TYPE_SOURCE_HOSTING_XQ_TRADE_RELATED_ITEM);

    /**
     * 获取持仓汇总id
     */
    public static long getStatPositionSummaryId() throws ErrorInfo {
        try {
            return statPositionIdMaker.createId();
        } catch (IdException e) {
            AppReport.reportErr("getStatPositionSummaryId ## id maker error : " + e.toString(), e);
            throw StatErrorCode.innerError;
        }
    }

    /**
     * 获取持仓明细id
     */
    public static long getStatPositionItemId() throws ErrorInfo {
        try {
            return statPositionItemIdMaker.createId();
        } catch (IdException e) {
            AppReport.reportErr("getStatPositionItemId ## id maker error : " + e.toString(), e);
            throw StatErrorCode.innerError;
        }
    }

    /**
     * 获取持仓小单元id
     */
    public static long getStatPositionUnitId() throws ErrorInfo {
        try {
            return statPositionUnitIdMaker.createId();
        } catch (IdException e) {
            AppReport.reportErr("getStatPositionUnitId ## id maker error : " + e.toString(), e);
            throw StatErrorCode.innerError;
        }
    }

    /**
     * 获取平仓汇总id
     */
    public static long getStatClosedPositionId() throws ErrorInfo {
        try {
            return statClosedPositionIdMaker.createId();
        } catch (IdException e) {
            AppReport.reportErr("getStatClosedPositionId ## id maker error : " + e.toString(), e);
            throw StatErrorCode.innerError;
        }
    }

    /**
     * 获取平仓明细id
     */
    public static long getStatClosedPositionItemId() throws ErrorInfo {
        try {
            return statClosedPositionItemIdMaker.createId();
        } catch (IdException e) {
            AppReport.reportErr("getStatClosedPositionItemId ## id maker error : " + e.toString(), e);
            throw StatErrorCode.innerError;
        }
    }

    /**
     * 获取平仓天汇总id
     */
    public static long getStatClosedPositionDateSummaryId() throws ErrorInfo {
        try {
            return statClosedPositionDateSummaryIdMaker.createId();
        } catch (IdException e) {
            AppReport.reportErr("getStatClosedPositionDateSummaryId ## id maker error : " + e.toString(), e);
            throw StatErrorCode.innerError;
        }
    }

    /**
     * 获取平仓小单元id
     */
    public static long getStatClosedUnitId() throws ErrorInfo {
        try {
            return statClosedUnitIdMaker.createId();
        } catch (IdException e) {
            AppReport.reportErr("getStatClosedUnitId ## id maker error : " + e.toString(), e);
            throw StatErrorCode.innerError;
        }
    }

    /**
     * 获取成交关联信息 id
     */
    public static long getSourceHostingXQTradeRelatedItemSourceId() throws ErrorInfo {
        try {
            return sourceHostingXQTradeRelatedItemIdMakder.createId();
        } catch (IdException e) {
            AppReport.reportErr("getSourceHostingXQTradeRelatedItemSourceId ## id maker error : " + e.toString(), e);
            throw StatErrorCode.innerError;
        }
    }
}
