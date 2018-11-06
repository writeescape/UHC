package net.upd4ting.uhcreloaded;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {
    public static void log(LogLevel level, String message) {
        if (message == null) return;

        switch (level) {
            case ERROR:
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4ERROR&8] &f" + message));
                break;
            case WARNING:
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6WARNING&8] &f" + message));
                break;
            case INFO:
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&eINFO&8] &f" + message));
                break;
            case SUCCESS:
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aSUCCESS&8] &f" + message));
                break;
        }
    }

    public enum LogLevel {ERROR, WARNING, INFO, SUCCESS}
}

