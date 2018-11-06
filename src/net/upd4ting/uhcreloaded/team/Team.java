package net.upd4ting.uhcreloaded.team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCPlayer;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.configuration.configs.TeamConfig;
import net.upd4ting.uhcreloaded.nms.common.NametagHandler;
import net.upd4ting.uhcreloaded.schematic.Schematic.BlockInfo;
import net.upd4ting.uhcreloaded.util.Util;
import net.upd4ting.uhcreloaded.util.Util.ActionMessage;

public class Team {
	private static List<Team> teams = new ArrayList<>();
	
	private Integer id;
	private String name;
	private String prefix;
	private BlockInfo block;
	private String owner;
	private List<UUID> players;
	private org.bukkit.scoreboard.Team handler;
	
	// Constructeur pour les deux
	public Team() {
		this.players = new ArrayList<>();
		this.id = teams.size();
	}
	
	// Constructeur non GUI
	public Team(String owner) {
		this();
		this.owner = owner;
	}
	
	// Constructeur pour le GUI
	public Team(String name, BlockInfo block, String prefix) {
		this();
		this.name = name;
		this.block = block;
		this.prefix = prefix;
	}
	
	@SuppressWarnings("deprecation")
	public void join(Player p) {
		players.add(p.getUniqueId());
		
		System.out.println("");
		
		if (prefix != null) {
			if (handler == null) {
				handler = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("UHCRTeam " + id);
				handler.setPrefix(prefix + " ");
				handler.setSuffix(ChatColor.RESET + "");
			}
			handler.addPlayer(p);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void leave(Player p) {
		players.remove(p.getUniqueId());
		
		if (prefix != null) {
			if (handler == null) {
				handler = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("UHCRTeam " + (teams.indexOf(this) + 1));
				handler.setPrefix(prefix + " ");
				handler.setSuffix(ChatColor.RESET + "");
			}
			handler.removePlayer(p);
		}
	}
	
	public void joinMessage(Player p) {
		LangConfig config = UHCReloaded.getLangConfiguration();
		final String message = config.getTeamPrefix() + " "+ config.getJoinTeam().replace("%p", p.getName());
		Util.sendActionConfigMessage(message, new ActionMessage() {
			@Override
			public void run() {
				for (Player current : getPlayers())
					current.sendMessage(message);
			}
		});
	}
	
	public void leaveMessage(Player p) {
		LangConfig config = UHCReloaded.getLangConfiguration();
		final String message = config.getTeamPrefix() + " " + config.getLeaveTeam().replace("%p", p.getName());
		Util.sendActionConfigMessage(message, new ActionMessage() {
			@Override
			public void run() {
				for (Player current : getPlayers())
					current.sendMessage(message);
			}
		});
	}
	
	public void sendHeal(Integer amplificator, Integer time) {
		for (Player p : getPlayers())
			UHCPlayer.instanceOf(p).sendHeal(amplificator, time);
	}
	
	public Integer getPlayerAlive() {
		Game game = UHCReloaded.getGame();
		Integer n = 0;
		for (Player p : getPlayers()) {
			if (game.getPlaying().contains(p))
				n++;
		}
		return n;
	}
	
	public void setOwner(String owner) { this.owner = owner; }
	public Integer getID() { return id; }
	public String getName() { return name; }
	public String getPrefix() { return prefix; }
	public String getOwner() { return owner; }
	public BlockInfo getBlockInfo() { return block; }
	public List<Player> getPlayers() {
		List<Player> list = new ArrayList<>();
		for (UUID u : players) {
			Player p = Bukkit.getPlayer(u);
			if (p != null && p.isOnline())
				list.add(p);
		}
		return list;
	}
	
	
	// Static function
	
	public static void createTeam(String nameMenu, BlockInfo info, String displayName) {
		Team team = new Team(nameMenu, info, displayName);
		teams.add(team);
	}
	
	public static Team createTeam(String owner) {
		Team team = new Team(owner);
		teams.add(team);
		return team;
	}
	
	public static Integer getNumber() {
		return teams.size();
	}
	
	public static List<Team> getTeams() {
		return teams;
	}
	
	public static Team getTeam(Player p) {
		for (Team t : teams) 
			if (t.getPlayers().contains(p)) 
				return t;
		return null;
	}
	
	public static Boolean hasTeam(Player p) {
		for (Team t : teams)
			if (t.getPlayers().contains(p))
				return true;
		return false;
	}
	
	// Fonction appelée dans le playerQuit pour enlevé la team du gars qui leave quand partie en waiting
	public static void leaveWaiting(Player p) {
		for (Team t : teams)
			if (t.getPlayers().contains(p)) {
				t.leave(p);
				t.leaveMessage(p);
			}
	}
	
	// Fonction pour set les name tag quand le GUI n'est pas activé
	public static void setNameTag() {
		for (Team t : teams) {
			for (Player p : t.getPlayers())
				setNameTag(p);
		}
	}
	
	public static void setNameTag(Player p) {
		Team t = getTeam(p);
		String randomNameGreen = Util.generateRandomString();
		String randomNameRed = Util.generateRandomString();
		
		NametagHandler handler = UHCReloaded.getNMSHandler().getNametagHandler();
		
		handler.init(randomNameGreen, randomNameGreen, net.upd4ting.uhcreloaded.nms.common.NametagHandler.NameTagColor.GREEN);
		
		for (Player pl : UHCReloaded.getGame().getPlaying())
			if (t.getPlayers().contains(pl))
				handler.addPlayer(pl);
		
		handler.sendToPlayer(p);
		
		handler.init(randomNameRed, randomNameRed, net.upd4ting.uhcreloaded.nms.common.NametagHandler.NameTagColor.RED);
		
		for (Player pl : UHCReloaded.getGame().getPlaying())
			if (!t.getPlayers().contains(pl))
				handler.addPlayer(pl);
		handler.sendToPlayer(p);
	}
	
	public static void setNameTagSpectator(Player p) {
		String random = Util.generateRandomString();
		
		NametagHandler handler = UHCReloaded.getNMSHandler().getNametagHandler();
		
		handler.init(random, random, net.upd4ting.uhcreloaded.nms.common.NametagHandler.NameTagColor.GRAY);
		
		for (Player pl : UHCReloaded.getGame().getPlaying())
				handler.addPlayer(pl);
		handler.sendToPlayer(p);
	}
	
	public static void fillTeam() {
		// Si fill activé on fill sinon ceux qui ont pas de team on les met dans une team
		TeamConfig config = UHCReloaded.getTeamConfiguration();

		for (Player p : UHCReloaded.getGame().getPlaying()) {
			if (!Team.hasTeam(p)) {
				if (config.isEnabled() && (config.isAutoFillActived() || config.isGUIEnabled()) && teams.size() > 0) {
					joinMinTeam(p);
				} else {
					Team team = Team.createTeam(p.getName());
					team.join(p);
				}
			}
		}
	}
	
	public static void joinMinTeam(Player p) {
		Collections.sort(Team.teams, teamComparator);
		Team team = Team.teams.get(0);
		if (team.getPlayers().size() >= UHCReloaded.getTeamConfiguration().getTeamSize()) {
			Team team2 = Team.createTeam(p.getName());
			team2.join(p);
		} else
			team.join(p);
	}
	
	public static Comparator<Team> teamComparator = new Comparator<Team>() {
		@Override
		public int compare(Team t1, Team t2) {
			return t1.getPlayers().size() - t2.getPlayers().size();
		}
	};
}
