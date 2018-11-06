package net.upd4ting.uhcreloaded.task;

import net.upd4ting.uhcreloaded.UHCReloaded;

public abstract class Timer extends Task {
	private Integer time;
	
	public Timer(String name, Integer time) {
		super(name, 20);
		this.time = time;
	}
	
	@Override
	public void tick() {
		if (UHCReloaded.getGame().isInGame() && isActive()) {
			time--;
			onSec();
		}
	}
	
	public abstract void onSec();
	
	public Integer getTime() { return time; }
	public String getDisplayTime() { return String.format("%02d:%02d", time / 60, time % 60); }
	public Boolean isActive() { return time > 0; }
}
