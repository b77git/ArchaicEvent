package com.archaic.archaicevent.Helper;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigHandler {
    private static final int DEFAULT_TEAM_LIMIT = 4;
    private static final int NUM_TIERS = 8;

    private Configuration config;
    private Logger logger;

    public int TeamLimit;
    public List<List<String>> tierMobs;

    public ConfigHandler(Configuration config, Logger logger) {
        this.config = config;
        this.logger = logger;

        config.load();
        loadConfig();
    }

    private void loadConfig() {
        logger.info("Loading configuration file...");

        TeamLimit = config.getInt("TeamLimit", Configuration.CATEGORY_GENERAL, DEFAULT_TEAM_LIMIT, 1, 2147483647,
                "The number of players allowed per team");

        tierMobs = new ArrayList<>();
        for (int i = 0; i < NUM_TIERS; i++) {
            String tierKey = "Tier" + (i + 1) + "Mobs";
            List<String> defaultMobs = getDefaultMobsForTier(i + 1);
            tierMobs.add(Arrays.asList(config.getStringList(tierKey, Configuration.CATEGORY_GENERAL, defaultMobs.toArray(new String[0]), "Mobs for Tier " + (i + 1))));
        }

        if (config.hasChanged()) {
            config.save();
        }
    }

    private List<String> getDefaultMobsForTier(int tier) {
        switch (tier) {
            case 1:
                return Arrays.asList("minecraft:zombie", "minecraft:skeleton");
            case 2:
                return Arrays.asList("minecraft:creeper", "minecraft:spider");
            case 3:
                return Arrays.asList("minecraft:blaze", "minecraft:witch");
            case 4:
                return Arrays.asList("minecraft:enderman", "minecraft:silverfish");
            case 5:
                return Arrays.asList("minecraft:stray", "minecraft:husk");
            case 6:
                return Arrays.asList("minecraft:guardian", "minecraft:shulker");
            case 7:
                return Arrays.asList("minecraft:evocation_illager", "minecraft:vex");
            case 8:
                return Arrays.asList("minecraft:elder_guardian", "minecraft:wither_skeleton");
            default:
                return new ArrayList<>();
        }
    }

    public void saveConfig() {
        logger.info("Saving configuration file...");

        config.get(Configuration.CATEGORY_GENERAL, "TeamLimit", DEFAULT_TEAM_LIMIT).set(TeamLimit);

        for (int i = 0; i < NUM_TIERS; i++) {
            String tierKey = "Tier" + (i + 1) + "Mobs";
            config.get(Configuration.CATEGORY_GENERAL, tierKey, getDefaultMobsForTier(i + 1).toArray(new String[0])).set(tierMobs.get(i).toArray(new String[0]));
        }
        // Save options for other tiers as needed

        if (config.hasChanged()) {
            config.save();
        }
    }
}
