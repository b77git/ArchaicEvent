package com.archaic.archaicevent.Helper;

import com.archaic.archaicevent.ArchaicEvent;
import net.minecraft.dispenser.ILocation;

import java.util.ArrayList;
import java.util.List;

import static com.archaic.archaicevent.Helper.JsonHelper.updateTeamDataInFile;

public class TeamData {
    private String name;
    private PlayerData owner;
    private BeaconData beacon;
    private List<PlayerData> members;

    public TeamData(String name, PlayerData owner){
        this.name = name;
        this.owner = owner;
        this.beacon = null;
        this.members = new ArrayList<>();
        this.members.add(owner);
    }

    public String getTeamName() {
        return name;
    }

    public void addMember(PlayerData player){
        this.members.add(player);
        player.setInTeam(true);

        // Update the JSON to reflect this change
        updateTeamDataInFile(this, ArchaicEvent.teamDatafile);
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
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(PlayerData owner) {
        this.owner = owner;
    }
}
