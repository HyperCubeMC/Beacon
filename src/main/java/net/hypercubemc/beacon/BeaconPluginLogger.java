package net.hypercubemc.beacon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static net.hypercubemc.beacon.AnsiCodes.*;

public class BeaconPluginLogger {
    private final String pluginName;

    public BeaconPluginLogger(String pluginName) {
        this.pluginName = pluginName;
    }

    public void debug(String debug) {
        Logger log = LogManager.getLogger(pluginName);
        log.debug(colorLightGrey + "[" + pluginName + "]" + debug + formatReset);
    }

    public void info(String info) {
        Logger log = LogManager.getLogger(pluginName);
        log.info(formatReset + "[" + pluginName + "]" + info + formatReset);
    }

    public void warn(String warn) {
        Logger log = LogManager.getLogger(pluginName);
        log.warn(colorYellow + "[" + pluginName + "]" + warn + formatReset);
    }

    public void error(String error) {
        Logger log = LogManager.getLogger(pluginName);
        log.error(colorBrightRed + "[" + pluginName + "]" + error + formatReset);
    }

    public void fatal(String fatal) {
        Logger log = LogManager.getLogger(pluginName);
        log.fatal(colorBrightRed + formatBold + "[" + pluginName + "]" + fatal + formatReset);
    }

    public void trace(String trace) {
        Logger log = LogManager.getLogger(pluginName);
        log.trace(colorLightGrey + "[" + pluginName + "]" + trace + formatReset);
    }
}
