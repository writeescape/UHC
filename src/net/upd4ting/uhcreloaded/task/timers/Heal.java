package net.upd4ting.uhcreloaded.task.timers;

import org.bukkit.entity.Player;

import net.upd4ting.uhcreloaded.UHCPlayer;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.configuration.configs.TimerConfig;
import net.upd4ting.uhcreloaded.nms.NMSHandler;
import net.upd4ting.uhcreloaded.task.Timer;
import net.upd4ting.uhcreloaded.util.Util;

public class Heal extends Timer {

	public Heal(Integer time) {
		super("HealTask", time);
	}

	@Override
	public void onSec() {
		LangConfig config = UHCReloaded.getLangConfiguration();
		TimerConfig timerConfig = UHCReloaded.getTimerConfiguration();
		Integer time = getTime();

		if (time == 300 || time == 180 || time == 60 || time == 30 || time == 10 || ((time <= 5) && (time > 0))) {
			Util.avertAll(config.getHealTime().replace("%t", this.getDisplayTime()));
			Util.playSoundToAll(NMSHandler.isBasicSound() ? "NOTE_STICKS" : "BLOCK_NOTE_PLING");
		}

		if (time == 0) {
			Util.avertAll(config.getHealEnabled());
			Util.playSoundToAll(NMSHandler.isBasicSound() ? "FIREWORK_LAUNCH" : "ENTITY_FIREWORK_LAUNCH");
			for (Player p : UHCReloaded.getGame().getPlaying())
				UHCPlayer.instanceOf(p).sendHeal(timerConfig.getHealRegenAmplificator(), timerConfig.getHealRegenTime());
		}
	}
}
