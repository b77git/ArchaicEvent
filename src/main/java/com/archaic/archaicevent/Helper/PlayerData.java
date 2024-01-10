package com.archaic.archaicevent.Helper;

import com.archaic.archaicevent.ArchaicEvent;

import java.util.List;

import static com.archaic.archaicevent.Helper.JsonHelper.readExistingPlayerDataFromFile;
import static com.archaic.archaicevent.Helper.JsonHelper.updatePlayerDataInFile;


public class PlayerData {
    private final String username;
    private final String uuid;
    private boolean alive;
    private boolean spyToggled;
    private boolean inTeam;
    private int mobEvolution;

    public PlayerData (String username, String uuid) {
        this.username = username;
        this.uuid = uuid;
        this.alive = true;
        this.inTeam = false;
        this.spyToggled = false;
        this.mobEvolution = 1;
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

    public boolean inTeam() {
        return inTeam;
    }

    public boolean isSpyToggled() {
        return spyToggled;
    }

    public String getPlayerName(){
        return username;
    }

    public String getPlayerUUID(){
        return uuid;
    }

    public int getMobEvolution() {
        return mobEvolution;
    }

    public void setinTeam(boolean inTeam) {
        this.inTeam = inTeam;
        // Update the JSON to reflect this change
        updatePlayerDataInFile(this, ArchaicEvent.playerDataFile);
    }

    public void setAlive(boolean alive) {
        this.alive = alive;

        // Update the JSON to reflect this change
        updatePlayerDataInFile(this, ArchaicEvent.playerDataFile);
    }

    public void setSpyToggled(boolean spyToggled) {
        this.spyToggled = spyToggled;
        updatePlayerDataInFile(this, ArchaicEvent.playerDataFile);
    }

    public void setMobEvolution(int mobEvolution) {
        this.mobEvolution = mobEvolution;
    }
}
