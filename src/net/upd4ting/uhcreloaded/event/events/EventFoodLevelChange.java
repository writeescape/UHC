package net.upd4ting.uhcreloaded.event.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.TimerConfig;
import net.upd4ting.uhcreloaded.task.TaskManager;
import net.upd4ting.uhcreloaded.task.Timer;

public class EventFoodLevelChange implements Listener {
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e) {
		if (UHCReloaded.getGame().isWaiting())
			e.setCancelled(true);
		else {
			TimerConfig config = UHCReloaded.getTimerConfiguration();
			Timer t = (Timer) TaskManager.getTask("PvpTask");
			
			if (config.isNoHunger() && t.isActive())
				e.setCancelled(true);
			
			if (UHCReloaded.getGame().isLimited((Player) e.getEntity())) {
				e.setCancelled(true);
			}
		}
	}
}
