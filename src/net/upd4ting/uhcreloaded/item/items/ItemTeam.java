package net.upd4ting.uhcreloaded.item.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.inventory.inv.InvTeam;
import net.upd4ting.uhcreloaded.item.Item;
import net.upd4ting.uhcreloaded.item.ItemAction;

public class ItemTeam extends Item {

	@SuppressWarnings("deprecation")
	public ItemTeam() {
		super(UHCReloaded.getLangConfiguration().getTeamNameItem(), Material.getMaterial(UHCReloaded.getTeamConfiguration().getGuiItemID()));
	}

	@Override
	public void use(Player p, ItemAction action) {
		new InvTeam(p).open();
	}
	
	
	@Override
	public Item clone() {
		return new ItemTeam();
	}
}
