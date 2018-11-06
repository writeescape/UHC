package net.upd4ting.uhcreloaded.item.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.item.Item;
import net.upd4ting.uhcreloaded.item.ItemAction;

public class ItemBed extends Item {

	@SuppressWarnings("deprecation")
	public ItemBed() {
		super(UHCReloaded.getLangConfiguration().getReturnTohub(), Material.getMaterial(UHCReloaded.getMainConfiguration().getLobbyItemId()));
	}

	@Override
	public void use(Player p, ItemAction action) {
		Game game = UHCReloaded.getGame();
		game.returnToHub(p);
	}
	
	
	@Override
	public Item clone() {
		return new ItemBed();
	}
}
