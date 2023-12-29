package com.archaic.archaicevent.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

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
        sender.sendMessage(new TextComponentString("Executing /archaic invite"));
    }
}