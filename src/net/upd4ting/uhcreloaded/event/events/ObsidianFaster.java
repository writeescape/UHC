package net.upd4ting.uhcreloaded.event.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.upd4ting.uhcreloaded.UHCReloaded;

public class ObsidianFaster implements Listener {

	@EventHandler
	public void onBeginBreak(BlockDamageEvent event) {
		if (UHCReloaded.getGame().isLimited(event.getPlayer())) {
			event.setCancelled(true);
			return;
		}
		
		event.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);
		if (event.getBlock().getType() == Material.OBSIDIAN)
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20000, 2, true, true));
		else
			event.getPlayer().removePotionEffect(PotionEffectType.FAST_DIGGING);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getPlayer() == null)
			return; // Peut être causé par le tree cutter
		
		event.getPlayer().removePotionEffect(PotionEffectType.FAST_DIGGING);
	}

}