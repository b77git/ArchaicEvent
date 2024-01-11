package com.archaic.archaicevent.Helper;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

    public void placeBeacon(BeaconData beaconData) {
        BeaconData.Coordinates coords = beaconData.getCoordinates();
        BlockPos targetPos = new BlockPos(coords.getX(), coords.getY(), coords.getZ());
        WorldServer world = loadDimension(coords.getDim());

        if (world != null) {
            world.setBlockState(targetPos, Blocks.BEACON.getDefaultState());
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

    public void giveBeacon(ICommandSender sender){
        if (sender instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) sender;

            ItemStack beaconStack = new ItemStack(Blocks.BEACON);

            // Check if the player's inventory is not full
            if (!player.inventory.addItemStackToInventory(beaconStack)) {
                // If the inventory is full, drop the beacon at the player's location
                player.dropItem(beaconStack, false);
            }
        }
    }

    public void removeBeacon(ICommandSender sender) {
        if (sender instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) sender;

            // Check if the player has a beacon in their inventory
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);

                if (stack.getItem() == Item.getItemFromBlock(Blocks.BEACON)) {
                    // Remove the beacon from the player's inventory
                    player.inventory.removeStackFromSlot(i);
                }
            }
        }
    }
}
