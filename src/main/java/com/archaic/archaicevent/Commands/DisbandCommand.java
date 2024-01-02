package com.archaic.archaicevent.Commands;

import com.archaic.archaicevent.ArchaicEvent;
import com.archaic.archaicevent.Helper.JsonHelper;
import com.archaic.archaicevent.Helper.PlayerData;
import com.archaic.archaicevent.Helper.TeamData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.Arrays;

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
        JsonHelper.removeTeamDataFromFile(teamData.getTeamName(), ArchaicEvent.teamDatafile);
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
}
