package net.hypercubemc.beacon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static net.hypercubemc.beacon.AnsiCodes.*;

public class BeaconPluginLogger {
    private String pluginName;
    
    public BeaconPluginLogger(pluginName) {
        this.pluginName = pluginName;
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
