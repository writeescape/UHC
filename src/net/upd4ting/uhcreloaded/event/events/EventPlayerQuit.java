package net.upd4ting.uhcreloaded.event.events;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.board.Board;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.configuration.configs.MainConfig;
import net.upd4ting.uhcreloaded.team.Team;
import net.upd4ting.uhcreloaded.util.Util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventPlayerQuit implements Listener {
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		final Player player = e.getPlayer();
		final LangConfig config = UHCReloaded.getLangConfiguration();
		MainConfig mainConfig = UHCReloaded.getMainConfiguration();
		Game game = UHCReloaded.getGame();
		
		e.setQuitMessage(null);
		
		// Gestion du reconnect
		if (game.isInGame() && game.getPlaying().contains(player))
			EventPlayerLogin.timeQuit.put(player.getUniqueId(), System.currentTimeMillis());
		
		// Gestion du quit
		game.leave(player);
		
		Board.leave(player);
		
		if (game.isInGame()) {
			e.setQuitMessage(null);
			Util.sendConfigBroadcast(config.getLeaveIngame().replace("%p", player.getName()));
			game.checkWin();
		} else if (game.isWaiting()) {
			e.setQuitMessage(null);
			// Check pour l'enlevé de sa team du coup
			Team.leaveWaiting(player);
			Util.sendConfigBroadcast(config.getLeave().replace("%p", player.getName())
					.replace("%current", ""+game.getPlaying().size())
					.replace("%max", ""+ mainConfig.getMaxPlayer()));
		}
	}
}
