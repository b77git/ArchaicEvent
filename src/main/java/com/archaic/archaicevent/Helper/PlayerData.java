package com.archaic.archaicevent.Helper;

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
}
