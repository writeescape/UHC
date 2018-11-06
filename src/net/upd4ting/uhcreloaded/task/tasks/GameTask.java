package net.upd4ting.uhcreloaded.task.tasks;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.task.Task;

public class GameTask extends Task {
	
	private Integer time;

	public GameTask() {
		super("GameTask", 20);
		this.time = 0;
	}

	@Override
	public void tick() {
		Game game = UHCReloaded.getGame();
		
		if (game.isInGame())
			time++;
	}
	
	public String getDisplayTime() { return String.format("%02d:%02d", time / 60, time % 60); }
}
