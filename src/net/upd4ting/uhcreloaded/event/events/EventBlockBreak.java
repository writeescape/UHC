package net.upd4ting.uhcreloaded.event.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.LootConfig.Loot;
import net.upd4ting.uhcreloaded.configuration.configs.LootConfig.LootItem;
import net.upd4ting.uhcreloaded.customevent.BlockBreakLootEvent;

public class EventBlockBreak implements Listener {
	
	@EventHandler
	public void onBlockBreak(final BlockBreakEvent e) {
		if (e.getPlayer() != null && UHCReloaded.getGame().isLimited(e.getPlayer())) {
			e.setCancelled(true);
			return;
		}
		
		Bukkit.getPluginManager().callEvent(new BlockBreakLootEvent(e.getPlayer(), e.getBlock()));
	}
	
	@EventHandler
	public void onBreakLoot(BlockBreakLootEvent e) {
		Block b = e.getBlock();
		Material mat = b.getType();
		
		// -- Check pour drop un LootItem
		
		checkLoot(b, e);
		
		// -- Check pour auto break
		
		if (!UHCReloaded.getMainConfiguration().isTreeCutterEnabled() || e.getPlayer() == null) 
			return;
		
		if (mat == Material.LOG || mat == Material.LOG_2) {
			TreeCutter cutter = new TreeCutter(1);

			cutter.addBlock(b);
			
			while (true) {
				Boolean changed = false;
				
				for (int i = 0; i < cutter.getBlocks().size(); i++) {
					Block block = cutter.getBlocks().get(i);
					if (block.getType() == Material.LOG || block.getType() == Material.LOG_2) {
						
						for (BlockFace face : BlockFace.values()) {
							if (block.getRelative(face).getType() == Material.LOG || block.getRelative(face).getType() == Material.LOG_2) {
								Block b2 = block.getRelative(face);
								if (!cutter.contains(b2)) {
									cutter.addBlock(b2);
									changed = true;
								}
							}
						}
							
					}
				}
				
				if (!changed)
					break;
			}
			
			cutter.start();
		}
	}
	
	@SuppressWarnings("deprecation")
	private Boolean checkLoot(Block b, Cancellable e) {
		if (UHCReloaded.getLootConfiguration().isOverridingBlocksLoot()) {
			
			Material type = b.getType();
			byte data = (byte) ((type == Material.LEAVES || type == Material.LEAVES_2) ?  b.getData() & 3 : b.getData());
			
			
			if (UHCReloaded.getLootConfiguration().containsBlockLoot(type, data)) {
				Loot loot = UHCReloaded.getLootConfiguration().getBlockLoot(type, data);
				e.setCancelled(true);
				b.setType(Material.AIR);

				World w = b.getWorld();
				
				if (loot.getExp() != 0) {
					ExperienceOrb orb = w.spawn(b.getLocation(), ExperienceOrb.class);
					orb.setExperience(loot.getExp());
				}
				
				for (LootItem item : loot.getLoots()) {
					ItemStack is = item.getLootItem();
					if (is != null) 
						w.dropItemNaturally(b.getLocation().add(0.5, 0, 0.5), is);
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("deprecation")
	private void breakLeaf(World world, int x, int y, int z, TreeCutter cutter) {
		Block block = world.getBlockAt(x, y, z);
		byte data = block.getData();

		if((data & 4) == 4)
		{
			return; // player placed leaf, ignore
		}

		byte range = 4;
		byte max = 32;
		int[] blocks = new int[max * max * max];
		int off = range + 1;
		int mul = max * max;
		int div = max / 2;

		if(validChunk(world, x - off, y - off, z - off, x + off, y + off, z + off))
		{
			int offX;
			int offY;
			int offZ;
			int type;

			for(offX = -range; offX <= range; offX++)
			{
				for(offY = -range; offY <= range; offY++)
				{
					for(offZ = -range; offZ <= range; offZ++)
					{
						Material mat = world.getBlockAt(x + offX, y + offY, z + offZ).getType();
						if ((mat == Material.LEAVES || mat == Material.LEAVES_2))
							type = Material.LEAVES.getId();
						else if ((mat == Material.LOG || mat == Material.LOG_2))
							type = Material.LOG.getId();
						blocks[(offX + div) * mul + (offY + div) * max + offZ + div] = ((mat == Material.LOG || mat == Material.LOG_2) ? 0 : ((mat == Material.LEAVES || mat == Material.LEAVES_2) ? -2 : -1));
					}
				}
			}

			for(offX = 1; offX <= 4; offX++)
			{
				for(offY = -range; offY <= range; offY++)
				{
					for(offZ = -range; offZ <= range; offZ++)
					{
						for(type = -range; type <= range; type++)
						{
							if(blocks[(offY + div) * mul + (offZ + div) * max + type + div] == offX - 1)
							{
								if(blocks[(offY + div - 1) * mul + (offZ + div) * max + type + div] == -2)
									blocks[(offY + div - 1) * mul + (offZ + div) * max + type + div] = offX;

								if(blocks[(offY + div + 1) * mul + (offZ + div) * max + type + div] == -2)
									blocks[(offY + div + 1) * mul + (offZ + div) * max + type + div] = offX;

								if(blocks[(offY + div) * mul + (offZ + div - 1) * max + type + div] == -2)
									blocks[(offY + div) * mul + (offZ + div - 1) * max + type + div] = offX;

								if(blocks[(offY + div) * mul + (offZ + div + 1) * max + type + div] == -2)
									blocks[(offY + div) * mul + (offZ + div + 1) * max + type + div] = offX;

								if(blocks[(offY + div) * mul + (offZ + div) * max + (type + div - 1)] == -2)
									blocks[(offY + div) * mul + (offZ + div) * max + (type + div - 1)] = offX;

								if(blocks[(offY + div) * mul + (offZ + div) * max + type + div + 1] == -2)
									blocks[(offY + div) * mul + (offZ + div) * max + type + div + 1] = offX;
							}
						}
					}
				}
			}
		}
		
		if(blocks[div * mul + div * max + div] < 0) {
			if (!cutter.contains(block))
				cutter.addBlock(block);
		}
	}

	public boolean validChunk(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		if(maxY >= 0 && minY < world.getMaxHeight())
		{
			minX >>= 4;
			minZ >>= 4;
			maxX >>= 4;
			maxZ >>= 4;

			for(int x = minX; x <= maxX; x++)
			{
				for(int z = minZ; z <= maxZ; z++)
				{
					if(!world.isChunkLoaded(x, z))
					{
						return false;
					}
				}
			}

			return true;
		}

		return false;
	}

	private void checkLeaves(Block block, TreeCutter cutter) {
		Location loc = block.getLocation();
		final World world = loc.getWorld();
		final int x = loc.getBlockX();
		final int y = loc.getBlockY();
		final int z = loc.getBlockZ();
		final int range = 4;
		final int off = range + 1;

		if(!validChunk(world, x - off, y - off, z - off, x + off, y + off, z + off))
		{
			return;
		}
		
		new BukkitRunnable() {
			@Override
			public void run() {
				for (int offX = - range; offX <= range; offX++) {
					for (int offY = - range; offY <= range; offY++) {
						for (int offZ = - range; offZ <= range; offZ++) {
							if ((world.getBlockAt(x + offX, y + offY, z + offZ).getType() == Material.LEAVES || world.getBlockAt(x + offX, y + offY, z + offZ).getType() == Material.LEAVES_2)) {
								breakLeaf(world, x + offX, y + offY, z + offZ, cutter);
							}
						}
					}
				}
			}
		}.runTask(UHCReloaded.getInstance());
	}
	
	
	public class TreeCutter extends BukkitRunnable {
		
		private Integer period;
		private List<Block> list = new ArrayList<>();
		
		public TreeCutter(Integer period) {
			this.period = period;
		}
		
		public void start() {
			this.runTaskTimer(UHCReloaded.getInstance(), 0, period);
		}
		
		public void addBlock(Block b) {
			list.add(b);
		}
		
		public List<Block> getBlocks() {
			return list;
		}
		
		public Boolean contains(Block b) {
			return list.contains(b);
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			if (list.isEmpty()) {
				cancel();
				return;
			}
						
			Block b = list.remove(0);
			World world = b.getWorld();
			Material mat = b.getType();
			
			checkLeaves(b, this);
			
			if (mat == Material.LEAVES || mat == Material.LEAVES_2) {
				LeavesDecayEvent event = new LeavesDecayEvent(b);
				Bukkit.getServer().getPluginManager().callEvent(event);
					
				checkLoot(b, event);
				
				if (!event.isCancelled()) {
					b.setType(Material.AIR);
				}
			} else {
				BlockBreakLootEvent event = new BlockBreakLootEvent(null, b);
				
				Bukkit.getPluginManager().callEvent(event);
				
				if (!event.isCancelled()) {
					for (ItemStack item : b.getDrops()) {
						b.getWorld().dropItemNaturally(b.getLocation(), item);
					}

					b.setType(Material.AIR);
				}
			}
			
			if ((new Random().nextInt(100) % 3) == 0)
				world.playEffect(b.getLocation(), Effect.STEP_SOUND, mat.getId());
		}
	}
}
