package net.upd4ting.uhcreloaded.event.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EventEntityRegainHealth implements Listener {
	@EventHandler
	public void onEntityRegainHealth(EntityRegainHealthEvent e) {
		/*Boolean cancel = true;
		
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			for (PotionEffect pe : p.getActivePotionEffects()) {
				if (pe.getType() == PotionEffectType.REGENERATION) {
					cancel = false;
					break;
				}
			}
		}
		
		if (cancel && (e.getRegainReason() == RegainReason.EATING || e.getRegainReason() == RegainReason.SATIATED))
			e.setCancelled(true);*/
	}
}
