package com.archaic.archaicevent;

import com.archaic.archaicevent.Helper.PlayerData;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.io.File;

import static com.archaic.archaicevent.Helper.JsonHelper.addPlayerDataToFile;

public class ServersideHandlers {

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        String playerName = event.player.getDisplayNameString();
        String playerUUID = event.player.getUniqueID().toString();

        PlayerData newPlayerData = new PlayerData(playerName, playerUUID);

        File dataFile = new File(ArchaicEvent.datafolderDir, "data.json");

        addPlayerDataToFile(newPlayerData, dataFile);

        // Continue with other server-side logic
        event.player.sendMessage(new TextComponentString("Welcome to the server!"));
    }
}
