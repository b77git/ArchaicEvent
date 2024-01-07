package com.archaic.archaicevent.Helper;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class BeaconManager {

    public void deleteBeacon(BeaconData beaconData) {
        BeaconData.Coordinates coords = beaconData.getCoordinates();
        BlockPos targetPos = new BlockPos(coords.getX(), coords.getY(), coords.getZ());
        WorldServer world = loadDimension(coords.getDim());

        if (world != null) {
            world.setBlockToAir(targetPos);
        }
    }

    public WorldServer loadDimension(int dimensionId) {
        if (!DimensionManager.isDimensionRegistered(dimensionId)) {
            return null;
        }

        WorldServer worldServer = DimensionManager.getWorld(dimensionId);

        // If the dimension is not loaded, attempt to load it
        if (worldServer == null) {
            DimensionManager.initDimension(dimensionId);
            worldServer = DimensionManager.getWorld(dimensionId);
        }

        return worldServer;
    }
}
