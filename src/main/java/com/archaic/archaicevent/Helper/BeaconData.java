package com.archaic.archaicevent.Helper;

import java.util.Date;

public class BeaconData {
    private final String teamName;
    private final int x_loc, y_loc, z_loc, dim;
    private boolean broken;
    private final Date lastMoved;

    public BeaconData(String teamName, int x_loc, int y_loc, int z_loc, int dim){
        this.teamName = teamName;
        this.x_loc = x_loc;
        this.y_loc = y_loc;
        this.z_loc = z_loc;
        this.dim = dim;
        this.broken = false;
        this.lastMoved = null;
    }

    public class Coordinates {
        private final int x;
        private final int y;
        private final int z;
        private final int dim;

        public Coordinates(int x, int y, int z, int dim) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.dim = dim;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public int getDim() {
            return dim;
        }
    }

    public Coordinates getCoordinates() {
        return new Coordinates(x_loc, y_loc, z_loc, dim);
    }

    public String getTeamName() {
        return teamName;
    }

    public Date getLastMoved() {
        return lastMoved;
    }

    public int getDim() {
        return dim;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }
}
