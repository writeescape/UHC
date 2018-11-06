package net.upd4ting.uhcreloaded.board.vars;

import net.upd4ting.uhcreloaded.Game.GameState;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.board.Sideline;
import net.upd4ting.uhcreloaded.board.Var;

import org.bukkit.entity.Player;

public class WbSize extends Var {

	public WbSize() {
		super("%wbSize", GameState.STARTED);
	}

	@Override
	public String process(Player p, Sideline sl, String conserned) {
		return conserned.replace(this.getName(), ""+ String.format("%.1f", UHCReloaded.getNMSHandler().getBorderHandler().getSize()));
	}
}
