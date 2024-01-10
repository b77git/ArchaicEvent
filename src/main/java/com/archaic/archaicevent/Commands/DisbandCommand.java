package com.archaic.archaicevent.Commands;

import com.archaic.archaicevent.ArchaicEvent;
import com.archaic.archaicevent.Helper.*;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class DisbandCommand extends CommandBase {

    @Override
    public String getName() {
        return "move";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/archaic move - Disband a team";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        PlayerData playerData = JsonHelper.getPlayerDataByName(sender.getDisplayName().getUnformattedText(), ArchaicEvent.playerDataFile);

        if (!playerData.inTeam()) {
            sender.sendMessage(new TextComponentString("You are not in a team!"));
            return;
        }

        TeamData playerTeam = JsonHelper.getTeamByMemberName(playerData.getPlayerName(), ArchaicEvent.teamDatafile);

        if (playerTeam.getOwner() == null) {
            sender.sendMessage(new TextComponentString("You are not the owner of your team!"));
            return;
        }

        disband(server, sender, playerTeam);
    }

    public static void disband(MinecraftServer server, ICommandSender sender, TeamData teamData) {
        BeaconData beaconData = teamData.getBeacon();

        //delete the teams beacon from world
        if (beaconData != null) {
            BeaconManager beaconManager = new BeaconManager();
            beaconManager.deleteBeacon(beaconData);
        }

        JsonHelper.removeTeamDataFromFile(teamData.getName(), ArchaicEvent.teamDatafile);
        removeBeacon(sender);
        notifyTeam(server, teamData);
    }

    private static void notifyTeam(MinecraftServer server, TeamData teamData) {
        for (PlayerData member : teamData.getMembers()) {
            EntityPlayerMP player = getPlayerByUsername(member.getPlayerName(), server);

            if (player != null) {
                player.sendMessage(new TextComponentString("Your team has been disbanded."));
            }
        }
    }

    private static EntityPlayerMP getPlayerByUsername(String playerName, MinecraftServer server) {
        return server.getPlayerList().getPlayerByUsername(playerName);
    }

    private static void removeBeacon(ICommandSender sender) {
        if (sender instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) sender;

            // Check if the player has a beacon in their inventory
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);

                if (stack.getItem() == Item.getItemFromBlock(Blocks.BEACON)) {
                    // Remove the beacon from the player's inventory
                    player.inventory.removeStackFromSlot(i);
                }
            }
        }
    }
}
