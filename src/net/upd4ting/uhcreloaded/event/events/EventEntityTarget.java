package net.upd4ting.uhcreloaded.event.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCReloaded;

public class EventEntityTarget implements Listener {
	
	@EventHandler
	public void onTarget(EntityTargetEvent e) {
		Game game = UHCReloaded.getGame();
		
		if (!game.isInGame()) {
			e.setCancelled(true);
			return;
		}
		
		if (!(e.getTarget() instanceof Player))
			return;
		
		Entity ent = e.getEntity();
		Player p = (Player) e.getTarget();
		
		if (game.isLimited(p)) {
			e.setCancelled(true);
			return;
		}
		
		if (e.getReason() == TargetReason.COLLISION && p.getLocation().distance(ent.getLocation()) > 1) // Empecher que les spectateurs produisent des collisions sur l'entitée et qu'n joueur soit pris pour cible alors qu'il ne l'a pas touchée...
			e.setCancelled(true);
	}
}
