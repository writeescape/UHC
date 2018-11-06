package net.upd4ting.uhcreloaded.board.vars;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.upd4ting.uhcreloaded.Game.GameState;
import net.upd4ting.uhcreloaded.board.Sideline;
import net.upd4ting.uhcreloaded.board.Var;
import net.upd4ting.uhcreloaded.util.Util;

public class Center extends Var {

	public Center() {
		super("%center", GameState.STARTED);
	}

	@Override
	public String process(Player p, Sideline sl, String conserned) {
		return conserned.replace(this.getName(), Util.getDirection(p, new Location(p.getWorld(), 0, 0, 0)));
	}

}
