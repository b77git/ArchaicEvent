package com.archaic.archaicevent.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

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
        sender.sendMessage(new TextComponentString("Executing /archaic create"));
    }
}
