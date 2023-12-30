package com.archaic.archaicevent.Helper;

import com.archaic.archaicevent.ArchaicEvent;
import com.google.gson.annotations.Expose;

import java.util.List;

import static com.archaic.archaicevent.Helper.JsonHelper.readExistingPlayerDataFromFile;
import static com.archaic.archaicevent.Helper.JsonHelper.updatePlayerDataInFile;


public class PlayerData {
    @Expose  private final String username;
    @Expose  private final String uuid;
    @Expose  private boolean alive;
    @Expose  private boolean spyToggled;
    @Expose private boolean inTeam;

    public PlayerData (String username, String uuid) {
        this.username = username;
        this.uuid = uuid;
        this.alive = true;
        this.inTeam = false;
        this.spyToggled = false;
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
}
