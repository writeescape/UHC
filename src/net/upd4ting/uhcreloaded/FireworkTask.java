package net.upd4ting.uhcreloaded;

import java.util.ArrayList;
import java.util.List;

import net.upd4ting.uhcreloaded.util.UtilFirework;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FireworkTask extends BukkitRunnable {
	
	private List<Player> players = new ArrayList<>();
	
	public FireworkTask(Player player) {
		this.players.add(player);
	}
	
	public FireworkTask(List<Player> players) {
		this.players.addAll(players);
	}
	
	
	@Override
	public void run() {
		for (Player p : players) {
			if (!p.isOnline())
				continue;
			
			UtilFirework.spawnRandomFirework(p.getLocation().add(0,1.5,0), false);
		}
	}
	
}
