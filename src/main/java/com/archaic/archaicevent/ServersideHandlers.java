package com.archaic.archaicevent;

import com.archaic.archaicevent.Helper.PlayerData;
import com.archaic.archaicevent.Helper.TeamData;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import static com.archaic.archaicevent.Helper.JsonHelper.addPlayerDataToFile;
import static com.archaic.archaicevent.Helper.JsonHelper.addTeamDataToFile;


public class ServersideHandlers {
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        String playerName = event.player.getDisplayNameString();
        String playerUUID = event.player.getUniqueID().toString();

        PlayerData newPlayerData = new PlayerData(playerName, playerUUID);

        PlayerData mockPlayer = new PlayerData("Notch","123");
        PlayerData mockPlayer1 = new PlayerData("asdasd","123123");
        PlayerData mockPlayer2 = new PlayerData("asdasdasdasdasda","123123123123");

        TeamData mockTeam = new TeamData("asd", mockPlayer1);
        TeamData mockTeam2 = new TeamData("asd", mockPlayer1);

        addPlayerDataToFile(newPlayerData, ArchaicEvent.playerDataFile);
        addPlayerDataToFile(mockPlayer1, ArchaicEvent.playerDataFile);
        addPlayerDataToFile(mockPlayer2, ArchaicEvent.playerDataFile);

        addTeamDataToFile(mockTeam, ArchaicEvent.teamDatafile);
        addTeamDataToFile(mockTeam2, ArchaicEvent.teamDatafile);

        // Continue with other server-side logic
        event.player.sendMessage(new TextComponentString("Welcome to the server!"));
    }
}
