package net.upd4ting.uhcreloaded.task.timers;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.nms.NMSHandler;
import net.upd4ting.uhcreloaded.task.Timer;
import net.upd4ting.uhcreloaded.util.Util;

import org.bukkit.World;

public class PvpStartTask extends Timer {
	
	public PvpStartTask(Integer time) {
		super("PvpTask", time);
	}
	
	public void onSec(){
		LangConfig config = UHCReloaded.getLangConfiguration();
		Integer time = getTime();

		if (time == 300 || time == 180 || time == 60 || time == 30 || time == 10 || ((time <= 5) && (time > 0))) {
			Util.avertAll(config.getPvpTime().replace("%t", this.getDisplayTime()));
			Util.playSoundToAll(NMSHandler.isBasicSound() ? "NOTE_STICKS" : "BLOCK_NOTE_PLING");		}
		if (time == 0) {
			for (World w : UHCReloaded.getGame().getWorlds())
	            w.setPVP(true);
			
			Util.avertAll(config.getPvpEnabled());
			Util.playSoundToAll(NMSHandler.isBasicSound() ? "WOLF_GROWL" : "ENTITY_WOLF_GROWL");
		}
	}
}
