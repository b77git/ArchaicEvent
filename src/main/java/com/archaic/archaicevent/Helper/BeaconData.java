package com.archaic.archaicevent.Helper;

public class BeaconData {
    private final String teamName;
    private final int x_loc, y_loc, z_loc;
    private final int dim;

    public BeaconData(String teamName, int x_loc, int y_loc, int z_loc, int dim){
        this.teamName = teamName;
        this.x_loc = x_loc;
        this.y_loc = y_loc;
        this.z_loc = z_loc;
        this.dim = dim;
    }
}
