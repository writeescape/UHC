package net.upd4ting.uhcreloaded.event.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class Frozen implements Listener {
	
	public static List<Player> frozen = new ArrayList<>();
	
	public static HashMap<Player, Integer> fireticks = new HashMap<>();
	public static HashMap<Player, Collection<PotionEffect>> effects = new HashMap<>();
	
	public static Boolean isFrozen(Player p) {
		return frozen.contains(p);
	}
	
	@EventHandler (priority=EventPriority.LOW)
    public void onMove(final PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (isFrozen(p)) {
			if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
				Location loc = p.getLocation();
				loc.setX(e.getFrom().getBlockX());
				loc.setZ(e.getFrom().getBlockZ());
				loc.add(0.5, 0, 0.5);
				e.getPlayer().teleport(loc);
			}
		}
	}
	
	@EventHandler
	public void onToggleFlight(PlayerToggleFlightEvent e) {
		if (isFrozen(e.getPlayer())) {
			e.setCancelled(true);
			e.getPlayer().setVelocity(new Vector().zero());
		}
	}
}