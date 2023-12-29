package com.archaic.archaicevent.Commands;

import com.archaic.archaicevent.ArchaicEvent;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.List;

public class CommandBaseHandler extends CommandBase {

    @Override
    public String getName() {
        return "archaic";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Commands for the Archaic mod";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        ArchaicEvent.logger.info("Executing /archaic command");
        if (args.length == 0) {
            sender.sendMessage(new TextComponentString("Usage: /archaic <command>"));
            return;
        }

        String subCommand = args[0];

        // Check permissions and dispatch to the appropriate method
        if (subCommand.equals("move") || subCommand.equals("invite") || subCommand.equals("create") || subCommand.equals("teams")) {
            // Commands requiring permission level 0
            if (checkPermission(sender, 0)) {
                executePlayerCommand(server, sender, subCommand, args);
            }
        } else if (subCommand.equals("spy")) {
            // Command requiring operator (op) permission
            if (checkPermission(sender, 2)) {
                executeOpCommand(server, sender, subCommand, args);
            }
        } else {
            sender.sendMessage(new TextComponentString("Unknown command: " + subCommand));
        }
    }

    private boolean checkPermission(ICommandSender sender, int requiredPermissionLevel) {
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;

            ArchaicEvent.logger.info("Permission level: " + requiredPermissionLevel);
            ArchaicEvent.logger.info("Can use command: " + player.canUseCommand(requiredPermissionLevel, getName()));

            return player.canUseCommand(requiredPermissionLevel, getName());
        }
        return true;
    }

    private void executePlayerCommand(MinecraftServer server, ICommandSender sender, String subCommand, String[] args) {
        switch (subCommand) {
            case "move":
                new MoveCommand().execute(server, sender, args);
                break;
            case "invite":
                new InviteCommand().execute(server, sender, args);
                break;
            case "create":
                new CreateCommand().execute(server, sender, args);
                break;
            case "teams":
                new TeamsCommand().execute(server, sender, args);
                break;
            case "help":
                sender.sendMessage(new TextComponentString("Executing /archaic help"));
                break;
            default:
                sender.sendMessage(new TextComponentString("Unknown subcommand: " + subCommand));
        }
    }

    private void executeOpCommand(MinecraftServer server, ICommandSender sender, String subCommand, String[] args) {
        switch (subCommand) {
            case "spy":
                new SpyCommand().execute(server, sender, args);
                break;
            default:
                sender.sendMessage(new TextComponentString("Unknown subcommand: " + subCommand));
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        // Provide auto-complete suggestions based on the entered arguments
        if (args.length == 1) {
            // Suggestions for the first argument (sub-command)
            List<String> suggestions = getListOfStringsMatchingLastWord(args, "move", "invite", "create", "teams", "help");

            // Only add "spy" to suggestions if the player is an operator
            if (sender.canUseCommand(2, getName())) {
                suggestions.add("spy");
            }
            return suggestions;
        }

        // Default to an empty list if no suggestions are provided
        return super.getTabCompletions(server, sender, args, targetPos);
    }
}