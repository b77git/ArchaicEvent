package com.archaic.archaicevent.Commands;

import com.archaic.archaicevent.Helper.PlayerData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class SpyCommand extends CommandBase {

    @Override
    public String getName() {
        return "spy";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/archaic spy - See all player commands when sent";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        sender.sendMessage(new TextComponentString("Executing /archaic spy"));
    }

    public void toggleSpy(MinecraftServer server, ICommandSender sender, String[] args){
        PlayerData player = new PlayerData(sender.getDisplayName().getUnformattedComponentText(), sender.getCommandSenderEntity().getUniqueID().toString());
        if (player.isSpyToggled()){
            player.setSpyToggled(true);
            return;
        }
        player.setSpyToggled(false);
    }
}
