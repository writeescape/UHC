package net.upd4ting.uhcreloaded.event.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCPlayer;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.EconomyConfig;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.configuration.configs.MainConfig;
import net.upd4ting.uhcreloaded.task.TaskManager;
import net.upd4ting.uhcreloaded.task.Timer;
import net.upd4ting.uhcreloaded.team.Team;
import net.upd4ting.uhcreloaded.util.Util;
import net.upd4ting.uhcreloaded.util.Util.ActionMessage;

public class EventDamage implements Listener {
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		Game game = UHCReloaded.getGame();
		
		if (game.isWaiting()) {
			e.setCancelled(true);
			return;
		}
		
		// Check si il va die ou non + cancel si damage pas actif
		if (e.getEntity() instanceof Player) {
			Player damaged = (Player) e.getEntity();
			
			if (game.isLimited(damaged)) {
				e.setCancelled(true);
				return;
			}
			
			Timer t = (Timer) TaskManager.getTask("DamageTask");
			
			// Anticipation des premiers dégats de chute
			if (e.getCause() == DamageCause.FALL && damaged.hasMetadata("nojumpdamage")) {
				e.setCancelled(true);
				damaged.removeMetadata("nojumpdamage", UHCReloaded.getInstance());
				damaged.setAllowFlight(false);
			}
			else if (t.isActive() || (!UHCReloaded.getMainConfiguration().isFallDamageEnabled() && e.getCause() == DamageCause.FALL))
				e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		Game game = UHCReloaded.getGame();
		if (game.isWaiting()) {
			e.setCancelled(true);
			return;
		}
		
		MainConfig config = UHCReloaded.getMainConfiguration();
		
		Entity entity = e.getEntity();
		Entity damager = e.getDamager();
		
		if (damager instanceof Player && game.isLimited((Player)damager)) {
			e.setCancelled(true);
			return;
		}
		
		// On cancel si la team est la même et que friendly fire desactive
		if (entity instanceof Player && (damager instanceof Player || (damager instanceof Projectile && ((Projectile)damager).getShooter() instanceof Player)) && !config.isFriendlyFireEnabled()) {
			
			Team t = Team.getTeam((Player)entity);
			if (t != null && t.getPlayers().contains(damager instanceof Player ? (Player) damager : (Player) ((Projectile)damager).getShooter()))
				e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDeath(final PlayerDeathEvent e) {
		MainConfig mainConfig = UHCReloaded.getMainConfiguration();
		EconomyConfig economyConfig = UHCReloaded.getEconomyConfiguration();
		final LangConfig langConfig = UHCReloaded.getLangConfiguration();
		final Game game = UHCReloaded.getGame();
		final Player p = e.getEntity();
		Player killer = p.getKiller();
		
		// Ajout du kill au killer
		if (killer != null) {
			UHCPlayer uhcp = UHCPlayer.instanceOf(killer);
			uhcp.setKill(uhcp.getKill() + 1);
			uhcp.setGameKill(uhcp.getGameKill() + 1);
			
			// Golden heads ( il faut qu'il se soit fait tué par quelqu'un )
			if (mainConfig.isGoldenHeadEnabled())
                e.getDrops().add(game.getGoldenHead(p));
			
			// Gain d'argent pour le killer
			if (economyConfig.isVaultEnabled()) {
				UHCReloaded.getEconomyHandler().depositPlayer(killer, economyConfig.getGainKill());
				Util.sendConfigMessage(killer, langConfig.getEarnOnKill().replace("%m", Integer.toString(economyConfig.getGainKill())));
			}
			
			// Message de mort
			Util.sendActionConfigMessage(langConfig.getDeathByPlayer(), new ActionMessage() {
				@Override
				public void run() {
					e.setDeathMessage(UHCReloaded.getPrefix() + langConfig.getDeathByPlayer().replaceAll("%p", p.getName()).replaceAll("%k", killer.getName()));
				}
			});
		} else {
			// Message de mort
			Util.sendActionConfigMessage(langConfig.getDeath(), new ActionMessage() {
				@Override
				public void run() {
					e.setDeathMessage(UHCReloaded.getPrefix() + langConfig.getDeath().replaceAll("%p", p.getName()));
				}
			});
		}
		
		// On fait loose le gars
		game.lose(p);
	}
}
