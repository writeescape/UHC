package net.upd4ting.uhcreloaded.event.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCReloaded;

public class EventBlockPlace implements Listener {
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Game game = UHCReloaded.getGame();
		Block b = e.getBlockPlaced();
		
		if (game.isLimited(e.getPlayer()) || (game.isInGame() && b.getType() == Material.SKULL_ITEM)) {
			e.setBuild(false);
			e.setCancelled(true);
		}
	}
}
