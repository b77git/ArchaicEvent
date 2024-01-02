package com.archaic.archaicevent.Commands;

import com.archaic.archaicevent.ArchaicEvent;
import com.archaic.archaicevent.Helper.JsonHelper;
import com.archaic.archaicevent.Helper.PlayerData;
import com.archaic.archaicevent.Helper.TeamData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.List;

public class InviteCommand extends CommandBase {

    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/archaic invite - Invite a player to the team";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length == 3){
            invite(server, sender, args);
        } else {
            sender.sendMessage(new TextComponentString("Usage: /archaic invite <playername>"));
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 2) {
            // If there are exactly two arguments, provide suggestions for player names
            //PLEASE DO NOT FORGET TO TEST THIS
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }
        return super.getTabCompletions(server, sender, args, targetPos);
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return index == 1;
    }

    private void invite(MinecraftServer server, ICommandSender sender, String[] args) {
        String senderUsername = sender.getDisplayName().getUnformattedText();
        String inviteeUsername = args[2];

        TeamData senderTeam = JsonHelper.getTeamByMemberName(senderUsername, ArchaicEvent.teamDatafile);

        if (!JsonHelper.doesPlayerExistByName(inviteeUsername, ArchaicEvent.playerDataFile)) {
            sender.sendMessage(new TextComponentString("This user does not exist!"));
            return;
        }

        PlayerData inviteeData = JsonHelper.getPlayerDataByName(inviteeUsername, ArchaicEvent.playerDataFile);

        if (inviteeData.inTeam()) {
            sender.sendMessage(new TextComponentString("This user is already in a team!"));
            return;
        }

        if (senderTeam.getPendingInvites().contains(inviteeData)) {
            sender.sendMessage(new TextComponentString("This user has already been invited to the team!"));
            return;
        }

        if (!isPlayerOnline(server, inviteeUsername)) {
            sender.sendMessage(new TextComponentString("This user must be online."));
            return;
        }

        senderTeam.addInvite(inviteeData);

        EntityPlayerMP inviteeUser = server.getPlayerList().getPlayerByUsername(inviteeUsername);
        inviteeUser.sendMessage(new TextComponentString("You have been invited to " + senderTeam.getTeamName()));
    }

    public static boolean isPlayerOnline(MinecraftServer server, String playerName) {
        if (server != null) {
            for (WorldServer world : server.worlds) {
                for (EntityPlayer player : world.playerEntities) {
                    if (isMatchingPlayer(player, playerName)) {
                        return true; // Player is online
                    }
                }
            }
        }

        return false; // Player is not online
    }

    private static boolean isMatchingPlayer(EntityPlayer player, String playerName) {
        return player instanceof EntityPlayerMP && player.getName().equals(playerName);
    }
}