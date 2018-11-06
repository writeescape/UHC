package net.upd4ting.uhcreloaded.nms.v1_9_R1;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.TimerConfig;
import net.upd4ting.uhcreloaded.nms.common.BorderHandler;

public class Border implements BorderHandler {

	@Override
	public void initialize(Boolean tp) {
		TimerConfig config = UHCReloaded.getTimerConfiguration();
		Integer size = tp ? config.getDmSizeStart() : config.getWbSizeStart();
		Integer damage = tp ? config.getDmDamage() : config.getWbDamage();
		
		for (World w : UHCReloaded.getGame().getWorlds()) {
			WorldBorder border = w.getWorldBorder();
			border.setCenter(0, 0);
			border.setSize(size * 2);
			border.setDamageAmount(damage);
			border.setDamageBuffer(1);
			if (!tp)
				w.setTime(0);
			w.setPVP(tp);
		}
	}

	@Override
	public void move(Boolean tp) {
		TimerConfig timerConfig = UHCReloaded.getTimerConfiguration();
		
		for (World w : UHCReloaded.getGame().getWorlds()) {
			WorldBorder border = w.getWorldBorder();
			border.setSize((tp ? timerConfig.getDmSizeEnd() : timerConfig.getWbSizeEnd()) * 2, tp ? 
					timerConfig.getDmShrinkTime() : timerConfig.getWbShrinkTime());
		}
	}

	@Override
	public Double getSize() {
		return Bukkit.getWorlds().get(0).getWorldBorder().getSize() / 2;
	}
}
