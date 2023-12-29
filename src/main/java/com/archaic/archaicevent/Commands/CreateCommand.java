package com.archaic.archaicevent.Commands;

import com.archaic.archaicevent.ArchaicEvent;
import com.archaic.archaicevent.Helper.JsonHelper;
import com.archaic.archaicevent.Helper.PlayerData;
import com.archaic.archaicevent.Helper.TeamData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.Arrays;

public class CreateCommand extends CommandBase {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/archaic create - Create a team";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(new TextComponentString("Please include a team name."));
            return;
        }

        createTeam(args, sender);
    }

    public void createTeam(String[] args, ICommandSender sender){
        String teamName = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        PlayerData owner = new PlayerData(sender.getDisplayName().getUnformattedComponentText(), sender.getCommandSenderEntity().getUniqueID().toString());

        if (!validateTeamName(teamName)) {
            sender.sendMessage(new TextComponentString("Invalid team name."));
            return;
        }

        if (owner.isInTeam()){
            sender.sendMessage(new TextComponentString("You are already in a team."));
            return;
        }

        TeamData teamData = new TeamData(teamName, owner);

        if (teamAlreadyExists(teamData)) {
            sender.sendMessage(new TextComponentString("This team name already exists."));
            return;
        }

        JsonHelper.addTeamDataToFile(teamData, ArchaicEvent.teamDatafile);
        sender.sendMessage(new TextComponentString("The team " + teamName + " has been created"));
    }

    private boolean validateTeamName(String teamName) {
        return !teamName.isEmpty();
    }

    private boolean teamAlreadyExists(TeamData teamData) {
        return JsonHelper.containsTeamData(JsonHelper.readExistingTeamDataFromFile(ArchaicEvent.teamDatafile), teamData);
    }
}
