package net.upd4ting.uhcreloaded.task;

import java.util.ArrayList;

import org.bukkit.scheduler.BukkitRunnable;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.board.Board;
import net.upd4ting.uhcreloaded.configuration.configs.MySQLConfig;
import net.upd4ting.uhcreloaded.configuration.configs.TimerConfig;
import net.upd4ting.uhcreloaded.inventory.RefreshInventory;
import net.upd4ting.uhcreloaded.task.tasks.GameTask;
import net.upd4ting.uhcreloaded.task.tasks.UpdateSQLTask;
import net.upd4ting.uhcreloaded.task.timers.DamageActiveTask;
import net.upd4ting.uhcreloaded.task.timers.Heal;
import net.upd4ting.uhcreloaded.task.timers.PvpStartTask;
import net.upd4ting.uhcreloaded.task.timers.TpTask;
import net.upd4ting.uhcreloaded.task.timers.WbReductingTimerTask;

public class TaskManager extends BukkitRunnable {
	
	public static ArrayList<Task> tasks = new ArrayList<>();
	public static UpdateSQLTask updateSQLTask;
	
	
	public TaskManager() {
		registerTask();
		this.runTaskTimer(UHCReloaded.getInstance(), 0, 1);
	}

	@Override
	public void run() {
		for (Task t : new ArrayList<>(tasks))
			if (t.isElapsed())
				t.processTick();
	}
	
	public static void registerTask() {
		if (UHCReloaded.getBoardConfiguration().isEnabled())
			runTask(new Board());
		runTask(new RefreshInventory());
		runTask(new GameTask());
		
		TimerConfig config = UHCReloaded.getTimerConfiguration();
		
		runTask(new DamageActiveTask(config.getTimeInvincible()));
		runTask(new PvpStartTask(config.getPvpStart()));
		if (config.isDmEnabled())
			runTask(new TpTask(config.getDmTimeStart()));
		if (config.isWbShrinkingEnabled())
			runTask(new WbReductingTimerTask(config.getWbTimeStart(), false));
		if (config.isHealEnabled())
			runTask(new Heal(config.getHealTimeStart()));
		
		MySQLConfig mysqlConfig = UHCReloaded.getMysqlConfiguration();
		if (mysqlConfig.isEnabled())
			updateSQLTask = new UpdateSQLTask(mysqlConfig.getUpdateTime());
	}
	
	public static void runTask(Task t) {
		tasks.add(t);
	}
	
	public static Task getTask(String name) {
		for (Task t : tasks)
			if (t.getName().equals(name))
				return t;
		return null;
	}
	
	public static void cancelTask(String name) {
		cancelTask(getTask(name));
	}
	
	public static void cancelTask(Task t) {
		tasks.remove(t);
	}
}
