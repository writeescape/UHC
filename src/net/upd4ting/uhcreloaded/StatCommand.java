package net.upd4ting.uhcreloaded;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;

public class StatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;

        Player p = (Player) sender;
        LangConfig config = UHCReloaded.getLangConfiguration();

        if (args.length == 1) {
            Player player = Bukkit.getPlayer(args[0]);

            if (player == null) {
                p.sendMessage(config.getStatCommandPlayerNotExist());
                return true;
            }

            UHCPlayer uhcp = UHCPlayer.instanceOf(player);

            for (String s : config.getStatCommand())
                p.sendMessage(s
                        .replaceAll("%name", player.getName())
                        .replaceAll("%kill", Integer.toString(uhcp.getKill()))
                        .replaceAll("%death", Integer.toString(uhcp.getDeath()))
                        .replaceAll("%win", Integer.toString(uhcp.getWin()))
                        .replaceAll("%lose", Integer.toString(uhcp.getLose()))
                        .replaceAll("%gamePlayed", Integer.toString(uhcp.getGamePlayed())));
        } else
            p.sendMessage(config.getStatCommandUsage());

        return true;
    }

}
