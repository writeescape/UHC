package net.upd4ting.uhcreloaded;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.upd4ting.uhcreloaded.configuration.configs.KitConfig;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.configuration.configs.MySQLConfig;
import net.upd4ting.uhcreloaded.task.TaskManager;
import net.upd4ting.uhcreloaded.task.tasks.UpdateSQLTask;
import net.upd4ting.uhcreloaded.util.Util;

public class UHCPlayer {
	public static HashMap<UUID, UHCPlayer> players = new HashMap<UUID, UHCPlayer>();
	
	private UUID uuid;
	private Integer kill;
	private Integer gameKill;
	private Integer death;
	private Integer win;
	private Integer lose;
	private Integer gamePlayed;
	private Integer selectedKit;
	
	private Integer rejoinTime;
	
	private List<UUID> invitations = new ArrayList<>();
	
	public UHCPlayer(UUID uuid) {
		this.uuid = uuid;
		this.kill = 0;
		this.gameKill = 0;
		this.death = 0;
		this.win = 0;
		this.lose = 0;
		this.gamePlayed = 0;
		this.selectedKit = -1;
		this.rejoinTime = 0;
		load();
		players.put(uuid, this);
	}
	
	public void load() {
		MySQLConfig config = UHCReloaded.getMysqlConfiguration();
		
		if (config.isEnabled()) {
			UpdateSQLTask task = TaskManager.updateSQLTask;
			
			if (task.isConnected()) {
				ResultSet rs = task.querySend("SELECT * FROM "+ config.getTable() + " WHERE uuid = '" + uuid.toString() + "'");
				if (rs != null) {
					try {
						while (rs.next()) {
							death = rs.getInt(2);
							kill = rs.getInt(3);
							win = rs.getInt(4);
							lose = rs.getInt(5);
							gamePlayed = rs.getInt(6);
							break;
						}
					} catch (SQLException e) { e.printStackTrace(); }
				}
			}
		}
	}
	
	public Boolean hasKit(Integer id) {
		KitConfig config = UHCReloaded.getKitConfiguration();
		UpdateSQLTask task = TaskManager.updateSQLTask;
		
		if (task.isConnected()) {
			ResultSet rs = task.querySend("SELECT * FROM "+ config.getKitPurchaseTable() + " WHERE uuid = '" + uuid.toString() + "'");
			
			try {
				while (rs.next()) {
					if (rs.getInt(2) == id)
						return true;
				}
			} catch (SQLException e) { e.printStackTrace(); }
			
			return false;
		} else
			return false;
	}
	
	public String getSelectedKitName() {
		Kit k = Kit.getKitByID(selectedKit);
		
		if (k == null)
			return "";
		else
			return k.getName();
	}
	
	public Boolean addKit(Integer id) {
		KitConfig config = UHCReloaded.getKitConfiguration();
		UpdateSQLTask task = TaskManager.updateSQLTask;
		
		if (task.isConnected()) {
			String insertQuery = "INSERT INTO " + config.getKitPurchaseTable() + " VALUES('" + uuid.toString() + "'," + id + ");";
			task.querySendWithoutResponse(insertQuery);
			return true;
		} else
			return false;
	}
	
	public void giveKit() {
		Kit k = Kit.getKitByID(selectedKit);
		
		if (k != null)
			k.give(getPlayer());
	}
	
	public void sendInvitation(Player invited) {
		UHCPlayer up = instanceOf(invited);
		up.getInvitations().add(this.getUUID());
	}
	
	public void acceptInvitation(Player inviter) {
		invitations.remove(inviter.getUniqueId());
	}
	
	public Boolean hasInvitation(Player inviter) {
		return invitations.contains(inviter.getUniqueId());
	}
	
	public void sendHeal(Integer amplificator, Integer time) {
		LangConfig config = UHCReloaded.getLangConfiguration();
		Player p = Bukkit.getPlayer(uuid);
		
		if (p.hasPotionEffect(PotionEffectType.REGENERATION)) {
			Boolean ok = false;
			for (PotionEffect pe : p.getActivePotionEffects()) {
				if (pe.getType().equals(PotionEffectType.REGENERATION) && pe.getAmplifier() == amplificator) {
					time += pe.getDuration() / 20;
					ok = true;
				}
			}
			
			if (ok) p.removePotionEffect(PotionEffectType.REGENERATION);
		}
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, time * 20, amplificator));
		Util.avert(p, config.getReceiveHeal());
	}
	
	public Player getPlayer() { return Bukkit.getPlayer(uuid); }
	public UUID getUUID() { return uuid; }
	public Integer getKill() { return kill; }
	public Integer getGameKill() { return gameKill; }
	public Integer getDeath() { return death; }
	public Integer getWin() { return win; }
	public Integer getLose() { return lose; }
	public Integer getGamePlayed() { return gamePlayed; }
	public Integer getSelectedKit() { return selectedKit; }
	public Integer getRejoinTime() { return rejoinTime; }
	public List<UUID> getInvitations() { return invitations; }
	
	public void setKill(Integer kill) { this.kill = kill; }
	public void setGameKill(Integer kill) { this.gameKill = kill; }
	public void setDeath(Integer death) { this.death = death; }
	public void setWin(Integer win) { this.win = win; }
	public void setLose(Integer lose) { this.lose = lose; }
	public void setGamePlayed(Integer gamePlayed) { this.gamePlayed = gamePlayed; }
	public void setRejoinTime(Integer rejoinTime) { this.rejoinTime = rejoinTime; }
	public void setSelectedKit(Integer id) { this.selectedKit = id; }
	
	
	public static UHCPlayer instanceOf(Player player) {
		return instanceOf(player.getUniqueId());
	}
	
	public static UHCPlayer instanceOf(UUID uuid) {
		if (!UHCPlayer.players.containsKey(uuid))
			new UHCPlayer(uuid);
		return UHCPlayer.players.get(uuid);
	}
}
