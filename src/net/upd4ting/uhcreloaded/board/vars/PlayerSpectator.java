package net.upd4ting.uhcreloaded.board.vars;

import org.bukkit.entity.Player;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.Game.GameState;
import net.upd4ting.uhcreloaded.board.Sideline;
import net.upd4ting.uhcreloaded.board.Var;

public class PlayerSpectator extends Var {

	public PlayerSpectator() {
		super("%playerSpectator", GameState.STARTED);
	}

	@Override
	public String process(Player p, Sideline sl, String conserned) {
		return conserned.replace(this.getName(), ""+ UHCReloaded.getGame().getSpectators().size());
	}
}
