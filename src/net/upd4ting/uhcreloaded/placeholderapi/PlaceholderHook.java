package net.upd4ting.uhcreloaded.placeholderapi;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import net.upd4ting.uhcreloaded.board.Var;

public class PlaceholderHook extends EZPlaceholderHook {

	public PlaceholderHook(Plugin plugin) {
		super(plugin, "uhcreloaded");
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		return Var.translateForPlaceholderAPI(identifier, p);
	}

}
