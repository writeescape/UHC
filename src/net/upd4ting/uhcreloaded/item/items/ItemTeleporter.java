package net.upd4ting.uhcreloaded.item.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import net.upd4ting.uhcreloaded.inventory.inv.InvTeleport;
import net.upd4ting.uhcreloaded.item.Item;
import net.upd4ting.uhcreloaded.item.ItemAction;

public class ItemTeleporter extends Item {

	public ItemTeleporter() {
		super(ChatColor.GOLD + "Teleporter", Material.COMPASS);
		
	}

	@Override
	public void use(Player p, ItemAction action) {
		new InvTeleport(p).open();
	}
	
	@Override
	public Item clone() {
		return new ItemTeleporter();
	}
}
