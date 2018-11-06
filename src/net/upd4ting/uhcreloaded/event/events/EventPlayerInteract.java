package net.upd4ting.uhcreloaded.event.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.MainConfig;
import net.upd4ting.uhcreloaded.team.Team;

public class EventPlayerInteract implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Game game = UHCReloaded.getGame();
		
		if (game.getSpectators().contains(p))
			e.setCancelled(true);
		
		MainConfig mainConfig = UHCReloaded.getMainConfiguration();
		
		ItemStack inHand = p.getItemInHand();
		
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (game.isInGame()) {
				if (mainConfig.isGoldenHeadEnabled() && inHand.getType() == Material.SKULL_ITEM) {
					e.setUseInteractedBlock(Result.DENY);
					e.setUseItemInHand(Result.DENY);
					e.setCancelled(true);
					
					inHand.setAmount(1);
					
					p.getInventory().removeItem(inHand);
					p.updateInventory();
					
					// On envoie le heal !
					Team.getTeam(p).sendHeal(mainConfig.getGoldenHeadRegenAmplificator(), mainConfig.getGoldenHeadRegenTime());
				}
			}
		}
	}
}
