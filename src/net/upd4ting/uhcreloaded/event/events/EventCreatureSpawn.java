package net.upd4ting.uhcreloaded.event.events;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.TimerConfig;


public class EventCreatureSpawn implements Listener {
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent e) {
		Location loc = e.getEntity().getLocation();
		TimerConfig config = UHCReloaded.getTimerConfiguration();
		
		if (Math.round(loc.getX()) > config.getWbSizeStart() || Math.round(loc.getZ()) > config.getWbSizeStart())
			e.setCancelled(true);
	}
}
