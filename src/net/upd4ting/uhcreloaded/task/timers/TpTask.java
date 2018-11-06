package net.upd4ting.uhcreloaded.task.timers;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.configuration.configs.TimerConfig;
import net.upd4ting.uhcreloaded.nms.NMSHandler;
import net.upd4ting.uhcreloaded.task.TaskManager;
import net.upd4ting.uhcreloaded.task.Timer;
import net.upd4ting.uhcreloaded.task.tasks.TeleportationTask;
import net.upd4ting.uhcreloaded.util.Util;

public class TpTask extends Timer {
	
	public TpTask(Integer time) {
		super("TpTask", time);
	}
	
	public void onSec() {
		LangConfig config = UHCReloaded.getLangConfiguration();
		TimerConfig timerConfig = UHCReloaded.getTimerConfiguration();
		Integer time = getTime();
		
	    if (time == 300 || time == 180 || time == 60 || time == 30 || time == 10 || ((time <= 5) && (time > 0))) {
	      Util.avertAll(config.getDmTime().replace("%t", this.getDisplayTime()));
	      Util.playSoundToAll(NMSHandler.isBasicSound() ? "NOTE_STICKS" : "BLOCK_NOTE_PLING");	
	    }
	    else if (time == 0) {
	    	Util.avertAll(config.getDmEnabled());

	    	new TeleportationTask(true);
	    	
	    	// TODO Have to wait that's finished to run that...
	    	// In a next update
	    	
	    	if (timerConfig.isDmShrinkingEnabled())
	    		TaskManager.runTask(new WbReductingTimerTask(timerConfig.getDmTimeStartShrinking(), true));
	    }
	}
}
