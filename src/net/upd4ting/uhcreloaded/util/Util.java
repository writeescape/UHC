package net.upd4ting.uhcreloaded.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.upd4ting.uhcreloaded.Logger;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.configuration.configs.MainConfig;

public class Util {
	// Some util function
	
	// Generate the chunks around a Location
	public static List<Chunk> loadChunks(Location loc, Integer radius, Boolean load) {
		List<Chunk> chunks = new ArrayList<>();
		
		int cx = loc.getBlockX() - radius;
		int cz = loc.getBlockZ() - radius;
		
		while (cx <= loc.getBlockX() + radius) {
			while (cz <= loc.getBlockZ() + radius) {
				Location l = new Location(loc.getWorld(), cx, 0, cz);
				if (load && !l.getChunk().isLoaded())
					l.getWorld().loadChunk(l.getChunk().getX(), l.getChunk().getZ(), true);
				chunks.add(l.getChunk());
				cz += 16;
			}
			cz = loc.getBlockZ() - radius;
			cx += 16;
		}
		
		return chunks;
	}
	
	public static File downloadFile(String websiteUrl, String fileName) {
		try {
			Class.forName("org.apache.commons.io.FileUtils");
			File file = new File(UHCReloaded.getInstance().getDataFolder().getPath() + 
					File.separatorChar + "bonus", fileName);
			FileUtils.copyURLToFile(new URL(websiteUrl + fileName), file);
			return file;
		} catch (IOException | ClassNotFoundException e) {
			Logger.log(Logger.LogLevel.ERROR, "Unable to download bonus file: " + fileName);
			Logger.log(Logger.LogLevel.ERROR,  "Install org apache commons on your environment!");
			e.printStackTrace();
			return null;
		}
	}
	
	public static void playSoundToAll(String nameSound) {
		for (Player p : UHCReloaded.getGame().getAll())
			playSound(p, nameSound);
	}
	
	public static void playSound(Player p, String nameSound) {
		try {
			Field f = Sound.class.getDeclaredField(nameSound);
			f.setAccessible(true);
			Sound sound = (Sound) f.get(null);
			p.playSound(p.getLocation(), sound, 1.0F, 1.0F);
		} catch (IllegalAccessException|IllegalArgumentException|NoSuchFieldException e) { e.printStackTrace(); }
	}
	
	public static void dropItem(Location loc, ItemStack[] contents) {
		for (ItemStack i : contents)
			if (i != null && i.getType() != Material.AIR)
				loc.getWorld().dropItemNaturally(loc, i);
	}
	
	public static void avertAll(String message) {
		for (Player p : UHCReloaded.getGame().getAll())
			avert(p, message);
	}
	
	public static void avert(final Player p, final String message) {
		sendActionConfigMessage(message, new ActionMessage() {
			@Override
			public void run() {
				p.sendMessage(UHCReloaded.getPrefix() + message);
				UHCReloaded.getNMSHandler().getTitleHandler().sendTitle(p, "", message);
			}
		});
	}
	
	public static String generateRandomString() {
		char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 
				'n', 'o', 'p', 'q', 'r', 's', 'u', 'v', 'w', 'x', 'y', 'z' };
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		
		for (int i = 0; i < 10; i++) {
			int index = r.nextInt(25);
			sb.append(letters[index]);
		}
		
		return sb.toString();
	}
	
    public static void logZ(String message){
    	Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', UHCReloaded.getPrefix())+ message);
    }
    
    public static Set<Vector> getCircle(Double radius, Integer amount, Boolean full, Double space) {
		Set<Vector> list = new HashSet<>();
		
		Double increment = (2 * Math.PI) / amount;
		
		for (Double angle = 0d; angle <= 2 * Math.PI; angle += increment) {
			Double x = radius * Math.cos(angle);
			Double z = radius * Math.sin(angle);
			list.add(new Vector(x.intValue(), 0, z.intValue()));
		}
		
		if (full) {
			while(radius > 0) {
				radius -= space;
				
				for (Double angle = 0d; angle <= 2 * Math.PI; angle += increment) {
					Double x = radius * Math.cos(angle);
					Double z = radius * Math.sin(angle);
					list.add(new Vector(x.intValue(), 0, z.intValue()));
				}
			}
		}
		
		return list;
	}
    
	public static Location getRandomLocation(World w, int size, Boolean up, List<Material> mat) {
		for (int i = 0; i <= 100; i++) {
			int x = new Interval<Integer>(-size, size).getAsRandomInt();
			int z = new Interval<Integer>(-size, size).getAsRandomInt();

			Location loc = new Location(w, x, 0, z);
			loc = w.getHighestBlockAt(loc).getLocation();
			
			if (loc != null && loc.getY() <= 100 && !mat.contains(loc.getBlock().getRelative(BlockFace.DOWN).getType()))
				if (up)
					return loc.add(0, 40, 0);
				else
					return loc;
		}
		
		if (mat.size() > 0) {
			mat.remove(0);
			return getRandomLocation(w, size, up, mat);
		} else {
			return null;
		}
	}
    
	public static void teleportPlayer(Player p, Location loc, TeleportRunnable runnable) {
		p.teleport(loc);
		if (runnable != null)
			runnable.run(p);
	}
	
	public static void teleportListPlayer(List<Player> list, Location center, Integer radius, TeleportRunnable runnable) {
		Integer i = 0;
		Set<Vector> spreadSpawns = getCircle(5d, list.size(), false, -1D);
		for (Player p : list) {
			Vector v = (Vector) spreadSpawns.toArray()[list.indexOf(p)];
			Location loc = new Location(center.getWorld(), center.getBlockX() + v.getX(), center.getBlockY() + v.getY(), center.getBlockZ() + v.getZ());
			teleportPlayer(p, loc, runnable);
			i++;
		}
	}
	
    public static ChatColor getColor(Player p) {
    	MainConfig config = UHCReloaded.getMainConfiguration();
    	
        if (!p.isOnline() || !UHCReloaded.getGame().getPlaying().contains(p))
            return ChatColor.GRAY;
        else if (p.getHealth() >= config.getDefaultHealthPlayer() / 2.0)
            return ChatColor.GREEN;
        return ChatColor.RED;
    }
	
	public static interface TeleportRunnable {		
		public void run(Player p);
	}
	
	public static String getDirection(Player me, Player team) {
		return getDirection(me, team.getLocation());
	}
	
	public static String getDirection(Player me, Location to) {
		LangConfig config = UHCReloaded.getLangConfiguration();
		Vector vMe = me.getEyeLocation().getDirection().setY(0);
		Vector vTeam = to.toVector().subtract(me.getLocation().toVector()).normalize().setY(0);
		
		Double angle = Math.toDegrees(vMe.angle(vTeam));
		
		while (angle < 0) // Au cas ou sa return des trucs en néfatifs
			angle += 360;
		
		if (angle <= 45) // Devant
			return config.getUpArrow();
		else if (angle > 45 && angle <= 135) // Gauche
			if (vTeam.crossProduct(vMe).getY() > 0)
				return config.getRightArrow();
			else
				return config.getLeftArrow();
		else if (angle > 135)
			return config.getDownArrow();
		
		return null;
	}
	
	public static void sendConfigBroadcast(final String message) {
		sendActionConfigMessage(message, new ActionMessage() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(UHCReloaded.getPrefix() + message);
			}
		});
	}
	
	public static void sendConfigMessage(final Player player, final String message) {
		sendActionConfigMessage(message, new ActionMessage() {
			@Override
			public void run() {
				player.sendMessage(UHCReloaded.getPrefix() + message);
			}
		});
	}
	
	public static void sendConfigMessage(final String prefix, final Player player, final String message) {
		sendActionConfigMessage(message, new ActionMessage() {
			@Override
			public void run() {
				player.sendMessage(prefix + message);
			}
		});
	}
	
	public static Boolean sendActionConfigMessage(String message, ActionMessage action) {
		if (!message.equals("DISABLED"))
			action.run();
		return !message.equals("DISABLED");
	}
	
	public static void sendActionConfigMessage(String message, ActionMessage action, ActionMessage other) {
		if (!sendActionConfigMessage(message, action))
			other.run();
	}
	
	public static interface ActionMessage { public void run(); }
	public static interface Callback { public void run(); }
}
