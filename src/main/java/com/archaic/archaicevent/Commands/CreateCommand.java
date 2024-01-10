package com.archaic.archaicevent.Commands;

import com.archaic.archaicevent.ArchaicEvent;
import com.archaic.archaicevent.Helper.JsonHelper;
import com.archaic.archaicevent.Helper.PlayerData;
import com.archaic.archaicevent.Helper.TeamData;
import ibxm.Player;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.material.Material;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.Arrays;

public class CreateCommand extends CommandBase {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/archaic create - Create a team";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(new TextComponentString("Please include a team name."));
            return;
        }

        createTeam(args, sender);
    }

    public void createTeam(String[] args, ICommandSender sender){
        String teamName = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        PlayerData owner = JsonHelper.getPlayerDataByName(sender.getDisplayName().getUnformattedText(), ArchaicEvent.playerDataFile);

        if (!validateTeamName(teamName)) {
            sender.sendMessage(new TextComponentString("Invalid team name."));
            return;
        }

        if (owner.inTeam()){
            sender.sendMessage(new TextComponentString("You are already in a team."));
            return;
        }

        TeamData teamData = new TeamData(teamName, owner);

        if (teamAlreadyExists(teamData)) {
            sender.sendMessage(new TextComponentString("A team with this name already exists."));
            return;
        }

        JsonHelper.addTeamDataToFile(teamData, ArchaicEvent.teamDatafile);
        giveBeacon(sender);
        sender.sendMessage(new TextComponentString("The team " + teamName + " has been created."));
    }

    private boolean validateTeamName(String teamName) {
        return !teamName.isEmpty();
    }

    private boolean teamAlreadyExists(TeamData teamData) {
        return JsonHelper.containsTeamData(JsonHelper.readExistingTeamDataFromFile(ArchaicEvent.teamDatafile), teamData);
    }

    private void giveBeacon(ICommandSender sender){
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
}
