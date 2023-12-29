package com.archaic.archaicevent.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class TeamsCommand extends CommandBase {

    @Override
    public String getName() {
        return "teams";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/archaic teams - View a list of all the current teams";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        sender.sendMessage(new TextComponentString("Executing /archaic teams"));
    }

}
