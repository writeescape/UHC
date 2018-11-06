package net.upd4ting.uhcreloaded.task.timers;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.task.Timer;
import net.upd4ting.uhcreloaded.util.Util;

public class DamageActiveTask extends Timer {
	
	public DamageActiveTask(Integer time) {
		super("DamageTask", time);
	}
	
	public void onSec() {
		LangConfig config = UHCReloaded.getLangConfiguration();
		Integer time = getTime();
		
		if (time == 30 || time == 10 || (time <= 5 && time != 0))
			Util.avertAll(config.getDamageTime().replace("%t", this.getDisplayTime()));
		
		if (time == 0) {
            Util.avertAll(config.getDamageEnabled());
		}
	}
}
