package net.upd4ting.uhcreloaded.event.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;

public class EventServerPing implements Listener {
	@EventHandler
	public void onEventServerPing(final ServerListPingEvent e) {
		final LangConfig config = UHCReloaded.getLangConfiguration();
		final Game game = UHCReloaded.getGame();
		
		if (game.isLoading()) {
			e.setMotd(config.getMotdLoading().replaceAll("%progress", game.getFillTask() == null ? "0.0" : game.getFillTask().getFormattedPercentageCompleted()));
		}
		else if (game.isFinished())
			e.setMotd(config.getMotdFinished());
		else if (game.isInGame())
			e.setMotd(config.getMotdIngame());
		else
			e.setMotd(config.getMotdWaiting());
	}
}
