package com.popupmc.aprilfoolsday.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Toggle implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player))
            return onConsoleCommand(sender, args);
        else
            return onPlayerCommand((Player)sender, args);
    }

    public boolean onPlayerCommand(@NotNull Player sender, @NotNull String[] args) {
        if(args.length > 0) {
            return toogleOthers(sender, args[0]);
        }

        ToggleStatus val = tooglePlayer(sender);

        if(val == ToggleStatus.ENABLED) {
            sender.kickPlayer("April Fools Day has been enabled for you, please " +
                    "re-login.");
        }
        else {
            sender.kickPlayer("April Fools Day has been disabled for you, please " +
                    "re-login.");
        }

        return true;
    }

    public boolean onConsoleCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if(args.length > 0) {
            return toogleOthers(sender, args[0]);
        }
        else {
            sender.sendMessage(ChatColor.RED + "Error: you must specify a player if from the console");
            return false;
        }
    }

    public boolean toogleOthers(@NotNull CommandSender sender, String targPlayerName) {
        if(!sender.hasPermission("afd.toggle.others")) {
            sender.sendMessage(ChatColor.RED + "Error: You don't have permission to toggle others");
            return false;
        }

        Player targPlayer = Bukkit.getPlayer(targPlayerName);

        if(targPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Error: Player not found");
            return false;
        }
        else {
            ToggleStatus val = tooglePlayer(targPlayer);
            if(val == ToggleStatus.ENABLED) {
                sender.sendMessage(ChatColor.GOLD + "AFD has been enabled for " + targPlayerName);
                targPlayer.kickPlayer("April Fools Day has been enabled for you by " + sender.getName() + ", please " +
                        "re-login.");
            }
            else {
                sender.sendMessage(ChatColor.GOLD + "AFD has been disabled for " + targPlayerName);
                targPlayer.kickPlayer("April Fools Day has been disabled for you by " + sender.getName() + ", please " +
                        "re-login.");
            }

            return true;
        }
    }

    public static ToggleStatus tooglePlayer(Player player) {
        // Get UUID String of player
        String UUID = player.getUniqueId().toString();

        // Get value, defaulting to false
        boolean val = players.getOrDefault(UUID, false);

        // Set opposite value
        players.put(UUID, !val);

        // Return opposite value
        return (val) ? ToggleStatus.DISABLED : ToggleStatus.ENABLED;
    }

    // Return player status
    public static ToggleStatus getStatus(String UUID) {

        // If doesn't exist then return default value
        if(!players.containsKey(UUID))
            return ToggleStatus.DEFAULT;

        // Otherwise return enabled or disabled
        return (players.getOrDefault(UUID, false)) ? ToggleStatus.ENABLED : ToggleStatus.DISABLED;
    }

    // If a player is provided then we perform a more intelligent check
    public static boolean getStatus(@NotNull Player player) {

        // Get UUID String of player
        String UUID = player.getUniqueId().toString();

        // Get stored value
        ToggleStatus val = getStatus(UUID);

        // If default then we calculate a sufficient value
        if(val == ToggleStatus.DEFAULT) {
            // If not op and not bypass permission then enabled by default, otherwise disabled
            if(!player.isOp() && !player.hasPermission("afd.bypass"))
                val = defStatusForRegPlayers;
            else
                val = ToggleStatus.DISABLED;
        }

        // Return true if enabled, false otherwise
        return val == ToggleStatus.ENABLED;
    }

    // Associates a player UUID to a boolean indicating enabled status
    // A player that exists indicates an explcitly set value of true or false, often by the player
    // A player that doesn't exist indicates to use a defaul calculated value
    public static HashMap<String, Boolean> players = new HashMap<>();

    // Set this to disabled when outside April Fools Day to have it entirely disabled by default
    public static final ToggleStatus defStatusForRegPlayers = ToggleStatus.ENABLED;
}
