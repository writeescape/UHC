package net.upd4ting.uhcreloaded.event.events;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.ChatConfig;
import net.upd4ting.uhcreloaded.configuration.configs.TeamConfig;
import net.upd4ting.uhcreloaded.team.Team;

public class EventPlayerAsyncChat implements Listener {
	
	enum RecipientType { TEAM, SPEC, ALL }
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Game game = UHCReloaded.getGame();
		ChatConfig chatConfig = UHCReloaded.getChatConfiguration();
		
		TeamConfig teamConfig = UHCReloaded.getTeamConfiguration();
		Player player = e.getPlayer();
		Team team = Team.getTeam(player);
		
		String prefix = "";
		String message = e.getMessage();
		
		// Set du prefix
		if (game.isInGame()) {
			// Spectateur
			if (game.getSpectators().contains(player)) {
				prefix = chatConfig.getPrefixSpectator();
				setRecipients(game, e, player.hasPermission("game.admin") ? RecipientType.ALL : RecipientType.SPEC);
			}
			else {
				if (teamConfig.isEnabled()) {
					if (team == null || team.getPlayers().size() == 1 || message.startsWith(chatConfig.getPrefixMessageGlobal())) {
						if (message.startsWith(chatConfig.getPrefixMessageGlobal()))
							message = message.replaceFirst(chatConfig.getPrefixMessageGlobal(), "");
						prefix = chatConfig.getPrefixAll();
						setRecipients(game, e, RecipientType.ALL);
					} else {
						prefix = chatConfig.getPrefixTeam();
						setRecipients(game, e, RecipientType.TEAM);
					}
				} else {
					prefix = chatConfig.getPrefixAll();
					setRecipients(game, e, RecipientType.ALL);
				}
			}
		}
		
		if (!chatConfig.isFormatingEnabled())
			return;
		
		String format = chatConfig.getFormat().replace("%message", "%s")
				.replace("%name", "%s").replace("%prefixteam", team == null || team.getPrefix() == null ? "" : team.getPrefix())
				.replace("%prefix", prefix);
		e.setMessage(message);
		e.setFormat(format);
	}
	
	private void setRecipients(Game game, AsyncPlayerChatEvent e, RecipientType type) {
		if (type == RecipientType.SPEC) {
			for (Player current : new ArrayList<>(e.getRecipients()))
				if (!game.getSpectators().contains(current) && !current.hasPermission("game.admin"))
					e.getRecipients().remove(current);
		} else if (type == RecipientType.TEAM) {
			Team t = Team.getTeam(e.getPlayer());
			
			for (Player current : new ArrayList<>(e.getRecipients()))
				if (!t.getPlayers().contains(current))
					e.getRecipients().remove(current);
		}
	}
}
