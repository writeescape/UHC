package net.upd4ting.uhcreloaded.board;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.Game.GameState;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.board.vars.Center;
import net.upd4ting.uhcreloaded.board.vars.Kill;
import net.upd4ting.uhcreloaded.board.vars.KillGame;
import net.upd4ting.uhcreloaded.board.vars.PlayerAlive;
import net.upd4ting.uhcreloaded.board.vars.PlayerMaximum;
import net.upd4ting.uhcreloaded.board.vars.PlayerSpectator;
import net.upd4ting.uhcreloaded.board.vars.PlayerTotal;
import net.upd4ting.uhcreloaded.board.vars.TeamAlive;
import net.upd4ting.uhcreloaded.board.vars.Teams;
import net.upd4ting.uhcreloaded.board.vars.Timers;
import net.upd4ting.uhcreloaded.board.vars.WbSize;
import net.upd4ting.uhcreloaded.board.vars.WbTime;
import net.upd4ting.uhcreloaded.board.vars.YLayer;

public abstract class Var {
	public static HashSet<Var> vars = new HashSet<>();
	
	private GameState state;
	private String name;
	
	public Var(String name, GameState state) {
		this.name = name;
		this.state = state;
		vars.add(this);
	}
	
	public abstract String process(Player p, Sideline sl, String conserned);
	
	public String getName() { return name; }
	public GameState getState() { return state; }
	
	public static void execute(Player p, Sideline sl, String line) {
		Game game = UHCReloaded.getGame();
		for (Var v : vars) {
			if (line.contains(v.getName()) && (v.getState() == null || game.getState() == v.getState() || (game.getState() == GameState.FINISH && v.getState() == GameState.STARTED))) {
				line = v.process(p, sl, line);
				if (line == null)
					break; // La ligne a été traitée
			}
		}
		
		// PlaceHolderAPI
		if (UHCReloaded.getMainConfiguration().getPlaceholderApi() && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") && line != null) {
			line = PlaceholderAPI.setPlaceholders(p, line);
		}
		
		if (line != null)
			sl.add(line);
	}
	
	public static String translateForPlaceholderAPI(String identifier, Player p) {
		String copy = "%" + identifier;
		for (Var v : vars) {
			if (copy.contains(v.getName())) {
				String s = v.process(p, null, copy);
				
				if (s != null)
					return s;
			}
		}
		return null;	
	}
	
	public static void registerVars() {
		new Teams();
		new Timers();
		new Kill();
		new PlayerAlive();
		new PlayerMaximum();
		new PlayerSpectator();
		new PlayerTotal();
		new TeamAlive();
		new WbSize();
		new WbTime();
		new Center();
		new YLayer();
		new KillGame();
	}
}
