package com.archaic.archaicevent.Events;

import com.archaic.archaicevent.ArchaicEvent;
import com.archaic.archaicevent.Helper.PlayerData;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import static com.archaic.archaicevent.Helper.JsonHelper.addPlayerDataToFile;


public class ServerJoin {
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        String playerName = event.player.getDisplayNameString();
        String playerUUID = event.player.getUniqueID().toString();

        PlayerData newPlayerData = new PlayerData(playerName, playerUUID);
        addPlayerDataToFile(newPlayerData, ArchaicEvent.playerDataFile);

        // Continue with other server-side logic
        event.player.sendMessage(new TextComponentString("Welcome to the server!"));
    }
}
