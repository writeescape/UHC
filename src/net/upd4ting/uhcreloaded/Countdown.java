package net.upd4ting.uhcreloaded;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.configuration.configs.MainConfig;
import net.upd4ting.uhcreloaded.configuration.configs.TimerConfig;
import net.upd4ting.uhcreloaded.nms.NMSHandler;
import net.upd4ting.uhcreloaded.util.Util;

public class Countdown extends BukkitRunnable {
	public static Boolean started = false;
	public static Boolean forced = false;
	
	Integer sec;
	
	public Countdown() {
		this.sec = UHCReloaded.getTimerConfiguration().getTimeToStart();
		started = true;
		this.runTaskTimer(UHCReloaded.getInstance(), 0, 20);
	}

	@Override
	public void run() {
		Game game = UHCReloaded.getGame();
		
		if (sec <= 0) {
			game.startGame();
			started = false;
			this.cancel();
			return;
		}
		
		MainConfig config = UHCReloaded.getMainConfiguration();
		TimerConfig timerConfig = UHCReloaded.getTimerConfiguration();
		LangConfig langConfig = UHCReloaded.getLangConfiguration();
		
		// Si pas assez de joueur et partie pas forcée on stop
		if (config.getMinPlayer() > game.getPlaying().size() && forced == false) {
			Util.avertAll(UHCReloaded.getPrefix() + langConfig.getNotEnoughPlayer());
			started = false;
			sec = timerConfig.getTimeToStart();
			this.cancel();
			return;
		}
		
		if (config.getMaxPlayer() <= game.getPlaying().size() && sec > timerConfig.getTimeToStartFull())
			sec = timerConfig.getTimeToStartFull();
		
		if (sec % 60 == 0 || sec == 30 || sec <= 5) {
			Util.avertAll(langConfig.getGameTimeStart().replace("%t", ""+sec));
			Util.playSoundToAll(NMSHandler.isBasicSound() ? "NOTE_STICKS" : "BLOCK_NOTE_PLING");
		}
		
		// Compteur dans la barre d'exp
		for (Player p : game.getPlaying())
			p.setLevel(sec);
		
		sec--;
	}
}
