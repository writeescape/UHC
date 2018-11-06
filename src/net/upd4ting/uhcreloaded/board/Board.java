package net.upd4ting.uhcreloaded.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.BoardConfig;
import net.upd4ting.uhcreloaded.task.Task;
import net.upd4ting.uhcreloaded.task.TaskManager;
import net.upd4ting.uhcreloaded.task.tasks.GameTask;

public class Board extends Task {
	
	public static HashMap<UUID, Sideline> playerSideline = new HashMap<>();
	
	public Board() {
		super("Board", 20);
	}
	
	@Override
	public void tick() {
		for (Entry<UUID, Sideline> entry : playerSideline.entrySet())
			sendBoard(Bukkit.getPlayer(entry.getKey()), entry.getValue());
		
		if (UHCReloaded.getGame().isFinished())
			TaskManager.cancelTask(this);
	}
	
	public static void start(Player p) {
		Sidebar sb = new Sidebar(p);
		sb.setName(UHCReloaded.getBoardConfiguration().getTitle());
		playerSideline.put(p.getUniqueId(), new Sideline(sb));
				
        // --Vie dans le scoreboard
        Scoreboard scoreboard = p.getScoreboard();
        Objective o = scoreboard.getObjective("health");
                
        if (o == null) {
        	o = scoreboard.registerNewObjective("health", UHCReloaded.getMainConfiguration().getDisplayStyle());
            o.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        }
        
        for (Player a : Bukkit.getOnlinePlayers())
            o.getScore(a.getName()).setScore((int) a.getHealth());
	}
	
	public static void leave(Player p) {
		playerSideline.remove(p.getUniqueId());
	}
	
	private static void sendBoard(Player p, Sideline line) {
		BoardConfig config = UHCReloaded.getBoardConfiguration();
		Game game = UHCReloaded.getGame();

		sendLines(p, line, game.isWaiting() ? config.getWaiting() : game.isInGame() ? 
				game.getSpectators().contains(p) ? config.getSpectator() : config.getInGame() : config.getInGame());
		
        // --Vie dans le scoreboard
        Scoreboard scoreboard = p.getScoreboard();
        Objective o = scoreboard.getObjective("health");
                
        if (o == null) {
        	o = scoreboard.registerNewObjective("health", UHCReloaded.getMainConfiguration().getDisplayStyle());
            o.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        }
        
        for (Player a : Bukkit.getOnlinePlayers())
            o.getScore(a.getName()).setScore((int) a.getHealth());
	}
	
	private static void sendLines(Player p, Sideline line, ArrayList<String> lines) {
		GameTask task = (GameTask) TaskManager.getTask("GameTask");
		line.sb.setName(UHCReloaded.getBoardConfiguration().getTitle().replace("%t", task.getDisplayTime()));
		for (String s : lines)
			Var.execute(p, line, s);
		line.flush();
	}
}
