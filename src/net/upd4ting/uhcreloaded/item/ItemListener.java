package net.upd4ting.uhcreloaded.item;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getAction() != Action.PHYSICAL) {
			ItemStack inHand = p.getItemInHand();
			
			if (inHand != null) {
				Item item = Item.getItem(p, inHand);
				
				if (item != null) {
					ItemAction action = e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK ? ItemAction.RIGHT : ItemAction.LEFT;
					item.use(p, action);
					
					if (e.getAction() != Action.LEFT_CLICK_BLOCK) // Pour qu'on puisse casser des block...
						e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack is = e.getCurrentItem();
		ClickType c = e.getClick();
		
		if (c != ClickType.LEFT && c != ClickType.RIGHT && c != ClickType.SHIFT_LEFT && c != ClickType.SHIFT_RIGHT) return;
		
		if (is != null) {
			Item item = Item.getItem(p, is);
			
			if (item != null && !item.noClickEvent) {
				ItemAction action = c == ClickType.LEFT || c == ClickType.SHIFT_LEFT ? ItemAction.LEFT : ItemAction.RIGHT;
				item.use(p, action);
				e.setCancelled(true);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		
		ItemStack inHand = p.getItemInHand();
		
		if (inHand != null) {
			Item item = Item.getItem(p, inHand);
			
			if (item != null) {
				e.setCancelled(true);
			}
		}
	}
}
