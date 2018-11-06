package net.upd4ting.uhcreloaded.inventory;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Inventory {
	public static HashMap<UUID, Inventory> currentInventory = new HashMap<>();
	
	protected String title;
	protected Integer size;
	protected Player player;
	protected Boolean autoRefresh;
	private InventoryItem[] items;
	
	public Inventory(String title, Integer size, Player player, Boolean autoRefresh){
		this.title = title;
		this.size = size;
		this.player = player;
		this.autoRefresh = autoRefresh;
		this.items = new InventoryItem[size];
	}
	
	public abstract void init();
	
	public void open(){
		init();
		refresh(false);
	}
	
	public void refresh(Boolean refresh){
		if(refresh) {
			init();
		}
		
		ItemStack[] is = new ItemStack[items.length];
		
		if (refresh)
			is = player.getOpenInventory().getTopInventory().getContents();
		
		//Ajout des items
		for(int i = 0; i <= items.length - 1; i++){
			if(items[i] == null) continue;
			is[i] = items[i].getItem();
		}
		
		if(!refresh){
			org.bukkit.inventory.Inventory inv = Bukkit.createInventory(null, size, title);
			inv.setContents(is);
			player.openInventory(inv);
		}else{
			org.bukkit.inventory.Inventory viewedInv = player.getOpenInventory().getTopInventory();
			viewedInv.setContents(is);
			player.updateInventory();
		}
		
		currentInventory.put(player.getUniqueId(), this);
	}
	
	//Ajout d'un item
	public void addItem(Integer pos, InventoryItem item){ 
		items[pos] = item;
	}
	
	public InventoryItem[] getItems(){
		return items;
	}
}
