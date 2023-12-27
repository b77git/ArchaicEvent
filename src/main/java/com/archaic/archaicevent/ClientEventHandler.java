package com.archaic.archaicevent;

import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

// Client-side event handler
public class ClientEventHandler {

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        // Check if it's a client-side event
        if (event.player.getEntityWorld().isRemote) {
            event.player.sendMessage(new TextComponentString(TextFormatting.RED + "This is a singleplayer world! Do not expect this mod to work as it is only intended for use on the Archaic Network event server."));
            return;
        }
        event.player.sendMessage(new TextComponentString("good"));
    }
}
