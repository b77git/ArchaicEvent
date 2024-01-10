package com.archaic.archaicevent.Commands;

import com.archaic.archaicevent.ArchaicEvent;
import com.archaic.archaicevent.Helper.JsonHelper;
import com.archaic.archaicevent.Helper.TeamData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.Arrays;

import static com.archaic.archaicevent.Commands.DisbandCommand.disband;

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
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length > 1) {
            String teamName = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            TeamData teamData = JsonHelper.getTeamDataByName(teamName, ArchaicEvent.teamDatafile);
            if (teamData != null) {
                disband(server, sender, teamData);
                sender.sendMessage(new TextComponentString(teamName + " has been disbanded."));
            } else {
                sender.sendMessage(new TextComponentString("Team not found: " + teamName));
            }
        } else {
            sender.sendMessage(new TextComponentString("Usage: /archaic move <teamName>"));
        }
    }
}
