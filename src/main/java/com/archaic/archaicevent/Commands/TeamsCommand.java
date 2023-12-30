package com.archaic.archaicevent.Commands;

import com.archaic.archaicevent.Gui.Teams;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

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
        Minecraft.getMinecraft().displayGuiScreen(new Teams());
    }
}
