package net.upd4ting.uhcreloaded.event.events;

import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import net.upd4ting.uhcreloaded.OrePopulator;
import net.upd4ting.uhcreloaded.UHCReloaded;

public class EventWorldInit implements Listener {
	
	@EventHandler
	public void onWorldInit(WorldInitEvent e) {
		if (UHCReloaded.getGenerationConfiguration().isRuleEnabled() && e.getWorld().getEnvironment() == Environment.NORMAL)
			e.getWorld().getPopulators().add(new OrePopulator());
	}
}
