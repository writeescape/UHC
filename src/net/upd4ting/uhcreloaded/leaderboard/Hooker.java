package net.upd4ting.uhcreloaded.leaderboard;

import java.util.Arrays;

import me.robin.leaderheads.datacollectors.OnlineDataCollector;
import me.robin.leaderheads.objects.BoardType;
import net.upd4ting.uhcreloaded.UHCPlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Hooker {
	
	public static void hookInLeaderheads() {
		Plugin leaderheads = Bukkit.getPluginManager().getPlugin("LeaderHeads");
		
		if (leaderheads != null) {
			new Kill();
			new Death();
			new Win();
			new Lose();
			new GamePlayed();
		}
	}
	
	public static class Kill extends OnlineDataCollector {

	    public Kill() {
	        super("uhc-kills", "UHCReloaded", BoardType.DEFAULT, "&bMenu title", "openmenu", Arrays.asList(null, null, "&e{amount} kills", null));
	    }
	    
	    @Override
	    public Double getScore(Player player) {
	        return Double.valueOf(UHCPlayer.instanceOf(player).getKill());
	    }
	}
	
	public static class Death extends OnlineDataCollector {

	    public Death() {
	        super("uhc-death", "UHCReloaded", BoardType.DEFAULT, "&bMenu title", "openmenu", Arrays.asList(null, null, "&e{amount} death", null));
	    }
	    
	    @Override
	    public Double getScore(Player player) {
	        return Double.valueOf(UHCPlayer.instanceOf(player).getDeath());
	    }
	}
	
	public static class Win extends OnlineDataCollector {

	    public Win() {
	        super("uhc-win", "UHCReloaded", BoardType.DEFAULT, "&bMenu title", "openmenu", Arrays.asList(null, null, "&e{amount} win", null));
	    }
	    
	    @Override
	    public Double getScore(Player player) {
	        return Double.valueOf(UHCPlayer.instanceOf(player).getWin());
	    }
	}
	
	public static class Lose extends OnlineDataCollector {

	    public Lose() {
	        super("uhc-lose", "UHCReloaded", BoardType.DEFAULT, "&bMenu title", "openmenu", Arrays.asList(null, null, "&e{amount} lose", null));
	    }
	    
	    @Override
	    public Double getScore(Player player) {
	        return Double.valueOf(UHCPlayer.instanceOf(player).getLose());
	    }
	}
	
	public static class GamePlayed extends OnlineDataCollector {

	    public GamePlayed() {
	        super("uhc-gameplayed", "UHCReloaded", BoardType.DEFAULT, "&bMenu title", "openmenu", Arrays.asList(null, null, "&e{amount} gameplayed", null));
	    }
	    
	    @Override
	    public Double getScore(Player player) {
	        return Double.valueOf(UHCPlayer.instanceOf(player).getGamePlayed());
	    }
	}
}
