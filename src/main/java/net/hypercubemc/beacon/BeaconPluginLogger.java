package net.hypercubemc.beacon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static net.hypercubemc.beacon.util.AnsiCodes.*;

public class BeaconPluginLogger {
    private final String pluginName;
    private final Logger log;

    BeaconPluginLogger(String pluginName) {
        this.pluginName = pluginName;
        this.log = LogManager.getLogger(pluginName);
    }

    public void debug(String debug) {
        log.debug(colorLightGrey + "[" + pluginName + "]" + debug + formatReset);
    }

    public void info(String info) {
        log.info(formatReset + "[" + pluginName + "]" + info + formatReset);
    }

    public void warn(String warn) {
        log.warn(colorYellow + "[" + pluginName + "]" + warn + formatReset);
    }

    public void error(String error) {
        log.error(colorBrightRed + "[" + pluginName + "]" + error + formatReset);
    }

    public void fatal(String fatal) {
        log.fatal(colorBrightRed + formatBold + "[" + pluginName + "]" + fatal + formatReset);
    }

    public void trace(String trace) {
        log.trace(colorLightGrey + "[" + pluginName + "]" + trace + formatReset);
    }
}
