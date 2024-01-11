package com.archaic.archaicevent.Commands;

import com.archaic.archaicevent.ArchaicEvent;
import com.archaic.archaicevent.Helper.JsonHelper;
import com.archaic.archaicevent.Helper.PlayerData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

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
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        toggleSpy(server, sender, args);
    }

    private void toggleSpy(MinecraftServer server, ICommandSender sender, String[] args){
        PlayerData playerData = JsonHelper.getPlayerDataByName(sender.getDisplayName().getUnformattedText(), ArchaicEvent.playerDataFile);
        if (playerData.isSpyToggled()){
            playerData.setSpyToggled(true);
            return;
        }
        playerData.setSpyToggled(false);
    }
}
