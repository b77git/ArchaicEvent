package com.archaic.archaicevent.Events;

import com.archaic.archaicevent.ArchaicEvent;
import com.archaic.archaicevent.Helper.BeaconData;
import com.archaic.archaicevent.Helper.JsonHelper;
import com.archaic.archaicevent.Helper.PlayerData;
import com.archaic.archaicevent.Helper.TeamData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBeacon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BeaconPlacementEventHandler {

    @SubscribeEvent
    public void onEntityPlace(EntityPlaceEvent event) {
        try {
            handleEntityPlaceEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleEntityPlaceEvent(EntityPlaceEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            BlockPos blockPos = event.getPos();
            Block placedBlock = getBlockAtLocation(event.getWorld(), blockPos);

            if (placedBlock instanceof BlockBeacon) {
                EntityPlayer player = (EntityPlayer) event.getEntity();
                int dimensionId = getDimensionId(event.getWorld());

                PlayerData playerData = getPlayerDataByName(player.getDisplayNameString());
                TeamData placerTeam = getTeamByMemberName(playerData.getPlayerName());

                if (placerTeam == null) {
                    event.setCanceled(true);
                    player.sendMessage(new TextComponentString("You must be in a team to place a beacon."));
                    return;
                }

                if (placerTeam.getBeacon() != null) {
                    event.setCanceled(true);
                    player.sendMessage(new TextComponentString("Your team has already placed a beacon."));
                    return;
                }

                int x = blockPos.getX();
                int y = blockPos.getY();
                int z = blockPos.getZ();

                BeaconData beacon = new BeaconData(placerTeam.getName(), x, y, z, dimensionId);
                placerTeam.setBeacon(beacon);

                player.sendMessage(new TextComponentString("You placed a Beacon at X: " + x + ", Y: " + y + ", Z: " + z +
                        " in dimension: " + dimensionId));
            }
        }
    }


    private static Block getBlockAtLocation(World world, BlockPos blockPos) {
        try {
            return world.getBlockState(blockPos).getBlock();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int getDimensionId(World world) {
        try {
            return world.provider.getDimension();
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Default dimension ID might break things since it is overworld
        }
    }

    private static PlayerData getPlayerDataByName(String playerName) {
        try {
            return JsonHelper.getPlayerDataByName(playerName, ArchaicEvent.playerDataFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static TeamData getTeamByMemberName(String memberName) {
        try {
            return JsonHelper.getTeamByMemberName(memberName, ArchaicEvent.teamDatafile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
