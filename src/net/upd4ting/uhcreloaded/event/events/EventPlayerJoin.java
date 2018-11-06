package net.upd4ting.uhcreloaded.event.events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.upd4ting.uhcreloaded.Countdown;
import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.board.Board;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.configuration.configs.MainConfig;
import net.upd4ting.uhcreloaded.configuration.configs.TeamConfig;
import net.upd4ting.uhcreloaded.team.Team;
import net.upd4ting.uhcreloaded.util.Util;

public class EventPlayerJoin implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		
		Player player = e.getPlayer();
		final LangConfig config = UHCReloaded.getLangConfiguration();
		TeamConfig teamConfig = UHCReloaded.getTeamConfiguration();
		final MainConfig mainConfig = UHCReloaded.getMainConfiguration();
		final Game game = UHCReloaded.getGame();
		
		// On gèrer les différents cas de join !
		
		if (!player.hasMetadata("joindata"))
			contactDevelopper(player);
		
		String data = player.getMetadata("joindata").get(0).asString();
		game.join(player, data);
		
		if (UHCReloaded.getBoardConfiguration().isEnabled())
			Board.start(player);
		
		if (data.equals("normal")) {
			e.setJoinMessage(null);
			Util.sendConfigBroadcast(config.getJoin().replace("%p", player.getName())
					.replace("%current", ""+game.getPlaying().size()).replace("%max", ""+ mainConfig.getMaxPlayer()));
			
			player.setGameMode(GameMode.ADVENTURE);
			game.giveItemWaiting(player);
			
			if (game.getSpawn() != null)
				player.teleport(game.getSpawn());
			
			// On regarde pour le timer
			if (Countdown.started == false && mainConfig.getMinPlayer() <= game.getPlaying().size())
				new Countdown();
			
		} else if (data.equals("spectator")) {
			game.setSpectator(player);
		} else if (data.equals("rejoin")) {
			e.setJoinMessage(null);
			Util.sendConfigBroadcast(config.getRejoin().replace("%p", player.getName()));
			
			if (!teamConfig.isGUIEnabled())
				Team.setNameTag(player);
		}
		else
			contactDevelopper(player);
	}
	
	private void contactDevelopper(Player p) {
		p.kickPlayer(ChatColor.RED + "Please contact the admin server to tell that the plugin have got a rupture, the admin must contact Upd4ting (developper)");
	}
}
