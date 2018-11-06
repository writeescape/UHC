package net.upd4ting.uhcreloaded.team;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.upd4ting.uhcreloaded.UHCPlayer;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.configuration.configs.TeamConfig;
import net.upd4ting.uhcreloaded.util.Util;
import net.upd4ting.uhcreloaded.util.Util.ActionMessage;

public class TeamCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		
		final Player p = (Player) sender;
		
		LangConfig config = UHCReloaded.getLangConfiguration();
		TeamConfig teamConfig = UHCReloaded.getTeamConfiguration();
		String teamPrefix = config.getTeamPrefix() + " ";
		
		if (!teamConfig.isEnabled())
			return false;
		
		if (teamConfig.isGUIEnabled()) {
			Util.sendConfigMessage(teamPrefix, p, config.getGuiActived());
			return true;
		}
		
		if (!UHCReloaded.getGame().isWaiting()) {
			Util.sendConfigMessage(teamPrefix, p, config.getCommandDisabled());
			return true;
		}
		
		if (args.length != 1 && args.length != 0) {
			Util.sendConfigMessage(teamPrefix, p, config.getWrongSyntax());
			return true;
		}
		
		// Help message
		if (args.length == 0) {
			for (String s : config.getHelpMessage())
				Util.sendConfigMessage("", p, s);
			return true;
		}
		
		// Leave command
		if (args[0].equals("leave")) {
			leaveTeam(p, true);
			return true;
		}
		
		String target = args[0];
		
		Player invited = Bukkit.getPlayer(target);
		
		// Pas en ligne
		if (invited == null || !invited.isOnline()) {
			Util.sendConfigMessage(teamPrefix, p, config.getPlayerNotOnline().replace("%p", target));
			return true;
		}
		
		UHCPlayer up = UHCPlayer.instanceOf(p);
		final UHCPlayer upi = UHCPlayer.instanceOf(invited);
		
		// On accepte l'invitation
		if (up.hasInvitation(invited)) {
			// On check si le gars qui invite a une team
			Team t = null;
			
			if (Team.hasTeam(invited))
				t = Team.getTeam(invited);
			else {
				t = Team.createTeam(invited.getName());
				t.join(invited);
			}
			
			// Le joueur qui rejoint on doit lui faire leave son autre team
			leaveTeam(p, false);
			
			// Ajout du joueur qui rejoint
			t.join(p);
			
			// Message a tout le monde
			t.joinMessage(p);
			
			// On accepte l'invit du coup
			up.acceptInvitation(invited);
			
			return true;
		}
		
		// Check si owner
		Team current = Team.getTeam(p);
		if (current != null && !current.getOwner().equals(p.getName())) {
			Util.sendConfigMessage(teamPrefix, p, config.getNotOwner());
			return true;
		}
		
		// Déja dans la team
		if (current != null && current.getPlayers().contains(invited)) {
			Util.sendConfigMessage(teamPrefix, p, config.getAlreadyInTeam().replace("%p", invited.getName()));
			return true;
		}
		
		// Déja invité
		if (upi.hasInvitation(p)) {
			Util.sendConfigMessage(teamPrefix, p, config.getAlreadyInvited().replace("%p", target));
			return true;
		}
		
		// On invite mais check si pas full + task de X secondes configurables !
		Integer curSize = Team.hasTeam(p) ? Team.getTeam(p).getPlayers().size() : 0;
		
		// Team Full
		if (curSize >= teamConfig.getTeamSize()) {
			Util.sendConfigMessage(teamPrefix, p, config.getTeamFull());
			return true;
		}
		
		up.sendInvitation(invited);
		Util.sendConfigMessage(teamPrefix, p, config.getSendInvitation().replace("%p", target).replace("%t", Integer.toString(teamConfig.getExpireTime())));
		Util.sendConfigMessage(teamPrefix, invited, config.getReceivedInvitation().replace("%p", p.getName()).replace("%t", Integer.toString(teamConfig.getExpireTime())));
		
		// Task pour supprimer l'invitation
		new BukkitRunnable() {
			@Override
			public void run() {
				if (upi.hasInvitation(p))
					upi.acceptInvitation(p);
			}
		}.runTaskLater(UHCReloaded.getInstance(), teamConfig.getExpireTime() * 20);
		
		return true;
	}
	
	public void leaveTeam(Player p, Boolean messageIfNotTeam) {
		LangConfig config = UHCReloaded.getLangConfiguration();
		String teamPrefix = config.getTeamPrefix() + " ";
		final Team current = Team.getTeam(p);
		
		// Si il est pas dans une team et qu'il essaie de leave on le préviens
		if (current == null) {
			if (messageIfNotTeam)
				Util.sendConfigMessage(teamPrefix, p, config.getNotInTeam());
		}
		else { // On lui fait quitter sa team et on redéfini le nouvelle owner
			
			// On le fait leave
			current.leave(p);
			// Message a sa team comme quoi il est parti
			current.leaveMessage(p);
			
			if (current.getPlayers().size() == 0) {
				Team.getTeams().remove(current);
			}
			else if (current.getOwner().equals(p.getName())) {
				// Nouveau owner
				String newOwner = current.getPlayers().get(0).getName();
				current.setOwner(newOwner);
				// Message a tout le monde
				final String message = teamPrefix + config.getNewTeamOwner().replace("%p", newOwner);
				Util.sendActionConfigMessage(message, new ActionMessage() {
					@Override
					public void run() {
						for (Player cur : current.getPlayers())
							cur.sendMessage(message);
					}
				});
			}
		}
	}

}
