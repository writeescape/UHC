package net.upd4ting.uhcreloaded.board.vars;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.board.Sideline;
import net.upd4ting.uhcreloaded.board.Var;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.team.Team;
import net.upd4ting.uhcreloaded.util.Util;
import net.upd4ting.uhcreloaded.util.Util.ActionMessage;

public class Teams extends Var {

	public Teams() {
		super("%teams", null);
	}

	@Override
	public String process(Player p, final Sideline sl, String conserned) {
		Team team = Team.getTeam(p);
		Game game = UHCReloaded.getGame();
		final LangConfig config = UHCReloaded.getLangConfiguration();
		
		if (sl == null) // Pour PlaceholderAPI
			return null;
		
		if (team != null) {
			ArrayList<String> list = new ArrayList<>();
			
			for (Player inTeam : team.getPlayers()) {
				if (inTeam == null || inTeam == p)
					continue;
				if (game.isWaiting())
					list.add(inTeam.getName());
				else if (game.isInGame()) {
					ChatColor color = Util.getColor(inTeam);
					String teamInfo = config.getTeamInfo().replaceAll("%p", color + inTeam.getName());
					
					if (color != ChatColor.GRAY && p.getWorld().getName().equals(inTeam.getWorld().getName())) {
						String direction = Util.getDirection(p, inTeam);
						teamInfo = teamInfo.replaceAll("%distance", Integer.toString((int) inTeam.getLocation().distance(p.getLocation())));
						
						if (direction != null) {
							teamInfo = teamInfo.replaceAll("%arrow", direction);
						}
					} else {
						teamInfo = teamInfo.replaceAll("%distance", "?").replaceAll("%arrow", "");
					}
					
					list.add(teamInfo);
				}
			}
			
			if (list.size() > 0) {
				// Ajout du délimiteur
				sl.add("");
				Util.sendActionConfigMessage(config.getTeammatesDelimitor(), new ActionMessage() {
					@Override
					public void run() {
						sl.add(config.getTeammatesDelimitor());
					}
				});
				
				for (String s : list)
					sl.add(s);
			}
		}
		
		return null;
	}

}
