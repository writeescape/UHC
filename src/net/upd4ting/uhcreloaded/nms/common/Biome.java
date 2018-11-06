package net.upd4ting.uhcreloaded.nms.common;

public enum Biome {
	OCEAN(0),  PLAINS(1),  DESERT(2),  EXTREME_HILLS(3),  FOREST(4),  TAIGA(5),  SWAMPLAND(6),  RIVER(7),  HELL(8),  SKY(9),  FROZEN_OCEAN(10),  FROZEN_RIVER(11),  ICE_FLATS(12),  ICE_MOUNTAINS(13),  MUSHROOM_ISLAND(14),  MUSHROOM_ISLAND_SHORE(15),  BEACHES(16),  DESERT_HILLS(17),  FOREST_HILLS(18),  TAIGA_HILLS(19),  SMALLER_EXTREME_HILLS(20),  JUNGLE(21),  JUNGLE_HILLS(22),  JUNGLE_EDGE(23),  DEEP_OCEAN(24),  STONE_BEACH(25),  COLD_BEACH(26),  BIRCH_FOREST(27),  BIRCH_FOREST_HILLS(28),  ROOFED_FOREST(29),  TAIGA_COLD(30),  TAIGA_COLD_HILLS(31),  REDWOOD_TAIGA(32),  REDWOOD_TAIGA_HILLS(33),  EXTREME_HILLS_WITH_TREES(34),  SAVANNA(35),  SAVANNA_ROCK(36),  MESA(37),  MESA_ROCK(38),  MESA_CLEAR_ROCK(39);

	private final int id;

	private Biome(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
	
	public static Biome getFromString(String s) {
		for (Biome b : Biome.values())
			if (b.name().equalsIgnoreCase(s))
				return b;
		return null;
	}
}
