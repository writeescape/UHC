package net.upd4ting.uhcreloaded.board.vars;

import org.bukkit.entity.Player;

import net.upd4ting.uhcreloaded.Game.GameState;
import net.upd4ting.uhcreloaded.board.Sideline;
import net.upd4ting.uhcreloaded.board.Var;
import net.upd4ting.uhcreloaded.task.TaskManager;
import net.upd4ting.uhcreloaded.task.Timer;

public class WbTime extends Var {
	
	public WbTime() {
		super("%wbTime", GameState.STARTED);
	}

	@Override
	public String process(Player p, Sideline sl, String conserned) {
		Timer t = (Timer) TaskManager.getTask("WbTask");
		
		if (t.isActive()) {
			return conserned.replace(this.getName(), t.getDisplayTime());
		} else
			return null;
	}
}
