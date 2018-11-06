package net.upd4ting.uhcreloaded.event.events;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import net.upd4ting.uhcreloaded.inventory.Inventory;
import net.upd4ting.uhcreloaded.inventory.InventoryItem.ActionItem;

public class EventInventory implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){	
		if(e.getCurrentItem() == null)
			return;
		
		Inventory current = null;

		if(Inventory.currentInventory.containsKey(e.getWhoClicked().getUniqueId())){
			current = Inventory.currentInventory.get(e.getWhoClicked().getUniqueId());
		}
		
		if(e.getSlot() == -1 || e.getCurrentItem() == null) 
			return;
		
		if(current != null && e.getClickedInventory() == e.getWhoClicked().getOpenInventory().getTopInventory()) {
			if(current.getItems()[e.getSlot()] != null){
				ActionItem action = current.getItems()[e.getSlot()].getAction();
				if (action != null)
					action.run(e);
			}

			e.setCancelled(true);
		}
	}

	@EventHandler
	public void closeInventory(InventoryCloseEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();

		if(Inventory.currentInventory.containsKey(uuid)) {
			Inventory.currentInventory.remove(uuid);
		}
	}
}
