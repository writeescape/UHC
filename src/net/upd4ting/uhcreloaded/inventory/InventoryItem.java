package net.upd4ting.uhcreloaded.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryItem extends ItemStack{
	private ItemStack item;
	private ActionItem action;
	
	public InventoryItem(ItemStack item, ActionItem action){
		super(item);
		this.item = item;
		this.action = action;
	}
	
	public InventoryItem(ItemStack item, Boolean refreshable, ActionItem action) {
		super(item);
		this.item = item;
		this.action = action;
	}
	
	
	public ItemStack getItem() {
		return item;
	}
	
	public ActionItem getAction() {
		return action;
	}
	
	public interface ActionItem{
		public void run(InventoryClickEvent event);
	}
}
