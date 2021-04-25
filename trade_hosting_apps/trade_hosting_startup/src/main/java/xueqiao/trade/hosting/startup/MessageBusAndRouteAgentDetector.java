package xueqiao.trade.hosting.startup;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MessageBusAndRouteAgentDetector {
    public boolean detectOnce() throws IOException, InterruptedException {
        List<String> outLines = new ArrayList<>();
        int ret = ipcs(outLines);
        if (ret != 0) {
            throw new IOException("call process failed, ret=" + ret);
        }
        return analysisOutputLines(outLines);
    }

    private int ipcs(List<String> outLines) throws IOException, InterruptedException {
        ProcessBuilder procBuilder = new ProcessBuilder();
        procBuilder.command("ipcs", "-m");
        Process proc = procBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        AppLog.d(StringUtils.join(procBuilder.command(), " "));
        String line = null;
        while ((line = reader.readLine()) != null) {
            AppLog.d(line);
            outLines.add(line);
        }
        return proc.waitFor();
    }

    private boolean analysisOutputLines(List<String> outLines) {
        int shmLines = 0;
        boolean hasCenterShm = false;
        boolean hasRouteAgent = false;

        for (String line : outLines) {
            if (!line.startsWith("0x")) {
                continue;
            }

            int pos = line.indexOf(' ');
            if (pos < 0) {
                continue;
            }

            try {
                int shmKey = Integer.valueOf(line.substring(2, pos), 16);
                if (shmKey == 4000) {
                    hasCenterShm = true;
                }
                if (shmKey == 0x1000) {
                    hasRouteAgent = true;
                }

                shmLines += 1;
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
                continue;
            }
        }

        AppLog.d("hasCenterShm = " + hasCenterShm + ", hasRouteAgent=" + hasRouteAgent + ", shmLines=" + shmLines);
        if (hasCenterShm && hasRouteAgent && shmLines > 10) {
            return true;
        }

        return false;
    }

}
