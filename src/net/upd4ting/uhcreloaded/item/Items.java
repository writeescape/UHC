package net.upd4ting.uhcreloaded.item;

import org.bukkit.entity.Player;

import net.upd4ting.uhcreloaded.item.items.ItemBed;
import net.upd4ting.uhcreloaded.item.items.ItemKit;
import net.upd4ting.uhcreloaded.item.items.ItemTeam;
import net.upd4ting.uhcreloaded.item.items.ItemTeleporter;

public enum Items {
	
	TEAM(new ItemTeam()),
	KIT(new ItemKit()),
	TELEPORTER(new ItemTeleporter()),
	BED(new ItemBed());
	
	private Item self;
	
	private Items(Item self) {
		this.self = self;
	}
	
	public void give(Player p, Integer slot) {
		self.clone().give(p, slot);
	}
}
