package net.upd4ting.uhcreloaded.board.vars;

import net.upd4ting.uhcreloaded.Game.GameState;
import net.upd4ting.uhcreloaded.board.Sideline;
import net.upd4ting.uhcreloaded.board.Var;

import org.bukkit.entity.Player;

public class YLayer extends Var {

	public YLayer() {
		super("%ylayer", GameState.STARTED);
	}

	@Override
	public String process(Player p, Sideline sl, String conserned) {
		return conserned.replace(this.getName(), ""+ (int) p.getLocation().getY());
	}

}
