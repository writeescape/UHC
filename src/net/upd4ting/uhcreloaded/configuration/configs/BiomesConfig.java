package net.upd4ting.uhcreloaded.configuration.configs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.Configuration;
import net.upd4ting.uhcreloaded.exception.InvalidConfigException;
import net.upd4ting.uhcreloaded.nms.common.Biome;
import net.upd4ting.uhcreloaded.nms.common.BiomeHandler;

public class BiomesConfig extends Configuration {
	
	private static final Random r = new Random();
	
	private Boolean swapEnabled;
	private List<Swap> swaps = new ArrayList<>();
	private List<Biome> randomable = new ArrayList<>();
	
	public BiomesConfig() {
		super(ConfType.STARTUP, "biomes.yml");
	}

	@Override
	public void loadData() throws InvalidConfigException {
		swapEnabled = this.conf.getBoolean("enabled");
		
		// Construct randomable
		
		for (Biome b : Biome.values())
			randomable.add(b);
		
		// Getting swaps
		
		for (String s : this.conf.getStringList("swaps")) {
			if (!s.contains(";"))
				throw new InvalidConfigException("[biomes.yml] Invalid swap: " + s + " ( check example of swaps in the config )");
			
			String[] splitted = s.split(";");
			
			if (splitted.length != 2)
				throw new InvalidConfigException("[biomes.yml] Invalid swap: " + s + " ( check example of swaps in the config )");
			
			String from = splitted[0];
			String to = splitted[1];
			
			if (from.equals("RANDOM") || (!from.equals("ALL") && Biome.getFromString(from) == null))
				throw new InvalidConfigException("[biomes.yml] Invalid swap: " + s + " ( check example of swaps in the config )");
			
			if (to.equals("ALL") || (!to.equals("RANDOM") && Biome.getFromString(to) == null))
				throw new InvalidConfigException("[biomes.yml] Invalid swap: " + s + " ( check example of swaps in the config )");

			Swap swap = new Swap(splitted[0], splitted[1]);
			swaps.add(swap);
			
			randomable.remove(Biome.valueOf(swap.from));
		}
	}
	
	public Boolean isSwapEnabled() { return swapEnabled; }
	public List<Swap> getSwaps() { return swaps; }
	public List<Biome> getRandomable() { return randomable; }
	
	public static class Swap {
		private String from;
		private String to;
		
		public Swap(String from, String to) {
			this.from = from;
			this.to = to;
		}
		
		public void execute(List<Biome> randomable) {
			BiomeHandler handler = UHCReloaded.getNMSHandler().getBiomeHandler();
			
			if (from.equals("ALL"))
				handler.swap(Biome.valueOf(to));
			else if (to.equals("RANDOM")) {
				Biome newB = randomable.get(r.nextInt(randomable.size()));
				handler.swap(Biome.valueOf(from), newB);
			}
			else
				handler.swap(Biome.valueOf(from), Biome.valueOf(to));
			
		}
	}
}
