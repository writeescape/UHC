package net.upd4ting.uhcreloaded.task.tasks;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Lists;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCPlayer;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.KitConfig;
import net.upd4ting.uhcreloaded.configuration.configs.TeamConfig;
import net.upd4ting.uhcreloaded.configuration.configs.TimerConfig;
import net.upd4ting.uhcreloaded.event.events.EventChunkUnload;
import net.upd4ting.uhcreloaded.item.Item;
import net.upd4ting.uhcreloaded.nms.NMSHandler;
import net.upd4ting.uhcreloaded.team.Team;
import net.upd4ting.uhcreloaded.util.Util;
import net.upd4ting.uhcreloaded.util.Util.TeleportRunnable;

public class TeleportationTask extends BukkitRunnable {
	private ArrayList<Player> players = new ArrayList<>();
	private Integer TP_SECOND = 10;
	private Integer count;
	private Boolean team;
	private Boolean dm; 
	
	private TeleportRunnable runnable = new TeleportRunnable() {

		@Override
		public void run(Player p) {
	        TimerConfig timerConfig = UHCReloaded.getTimerConfiguration();
	        KitConfig kitConfig = UHCReloaded.getKitConfiguration();
			
			UHCReloaded.getGame().freeze(p);
			
			//-- Give STUFF
			p.getInventory().clear();
			p.setAllowFlight(false);
			p.setFlying(false);
	        p.setGameMode(GameMode.SURVIVAL);
	        
	        p.setLevel(0);
	        
	        // On clear ses items
	        Item.clear(p);
	        
	        // Kit
	        if (kitConfig.isKitEnabled())
	        	UHCPlayer.instanceOf(p).giveKit();
	        
	        // Anticiper les prochains dégats de chute
	        p.setMetadata("nojumpdamage", new FixedMetadataValue(UHCReloaded.getInstance(), "nojumpdamage"));
	        
	        p.updateInventory();
	        p.closeInventory();
	        
	        if (timerConfig.isSpeedEnabled())
	        	p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, timerConfig.getSpeedTime() * 20, timerConfig.getSpeedValue() - 1, timerConfig.getParticleEffect(), timerConfig.getParticleEffect()));
			
			players.remove(p);
		}
		
	};

	public TeleportationTask(Boolean dm) {
		TeamConfig config = UHCReloaded.getTeamConfiguration();
		
		this.team = config.isEnabled();
		this.players = Lists.newArrayList(UHCReloaded.getGame().getPlaying());
		this.count = team ? (TP_SECOND / config.getTeamSize()) +1 : TP_SECOND;
		this.dm = dm;
		this.runTaskTimer(UHCReloaded.getInstance(), 0, 20 / count);
	}

	public void run() {
		Game game = UHCReloaded.getGame();
		
		if (!players.isEmpty()) {
        	if (team) {
        		Player player = players.get(0);
        		
        		if (player == null || !player.isOnline()) {
        			players.remove(0);
        			return;
        		}
        		
        		Location spawn = dm ? game.getSpawnsDm().remove(0) : game.getSpawns().remove(0);
        		Team team = Team.getTeam(player);
        		
        		if (team == null) // Should not happen...
        			return;
        		
        		Util.teleportListPlayer(team.getPlayers(), spawn, 5, dm ? new TeleportRunnable() {
					@Override
					public void run(Player p) {
				        // Anticiper les prochains dégats de chute
				        p.setMetadata("nojumpdamage", new FixedMetadataValue(UHCReloaded.getInstance(), "nojumpdamage"));
				       
				        players.remove(p);
					}
        		}: runnable);
        	} else {
				Player p = players.get(0);
				
        		if (p == null || !p.isOnline()) {
        			players.remove(0);
        			return;
        		}
        		
				Location l = dm ? game.getSpawnsDm().remove(0) : game.getSpawns().remove(0);
				Util.teleportPlayer(p, l, dm ? new TeleportRunnable() {
					@Override
					public void run(Player p) {
				        // Anticiper les prochains dégats de chute
				        p.setMetadata("nojumpdamage", new FixedMetadataValue(UHCReloaded.getInstance(), "nojumpdamage"));
					
				        players.remove(p);
					}
				}: runnable);
        	}
		}
		if (players.isEmpty()) {
        	if (!dm) {
        		game.unfreezeAll();
        		EventChunkUnload.keepChunk.clear();
        		Util.avertAll(UHCReloaded.getLangConfiguration().getGameStart());
        	}
        	
			// Initialisation de la WB
			game.initialiseWb(dm);
			
			Util.playSoundToAll(NMSHandler.isBasicSound() ? "EXPLODE" : "ENTITY_GENERIC_EXPLODE");
			this.cancel();
		}
	}
}
