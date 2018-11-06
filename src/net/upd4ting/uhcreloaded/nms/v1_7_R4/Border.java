package net.upd4ting.uhcreloaded.nms.v1_7_R4;

import java.util.ArrayList;
import java.util.List;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.TimerConfig;
import net.upd4ting.uhcreloaded.nms.common.BorderHandler;
import net.upd4ting.uhcreloaded.nms.common.Init;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Border implements BorderHandler, Listener, Init {
	
	private Boolean active;
	private Double size;
	private Integer previousSize;
	private Boolean moving;
	private Double sizeToMove;
	private Double blockPerSecond;
	private Integer damage;
	
	private List<Block> currentBlock = new ArrayList<>();
	private List<Player> outside = new ArrayList<>();
	
	public Border() {
		this.size = 0d;
		this.previousSize = -1;
		this.moving = false;
		this.sizeToMove = 0d;
		this.active = false;
	}
	
	@Override
	public void init() {
		Bukkit.getPluginManager().registerEvents(this, UHCReloaded.getInstance());
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (!active)
			return;
		if (e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockZ() == e.getTo().getBlockZ())
			return;
		
		Player p = e.getPlayer();
		Location loc = e.getTo();
		Game game = UHCReloaded.getGame();
		
		if (!game.getPlaying().contains(p))
			return;
		
		// Detect if out of size
		if (!isInsideBorder(loc)) {
			if ((moving || !isInsideBorder(e.getFrom())) && game.getPlaying().contains(p)) {
				if (!outside.contains(p))
					outside.add(p);
			} else {
				Location tpBack = e.getFrom().clone();
				tpBack.setPitch(e.getTo().getPitch());
				tpBack.setYaw(e.getTo().getYaw());
				p.teleport(tpBack);
			}
		} else if (outside.contains(p)) {
			outside.remove(p);
		}
	}
	
	@Override
	public void initialize(Boolean tp) {
		TimerConfig config = UHCReloaded.getTimerConfiguration();
		size = tp ? config.getDmSizeStart().doubleValue() : config.getWbSizeStart().doubleValue();
		damage = tp ? config.getDmDamage() : config.getWbDamage();
		
		for (World w : UHCReloaded.getGame().getWorlds())
			w.setPVP(tp);
		
		active = true;
		
		switchBlock();
	}

	@Override
	public void move(Boolean tp) {
		TimerConfig timerConfig = UHCReloaded.getTimerConfiguration();
				
		moving = true;
		sizeToMove = tp ? timerConfig.getDmSizeEnd().doubleValue() : timerConfig.getWbSizeEnd().doubleValue();
		blockPerSecond = (size - sizeToMove) / (tp ? timerConfig.getDmShrinkTime() : timerConfig.getWbShrinkTime());
		
		new BukkitRunnable() {
			@Override
			public void run() {
				
				// For damaging player that are aware of the border
				for (Player p : new ArrayList<>(outside)) {
					if (!p.isOnline() || UHCReloaded.getGame().getSpectators().contains(p)) {
						outside.remove(p);
					} else {
						p.damage(damage);
					}
				}
				
				// To move the border
				if (size > sizeToMove) {
					size -= blockPerSecond;
					switchBlock();
				} else {
					moving = false;
					outside.clear();
					this.cancel();
				}
			}
		}.runTaskTimer(UHCReloaded.getInstance(), 0, 20);
	}

	@Override
	public Double getSize() {
		return size.doubleValue();
	}
	
	private void switchBlock() {
		if (size.intValue() == previousSize) return;
		
		previousSize = size.intValue();
		
		for (Block b : currentBlock) b.setType(Material.AIR);
		
		currentBlock.clear();
		currentBlock = getBlockBorder();
		
		for (Block b : currentBlock) b.setType(Material.BEDROCK);
	}
	
	private List<Block> getBlockBorder() {
		List<Block> blocks = new ArrayList<>();
	
		
		for (int i = 0; i < 2; i++) {
			for (int x = -size.intValue(); x <= size.intValue(); x++) {
				for (int z = -size.intValue(); z <= size.intValue(); z+= size.intValue()*2) {
					// In all worlds
					for (World w : UHCReloaded.getGame().getWorlds()) {
						Location loc = new Location(w, i == 1 ? x : z, 0, i == 1 ? z : x);
						loc = w.getHighestBlockAt(loc).getLocation();
						blocks.add(loc.getBlock());
					}
				}
			}
		}
		
		return blocks;
	}
	
	private Boolean isInsideBorder(Location loc) {
		return loc.getBlockX() < size && loc.getBlockX() > -size && loc.getBlockZ() < size && loc.getBlockZ() > -size;
	}
}
