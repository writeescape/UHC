package net.upd4ting.uhcreloaded;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

import net.upd4ting.uhcreloaded.configuration.configs.GenerationConfig;

public class OrePopulator extends BlockPopulator {
	private int stackDepth = 0;
	private Queue<DeferredGenerateTask> deferredGenerateTasks;

	public OrePopulator() {
		this.deferredGenerateTasks = new LinkedList<DeferredGenerateTask>();
	}

	public void populate(World world, Random random, Chunk chunk) {
		applyGenerateRules(world, random, chunk);
		if (this.stackDepth == 0)
			while (this.deferredGenerateTasks.size() > 0) {
				DeferredGenerateTask task = (DeferredGenerateTask) this.deferredGenerateTasks.remove();
				task.execute();
			}
	}

	private void applyGenerateRules(World world, Random random, Chunk chunk) {
		GenerationConfig config = UHCReloaded.getGenerationConfiguration();
		
		if (this.stackDepth > 0) {
			this.deferredGenerateTasks.add(new DeferredGenerateTask(world, random, chunk.getX(), chunk.getZ()));
			return;
		}
		this.stackDepth += 1;
		for (GenRule rule : GenRule.rules)
			for (int i = 0; i < rule.getRounds(); i++)
				if (random.nextInt(100) <= rule.getPropability()) {
					try {
						int x = chunk.getX() * 16 + random.nextInt(16);
						int y = rule.getMinHeight() + random.nextInt(rule.getMaxHeight() - rule.getMinHeight());
						int z = chunk.getZ() * 16 + random.nextInt(16);
						generate(world, random, x, y, z, rule.getSize(), rule.getMaterial());
					} catch (NullPointerException localNullPointerException) {  }

				}
		this.stackDepth -= 1;
		
		int xA = chunk.getX() * 16;
		int zA = chunk.getZ() * 16;
		int y = 62;
		
		for (int x = xA; x < xA + 16; x++) {
			for (int z = zA; z < zA + 16; z++) {
				
				Block block = new Location(world, x, y, z).getBlock();
				
				if (config.isSugarCaneGeneratorEnabled()) {
					if (block != null && (block.getType() == Material.SAND || block.getType() == Material.GRASS)) {
						if (block.getLocation().add(0,1,0).getBlock().getType() == Material.AIR
								&& block.getLocation().add(0,2,0).getBlock().getType() == Material.AIR
								 && block.getLocation().add(0,3,0).getBlock().getType() == Material.AIR 
								 && block.getLocation().add(0,4,0).getBlock().getType() == Material.AIR && hasWaterNear(block)) {
							int ran = random.nextInt(100);

							if (ran <= config.getSugarCaneGeneratorPercent()) {
								int number = random.nextInt(4);
								Location loc = block.getLocation();

								while (number > 0) {
									loc = loc.add(0,1,0);
									loc.getBlock().setType(Material.SUGAR_CANE_BLOCK);
									number--;
								}
							}
						}
					}
				}
			}
		}
	}
	
	private Boolean hasWaterNear(Block b) {
		if (isWater(b.getRelative(BlockFace.EAST).getType()) || 
				isWater(b.getRelative(BlockFace.NORTH).getType()) || 
				isWater(b.getRelative(BlockFace.WEST).getType()) ||
				isWater(b.getRelative(BlockFace.SOUTH).getType()))
			return true;
		
		return false;
	}
	
	private Boolean isWater(Material mat) {
		return mat == Material.WATER || mat == Material.STATIONARY_WATER;
	}

	private void generate(World world, Random rand, int x, int y, int z, int size, Material material) {
		double rpi = rand.nextDouble() * 3.141592653589793D;
		double x1 = x + 8 + Math.sin(rpi) * size / 8.0D;
		double x2 = x + 8 - Math.sin(rpi) * size / 8.0D;
		double z1 = z + 8 + Math.cos(rpi) * size / 8.0D;
		double z2 = z + 8 - Math.cos(rpi) * size / 8.0D;
		double y1 = y + rand.nextInt(3) + 2;
		double y2 = y + rand.nextInt(3) + 2;
		for (int i = 0; i <= size; i++) {
			double xPos = x1 + (x2 - x1) * i / size;
			double yPos = y1 + (y2 - y1) * i / size;
			double zPos = z1 + (z2 - z1) * i / size;
			double fuzz = rand.nextDouble() * size / 16.0D;
			double fuzzXZ = (Math.sin((float)(i * 3.141592653589793D / size)) + 1.0D) * fuzz + 1.0D;
			double fuzzY = (Math.sin((float)(i * 3.141592653589793D / size)) + 1.0D) * fuzz + 1.0D;
			int xStart = (int)Math.floor(xPos - fuzzXZ / 2.0D);
			int yStart = (int)Math.floor(yPos - fuzzY / 2.0D);
			int zStart = (int)Math.floor(zPos - fuzzXZ / 2.0D);
			int xEnd = (int)Math.floor(xPos + fuzzXZ / 2.0D);
			int yEnd = (int)Math.floor(yPos + fuzzY / 2.0D);
			int zEnd = (int)Math.floor(zPos + fuzzXZ / 2.0D);
			for (int ix = xStart; ix <= xEnd; ix++) {
				double xThresh = (ix + 0.5D - xPos) / (fuzzXZ / 2.0D);
				if (xThresh * xThresh < 1.0D)
					for (int iy = yStart; iy <= yEnd; iy++) {
						double yThresh = (iy + 0.5D - yPos) / (fuzzY / 2.0D);
						if (xThresh * xThresh + yThresh * yThresh < 1.0D)
							for (int iz = zStart; iz <= zEnd; iz++) {
								double zThresh = (iz + 0.5D - zPos) / (fuzzXZ / 2.0D);
								if (xThresh * xThresh + yThresh * yThresh + zThresh * zThresh < 1.0D) {
									Block block = tryGetBlock(world, ix, iy, iz);
									if ((block != null) && (block.getType() == Material.STONE)) {
										block.setType(material);
									}
								}
							}
					}
			}
		}
	}

	private Block tryGetBlock(World world, int x, int y, int z) {
		int cx = x >> 4;
		int cz = z >> 4;
		if ((!world.isChunkLoaded(cx, cz)) && (!world.loadChunk(cx, cz, false))) return null;
		Chunk chunk = world.getChunkAt(cx, cz);
		if (chunk == null) return null;
		return chunk.getBlock(x & 0xF, y, z & 0xF);
	}

	private class DeferredGenerateTask {
		private World world;
		private Random random;
		private int cx;
		private int cz;

		public DeferredGenerateTask(World world, Random random, int cx, int cz) {
			this.world = world;
			this.random = random;
			this.cx = cx;
			this.cz = cz;
		}

		public void execute() {
			OrePopulator.this.applyGenerateRules(this.world, this.random, this.world.getChunkAt(this.cx, this.cz));
		}
	}
}