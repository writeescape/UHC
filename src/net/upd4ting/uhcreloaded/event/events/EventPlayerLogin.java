package net.upd4ting.uhcreloaded.event.events;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.metadata.FixedMetadataValue;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCPlayer;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.configuration.configs.MainConfig;
import net.upd4ting.uhcreloaded.util.Util;
import net.upd4ting.uhcreloaded.util.Util.ActionMessage;

public class EventPlayerLogin implements Listener {
	
	public static HashMap<UUID, Long> timeQuit = new HashMap<>();
	
	@EventHandler
	public void onLogin(final PlayerLoginEvent e) {
		Player player = e.getPlayer();
		MainConfig config = UHCReloaded.getMainConfiguration();
		final LangConfig langConfig = UHCReloaded.getLangConfiguration();
		Game game = UHCReloaded.getGame();
		
		ActionMessage other = new ActionMessage() {
			@Override
			public void run() {
				kick(e, "");
			}
		};
		
		if (game.getAll().size() >= config.getMaxPlayer() && !player.hasPermission(config.getBypassMax()))
			Util.sendActionConfigMessage(langConfig.getKickGameFull(), new ActionMessage() {
				@Override
				public void run() {
					kick(e, langConfig.getKickGameFull());
				}
			}, other);
		else if (game.isLoading())
			Util.sendActionConfigMessage(langConfig.getKickLoading(), new ActionMessage() {
				@Override
				public void run() {
					kick(e, langConfig.getKickLoading().replaceAll("%progress",  game.getFillTask() == null ? "0.0" : game.getFillTask().getFormattedPercentageCompleted()));
				}
			}, other);
		else if (game.isWaiting()) {
			// On le fait rejoindre en normal
			player.setMetadata("joindata", new FixedMetadataValue(UHCReloaded.getInstance(), "normal"));
		} else if (game.isFinished())
			Util.sendActionConfigMessage(langConfig.getKickGameFinished(), new ActionMessage() {
				@Override
				public void run() {
					kick(e, langConfig.getKickGameFinished());
				}
			}, other);
		else if (game.isInGame()) {
			// Premier cas, il rejoin la partie
			if (config.isRejoinEnabled() && containsKey(player.getUniqueId())) {
				// On le fait rejoindre en rejoin
				player.setMetadata("joindata", new FixedMetadataValue(UHCReloaded.getInstance(), "rejoin"));
			}
			// Deuxième cas, il join en spectateur
			else if (config.isSpectatorJoinEnabled()) {
				// Check de la permission
				String perm = config.getSpectatorJoinPermission();
				if (perm.equals("none") || player.hasPermission(perm))
					player.setMetadata("joindata", new FixedMetadataValue(UHCReloaded.getInstance(), "spectator"));
				else
					Util.sendActionConfigMessage(langConfig.getKickGamestarted(), new ActionMessage() {
						@Override
						public void run() {
							kick(e, langConfig.getKickGamestarted());
						}
					}, other);
			}
			// Troisieme cas il est rejeté directement
			else
				Util.sendActionConfigMessage(langConfig.getKickGamestarted(), new ActionMessage() {
					@Override
					public void run() {
						kick(e, langConfig.getKickGamestarted());
					}
				}, other);
		}
	}
	
	private void kick(PlayerLoginEvent e, String message) {
		e.setKickMessage(message);
		e.setResult(Result.KICK_OTHER);
	}
	
	private Boolean containsKey(UUID uuid) {
		if (!timeQuit.containsKey(uuid))
			return false;
		MainConfig config = UHCReloaded.getMainConfiguration();
		Long time = timeQuit.remove(uuid);
		Long current = System.currentTimeMillis();
		UHCPlayer uhcp = UHCPlayer.instanceOf(uuid);
		
		if ((current - time) / 1000 < config.getRejoinTimeMax() && uhcp.getRejoinTime() < config.getRejoinMaxRejoin())
			return true;
		else {
			return false;
		}
	}
}
