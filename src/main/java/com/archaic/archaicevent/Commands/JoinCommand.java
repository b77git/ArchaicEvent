package com.archaic.archaicevent.Commands;

import com.archaic.archaicevent.ArchaicEvent;
import com.archaic.archaicevent.Helper.JsonHelper;
import com.archaic.archaicevent.Helper.PlayerData;
import com.archaic.archaicevent.Helper.TeamData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinCommand extends CommandBase {

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/archaic join - Join a team you were invited to";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        joinTeam(server ,sender, args);
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 2) {
            PlayerData playerData = JsonHelper.getPlayerDataByName(sender.getDisplayName().getUnformattedText(), ArchaicEvent.playerDataFile);
            List<String> teamNamesWithInvites = getTeamNamesWithInvites(playerData);
            return getListOfStringsMatchingLastWord(args, teamNamesWithInvites);
        }
        return super.getTabCompletions(server, sender, args, targetPos);
    }

    private List<String> getTeamNamesWithInvites(PlayerData playerData) {
        List<String> teamNames = new ArrayList<>();
        for (TeamData teamData : JsonHelper.readExistingTeamDataFromFile(ArchaicEvent.teamDatafile)) {
            if (teamData.getPendingInvites().contains(playerData)) {
                teamNames.add(teamData.getName());
            }
        }
        return teamNames;
    }

    private void joinTeam(MinecraftServer server, ICommandSender sender, String[] args) {
        String teamName = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        PlayerData playerData = JsonHelper.getPlayerDataByName(sender.getDisplayName().getUnformattedText(), ArchaicEvent.playerDataFile);

        if (!JsonHelper.doesTeamExistByName(teamName, ArchaicEvent.teamDatafile)){
            sender.sendMessage(new TextComponentString("This team does not exist."));
            return;
        }

        TeamData Team = JsonHelper.getTeamDataByName(teamName, ArchaicEvent.teamDatafile);

        if (!Team.getPendingInvites().contains(playerData)){
            sender.sendMessage(new TextComponentString("You were not invited to " + teamName));
            return;
        }

        if (Team.getMembers().size() >= ArchaicEvent.configHandler.TeamLimit){
            sender.sendMessage(new TextComponentString(teamName + " has already reached the team limit!"));
            return;
        }

        Team.addMember(playerData);
        sender.sendMessage(new TextComponentString("You have joined " + teamName));
    }

    private static void notifyTeam(MinecraftServer server, TeamData teamData, PlayerData joiningPlayer) {
        for (PlayerData member : teamData.getMembers()) {
            if (member == joiningPlayer){
                continue;
            }
            EntityPlayerMP player = getPlayerByUsername(member.getPlayerName(), server);

            if (player != null) {
                player.sendMessage(new TextComponentString(joiningPlayer.getPlayerName() + " has joined the team!"));
            }
        }
    }

    private static EntityPlayerMP getPlayerByUsername(String playerName, MinecraftServer server) {
        return server.getPlayerList().getPlayerByUsername(playerName);
    }
}