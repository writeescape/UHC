package net.upd4ting.uhcreloaded.task.timers;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.nms.NMSHandler;
import net.upd4ting.uhcreloaded.task.Timer;
import net.upd4ting.uhcreloaded.util.Util;

public class WbReductingTimerTask extends Timer { 
	
	private Boolean dm;
	
	public WbReductingTimerTask(Integer time, Boolean dm) {
		super("WbTask", time);
		this.dm = dm;
	}
	
	public void onSec() {
		LangConfig config = UHCReloaded.getLangConfiguration();
		Integer time = getTime();
		
		
		if (time == 300 || time == 180 || time == 60 || time == 30 || time == 10 || ((time <= 5) && (time > 0))) {
			Util.avertAll(config.getWbTime().replace("%t", this.getDisplayTime()));
			Util.playSoundToAll(NMSHandler.isBasicSound() ? "NOTE_STICKS" : "BLOCK_NOTE_PLING");	
		}
		else if (time == 0) {
			Util.avertAll(config.getWbEnabled());
			UHCReloaded.getNMSHandler().getBorderHandler().move(dm);
		}
	}
}
