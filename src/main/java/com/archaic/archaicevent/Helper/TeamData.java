package com.archaic.archaicevent.Helper;

import com.archaic.archaicevent.ArchaicEvent;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.archaic.archaicevent.Helper.JsonHelper.updateTeamDataInFile;

public class TeamData {
    @Expose  private String name;
    @Expose  private PlayerData owner;
    @Expose  private BeaconData beacon;
    @Expose  private List<PlayerData> members;
    @Expose  private List<PlayerData> pendingInvites;


    public TeamData(String name, PlayerData owner){
        this.name = name;
        this.owner = owner;
        this.beacon = null;
        this.members = new ArrayList<>();
        this.pendingInvites = new ArrayList<>();

        this.members.add(owner);
        owner.setinTeam(true);
    }

    public String getTeamName() {
        return name;
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
