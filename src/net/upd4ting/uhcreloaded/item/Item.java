package net.upd4ting.uhcreloaded.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.upd4ting.uhcreloaded.util.UtilItem;

public abstract class Item implements Cloneable {
	private static Map<UUID, List<Item>> playerItems = new HashMap<>();
	private static Integer ids = 0;
	
	private Integer id;
	private ItemStack item;
	protected Boolean noClickEvent;
	
	public Item(String name, Material mat, String... lores) {
		this.item = UtilItem.create(name, mat, (byte)0, Arrays.asList(lores));
		this.id = ids++;
		this.noClickEvent = false;
	}
	 
	public abstract void use(Player p, ItemAction action);
	
	public void give(Player p, Integer slot) {
		p.getInventory().setItem(slot, item);
		p.updateInventory();
		
		List<Item> items = playerItems.getOrDefault(p.getUniqueId(), new ArrayList<>());
		items.add(this);
		playerItems.put(p.getUniqueId(), items);
	}
	
	public void changeItem(ItemStack i) {
		for (UUID p : playerItems.keySet())
			changeItem(Bukkit.getPlayer(p), i);
	}
	
	public void changeItem(Player p, ItemStack i) {
		Boolean hasGot = false;
		
		for (Item it : playerItems.getOrDefault(p.getUniqueId(), new ArrayList<>()))
			hasGot = it.equals(this);
		
		if (!hasGot)
			return;
		
		Inventory inv = p.getInventory();
		Integer slot = inv.first(item);
		p.getInventory().removeItem(item);
		item = i;
		p.getInventory().setItem(slot, item);
		p.updateInventory();
	}
	
	@Override
	public Item clone() {
		return null;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Item)) return false;
		
		Item it = (Item) o;
		
		return this.id == it.id;
	}
	
	public ItemStack getItemClone() {
		return item.clone();
	}
		
	public static Item getItem(Player p, ItemStack i) {
		for (Item it : playerItems.getOrDefault(p.getUniqueId(), new ArrayList<>()))
			if (it.item.isSimilar(i))
				return it;
		return null;
	}
	
	public static void clear(Player p) {
		playerItems.remove(p.getUniqueId());
	}
}
