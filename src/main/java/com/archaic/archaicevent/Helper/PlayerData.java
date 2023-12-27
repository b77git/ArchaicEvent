package com.archaic.archaicevent.Helper;

import com.archaic.archaicevent.ArchaicEvent;

import java.util.List;

import static com.archaic.archaicevent.Helper.JsonHelper.readExistingPlayerDataFromFile;
import static com.archaic.archaicevent.Helper.JsonHelper.updatePlayerDataInFile;


public class PlayerData {
    private final String username;
    private final String uuid;
    private boolean alive;
    private boolean inTeam;

    public PlayerData (String username, String uuid){
        this.username = username;
        this.uuid = uuid;
        this.alive = true;
        this.inTeam = false;
    }

    public static PlayerData getPlayerData(String playerName){
        List<PlayerData> existingData = readExistingPlayerDataFromFile(ArchaicEvent.playerDataFile);

        for (PlayerData data : existingData) {
            if (data.getPlayerName().equals(playerName)) {
                return data; // Match found
            }
        }
        return null; // No match found
    }

    public String getPlayerName(){
        return username;
    }

    public String getPlayerUUID(){
        return uuid;
    }

    public void setInTeam(boolean inTeam) {
        this.inTeam = inTeam;

        // Update the JSON to reflect this change
        updatePlayerDataInFile(this, ArchaicEvent.playerDataFile);
    }

    public void setAlive(boolean alive) {
        this.alive = alive;

        // Update the JSON to reflect this change
        updatePlayerDataInFile(this, ArchaicEvent.playerDataFile);
    }
}
