package net.upd4ting.uhcreloaded.nms.v1_7_R4;

import java.lang.reflect.Field;
import java.util.Arrays;

import net.minecraft.server.v1_7_R4.BiomeBase;
import net.upd4ting.uhcreloaded.nms.common.Biome;
import net.upd4ting.uhcreloaded.nms.common.BiomeHandler;

public class BiomeReplacer implements BiomeHandler {
	public BiomeBase[] copy;
	public BiomeBase[] biomes;
	
	public BiomeReplacer() {
		try {
			Field biomeF = BiomeBase.class.getDeclaredField("biomes");
			biomeF.setAccessible(true);
			biomes = (BiomeBase[])biomeF.get(null);
			copy = (BiomeBase[]) Arrays.copyOf(biomes, biomes.length);
		}
		catch (IllegalAccessException|NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	public void swap(Biome from, Biome to) {
		biomes[from.getId()] = copy[to.getId()];
	}
	
	public void swap(Biome to) {
		for (int i = 0; i < biomes.length; i++) {
			if (i != to.getId() && biomes[i] != null) 
				biomes[i] = copy[to.getId()];
		}
	}
}
