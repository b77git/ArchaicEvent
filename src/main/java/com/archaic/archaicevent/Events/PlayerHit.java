package com.archaic.archaicevent.Events;

import com.archaic.archaicevent.ArchaicEvent;
import com.archaic.archaicevent.Helper.JsonHelper;
import com.archaic.archaicevent.Helper.PlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.time.LocalDateTime;

public class PlayerHit {
    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            PlayerData playerData = JsonHelper.getPlayerDataByName(player.getDisplayName().getUnformattedText(), ArchaicEvent.playerDataFile);
            LocalDateTime currentTime = LocalDateTime.now();
            playerData.setLastHit(currentTime);
        }
    }
}