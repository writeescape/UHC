package net.upd4ting.uhcreloaded.board.vars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.entity.Player;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.Game.GameState;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.board.Sideline;
import net.upd4ting.uhcreloaded.board.Var;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.task.Timer;
import net.upd4ting.uhcreloaded.util.Util;
import net.upd4ting.uhcreloaded.util.Util.ActionMessage;

public class Timers extends Var {

	public Timers() {
		super("%timers", GameState.STARTED);
	}

	@Override
	public String process(Player p, final Sideline sl, String conserned) {
		Game game = UHCReloaded.getGame();
		final LangConfig config = UHCReloaded.getLangConfiguration();
		
		if (sl == null) // Pour PlaceholderAPI
			return null;
		
		if (game.hasTimersActive()) {
			// On met le separator
			Util.sendActionConfigMessage(config.getTimerDelimitor(), new ActionMessage() {
				@Override
				public void run() {
					sl.add(config.getTimerDelimitor());
				}
			});
			
			// On en prend deux
			List<Timer> list = new ArrayList<>(game.getTimers());
			Collections.sort(list, comparator);
			
			Integer i = 0, nb = 0;
			
			while (nb < 2 && i < list.size()) {
				Timer t = list.get(i);
				
				if (t.isActive()) {
					String message = "";
					
					if (t.getName().equals("DamageTask"))
						message = config.getDamage();
					else if (t.getName().equals("PvpTask"))
						message = config.getPvp();
					else if (t.getName().equals("TpTask"))
						message = config.getDeathMatch();
					else if (t.getName().equals("WbTask"))
						message = config.getBorder();
					else if (t.getName().equals("HealTask"))
						message = config.getHeal();
					
					sl.add(message.replace("%t", t.getDisplayTime()));
					
					nb++;
				}
				
				i++;
			}
		}
		
		return null;
	}
	
	private static Comparator<Timer> comparator = new Comparator<Timer>() {
		@Override
		public int compare(Timer t1, Timer t2) {
			return t1.getTime() - t2.getTime();
		}
	};

}
