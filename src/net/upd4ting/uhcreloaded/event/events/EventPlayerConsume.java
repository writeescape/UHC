package net.upd4ting.uhcreloaded.event.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.MainConfig;

public class EventPlayerConsume implements Listener {
	
	@EventHandler
	public void onPlayerConsumeItem(PlayerItemConsumeEvent e) {
		if (e.getItem().getType() == Material.GOLDEN_APPLE) {
			MainConfig config = UHCReloaded.getMainConfiguration();
			
			if (!config.isGoldenHeadEnabled())
				return;
			
			e.setCancelled(true);
			
			if (e.getPlayer().getInventory().first(Material.GOLDEN_APPLE) != -1) {
				ItemStack item = e.getItem();
				item.setAmount(1);
				e.getPlayer().getInventory().removeItem(item);
				e.getPlayer().updateInventory();
			} else { // It's that we are in 1.9 and that the item come from the off hand
				ItemStack item = e.getPlayer().getInventory().getItemInOffHand();
				Integer amount = item.getAmount() - 1;
				
				if (amount <= 0)
					e.getPlayer().getInventory().setItemInOffHand(null);
				else
					item.setAmount(amount);
				
				e.getPlayer().updateInventory();
			}
			
			e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel() + 5 > 20 ? 20 : e.getPlayer().getFoodLevel() + 5);
			
			if (e.getItem().getDurability() == (short) 1)
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, config.getGoldenNotchTime() * 20, 1));
			else {
				if (e.getPlayer().hasPotionEffect(PotionEffectType.ABSORPTION))
					e.getPlayer().removePotionEffect(PotionEffectType.ABSORPTION);
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 120 * 20, 0));
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, config.getGoldenNormalTime() * 20, 1));
			}
				
		}
	}
}
