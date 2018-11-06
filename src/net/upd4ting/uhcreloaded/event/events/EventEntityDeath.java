package net.upd4ting.uhcreloaded.event.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.LootConfig.Loot;
import net.upd4ting.uhcreloaded.configuration.configs.LootConfig.LootItem;

public class EventEntityDeath implements Listener{
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		Entity ent = e.getEntity();
		
		if (UHCReloaded.getLootConfiguration().isOverridingEntitiesLoot()) {
			
			EntityType type = ent.getType();
			if (UHCReloaded.getLootConfiguration().getEntitiesLoots().containsKey(type)) {
				Loot loot = UHCReloaded.getLootConfiguration().getEntitiesLoots().get(type);
				
				e.setDroppedExp(loot.getExp());
				e.getDrops().clear();
				
				for (LootItem item : loot.getLoots()) {
					ItemStack is = item.getLootItem();
					
					if (is != null)
						e.getDrops().add(is);
				}
			}
		}
	}
}
