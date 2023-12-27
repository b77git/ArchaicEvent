package com.archaic.archaicevent;

import com.archaic.archaicevent.Helper.PlayerData;
import com.mojang.realmsclient.gui.ChatFormatting;
import ibxm.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ServersideHandlers {

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        File dataFile = new File(ArchaicEvent.datafolderDir, "data.json");
        try (FileWriter writer = new FileWriter(dataFile)) {
            EntityPlayer player = event.player;

            String name = ChatFormatting.stripFormatting(player.getDisplayNameString());
            String uuid = player.getUniqueID().toString();
            PlayerData data = new PlayerData(name, uuid);

            ArchaicEvent.gson.toJson(data, writer);
        } catch (IOException e){
            ArchaicEvent.logger.info("An error writing to the file has occured: " + e);
        }
    }
}
