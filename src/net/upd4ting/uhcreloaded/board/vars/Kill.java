package net.upd4ting.uhcreloaded.board.vars;

import org.bukkit.entity.Player;

import net.upd4ting.uhcreloaded.UHCPlayer;
import net.upd4ting.uhcreloaded.Game.GameState;
import net.upd4ting.uhcreloaded.board.Sideline;
import net.upd4ting.uhcreloaded.board.Var;

public class Kill extends Var {

	public Kill() {
		super("%kill", GameState.STARTED);
	}

	@Override
	public String process(Player p, final Sideline sl, String conserned) {
		return conserned.replace(this.getName(), ""+  UHCPlayer.instanceOf(p).getKill());
	}
}
