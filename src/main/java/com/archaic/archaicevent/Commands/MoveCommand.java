package com.archaic.archaicevent.Commands;

import com.archaic.archaicevent.ArchaicEvent;
import com.archaic.archaicevent.Helper.*;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MoveCommand extends CommandBase {
    private static final int COOLDOWN_HOURS = 6;
    private static final int COOLDOWN_MINUTES = 0;
    private static final int PLACEMENT_MINUTES = 30;

    private BeaconManager beaconManager = new BeaconManager();

    @Override
    public String getName() {
        return "move";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/archaic move - Move the team beacon";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        moveBeacon(server, sender, args);
    }

    private void moveBeacon(MinecraftServer server, ICommandSender sender, String[] args) {
        PlayerData playerData = JsonHelper.getPlayerDataByName(sender.getDisplayName().getUnformattedText(), ArchaicEvent.playerDataFile);

        if (!playerData.inTeam()) {
            sender.sendMessage(new TextComponentString("You must be in a team to do this!"));
            return;
        }

        TeamData teamData = JsonHelper.getTeamByMemberName(playerData.getPlayerName(), ArchaicEvent.teamDatafile);
        BeaconData beaconData = teamData.getBeacon();

        if (teamData.getOwner() != playerData) {
            sender.sendMessage(new TextComponentString("Only the team owner can move the beacon."));
            return;
        }

        if (beaconData == null) {
            sender.sendMessage(new TextComponentString(beaconData.getTeamName() + " does not have a beacon placed"));
            return;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime lastMoved = beaconData.getLastMoved();

        if (!onCooldown(sender, lastMoved, currentTime)) {
            teamData.setPreviousbeacon(beaconData);
            teamData.setBeacon(null);

            beaconManager.deleteBeacon(beaconData);
            beaconManager.giveBeacon(sender);

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.schedule(validatePlacement(sender, teamData), PLACEMENT_MINUTES, TimeUnit.MINUTES);

            sender.sendMessage(new TextComponentString("You have " + PLACEMENT_MINUTES + " minutes to place the beacon!"));
        }
    }

    private boolean onCooldown(ICommandSender sender, LocalDateTime lastMoved, LocalDateTime currentTime) {
        if (lastMoved != null && Duration.between(lastMoved, currentTime).toHours() < COOLDOWN_HOURS) {
            Duration remainingTime = Duration.ofHours(COOLDOWN_HOURS).minus(Duration.between(lastMoved, currentTime));
            long remainingHours = remainingTime.toHours();
            long remainingMinutes = remainingTime.toMinutes() - (remainingHours * 60);

            // Format and print the result
            if (remainingHours > 0) {
                sender.sendMessage(new TextComponentString("Moving the beacon is on cooldown for " + remainingHours + " hours and " + remainingMinutes + " minutes."));
            } else {
                sender.sendMessage(new TextComponentString("Moving the beacon is on cooldown for " + remainingMinutes + " minutes."));
            }
            return true;
        }
        return false;
    }

    private Runnable validatePlacement(ICommandSender sender, TeamData teamData) {
        return () -> {
            if (teamData.getBeacon() == null) {
                teamData.setBeacon(teamData.getPreviousbeacon());
                beaconManager.placeBeacon(teamData.getBeacon());
                beaconManager.removeBeacon(sender);
                sender.sendMessage(new TextComponentString("You have failed to place the beacon in time. It has been restored to the previous location."));
            }
        };
    }
}   