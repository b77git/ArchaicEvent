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
}
