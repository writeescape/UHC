package net.upd4ting.uhcreloaded;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.upd4ting.uhcreloaded.configuration.configs.BiomesConfig.Swap;
import net.upd4ting.uhcreloaded.configuration.configs.CommandConfig;
import net.upd4ting.uhcreloaded.configuration.configs.EconomyConfig;
import net.upd4ting.uhcreloaded.configuration.configs.KitConfig;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.configuration.configs.MainConfig;
import net.upd4ting.uhcreloaded.configuration.configs.MySQLConfig;
import net.upd4ting.uhcreloaded.configuration.configs.TeamConfig;
import net.upd4ting.uhcreloaded.configuration.configs.TimerConfig;
import net.upd4ting.uhcreloaded.event.events.EventChunkUnload;
import net.upd4ting.uhcreloaded.event.events.Frozen;
import net.upd4ting.uhcreloaded.item.Items;
import net.upd4ting.uhcreloaded.loader.WorldFillTask;
import net.upd4ting.uhcreloaded.schematic.Schematic;
import net.upd4ting.uhcreloaded.schematic.Schematic.BlockInfo;
import net.upd4ting.uhcreloaded.schematic.Schematic.SchematicEvent;
import net.upd4ting.uhcreloaded.task.Task;
import net.upd4ting.uhcreloaded.task.TaskManager;
import net.upd4ting.uhcreloaded.task.Timer;
import net.upd4ting.uhcreloaded.task.tasks.TeleportationTask;
import net.upd4ting.uhcreloaded.task.tasks.UpdateSQLTask;
import net.upd4ting.uhcreloaded.team.Team;
import net.upd4ting.uhcreloaded.util.Util;
import net.upd4ting.uhcreloaded.util.Util.Callback;
import net.upd4ting.uhcreloaded.util.Util.TeleportRunnable;

@SuppressWarnings("deprecation")
public class Game {
	public static enum GameState { LOADING, WAITING, STARTED, FINISH }
	
	private GameState state;
	private Location spawn;
	private WorldFillTask fillTask;
	private List<UUID> players = new ArrayList<>(); // List of all player that ever joined the game at the beggining
	private List<UUID> playing = new ArrayList<>(); // List of player currently playing
	private List<UUID> specs = new ArrayList<>(); // List of player currently spectating
	private List<Location> spawns = new ArrayList<>();
	private List<Location> spawnsDm = new ArrayList<>();
	
	public Game() {
		this.state = GameState.LOADING;
		
		// -- Prepare worlds
		prepareWorld();
	}
	
	public void unloadWorld() {
        for (World w : getWorlds()) 
        	Bukkit.getServer().unloadWorld(w, false);
	}
	
	public void disableSaving() {
		for (World w : getWorlds())
			w.setAutoSave(false);
	}
	
	public void join(Player p, String data) {
		if (data.equals("normal")) {
			playing.add(p.getUniqueId());
			
			if (playing.size() > spawns.size())
				loadSpawn();
		}
		else if (data.equals("spectator"))
			specs.add(p.getUniqueId());
		else if (data.equals("rejoin")) {
			playing.add(p.getUniqueId());
			UHCPlayer uhcp = UHCPlayer.instanceOf(p);
			uhcp.setRejoinTime(uhcp.getRejoinTime() + 1);
		}
	}
	
	public void leave(Player p) {
		if (playing.contains(p.getUniqueId()))
			playing.remove(p.getUniqueId());
		if (specs.contains(p.getUniqueId()))
			specs.remove(p.getUniqueId());
	}
	
	public void initialiseWb(Boolean tp) {
		UHCReloaded.getNMSHandler().getBorderHandler().initialize(tp);
	}
	
	public void generateLobby() {
		final MainConfig config = UHCReloaded.getMainConfiguration();
		final TimerConfig timerConfig = UHCReloaded.getTimerConfiguration();
		
		if (config.isSchematicEnabled()) {
			final Game game = UHCReloaded.getGame();
			Location loc = new Location(Bukkit.getWorlds().get(0), 100, 150, timerConfig.getWbSizeStart() + 200);
			File schematicFile = new File(UHCReloaded.getInstance().getDataFolder() + "/lobby.schematic");
			
			Schematic schematic = new Schematic(schematicFile, new SchematicEvent() {
				@Override
				public BlockInfo onPaste(Location loc, BlockInfo info) {
					if (info.material != null && info.material.getId() == config.getSchematicBlock()) {
						Logger.log(Logger.LogLevel.INFO, "We found the specified spawn location.");
						game.setSpawn(loc);
						info.material = Material.AIR;
					}
					
					return info;
				}

				@Override
				public void onPasteEnd() {
					startLoadChunks();
				}

				@Override
				public void onFileNotFound() {
					Logger.log(Logger.LogLevel.ERROR, "Unable to find 'lobby.schematic' !");
					Bukkit.shutdown();
				}
				
			});
			
			schematic.paste(loc, true);
		} else
			startLoadChunks();
	}
	
	private void startLoadChunks() {
		MainConfig config = UHCReloaded.getMainConfiguration();
		TimerConfig timerConfig = UHCReloaded.getTimerConfiguration();
		
		Callback callback = new Callback() {
			@Override
			public void run() {
				Game game = UHCReloaded.getGame();
				game.setState(GameState.WAITING);
			}
		};
		
		this.fillTask = new WorldFillTask(Bukkit.getServer(), Bukkit.getWorlds().get(0).getName(), timerConfig.getWbSizeStart(), config.getChunkPerInterval(), callback);
		if (fillTask.valid())
		{
			int task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(UHCReloaded.getInstance(), fillTask, config.getInterval(), config.getInterval());
			fillTask.setTaskID(task);
			Logger.log(Logger.LogLevel.INFO, "World map generation task started.");
		}
		else
			Logger.log(Logger.LogLevel.ERROR, "The world map generation task failed to start.");
	}
	
	public void startGame() {
		TeamConfig teamConfig = UHCReloaded.getTeamConfiguration();
		EconomyConfig economyConfig = UHCReloaded.getEconomyConfiguration();
		LangConfig langConfig = UHCReloaded.getLangConfiguration();
		
		state = GameState.STARTED;
		
		// On set les teams correctements
		Team.fillTeam();
		if ((!teamConfig.isGUIEnabled() && teamConfig.isEnabled()) || !teamConfig.isEnabled())
			Team.setNameTag();
		
		// Gamerule
		if (UHCReloaded.getMainConfiguration().isEternalDayEnabled())
			Bukkit.getWorlds().get(0).setGameRuleValue("doDaylightCycle", "false");
		
		// Pas de regen naturelle !
		for (World w : getWorlds())
			w.setGameRuleValue("naturalRegeneration", "false");
		
		// On save les insides dans les players
		players = new ArrayList<>(this.playing);
		
		// On leurs ajoute tous une game joué
		for (OfflinePlayer p : getPlayers()) {
			UHCPlayer uhcp = UHCPlayer.instanceOf(p.getUniqueId());
			uhcp.setGamePlayed(uhcp.getGamePlayed() + 1);
			
			if (p.isOnline() && economyConfig.isVaultEnabled()) { // Ajout gain participation
				UHCReloaded.getEconomyHandler().depositPlayer(p, economyConfig.getGainParticipate());
				Util.sendConfigMessage(p.getPlayer(), langConfig.getEarnOnParticipate().replace("%m", Integer.toString(economyConfig.getGainParticipate())));
			}
		}
		
		// Start les téléportations
		new TeleportationTask(false);
	}

	public void setSpectator(Player p) {
		MainConfig config = UHCReloaded.getMainConfiguration();
		LangConfig langConfig = UHCReloaded.getLangConfiguration();
		
		if (!specs.contains(p.getUniqueId()))
			specs.add(p.getUniqueId());
		
		if (config.isSpectatorOld()) {
			p.setGameMode(GameMode.CREATIVE);
			p.setAllowFlight(true);
			p.setFlying(true);
			
			for (Player pl : getAll())
				pl.hidePlayer(p);
			
			Items.TELEPORTER.give(p, 0);
			
			Team.setNameTagSpectator(p);
		} else {
			p.setGameMode(GameMode.SPECTATOR);
		}
		
		Items.BED.give(p, 8);
		
		p.updateInventory();
		
		Util.sendConfigMessage(p, langConfig.getJoinSpec());
	}
	
	public void lose(final Player p) {
		final MainConfig config = UHCReloaded.getMainConfiguration();
		
		playing.remove(p.getUniqueId());
		
		// Ajout d'une mort dans les stats
		UHCPlayer uhcp = UHCPlayer.instanceOf(p);
		uhcp.setDeath(uhcp.getDeath() + 1);
		
		// Check win
		checkWin();
		
		new BukkitRunnable() {
			@Override
			public void run() {
				// Respawn
				UHCReloaded.getNMSHandler().getRespawnHandler().respawn(p);
				
				// Mettre en spectateur ou non
				if (config.isSpectatorEnabled() && (config.getSpectatorPermission().equals("none") || 
														p.hasPermission(config.getSpectatorPermission()))) {
					setSpectator(p);
				} else
					returnToHub(p);
			}
		}.runTaskLater(UHCReloaded.getInstance(), 1);
	}
	
	public void revive(Player p) {
		final TeamConfig teamConfig = UHCReloaded.getTeamConfiguration();
		LangConfig langConfig = UHCReloaded.getLangConfiguration();
		
		specs.remove(p.getUniqueId());
		playing.add(p.getUniqueId());

		List<Material> mat = new ArrayList<>(Arrays.asList(Material.LEAVES, Material.LEAVES_2, Material.WATER, Material.LAVA, Material.LEAVES, Material.AIR, Material.WATER_LILY, Material.STATIONARY_WATER, Material.STATIONARY_LAVA));
		Location randomLocation = Util.getRandomLocation(p.getWorld(), UHCReloaded.getNMSHandler().getBorderHandler().getSize().intValue(), 
				true, mat);
		
		Util.teleportPlayer(p, randomLocation, new TeleportRunnable() {

			@Override
			public void run(Player p) {
				// On vide son inventaire
				p.getInventory().clear();
				p.updateInventory();
				
				// On le met en survie et on le raffiche a tout le monde (Si spectator mode
				// = mode gm3 on avait pas cacher le joueur mais pas grave on le raffiche quand meme
				p.setGameMode(GameMode.SURVIVAL);
				for (Player pl : UHCReloaded.getGame().getAll())
					pl.showPlayer(p);

		        // Anticiper les prochains dégats de chute
		        p.setMetadata("nojumpdamage", new FixedMetadataValue(UHCReloaded.getInstance(), "nojumpdamage"));
		        
				// Pour fix bug du name dans le tab
				if (!teamConfig.isEnabled())
					Team.setNameTag(p);
				else {
					Team t = Team.getTeam(p);
					t.leave(p);
					t.join(p);
				}
			}
			
		});
		
		UHCReloaded.getNMSHandler().getTitleHandler().sendTitle(p, "", langConfig.getRevived());
	}
	
	public void checkWin() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (playing.size() == 0)
					closeGame();
				else if (playing.size() == 1)
					win(getPlaying().get(0));
				else {
					if (getTeamAlive() == 1)
						win(Team.getTeam(getPlaying().get(0)));
				}
			}
		}.runTaskLater(UHCReloaded.getInstance(), 1);
	}
	
	public void win(Player p) {
		EconomyConfig economyConfig = UHCReloaded.getEconomyConfiguration();
		LangConfig langConfig = UHCReloaded.getLangConfiguration();
		CommandConfig commandConfig = UHCReloaded.getCommandConfiguration();
		
		Util.avertAll(UHCReloaded.getLangConfiguration().getPlayerWin().replace("%p", p.getName()));
		
		new FireworkTask(p).runTaskTimer(UHCReloaded.getInstance(), 0, 15);
		
		// Ajout d'une win
		UHCPlayer uhcp = UHCPlayer.instanceOf(p);
		uhcp.setWin(uhcp.getWin() + 1);
		
		// Gain d'argent pour le winner
		if (economyConfig.isVaultEnabled()) {
			UHCReloaded.getEconomyHandler().depositPlayer(p, economyConfig.getGainWin());
			Util.sendConfigMessage(p, langConfig.getEarnOnWin().replace("%m", Integer.toString(economyConfig.getGainWin())));
		}
		
		// Ajout d'une los
		for (OfflinePlayer pl : getPlayers()) {
			if (pl != p) {
				UHCPlayer uhcpp = UHCPlayer.instanceOf(pl.getUniqueId());
				uhcpp.setLose(uhcpp.getLose() + 1);
			}
		}
		
		// Execution des commandes
		commandConfig.checkExecuteCommand(p);
		
		closeGame();
	}
	
	public void win(Team team) {
		EconomyConfig economyConfig = UHCReloaded.getEconomyConfiguration();
		LangConfig langConfig = UHCReloaded.getLangConfiguration();
		CommandConfig commandConfig = UHCReloaded.getCommandConfiguration();
		StringBuilder sb = new StringBuilder();
		
		// Construction du message + ajout d'une win
		for (Player p : team.getPlayers()) {
			sb.append(p.getName() + ",");
			UHCPlayer uhcp = UHCPlayer.instanceOf(p);
			uhcp.setWin(uhcp.getWin() + 1);
			
			// Gain d'argent pour le winner
			if (economyConfig.isVaultEnabled()) {
				UHCReloaded.getEconomyHandler().depositPlayer(p, economyConfig.getGainWin());
				Util.sendConfigMessage(p, langConfig.getEarnOnWin().replace("%m", Integer.toString(economyConfig.getGainWin())));
			}
			
			// Execution des commandes
			commandConfig.checkExecuteCommand(p);
		}
		
		// Ajout d'une lose
		for (OfflinePlayer p : getPlayers()) {
			if (!team.getPlayers().contains(p)) {
				UHCPlayer uhcp = UHCPlayer.instanceOf(p.getUniqueId());
				uhcp.setLose(uhcp.getLose() + 1);
			}
		}
		
		String message = sb.toString();
		Util.avertAll(UHCReloaded.getLangConfiguration().getTeamWin().replace("%winners", message.substring(0, message.length() - 2)));
		
		new FireworkTask(team.getPlayers()).runTaskTimer(UHCReloaded.getInstance(), 0, 15);
		
		closeGame();
	}
	
	public void closeGame() {
		TimerConfig timerConfig = UHCReloaded.getTimerConfiguration();
		MySQLConfig mysqlCOnfig = UHCReloaded.getMysqlConfiguration();
		final MainConfig config = UHCReloaded.getMainConfiguration();
		
		state = GameState.FINISH;
		
		displayLeaderboard();
		
		// Fermeture du MYSQL
		if (mysqlCOnfig.isEnabled()) {
			UpdateSQLTask task = TaskManager.updateSQLTask;
			task.close();
		}
		
		new BukkitRunnable() {
			@Override
			public void run() {
				// Renvoi au lobby
				new BukkitRunnable() {
					@Override
					public void run() {
						if (getAll().size() <= 0) {
							cancel();
							return;
						}
						returnToHub(getAll().get(0));
					}
				}.runTaskTimer(UHCReloaded.getInstance(), 0, 1);
				
				new BukkitRunnable() {
					@Override
					public void run() { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), config.getCommandRestartServer()); }
				}.runTaskLater(UHCReloaded.getInstance(), 40);
			}
		}.runTaskLater(UHCReloaded.getInstance(), timerConfig.getTimeToShutdown() * 20);
	}
	
	public void displayLeaderboard() {
		final LangConfig config = UHCReloaded.getLangConfiguration();
		
		ArrayList<UUID> players = new ArrayList<>(UHCPlayer.players.keySet());
		Collections.sort(players, killHightFirst);
		
		for(int i = 0; i <= 1; i++)
			Bukkit.broadcastMessage("  ");
		Util.sendConfigBroadcast(config.getLeaderboard());
		
		for (int i = 0; i < 3 && i < players.size(); i++) {
			UUID uuid = players.get(i);
			Bukkit.broadcastMessage(ChatColor.GRAY + "" + (i+1) + ". " + ChatColor.YELLOW + Bukkit.getOfflinePlayer(uuid).getName()
					+ ChatColor.RED + " " + UHCPlayer.instanceOf(uuid).getGameKill());
		}
	}
	
	private Comparator<UUID> killHightFirst = new Comparator<UUID>() {

		@Override
		public int compare(UUID p1, UUID p2) {
			return UHCPlayer.instanceOf(p2).getGameKill() - UHCPlayer.instanceOf(p1).getGameKill();
		}
		
	};
	
	public void giveItemWaiting(Player p) {
		MainConfig mainConfig = UHCReloaded.getMainConfiguration();
		TeamConfig teamConfig = UHCReloaded.getTeamConfiguration();
		KitConfig kitConfig = UHCReloaded.getKitConfiguration();
		
		p.getInventory().clear();
		
		Items.BED.give(p, 8);
		if (teamConfig.isGUIEnabled())
			Items.TEAM.give(p, teamConfig.getGuiSlotItem());
		if (kitConfig.isKitEnabled())
			Items.KIT.give(p, kitConfig.getItemSlot());
		
		p.updateInventory();
		
        p.setHealthScaled(true);
        p.setHealthScale(mainConfig.getDefaultHealthPlayer());
        p.setMaxHealth(mainConfig.getDefaultHealthPlayer());
        p.setHealth(mainConfig.getDefaultHealthPlayer());
	}
	
	public void returnToHub(Player p) {
		MainConfig mainConfig = UHCReloaded.getMainConfiguration();
		
		if (mainConfig.isLobbyEnabled()) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(mainConfig.getLobbyName());
            p.sendPluginMessage(UHCReloaded.getInstance(), "BungeeCord", out.toByteArray());
		}
		else
			p.kickPlayer("");
	}
	
	public void freeze(Player p) {
		if (!Frozen.isFrozen(p)) {
			Frozen.frozen.add(p);
			Frozen.fireticks.put(p, p.getFireTicks());
			Frozen.effects.put(p, p.getActivePotionEffects());
			for (PotionEffect pe : p.getActivePotionEffects()) {
				p.removePotionEffect(pe.getType());
			}
			p.setFireTicks(0);
			p.setFlySpeed(0);
			p.setAllowFlight(true);
			p.setFlying(true);
			p.teleport(p.getLocation().add(0, 0.01, 0));
		}
	}
	
	public void unfreeze(Player p) {
		if (Frozen.isFrozen(p)) {
			Frozen.frozen.remove(p);
			p.setFireTicks(Frozen.fireticks.get(p));
			Frozen.fireticks.remove(p);
			for (PotionEffect pe : Frozen.effects.get(p)) {
				pe.apply(p);
			}
			Frozen.effects.remove(p);
			p.setFlySpeed(0.1f);
			p.setWalkSpeed(0.2f);
			p.setFlying(false);
			p.setAllowFlight(false);
		}
	}
	
	public void unfreezeAll() {
		for (Player p : getPlaying())
			unfreeze(p);
	}
	
	public Integer getTeamAlive() {
		Integer number = 0;
		for (Team t : Team.getTeams()) {
			if (t.getPlayerAlive() > 0)
				number++;
		}
		return number;
	}
    
    public Boolean hasTimersActive() {
    	for (Timer t : getTimers()) 
    		if (t.isActive()) 
    			return true;
    	return false;
    }
    
	public List<World> getWorlds() {
		List<World> worlds = new ArrayList<>();
		Integer i = 0;
		worlds.add(Bukkit.getWorlds().get(i++));
		if (Bukkit.getAllowNether()) 
			worlds.add(Bukkit.getWorlds().get(i++));
		if (Bukkit.getAllowEnd()) 
			worlds.add(Bukkit.getWorlds().get(i++));
		
		if (UHCReloaded.getTimerConfiguration().isDmEnabled()) {
			String world = UHCReloaded.getTimerConfiguration().getDmWorldName();
			
			Boolean exist = false;
			
			for (World w : worlds)
				if (w.getName().equals(world))
					exist = true;
			
			if (!exist)
				worlds.add(Bukkit.getWorld(world));
		}
		
		return worlds;
	}
	
	public void loadSpawn() {
		TimerConfig config = UHCReloaded.getTimerConfiguration();
		World w = Bukkit.getWorlds().get(0);
		World dmWorld = Bukkit.getWorld(config.getDmWorldName());
		
		List<Material> mat = new ArrayList<>(Arrays.asList(Material.LEAVES, Material.LEAVES_2, Material.WATER, Material.LAVA, Material.LEAVES, Material.AIR, Material.WATER_LILY, Material.STATIONARY_WATER, Material.STATIONARY_LAVA));
		
		Location tp = Util.getRandomLocation(w, config.getWbSizeStart() - 10, true, mat);
		
		EventChunkUnload.keepChunk.addAll(Util.loadChunks(tp, 50, false));
		
		spawns.add(tp);

		//Chargement spawn pour le deathmatch
		if (config.isDmEnabled()) {
			Location tp2 = Util.getRandomLocation(dmWorld, config.getDmSizeStart() - 10, true, mat);
			
			EventChunkUnload.keepChunk.addAll(Util.loadChunks(tp, 50, false));
			
			spawnsDm.add(tp2);
		}
	}
	
	public ItemStack getGoldenHead(Player p) {
		LangConfig langConfig = UHCReloaded.getLangConfiguration();
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(p.getName());
        meta.setDisplayName(langConfig.getGoldenHead());
        skull.setItemMeta(meta);
        return skull;
	}
	
	private void prepareWorld() {
        // -- Delete des worlds
		deleteWorld(new File(Bukkit.getWorldContainer(), "world"));
		deleteWorld(new File(Bukkit.getWorldContainer(), "world_nether"));
		
		Bukkit.getServer().setSpawnRadius(0);
		
		// -- Execute Swaps Biomes
		if (UHCReloaded.getBiomesConfiguration().isSwapEnabled())
			for (Swap swap : UHCReloaded.getBiomesConfiguration().getSwaps())
				swap.execute(UHCReloaded.getBiomesConfiguration().getRandomable());
	}
	
	private Boolean deleteWorld(File f) {
        if (f.exists() && f.isDirectory()) {
            File[] listFiles;
            for (int length = (listFiles = f.listFiles()).length, i = 0; i < length; i++) {
                File f2 = listFiles[i];
                if (!deleteWorld(f2)) {
                    return false;
                }
            }
        }
        return f.delete();
	}
	
	public void setState(GameState newState) { state = newState; }
	public void setSpawn(Location nSpawn) { spawn = nSpawn; }
	public Location getSpawn() { return spawn; }
	public GameState getState() { return state; }
	public WorldFillTask getFillTask() { return fillTask; }
	public Boolean isLoading() { return state == GameState.LOADING; }
	public Boolean isWaiting() { return state == GameState.WAITING; }
	public Boolean isInGame() { return state == GameState.STARTED; }
	public Boolean isFinished() { return state == GameState.FINISH; }
	public Boolean isLimited(Player p) { return !isInGame() || getSpectators().contains(p); }
	public List<OfflinePlayer> getPlayers() { 
		List<OfflinePlayer> l = new ArrayList<>();
		for (UUID u : players)
			l.add(Bukkit.getOfflinePlayer(u));
		return l;
	}
	public List<Player> getAll() {
		List<Player> list = new ArrayList<>();
		list.addAll(getPlaying());
		list.addAll(getSpectators());
		return list;
	}
	public List<Player> getPlaying() { 
		return getListPlayer(playing);
	}
	public List<Player> getSpectators() { 
		return getListPlayer(specs);
	}
	public List<Timer> getTimers() { 
		List<Timer> list = new ArrayList<>();
		for (Task t : TaskManager.tasks) {
			if (t instanceof Timer)
				list.add((Timer)t);
		}
		return list;
	}
	public List<Location> getSpawns() { return spawns; }
	public List<Location> getSpawnsDm() { return spawnsDm; }
	
	private List<Player> getListPlayer(List<UUID> l) {
		List<Player> list = new ArrayList<>();
		for (UUID u : new ArrayList<>(l)) {
			Player p = Bukkit.getPlayer(u);
			if (p == null || !p.isOnline())
				playing.remove(u);
			else
				list.add(p);
		}
		return list;
	}
}
