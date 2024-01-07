package com.archaic.archaicevent.Helper;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

public class ConfigHandler {
    private Configuration config;
    private Logger logger;

    private static final int DEFAULT_TEAM_LIMIT = 4;


    public int TeamLimit;

    public ConfigHandler(Configuration config, Logger logger) {
        this.config = config;
        this.logger = logger;

        config.load();
        loadConfig();
    }

    private void loadConfig() {
        logger.info("Loading configuration file...");

        TeamLimit = config.getInt("TeamLimit", Configuration.CATEGORY_GENERAL, 1, 2147483647, DEFAULT_TEAM_LIMIT,
                "The number of players allowed per team");

        if (config.hasChanged()) {
            config.save();
        }
    }

    public void saveConfig() {
        logger.info("Saving configuration file...");

        config.get(Configuration.CATEGORY_GENERAL, "TeamLimit", DEFAULT_TEAM_LIMIT).set(TeamLimit);

        if (config.hasChanged()) {
            config.save();
        }
    }
}