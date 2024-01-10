package com.archaic.archaicevent.Spawning;

import com.archaic.archaicevent.ArchaicEvent;
import com.archaic.archaicevent.Helper.JsonHelper;
import com.archaic.archaicevent.Helper.PlayerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;

@Mod.EventBusSubscriber
public class SpawnEvolutions {

    private static final int MIN_SPAWN_RADIUS = 30;
    private static final int MAX_SPAWN_RADIUS = 50;
    private static final int MIN_SPAWN_COOLDOWN = 3 * 20;
    private static final int MAX_SPAWN_COOLDOWN = 10 * 20;
    private static final int MOB_CAP = 20;
    private static final int SPAWN_COUNT = 3;

    private static Map<EntityPlayer, Integer> mobsSpawned = new HashMap<>();
    private static Map<EntityPlayer, Integer> spawnCooldowns = new HashMap<>();
    private static Map<Integer, List<String>> evolutionMobs = new HashMap<>();

    static {
        loadEvolutionMobs();
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.world instanceof WorldServer) {
            WorldServer world = (WorldServer) event.world;

            for (EntityPlayer player : world.playerEntities) {
                PlayerData playerData = JsonHelper.getPlayerDataByName(player.getDisplayName().getUnformattedText(), ArchaicEvent.playerDataFile);

                if (playerData != null) {
                    // Check and update the spawn cooldown
                    if (!spawnCooldowns.containsKey(player) || spawnCooldowns.get(player) <= 0) {
                        spawnMobsAroundPlayer(world, player, SPAWN_COUNT, playerData.getMobEvolution());
                        spawnCooldowns.put(player, getRandomSpawnCooldown());
                    } else {
                        spawnCooldowns.put(player, spawnCooldowns.get(player) - 1);
                    }
                }
            }
        }
    }

    private static int getRandomSpawnCooldown() {
        Random rand = new Random();
        return rand.nextInt(MAX_SPAWN_COOLDOWN - MIN_SPAWN_COOLDOWN + 1) + MIN_SPAWN_COOLDOWN;
    }

    private static void spawnMobsAroundPlayer(WorldServer world, EntityPlayer player, int numberOfMobs, int evolutionLevel) {
        BlockPos playerPos = new BlockPos(player.posX, player.posY, player.posZ);
        Random rand = new Random();

        for (int i = 0; i < numberOfMobs && (mobsSpawned.get(player) == null || mobsSpawned.get(player) < MOB_CAP); i++) {
            int xOffset = rand.nextInt(MAX_SPAWN_RADIUS - MIN_SPAWN_RADIUS) + MIN_SPAWN_RADIUS;
            int zOffset = rand.nextInt(MAX_SPAWN_RADIUS - MIN_SPAWN_RADIUS) + MIN_SPAWN_RADIUS;

            BlockPos spawnPos = findSurfaceSpawnPosition(world, playerPos.add(xOffset, 0, zOffset));

            EntityLivingBase mobToSpawn = getMobToSpawn(player, world.getBiome(spawnPos), evolutionLevel);

            if (mobToSpawn != null) {
                // Set custom data to mark it as spawned by a specific player
                mobToSpawn.getEntityData().setBoolean("ArchaicEventSpawnedMob", true);
                mobToSpawn.getEntityData().setString("ArchaicEventSpawningPlayer", player.getName());

                mobToSpawn.setLocationAndAngles(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), rand.nextFloat() * 360.0F, 0.0F);
                world.spawnEntity(mobToSpawn);
                player.sendMessage(new TextComponentString("Spawning mob at coordinates X: " + spawnPos.getX() + ", Y: " + spawnPos.getY() + ", Z: " + spawnPos.getZ() + ". Mob cap is currently at " + mobsSpawned + "."));
                mobsSpawned.put(player, mobsSpawned.get(player) + 1);
            }
        }
    }

    private static BlockPos findSurfaceSpawnPosition(WorldServer world, BlockPos startPos) {
        int surfaceY = world.getHeight(startPos).getY();
        return new BlockPos(startPos.getX(), surfaceY, startPos.getZ());
    }

    private static EntityLivingBase getMobToSpawn(EntityPlayer player, Biome biome, int evolutionLevel) {
        List<String> possibleMobIds = evolutionMobs.get(evolutionLevel);

        if (possibleMobIds != null && !possibleMobIds.isEmpty()) {
            String selectedMobId = possibleMobIds.get(new Random().nextInt(possibleMobIds.size()));

            try {
                ResourceLocation mobLocation = new ResourceLocation(selectedMobId);
                return (EntityLivingBase) EntityList.createEntityByIDFromName(mobLocation, player.world);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private static void loadEvolutionMobs() {
        for (int i = 1; i <= 8; i++) {
            evolutionMobs.put(i, ArchaicEvent.configHandler.tierMobs.get(i - 1));
        }
    }

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();

        // Check if the dead entity is a mob spawned by this mod
        if (entity instanceof EntityLivingBase && entity.getEntityData().getBoolean("ArchaicEventSpawnedMob")) {
            EntityLivingBase livingEntity = (EntityLivingBase) entity;
            String spawningPlayerName = livingEntity.getEntityData().getString("ArchaicEventSpawningPlayer");

            if (!spawningPlayerName.isEmpty()) {
                EntityPlayer spawningPlayer = livingEntity.world.getPlayerEntityByName(spawningPlayerName);

                if (spawningPlayer != null) {
                    // Update the spawn count for the player
                    mobsSpawned.put(spawningPlayer, mobsSpawned.getOrDefault(spawningPlayer, 0) - 1);
                }
            }
        }
    }
}