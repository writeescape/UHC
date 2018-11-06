package net.upd4ting.uhcreloaded.event.events;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class EventPlayerItemPrepare implements Listener {
	@EventHandler
	public void onCraft(CraftItemEvent e) {
		if (isWood(e.getCurrentItem().getType()))
			e.setCurrentItem(enchantAndUpgrade(e.getCurrentItem()));
	}
	
	public Boolean isWood(Material type) {
		return type == Material.WOOD_AXE || type == Material.WOOD_PICKAXE 
				|| type == Material.WOOD_SPADE || type == Material.WOOD_SWORD;
	}
	
	public ItemStack enchantAndUpgrade(ItemStack item) {
		Material type = item.getType();
		
		if (type == Material.WOOD_AXE)
			item.setType(Material.STONE_AXE);
		else if (type == Material.WOOD_PICKAXE)
			item.setType(Material.STONE_PICKAXE);
		else if (type == Material.WOOD_SPADE)
			item.setType(Material.STONE_SPADE);
		else if (type == Material.WOOD_SWORD)
			item.setType(Material.STONE_SWORD);
		
		item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 2);
		return item;
	}
}