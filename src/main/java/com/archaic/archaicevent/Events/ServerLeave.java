package com.archaic.archaicevent.Events;

import com.archaic.archaicevent.ArchaicEvent;
import com.archaic.archaicevent.Helper.JsonHelper;
import com.archaic.archaicevent.Helper.PlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.time.Duration;
import java.time.LocalDateTime;

public class ServerLeave {
    private final int COMBAT_LOG_TIMER = 60;

    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        EntityPlayer player = event.player;
        PlayerData playerData = JsonHelper.getPlayerDataByName(player.getDisplayName().getUnformattedText(), ArchaicEvent.playerDataFile);
        LocalDateTime hitTime = playerData.getLastHit();
        LocalDateTime currentTime = LocalDateTime.now();

        if (hitTime == null) {
            return;
        }
        Duration duration = Duration.between(hitTime, currentTime);

        if (duration.getSeconds() <= COMBAT_LOG_TIMER){
            event.player.onDeath(null);
        }
    }
}
