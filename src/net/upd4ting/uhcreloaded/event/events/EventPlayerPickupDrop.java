package net.upd4ting.uhcreloaded.event.events;

import net.upd4ting.uhcreloaded.UHCReloaded;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class EventPlayerPickupDrop implements Listener {
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (UHCReloaded.getGame().isLimited(p))
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		if (UHCReloaded.getGame().isLimited(p))
			e.setCancelled(true);
	}
}
