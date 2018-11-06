package net.upd4ting.uhcreloaded.event.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.configuration.configs.MainConfig;
import net.upd4ting.uhcreloaded.team.Team;
import net.upd4ting.uhcreloaded.util.Util;

public class EventPlayerMove implements Listener {
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Game game = UHCReloaded.getGame();
		MainConfig config = UHCReloaded.getMainConfiguration();
		LangConfig langConfig = UHCReloaded.getLangConfiguration();
		
		// Si game en waiting voir pour retp dans la schématique
		if (game.isWaiting() && game.getSpawn() != null) {
			if (p.getLocation().getY() <= 150){
				p.teleport(game.getSpawn());
				Util.sendConfigMessage(p, langConfig.getLobbyTp());
			}
		} else if (game.isInGame() && game.getSpectators().contains(p)) { // Check pour les spectateurs
			Team team = Team.getTeam(p);
			
			if (team == null) // Spectateur qui a rejoint apres donc pas de team donc il peut spec tout le monde
				return;
			
			Double minDistance = -1d;
			Player nearest = null;
			
			for (Player player : game.getPlaying()) {
				if (config.isSpectatorOnlyMates() && !team.getPlayers().contains(player))
					continue;
				if (!player.getWorld().getName().equals(p.getWorld().getName()))
					continue;
				
				Double d = player.getLocation().distance(p.getLocation());
				if (minDistance == -1 || d < minDistance) {
					minDistance = d;
					nearest = player;
				}
			}
			
			if (minDistance > config.getSpectatorDistance()) {
				p.teleport(nearest.getLocation().add(0,2,0));
				Util.sendConfigMessage(p, langConfig.getSpectatorTp());
			}
		}
	}
}
