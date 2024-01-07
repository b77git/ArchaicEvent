package com.archaic.archaicevent.Helper;

import com.archaic.archaicevent.ArchaicEvent;

import java.util.ArrayList;
import java.util.List;

import static com.archaic.archaicevent.Helper.JsonHelper.updateTeamDataInFile;

public class TeamData {
    private String name;
    private PlayerData owner;
    private BeaconData beacon;
    private List<PlayerData> members;
    private List<PlayerData> pendingInvites;


    public TeamData(String name, PlayerData owner){
        this.name = name;
        this.owner = owner;
        this.beacon = null;
        this.members = new ArrayList<>();
        this.pendingInvites = new ArrayList<>();

        this.members.add(owner);
        owner.setinTeam(true);
    }

    public void addMember(PlayerData player){
        this.members.add(player);
        player.setinTeam(true);

        // Update the JSON to reflect this change
        updateTeamDataInFile(this, ArchaicEvent.teamDatafile);
    }

    public void addInvite(PlayerData player){
        this.pendingInvites.add(player);
    }

    public String getName() {
        return name;
    }

    public BeaconData getBeacon() {
        return beacon;
    }

    public PlayerData getOwner() {
        return owner;
    }

    public List<PlayerData> getMembers() {
        return members;
    }

    public List<PlayerData> getPendingInvites() {
        return pendingInvites;
    }

    public void setBeacon(BeaconData beacon) {
        this.beacon = beacon;
        updateTeamDataInFile(this, ArchaicEvent.teamDatafile);
    }

    public void setName(String name) {
        this.name = name;
        updateTeamDataInFile(this, ArchaicEvent.teamDatafile);
    }

    public void setOwner(PlayerData owner) {
        this.owner = owner;
        updateTeamDataInFile(this, ArchaicEvent.teamDatafile);
    }
}
