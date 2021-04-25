package xueqiao.trade.hosting.quot.comb;

import com.google.common.collect.ImmutableMap;
import org.soldier.base.logger.AppLog;

import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;
import org.soldier.platform.svr_platform.container.UnixSocketServiceContainer;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.quot.comb.core.QuotCombMessageConsumer;
import xueqiao.trade.hosting.quot.comb.core.QuotCombProcessor;
import xueqiao.trade.hosting.quot.comb.thriftapi.server.TradeHostingQuotCombAdaptor;
import xueqiao.trade.hosting.quot.dispatcher.client.THQDClient;
import xueqiao.trade.hosting.storage.apis.StorageInitialzer;

import java.util.Properties;

public class App {

    private static void serve() throws Exception {
        Properties props = new Properties();
        props.put("Adaptor-Class", TradeHostingQuotCombAdaptor.class.getName());
        UnixSocketServiceContainer quotCombContainer = new UnixSocketServiceContainer() {
            @Override
            protected boolean willServe() {
                return true;
            }
        };
        quotCombContainer.start(props);
    }

    public static void main(String[] args) {
        AppLog.init("apps/trade_hosting_quot_comb");
        
        try {
            StorageInitialzer.initApis();
            THQDClient.init("trade_hosting_quot_comb");
            THQDClient.Global().setQuotationCallback(QuotCombProcessor.Global());
            HostingMessageContext.Global().createConsumerAgent("hosting_quot_comb"
                    , new QuotCombMessageConsumer());

            AttrReporterFactory.getDefault().keep(AttrReporterFactory.getDefault().requireKey(
                    "xueqiao.trade.hosting.process.keepalive"
                    , ImmutableMap.of("processname", "trade_hosting_quot_comb")), 1);

            serve();
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            Runtime.getRuntime().halt(-1);
        }
        
    }
    
}
