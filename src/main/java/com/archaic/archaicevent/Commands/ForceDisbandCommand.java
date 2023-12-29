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

public class ForceDisbandCommand extends CommandBase {

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
        if (args.length > 1) {
            disband(server, sender, args);
        } else {
            sender.sendMessage(new TextComponentString("Usage: /archaic move <teamName>"));
        }
    }

    private void disband(MinecraftServer server, ICommandSender sender, String[] args) {
        String teamName = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        TeamData teamData = JsonHelper.getTeamDataByName(teamName, ArchaicEvent.teamDatafile);

        if (teamData != null) {
            JsonHelper.removeTeamDataFromFile(teamName, ArchaicEvent.teamDatafile);
            notifyTeam(server, teamData);
        } else {
            sender.sendMessage(new TextComponentString("Team not found: " + teamName));
        }
    }

    private void notifyTeam(MinecraftServer server, TeamData teamData) {
        for (PlayerData member : teamData.getMembers()) {
            EntityPlayerMP player = getPlayerByUsername(member.getPlayerName(), server);

            if (player != null) {
                // Send a message to the online player
                player.sendMessage(new TextComponentString("Your team has been disbanded by an admin."));
            }
        }
    }

    private EntityPlayerMP getPlayerByUsername(String playerName, MinecraftServer server) {
        return server.getPlayerList().getPlayerByUsername(playerName);
    }
}
