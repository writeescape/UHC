package net.upd4ting.uhcreloaded.inventory.inv;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.inventory.Inventory;
import net.upd4ting.uhcreloaded.inventory.InventoryItem;
import net.upd4ting.uhcreloaded.inventory.InventoryItem.ActionItem;

public class InvTeleport extends Inventory {

	public InvTeleport(Player player) {
		super(ChatColor.GOLD + "Teleporter", 54, player, true);
	}

	@Override
	public void init() {
		Integer index = 0;
		for (final Player p : UHCReloaded.getGame().getPlaying()) {
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwner(p.getName());
            meta.setDisplayName(ChatColor.GREEN + p.getName());
            skull.setItemMeta(meta);
            
            addItem(index, new InventoryItem(skull, new ActionItem() {

				@Override
				public void run(InventoryClickEvent event) {
					player.teleport(p);
				}
            	
            }));
            
            index++;
		}
	}

}
