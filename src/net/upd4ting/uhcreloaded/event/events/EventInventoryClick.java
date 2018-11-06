package net.upd4ting.uhcreloaded.event.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCReloaded;

public class EventInventoryClick implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Game game = UHCReloaded.getGame();
		
		if (game.getSpectators().contains(p) && e.getClickedInventory() != null && e.getClickedInventory().getHolder() != null && !(e.getClickedInventory().getHolder() instanceof Player)) // Si ce n'est pas l'inventaire d'un joeuur il peut pas cliqué et comme il peut pas regarder l'inventaire d'un autre joueur il pourra pas lui donner du stuff
			e.setCancelled(true);
	}
}
