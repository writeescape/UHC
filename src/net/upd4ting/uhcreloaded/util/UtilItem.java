package net.upd4ting.uhcreloaded.util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UtilItem {
	
	public static ItemStack create(String name, Material mat, byte data, List<String> lores) {
		ItemStack is = new ItemStack(mat, 1, data);

		ItemMeta isM = is.getItemMeta();
		isM.setDisplayName(name);

		isM.setLore(lores);

		is.setItemMeta(isM);

		return is;
	}
}
