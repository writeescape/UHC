package net.upd4ting.uhcreloaded.item.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.inventory.inv.InvKit;
import net.upd4ting.uhcreloaded.item.Item;
import net.upd4ting.uhcreloaded.item.ItemAction;

public class ItemKit extends Item {

	@SuppressWarnings("deprecation")
	public ItemKit() {
		super(UHCReloaded.getLangConfiguration().getKitNameItem(), Material.getMaterial(UHCReloaded.getKitConfiguration().getItemId()));
	}

	@Override
	public void use(Player p, ItemAction action) {
		new InvKit(p).open();
	}
	
	
	@Override
	public Item clone() {
		return new ItemKit();
	}
}
