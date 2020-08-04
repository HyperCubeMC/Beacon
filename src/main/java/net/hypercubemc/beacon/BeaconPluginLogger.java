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
        log.debug(debug);
    }

    public void info(String info) {
        log.info(info);
    }

    public void warn(String warn) {
        log.warn(warn);
    }

    public void error(String error) {
        log.error(error);
    }

    public void fatal(String fatal) {
        log.fatal(fatal);
    }

    public void trace(String trace) {
        log.trace(trace);
    }
}
