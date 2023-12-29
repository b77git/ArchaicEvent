package com.archaic.archaicevent.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class MoveCommand extends CommandBase {

    @Override
    public String getName() {
        return "move";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/archaic move - Move the team beacon";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        sender.sendMessage(new TextComponentString("Executing /archaic move"));
    }
}