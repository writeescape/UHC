package net.upd4ting.uhcreloaded.util;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import org.bukkit.Material;

public class IDTools {

    // The List for the IDs and SubIDs to get the Material for each ID and subID
    private Map<String, Material> IdList = new HashMap<>();

    // Intialization for the IDList
    public IDTools() {

        // For these guys who Intialize the Class more than one
        IdList.clear();
        IdList.put("0:0", Material.AIR);
            IdList.put("1:0", Material.STONE);
            IdList.put("1:1", Material.GRANITE);
            IdList.put("1:2", Material.POLISHED_GRANITE);
            IdList.put("1:3", Material.DIORITE);
            IdList.put("1:4", Material.POLISHED_DIORITE);
            IdList.put("1:5", Material.ANDESITE);
            IdList.put("1:6", Material.POLISHED_ANDESITE);
            IdList.put("2:0" , Material.GRASS);
            IdList.put("3:0" , Material.DIRT);
            IdList.put("3:1", Material.COARSE_DIRT);
            IdList.put("3:2", Material.PODZOL);
            IdList.put("4:0" , Material.COBBLESTONE);
            IdList.put("5:0" , Material.OAK_PLANKS);
            IdList.put("5:1", Material.SPRUCE_PLANKS);
            IdList.put("5:2", Material.BIRCH_PLANKS);
            IdList.put("5:3", Material.JUNGLE_PLANKS);
            IdList.put("5:4", Material.ACACIA_PLANKS);
            IdList.put("5:5", Material.DARK_OAK_PLANKS);
            IdList.put("6:0" , Material.OAK_SAPLING);
            IdList.put("6:1", Material.SPRUCE_SAPLING);
            IdList.put("6:2", Material.BIRCH_SAPLING);
            IdList.put("6:3", Material.JUNGLE_SAPLING);
            IdList.put("6:4", Material.ACACIA_SAPLING);
            IdList.put("6:5", Material.DARK_OAK_SAPLING);
            IdList.put("7:0" , Material.BEDROCK);
            IdList.put("8:0" , Material.WATER);
            IdList.put("9:0" , Material.WATER);
            IdList.put("10:0" , Material.LAVA);
            IdList.put("11:0" , Material.LAVA);
            IdList.put("12:0" , Material.SAND);
            IdList.put("12:1", Material.RED_SAND);
            IdList.put("13:0" , Material.GRAVEL);
            IdList.put("14:0" , Material.GOLD_ORE);
            IdList.put("15:0" , Material.IRON_ORE);
            IdList.put("16:0" , Material.COAL_ORE);
            IdList.put("17:0" , Material.OAK_WOOD);
            IdList.put("17:1", Material.SPRUCE_WOOD);
            IdList.put("17:2", Material.BIRCH_WOOD);
            IdList.put("17:3", Material.JUNGLE_WOOD);
            IdList.put("18:0" , Material.OAK_LEAVES);
            IdList.put("18:1", Material.SPRUCE_LEAVES);
            IdList.put("18:2", Material.BIRCH_LEAVES);
            IdList.put("18:3", Material.JUNGLE_LEAVES);
            IdList.put("19:0" , Material.SPONGE);
            IdList.put("19:1", Material.WET_SPONGE);
            IdList.put("20:0" , Material.GLASS);
            IdList.put("21:0" , Material.LAPIS_ORE);
            IdList.put("22:0" , Material.LAPIS_BLOCK);
            IdList.put("23:0" , Material.DISPENSER);
            IdList.put("24:0" , Material.SANDSTONE);
            IdList.put("24:1", Material.CHISELED_SANDSTONE);
            IdList.put("24:2", Material.SMOOTH_SANDSTONE);
            IdList.put("25:0" , Material.NOTE_BLOCK);
            IdList.put("26:0" , Material.RED_BED);
            IdList.put("27:0" , Material.POWERED_RAIL);
            IdList.put("28:0" , Material.DETECTOR_RAIL);
            IdList.put("29:0" , Material.STICKY_PISTON);
            IdList.put("30:0" , Material.COBWEB);
            IdList.put("31:0" , Material.DEAD_BUSH);
            IdList.put("31:1", Material.GRASS);
            IdList.put("31:2", Material.FERN);
            IdList.put("32:0" , Material.DEAD_BUSH);
            IdList.put("33:0" , Material.PISTON);
            IdList.put("34:0" , Material.PISTON_HEAD);
            IdList.put("35:0" , Material.WHITE_WOOL);
            IdList.put("35:1", Material.ORANGE_WOOL);
            IdList.put("35:2", Material.MAGENTA_WOOL);
            IdList.put("35:3", Material.LIGHT_BLUE_WOOL);
            IdList.put("35:4", Material.YELLOW_WOOL);
            IdList.put("35:5", Material.LIME_WOOL);
            IdList.put("35:6", Material.PINK_WOOL);
            IdList.put("35.7", Material.GRAY_WOOL);
            IdList.put("35.8", Material.LIGHT_GRAY_WOOL);
            IdList.put("35.9", Material.CYAN_WOOL);
            IdList.put("35:10", Material.PURPLE_WOOL);
            IdList.put("35:11", Material.BLUE_WOOL);
            IdList.put("35:12", Material.BROWN_WOOL);
            IdList.put("35:13", Material.GREEN_WOOL);
            IdList.put("35:14", Material.RED_WOOL);
            IdList.put("35:15", Material.BLACK_WOOL);
            IdList.put("37:0" , Material.DANDELION);
            IdList.put("38:0" , Material.POPPY);
            IdList.put("38:1", Material.BLUE_ORCHID);
            IdList.put("38:2", Material.ALLIUM);
            IdList.put("38:3", Material.AZURE_BLUET);
            IdList.put("38:4", Material.RED_TULIP);
            IdList.put("38:5", Material.ORANGE_TULIP);
            IdList.put("38:6", Material.WHITE_TULIP);
            IdList.put("38:7", Material.PINK_TULIP);
            IdList.put("38:8", Material.OXEYE_DAISY);
            IdList.put("39:0" , Material.BROWN_MUSHROOM);
            IdList.put("40:0" , Material.RED_MUSHROOM);
            IdList.put("41:0" , Material.GOLD_BLOCK);
            IdList.put("42:0" , Material.IRON_BLOCK);
            IdList.put("44:0" , Material.STONE_SLAB);
            IdList.put("44:1", Material.SANDSTONE_SLAB);
            IdList.put("44:2", Material.OAK_SLAB);
            IdList.put("44:3", Material.COBBLESTONE_SLAB);
            IdList.put("44:4", Material.BRICK_SLAB);
            IdList.put("44:5", Material.STONE_BRICK_SLAB);
            IdList.put("44:6", Material.NETHER_BRICK_SLAB);
            IdList.put("44:7", Material.QUARTZ_SLAB);
            IdList.put("45:0" , Material.BRICKS);
            IdList.put("46:0" , Material.TNT);
            IdList.put("47:0" , Material.BOOKSHELF);
            IdList.put("48:0" , Material.MOSSY_STONE_BRICKS);
            IdList.put("49:0" , Material.OBSIDIAN);
            IdList.put("50:0" , Material.TORCH);
            IdList.put("51:0" , Material.FIRE);
            IdList.put("52:0" , Material.SPAWNER);
            IdList.put("53:0" , Material.OAK_STAIRS);
            IdList.put("54:0" , Material.CHEST);
            IdList.put("55:0" , Material.REDSTONE_WIRE);
            IdList.put("56:0" , Material.DIAMOND_ORE);
            IdList.put("57:0" , Material.DIAMOND_BLOCK);
            IdList.put("58:0" , Material.CRAFTING_TABLE);
            IdList.put("60:0" , Material.FARMLAND);
            IdList.put("61:0" , Material.FURNACE);
            IdList.put("62:0" , Material.FURNACE);
            IdList.put("63:0" , Material.WALL_SIGN);
            IdList.put("64:0" , Material.OAK_DOOR);
            IdList.put("65:0" , Material.LADDER);
            IdList.put("66:0" , Material.RAIL);
            IdList.put("67:0" , Material.COBBLESTONE_STAIRS);
            IdList.put("68:0" , Material.WALL_SIGN);
            IdList.put("69:0" , Material.LEVER);
            IdList.put("70:0" , Material.STONE_PRESSURE_PLATE);
            IdList.put("71:0" , Material.IRON_DOOR);
            IdList.put("72:0" , Material.OAK_PRESSURE_PLATE);
            IdList.put("73:0" , Material.REDSTONE_ORE);
            IdList.put("74:0" , Material.REDSTONE_ORE);
            IdList.put("75:0" , Material.REDSTONE_TORCH);
            IdList.put("76:0" , Material.REDSTONE_TORCH);
            IdList.put("77:0" , Material.STONE_BUTTON);
            IdList.put("78:0" , Material.SNOW);
            IdList.put("79:0" , Material.ICE);
            IdList.put("80:0" , Material.SNOW_BLOCK);
            IdList.put("81:0" , Material.CACTUS);
            IdList.put("82:0" , Material.CLAY);
            IdList.put("83:0" , Material.SUGAR_CANE);
            IdList.put("84:0" , Material.JUKEBOX);
            IdList.put("85:0" , Material.OAK_FENCE);
            IdList.put("86:0" , Material.PUMPKIN);
            IdList.put("87:0" , Material.NETHERRACK);
            IdList.put("88:0" , Material.SOUL_SAND);
            IdList.put("89:0" , Material.GLOWSTONE);
            IdList.put("90:0" , Material.NETHER_PORTAL);
            IdList.put("91:0" , Material.JACK_O_LANTERN);
            IdList.put("92:0" , Material.CAKE);
            IdList.put("93:0" , Material.REPEATER);
            IdList.put("94:0" , Material.REPEATER);
            IdList.put("95:0" , Material.WHITE_STAINED_GLASS);
            IdList.put("95:1", Material.ORANGE_STAINED_GLASS);
            IdList.put("95:2", Material.MAGENTA_STAINED_GLASS);
            IdList.put("95:3", Material.LIGHT_BLUE_STAINED_GLASS);
            IdList.put("95:4", Material.YELLOW_STAINED_GLASS);
            IdList.put("95:5", Material.LIME_STAINED_GLASS);
            IdList.put("95:6", Material.PINK_STAINED_GLASS);
            IdList.put("95:7", Material.GRAY_STAINED_GLASS);
            IdList.put("95:8", Material.LIGHT_GRAY_STAINED_GLASS);
            IdList.put("95:9", Material.CYAN_STAINED_GLASS);
            IdList.put("95:10", Material.PURPLE_STAINED_GLASS);
            IdList.put("95:11", Material.BLUE_STAINED_GLASS);
            IdList.put("95:12", Material.BROWN_STAINED_GLASS);
            IdList.put("95:13", Material.GREEN_STAINED_GLASS);
            IdList.put("95:14", Material.RED_STAINED_GLASS);
            IdList.put("95:15", Material.BLACK_STAINED_GLASS);
            IdList.put("96:0" , Material.OAK_TRAPDOOR);
            IdList.put("97:0" , Material.INFESTED_STONE);
            IdList.put("98:0" , Material.STONE_BRICKS);
            IdList.put("98:1", Material.MOSSY_STONE_BRICKS);
            IdList.put("98:2", Material.CRACKED_STONE_BRICKS);
            IdList.put("98:3", Material.CHISELED_STONE_BRICKS);
            IdList.put("99:0" , Material.BROWN_MUSHROOM_BLOCK);
            IdList.put("100:0" , Material.RED_MUSHROOM_BLOCK);
            IdList.put("101:0" , Material.IRON_BARS);
            IdList.put("102:0" , Material.GLASS_PANE);
            IdList.put("103:0" , Material.MELON);
            IdList.put("104:0" , Material.PUMPKIN_STEM);
            IdList.put("105:0" , Material.MELON_STEM);
            IdList.put("106:0" , Material.VINE);
            IdList.put("107:0" , Material.OAK_FENCE_GATE);
            IdList.put("108:0" , Material.BRICK_STAIRS);
            IdList.put("109:0" , Material.STONE_BRICK_STAIRS);
            IdList.put("110:0" , Material.MYCELIUM);
            IdList.put("111:0" , Material.LILY_PAD);
            IdList.put("112:0" , Material.NETHER_BRICK);
            IdList.put("113:0" , Material.NETHER_BRICK_FENCE);
            IdList.put("114:0" , Material.NETHER_BRICK_STAIRS);
            IdList.put("115:0" , Material.NETHER_WART);
            IdList.put("116:0" , Material.ENCHANTING_TABLE);
            IdList.put("117:0" , Material.BREWING_STAND);
            IdList.put("118:0" , Material.CAULDRON);
            IdList.put("119:0" , Material.END_PORTAL);
            IdList.put("120:0" , Material.END_PORTAL_FRAME);
            IdList.put("121:0" , Material.END_STONE);
            IdList.put("122:0" , Material.DRAGON_EGG);
            IdList.put("123:0" , Material.REDSTONE_LAMP);
            IdList.put("124:0" , Material.REDSTONE_LAMP);

            IdList.put("126:0" , Material.OAK_SLAB);
            IdList.put("126:1", Material.SPRUCE_SLAB);
            IdList.put("126:2", Material.BIRCH_SLAB);
            IdList.put("126:3", Material.JUNGLE_SLAB);
            IdList.put("126:4", Material.ACACIA_SLAB);
            IdList.put("126:5", Material.DARK_OAK_SLAB);
            IdList.put("127:0" , Material.COCOA);
            IdList.put("128:0" , Material.SANDSTONE_STAIRS);
            IdList.put("129:0" , Material.EMERALD_ORE);
            IdList.put("130:0" , Material.ENDER_CHEST);
            IdList.put("131:0" , Material.TRIPWIRE_HOOK);
            IdList.put("132:0" , Material.TRIPWIRE);
            IdList.put("133:0" , Material.EMERALD_BLOCK);
            IdList.put("134:0" , Material.SPRUCE_STAIRS);
            IdList.put("135:0" , Material.BIRCH_STAIRS);
            IdList.put("136:0" , Material.JUNGLE_STAIRS);
            IdList.put("137:0" , Material.COMMAND_BLOCK);
            IdList.put("138:0" , Material.BEACON);
            IdList.put("139:0" , Material.COBBLESTONE_WALL);
            IdList.put("139:1", Material.MOSSY_COBBLESTONE_WALL);
            IdList.put("140:0" , Material.FLOWER_POT);
            IdList.put("141:0" , Material.CARROTS);
            IdList.put("142:0" , Material.POTATOES);
            IdList.put("143:0" , Material.OAK_BUTTON);
            IdList.put("144:0" , Material.PLAYER_HEAD);
            IdList.put("145:0" , Material.ANVIL);
            IdList.put("146:0" , Material.TRAPPED_CHEST);

            IdList.put("151:0" , Material.DAYLIGHT_DETECTOR);
            IdList.put("152:0" , Material.REDSTONE_BLOCK);
            IdList.put("153:0" , Material.NETHER_QUARTZ_ORE);
            IdList.put("154:0" , Material.HOPPER);
            IdList.put("155:0" , Material.QUARTZ_BLOCK);
            IdList.put("155:1", Material.CHISELED_QUARTZ_BLOCK);
            IdList.put("155:2", Material.QUARTZ_PILLAR);
            IdList.put("156:0" , Material.QUARTZ_STAIRS);
            IdList.put("157:0" , Material.ACTIVATOR_RAIL);
            IdList.put("158:0" , Material.DROPPER);
            IdList.put("159:0" , Material.WHITE_TERRACOTTA);
            IdList.put("159:1", Material.ORANGE_TERRACOTTA);
            IdList.put("159:2", Material.MAGENTA_TERRACOTTA);
            IdList.put("159:3", Material.LIGHT_BLUE_TERRACOTTA);
            IdList.put("159:4", Material.YELLOW_TERRACOTTA);
            IdList.put("159:5", Material.LIME_TERRACOTTA);
            IdList.put("159:6", Material.PINK_TERRACOTTA);
            IdList.put("159:7", Material.GRAY_TERRACOTTA);
            IdList.put("159:8", Material.LIGHT_GRAY_TERRACOTTA);
            IdList.put("159:9", Material.CYAN_TERRACOTTA);
            IdList.put("159:10", Material.PURPLE_TERRACOTTA);
            IdList.put("159:11", Material.BLUE_TERRACOTTA);
            IdList.put("159:12", Material.BROWN_TERRACOTTA);
            IdList.put("159:13", Material.GREEN_TERRACOTTA);
            IdList.put("159:14", Material.RED_TERRACOTTA);
            IdList.put("159:15", Material.BLACK_TERRACOTTA);
            IdList.put("160:0" , Material.WHITE_STAINED_GLASS_PANE);
            IdList.put("160:1", Material.ORANGE_STAINED_GLASS_PANE);
            IdList.put("160:2", Material.MAGENTA_STAINED_GLASS_PANE);
            IdList.put("160:3", Material.LIGHT_BLUE_STAINED_GLASS_PANE);
            IdList.put("160:4", Material.YELLOW_STAINED_GLASS_PANE);
            IdList.put("160:5", Material.LIME_STAINED_GLASS_PANE);
            IdList.put("160:6", Material.PINK_STAINED_GLASS_PANE);
            IdList.put("160:7", Material.GRAY_STAINED_GLASS_PANE);
            IdList.put("160:8", Material.LIGHT_GRAY_STAINED_GLASS_PANE);
            IdList.put("160:9", Material.CYAN_STAINED_GLASS_PANE);
            IdList.put("160:10", Material.PURPLE_STAINED_GLASS_PANE);
            IdList.put("160:11", Material.BLUE_STAINED_GLASS_PANE);
            IdList.put("160:12", Material.BROWN_STAINED_GLASS_PANE);
            IdList.put("160:13", Material.GREEN_STAINED_GLASS_PANE);
            IdList.put("160:14", Material.RED_STAINED_GLASS_PANE);
            IdList.put("160:15", Material.BLACK_STAINED_GLASS_PANE);
            IdList.put("161:0" , Material.ACACIA_LEAVES);
            IdList.put("161:1", Material.DARK_OAK_LEAVES);
            IdList.put("162:0" , Material.ACACIA_WOOD);
            IdList.put("162:1", Material.DARK_OAK_WOOD);
            IdList.put("163:0" , Material.ACACIA_STAIRS);
            IdList.put("164:0" , Material.DARK_OAK_STAIRS);
            IdList.put("165:0" , Material.SLIME_BLOCK);
            IdList.put("166:0" , Material.BARRIER);
            IdList.put("167:0" , Material.IRON_TRAPDOOR);
            IdList.put("168:0" , Material.PRISMARINE);
            IdList.put("168:1", Material.PRISMARINE_BRICKS);
            IdList.put("168:2", Material.DARK_PRISMARINE);
            IdList.put("169:0" , Material.SEA_LANTERN);
            IdList.put("170:0" , Material.HAY_BLOCK);
            IdList.put("171:0" , Material.WHITE_CARPET);
            IdList.put("171:1", Material.ORANGE_CARPET);
            IdList.put("171:2", Material.MAGENTA_CARPET);
            IdList.put("171:3", Material.LIGHT_BLUE_CARPET);
            IdList.put("171:4", Material.YELLOW_CARPET);
            IdList.put("171:5", Material.LIME_CARPET);
            IdList.put("171:6", Material.PINK_CARPET);
            IdList.put("171:7", Material.GRAY_CARPET);
            IdList.put("171:8", Material.LIGHT_GRAY_CARPET);
            IdList.put("171:9", Material.CYAN_CARPET);
            IdList.put("171:10", Material.PURPLE_CARPET);
            IdList.put("171:11", Material.BLUE_CARPET);
            IdList.put("171:12", Material.BROWN_CARPET);
            IdList.put("171:13", Material.GREEN_CARPET);
            IdList.put("171:14", Material.RED_CARPET);
            IdList.put("171:15", Material.BLACK_CARPET);
            IdList.put("172:0" , Material.TERRACOTTA);
            IdList.put("173:0" , Material.COAL_BLOCK);
            IdList.put("174:0" , Material.PACKED_ICE);
            IdList.put("175:0" , Material.SUNFLOWER);
            IdList.put("175:1", Material.LILAC);
            IdList.put("175:2", Material.TALL_GRASS);
            IdList.put("175:3", Material.LARGE_FERN);
            IdList.put("175:4", Material.ROSE_BUSH);
            IdList.put("175:5", Material.PEONY);
            IdList.put("176:0" , Material.WHITE_BANNER);
            IdList.put("177:0" , Material.WHITE_BANNER);
            IdList.put("178:0" , Material.DAYLIGHT_DETECTOR);
            IdList.put("179:0" , Material.RED_SANDSTONE);
            IdList.put("179:1", Material.CHISELED_RED_SANDSTONE);
            IdList.put("179:2", Material.SMOOTH_RED_SANDSTONE);
            IdList.put("180:0" , Material.RED_SANDSTONE_STAIRS);
            IdList.put("181:0" , Material.RED_SANDSTONE_SLAB);
            IdList.put("182:0" , Material.RED_SANDSTONE_SLAB);
            IdList.put("183:0" , Material.SPRUCE_FENCE_GATE);
            IdList.put("184:0" , Material.BIRCH_FENCE_GATE);
            IdList.put("185:0" , Material.JUNGLE_FENCE_GATE);
            IdList.put("186:0" , Material.DARK_OAK_FENCE_GATE);
            IdList.put("187:0" , Material.ACACIA_FENCE_GATE);
            IdList.put("188:0" , Material.SPRUCE_FENCE);
            IdList.put("189:0" , Material.BIRCH_FENCE);
            IdList.put("190:0" , Material.JUNGLE_FENCE);
            IdList.put("191:0" , Material.DARK_OAK_FENCE);
            IdList.put("192:0" , Material.ACACIA_FENCE);
            IdList.put("193:0" , Material.SPRUCE_DOOR);
            IdList.put("194:0" , Material.BIRCH_DOOR);
            IdList.put("195:0" , Material.JUNGLE_DOOR);
            IdList.put("196:0" , Material.ACACIA_DOOR);
            IdList.put("197:0" , Material.DARK_OAK_DOOR);
            IdList.put("198:0" , Material.END_ROD);
            IdList.put("199:0" , Material.CHORUS_PLANT);
            IdList.put("200:0" , Material.CHORUS_FLOWER);
            IdList.put("201:0" , Material.PURPUR_BLOCK);
            IdList.put("202:0" , Material.PURPUR_PILLAR);
            IdList.put("203:0" , Material.PURPUR_STAIRS);
            IdList.put("204:0" , Material.PURPUR_BLOCK);
            IdList.put("205:0" , Material.PURPUR_SLAB);
            IdList.put("206:0" , Material.END_STONE_BRICKS);

            IdList.put("208:0" , Material.GRASS_PATH);
            IdList.put("209:0" , Material.END_GATEWAY);
            IdList.put("210:0" , Material.REPEATING_COMMAND_BLOCK);
            IdList.put("211:0" , Material.CHAIN_COMMAND_BLOCK);
            IdList.put("212:0" , Material.FROSTED_ICE);
            IdList.put("213:0" , Material.MAGMA_BLOCK);
            IdList.put("214:0" , Material.NETHER_WART_BLOCK);
            IdList.put("215:0" , Material.RED_NETHER_BRICKS);
            IdList.put("216:0" , Material.BONE_BLOCK);
            IdList.put("217:0" , Material.STRUCTURE_VOID);
            IdList.put("218:0" , Material.OBSERVER);
            IdList.put("219:0" , Material.WHITE_SHULKER_BOX);
            IdList.put("220:0" , Material.ORANGE_SHULKER_BOX);
            IdList.put("221:0" , Material.MAGENTA_SHULKER_BOX);
            IdList.put("222:0" , Material.LIGHT_BLUE_SHULKER_BOX);
            IdList.put("223:0" , Material.YELLOW_SHULKER_BOX);
            IdList.put("224:0" , Material.LIME_SHULKER_BOX);
            IdList.put("225:0" , Material.PINK_SHULKER_BOX);
            IdList.put("226:0" , Material.GRAY_SHULKER_BOX);
            IdList.put("227:0" , Material.LIGHT_GRAY_SHULKER_BOX);
            IdList.put("228:0" , Material.CYAN_SHULKER_BOX);
            IdList.put("229:0" , Material.PURPLE_SHULKER_BOX);
            IdList.put("230:0" , Material.BLUE_SHULKER_BOX);
            IdList.put("231:0" , Material.BROWN_SHULKER_BOX);
            IdList.put("232:0" , Material.GREEN_SHULKER_BOX);
            IdList.put("233:0" , Material.RED_SHULKER_BOX);
            IdList.put("234:0" , Material.BLACK_SHULKER_BOX);
            IdList.put("235:0" , Material.WHITE_GLAZED_TERRACOTTA);
            IdList.put("236:0" , Material.ORANGE_GLAZED_TERRACOTTA);
            IdList.put("237:0" , Material.MAGENTA_GLAZED_TERRACOTTA);
            IdList.put("238:0" , Material.LIGHT_BLUE_GLAZED_TERRACOTTA);
            IdList.put("239:0" , Material.YELLOW_GLAZED_TERRACOTTA);
            IdList.put("240:0" , Material.LIME_GLAZED_TERRACOTTA);
            IdList.put("241:0" , Material.PINK_GLAZED_TERRACOTTA);
            IdList.put("242:0" , Material.GRAY_GLAZED_TERRACOTTA);
            IdList.put("243:0" , Material.LIGHT_GRAY_GLAZED_TERRACOTTA);
            IdList.put("244:0" , Material.CYAN_GLAZED_TERRACOTTA);
            IdList.put("245:0" , Material.PURPLE_GLAZED_TERRACOTTA);
            IdList.put("246:0" , Material.BLUE_GLAZED_TERRACOTTA);
            IdList.put("247:0" , Material.BROWN_GLAZED_TERRACOTTA);
            IdList.put("248:0" , Material.GREEN_GLAZED_TERRACOTTA);
            IdList.put("249:0" , Material.RED_GLAZED_TERRACOTTA);
            IdList.put("250:0" , Material.BLACK_GLAZED_TERRACOTTA);
            IdList.put("251:0" , Material.WHITE_CONCRETE);
            IdList.put("251:1", Material.ORANGE_CONCRETE);
            IdList.put("251:2", Material.MAGENTA_CONCRETE);
            IdList.put("251:3", Material.LIGHT_BLUE_CONCRETE);
            IdList.put("251:4", Material.YELLOW_CONCRETE);
            IdList.put("251:5", Material.LIME_CONCRETE);
            IdList.put("251:6", Material.PINK_CONCRETE);
            IdList.put("251:7", Material.GRAY_CONCRETE);
            IdList.put("251:8", Material.LIGHT_GRAY_CONCRETE);
            IdList.put("251:9", Material.CYAN_CONCRETE);
            IdList.put("251:10", Material.PURPLE_CONCRETE);
            IdList.put("251:11", Material.BLUE_CONCRETE);
            IdList.put("251:12", Material.BROWN_CONCRETE);
            IdList.put("251:13", Material.GREEN_CONCRETE);
            IdList.put("251:14", Material.RED_CONCRETE);
            IdList.put("251:15", Material.BLACK_CONCRETE);
            IdList.put("252:0" , Material.WHITE_CONCRETE_POWDER);
            IdList.put("252:1", Material.ORANGE_CONCRETE_POWDER);
            IdList.put("252:2", Material.MAGENTA_CONCRETE_POWDER);
            IdList.put("252:3", Material.LIGHT_BLUE_CONCRETE_POWDER);
            IdList.put("252:4", Material.YELLOW_CONCRETE_POWDER);
            IdList.put("252:5", Material.LIME_CONCRETE_POWDER);
            IdList.put("252:6", Material.PINK_CONCRETE_POWDER);
            IdList.put("252:7", Material.GRAY_CONCRETE_POWDER);
            IdList.put("252:8", Material.LIGHT_GRAY_CONCRETE_POWDER);
            IdList.put("252:9", Material.CYAN_CONCRETE_POWDER);
            IdList.put("252:10", Material.PURPLE_CONCRETE_POWDER);
            IdList.put("252:11", Material.BLUE_CONCRETE_POWDER);
            IdList.put("252:12", Material.BROWN_CONCRETE_POWDER);
            IdList.put("252:13", Material.GREEN_CONCRETE_POWDER);
            IdList.put("252:14", Material.RED_CONCRETE_POWDER);
            IdList.put("252:15", Material.BLACK_CONCRETE_POWDER);
            IdList.put("255:0" , Material.STRUCTURE_BLOCK);
            IdList.put("256:0" , Material.IRON_SHOVEL);
            IdList.put("257:0" , Material.IRON_PICKAXE);
            IdList.put("258:0" , Material.IRON_AXE);
            IdList.put("259:0" , Material.FLINT_AND_STEEL);
            IdList.put("260:0" , Material.APPLE);
            IdList.put("261:0" , Material.BOW);
            IdList.put("262:0" , Material.ARROW);
            IdList.put("263:0" , Material.COAL);
            IdList.put("263:1", Material.CHARCOAL);
            IdList.put("264:0" , Material.DIAMOND);
            IdList.put("265:0" , Material.IRON_INGOT);
            IdList.put("266:0" , Material.GOLD_INGOT);
            IdList.put("267:0" , Material.IRON_SWORD);
            IdList.put("268:0" , Material.WOODEN_SWORD);
            IdList.put("269:0" , Material.WOODEN_SHOVEL);
            IdList.put("270:0" , Material.WOODEN_PICKAXE);
            IdList.put("271:0" , Material.WOODEN_AXE);
            IdList.put("272:0" , Material.STONE_SWORD);
            IdList.put("273:0" , Material.STONE_SHOVEL);
            IdList.put("274:0" , Material.STONE_PICKAXE);
            IdList.put("275:0" , Material.STONE_AXE);
            IdList.put("276:0" , Material.DIAMOND_SWORD);
            IdList.put("277:0" , Material.DIAMOND_SHOVEL);
            IdList.put("278:0" , Material.DIAMOND_PICKAXE);
            IdList.put("279:0" , Material.DIAMOND_AXE);
            IdList.put("280:0" , Material.STICK);
            IdList.put("281:0" , Material.BOWL);
            IdList.put("282:0" , Material.MUSHROOM_STEW);
            IdList.put("283:0" , Material.GOLDEN_SWORD);
            IdList.put("284:0" , Material.GOLDEN_SHOVEL);
            IdList.put("285:0" , Material.GOLDEN_PICKAXE);
            IdList.put("286:0" , Material.GOLDEN_AXE);
            IdList.put("287:0" , Material.STRING);
            IdList.put("288:0" , Material.FEATHER);
            IdList.put("289:0" , Material.GUNPOWDER);
            IdList.put("290:0" , Material.WOODEN_HOE);
            IdList.put("291:0" , Material.STONE_HOE);
            IdList.put("292:0" , Material.IRON_HOE);
            IdList.put("293:0" , Material.DIAMOND_HOE);
            IdList.put("294:0" , Material.GOLDEN_HOE);
            IdList.put("295:0" , Material.WHEAT_SEEDS);
            IdList.put("296:0" , Material.WHEAT);
            IdList.put("297:0" , Material.BREAD);
            IdList.put("298:0" , Material.LEATHER_HELMET);
            IdList.put("299:0" , Material.LEATHER_CHESTPLATE);
            IdList.put("300:0" , Material.LEATHER_LEGGINGS);
            IdList.put("301:0" , Material.LEATHER_BOOTS);
            IdList.put("302:0" , Material.CHAINMAIL_HELMET);
            IdList.put("303:0" , Material.CHAINMAIL_CHESTPLATE);
            IdList.put("304:0" , Material.CHAINMAIL_LEGGINGS);
            IdList.put("305:0" , Material.CHAINMAIL_BOOTS);
            IdList.put("306:0" , Material.IRON_HELMET);
            IdList.put("307:0" , Material.IRON_CHESTPLATE);
            IdList.put("308:0" , Material.IRON_LEGGINGS);
            IdList.put("309:0" , Material.IRON_BOOTS);
            IdList.put("310:0" , Material.DIAMOND_HELMET);
            IdList.put("311:0" , Material.DIAMOND_CHESTPLATE);
            IdList.put("312:0" , Material.DIAMOND_LEGGINGS);
            IdList.put("313:0" , Material.DIAMOND_BOOTS);
            IdList.put("314:0" , Material.GOLDEN_HELMET);
            IdList.put("315:0" , Material.GOLDEN_CHESTPLATE);
            IdList.put("316:0" , Material.GOLDEN_LEGGINGS);
            IdList.put("317:0" , Material.GOLDEN_BOOTS);
            IdList.put("318:0" , Material.FLINT);
            IdList.put("319:0" , Material.PORKCHOP);
            IdList.put("320:0" , Material.COOKED_PORKCHOP);
            IdList.put("321:0" , Material.PAINTING);
            IdList.put("322:0" , Material.GOLDEN_APPLE);
            IdList.put("322:1", Material.ENCHANTED_GOLDEN_APPLE);
            IdList.put("323:0" , Material.SIGN);
            IdList.put("324:0" , Material.OAK_DOOR);
            IdList.put("325:0" , Material.BUCKET);
            IdList.put("326:0" , Material.WATER_BUCKET);
            IdList.put("327:0" , Material.LAVA_BUCKET);
            IdList.put("328:0" , Material.MINECART);
            IdList.put("329:0" , Material.SADDLE);
            IdList.put("330:0" , Material.IRON_DOOR);
            IdList.put("331:0" , Material.REDSTONE);
            IdList.put("332:0" , Material.SNOWBALL);
            IdList.put("333:0" , Material.OAK_BOAT);
            IdList.put("334:0" , Material.LEATHER);
            IdList.put("335:0" , Material.MILK_BUCKET);
            IdList.put("336:0" , Material.BRICK);
            IdList.put("337:0" , Material.CLAY);
            IdList.put("338:0" , Material.SUGAR_CANE);
            IdList.put("339:0" , Material.PAPER);
            IdList.put("340:0" , Material.BOOK);
            IdList.put("341:0" , Material.SLIME_BALL);
            IdList.put("342:0" , Material.CHEST_MINECART);
            IdList.put("343:0" , Material.FURNACE_MINECART);
            IdList.put("344:0" , Material.EGG);
            IdList.put("345:0" , Material.COMPASS);
            IdList.put("346:0" , Material.FISHING_ROD);
            IdList.put("347:0" , Material.CLOCK);
            IdList.put("348:0" , Material.GLOWSTONE_DUST);
            IdList.put("349:0" , Material.LEGACY_RAW_FISH);
            IdList.put("349:1", Material.SALMON);
            IdList.put("349:2", Material.TROPICAL_FISH);
            IdList.put("349:3", Material.PUFFERFISH);
            IdList.put("350:0" , Material.COOKED_COD);
            IdList.put("350:1", Material.COOKED_SALMON);
            IdList.put("351:0" , Material.INK_SAC);
            IdList.put("351:1", Material.ROSE_RED);
            IdList.put("351:2", Material.CACTUS_GREEN);
            IdList.put("351:3", Material.COCOA_BEANS);
            IdList.put("351:4", Material.LAPIS_LAZULI);
            IdList.put("351:5", Material.PURPLE_DYE);
            IdList.put("351:6", Material.CYAN_DYE);
            IdList.put("351:7", Material.LIGHT_GRAY_DYE);
            IdList.put("351:8", Material.GRAY_DYE);
            IdList.put("351:9", Material.PINK_DYE);
            IdList.put("351:10", Material.LIME_DYE);
            IdList.put("351:11", Material.DANDELION_YELLOW);
            IdList.put("351:12", Material.LIGHT_BLUE_DYE);
            IdList.put("351:13", Material.MAGENTA_DYE);
            IdList.put("351:14", Material.ORANGE_DYE);
            IdList.put("351:15", Material.BONE_MEAL);
            IdList.put("352:0" , Material.BONE);
            IdList.put("353:0" , Material.SUGAR);
            IdList.put("354:0" , Material.CAKE);
            IdList.put("355:0" , Material.RED_BED);
            IdList.put("356:0" , Material.REPEATER);
            IdList.put("357:0" , Material.COOKIE);
            IdList.put("358:0" , Material.MAP);
            IdList.put("359:0" , Material.SHEARS);
            IdList.put("360:0" , Material.MELON);
            IdList.put("361:0" , Material.PUMPKIN_SEEDS);
            IdList.put("362:0" , Material.MELON_SEEDS);
            IdList.put("363:0" , Material.BEEF);
            IdList.put("364:0" , Material.COOKED_BEEF);
            IdList.put("365:0" , Material.CHICKEN);
            IdList.put("366:0" , Material.COOKED_CHICKEN);
            IdList.put("367:0" , Material.ROTTEN_FLESH);
            IdList.put("368:0" , Material.ENDER_PEARL);
            IdList.put("369:0" , Material.BLAZE_ROD);
            IdList.put("370:0" , Material.GHAST_TEAR);
            IdList.put("371:0" , Material.GOLD_NUGGET);
            IdList.put("372:0" , Material.NETHER_WART);
            IdList.put("373:0" , Material.POTION);
            IdList.put("374:0" , Material.GLASS_BOTTLE);
            IdList.put("375:0" , Material.SPIDER_EYE);
            IdList.put("376:0" , Material.FERMENTED_SPIDER_EYE);
            IdList.put("377:0" , Material.BLAZE_POWDER);
            IdList.put("378:0" , Material.MAGMA_CREAM);
            IdList.put("379:0" , Material.BREWING_STAND);
            IdList.put("380:0" , Material.CAULDRON);
            IdList.put("381:0" , Material.ENDER_EYE);
            IdList.put("382:0" , Material.GLISTERING_MELON_SLICE);
            IdList.put("383:4", Material.ELDER_GUARDIAN_SPAWN_EGG);
            IdList.put("383:5", Material.WITHER_SKELETON_SPAWN_EGG);
            IdList.put("383:6", Material.STRAY_SPAWN_EGG);
            IdList.put("383:23", Material.HUSK_SPAWN_EGG);
            IdList.put("383:27", Material.ZOMBIE_VILLAGER_SPAWN_EGG);
            IdList.put("383:28", Material.SKELETON_HORSE_SPAWN_EGG);
            IdList.put("383:29", Material.ZOMBIE_HORSE_SPAWN_EGG);
            IdList.put("383:31", Material.DONKEY_SPAWN_EGG);
            IdList.put("383:32", Material.MULE_SPAWN_EGG);
            IdList.put("383:34", Material.EVOKER_SPAWN_EGG);
            IdList.put("383:35", Material.VEX_SPAWN_EGG);
            IdList.put("383:36", Material.VINDICATOR_SPAWN_EGG);
            IdList.put("383:50", Material.CREEPER_SPAWN_EGG);
            IdList.put("383:51", Material.SKELETON_SPAWN_EGG);
            IdList.put("383:52", Material.SPIDER_SPAWN_EGG);
            IdList.put("383:54", Material.ZOMBIE_SPAWN_EGG);
            IdList.put("383:55", Material.SLIME_SPAWN_EGG);
            IdList.put("383:56", Material.GHAST_SPAWN_EGG);
            IdList.put("383:57", Material.ZOMBIE_PIGMAN_SPAWN_EGG);
            IdList.put("383:58", Material.ENDERMAN_SPAWN_EGG);
            IdList.put("383:59", Material.CAVE_SPIDER_SPAWN_EGG);
            IdList.put("383:60", Material.SILVERFISH_SPAWN_EGG);
            IdList.put("383:61", Material.BLAZE_SPAWN_EGG);
            IdList.put("383:62", Material.MAGMA_CUBE_SPAWN_EGG);
            IdList.put("383:65", Material.BAT_SPAWN_EGG);
            IdList.put("383:66", Material.WITCH_SPAWN_EGG);
            IdList.put("383:67", Material.ENDERMITE_SPAWN_EGG);
            IdList.put("383:68", Material.GUARDIAN_SPAWN_EGG);
            IdList.put("383:69", Material.SHULKER_SPAWN_EGG);
            IdList.put("383.9", Material.PIG_SPAWN_EGG);
            IdList.put("383.91", Material.SHEEP_SPAWN_EGG);
            IdList.put("383.92", Material.COW_SPAWN_EGG);
            IdList.put("383.93", Material.CHICKEN_SPAWN_EGG);
            IdList.put("383.94", Material.SQUID_SPAWN_EGG);
            IdList.put("383.95", Material.WOLF_SPAWN_EGG);
            IdList.put("383.96", Material.MOOSHROOM_SPAWN_EGG);
            IdList.put("383.98", Material.OCELOT_SPAWN_EGG);
            IdList.put("383:100", Material.HORSE_SPAWN_EGG);
            IdList.put("383:101", Material.RABBIT_SPAWN_EGG);
            IdList.put("383:102", Material.POLAR_BEAR_SPAWN_EGG);
            IdList.put("383:103", Material.LLAMA_SPAWN_EGG);
            IdList.put("383:105", Material.PARROT_SPAWN_EGG);
            IdList.put("383:120", Material.VILLAGER_SPAWN_EGG);
            IdList.put("384:0" , Material.EXPERIENCE_BOTTLE);
            IdList.put("385:0" , Material.FIRE_CHARGE);
            IdList.put("386:0" , Material.WRITABLE_BOOK);
            IdList.put("387:0" , Material.WRITTEN_BOOK);
            IdList.put("388:0" , Material.EMERALD);
            IdList.put("389:0" , Material.ITEM_FRAME);
            IdList.put("390:0" , Material.FLOWER_POT);
            IdList.put("391:0" , Material.CARROT);
            IdList.put("392:0" , Material.POTATO);
            IdList.put("393:0" , Material.BAKED_POTATO);
            IdList.put("394:0" , Material.POISONOUS_POTATO);
            IdList.put("395:0" , Material.MAP);
            IdList.put("396:0" , Material.GOLDEN_CARROT);
            IdList.put("397:0" , Material.SKELETON_SKULL);
            IdList.put("397:1", Material.WITHER_SKELETON_SKULL);
            IdList.put("397:2", Material.ZOMBIE_HEAD);
            IdList.put("397:3", Material.PLAYER_HEAD);
            IdList.put("397:4", Material.CREEPER_HEAD);
            IdList.put("397:5", Material.DRAGON_HEAD);
            IdList.put("398:0" , Material.CARROT_ON_A_STICK);
            IdList.put("399:0" , Material.NETHER_STAR);
            IdList.put("400:0" , Material.PUMPKIN_PIE);
            IdList.put("401:0" , Material.FIREWORK_ROCKET);
            IdList.put("402:0" , Material.FIREWORK_STAR);
            IdList.put("403:0" , Material.ENCHANTED_BOOK);
            IdList.put("404:0" , Material.COMPARATOR);
            IdList.put("405:0" , Material.NETHER_BRICK);
            IdList.put("406:0" , Material.QUARTZ);
            IdList.put("407:0" , Material.TNT_MINECART);
            IdList.put("408:0" , Material.HOPPER_MINECART);
            IdList.put("409:0" , Material.PRISMARINE_SHARD);
            IdList.put("410:0" , Material.PRISMARINE_CRYSTALS);
            IdList.put("411:0" , Material.RABBIT);
            IdList.put("412:0" , Material.COOKED_RABBIT);
            IdList.put("413:0" , Material.RABBIT_STEW);
            IdList.put("414:0" , Material.RABBIT_FOOT);
            IdList.put("415:0" , Material.RABBIT_HIDE);
            IdList.put("416:0" , Material.ARMOR_STAND);
            IdList.put("417:0" , Material.IRON_HORSE_ARMOR);
            IdList.put("418:0" , Material.GOLDEN_HORSE_ARMOR);
            IdList.put("419:0" , Material.DIAMOND_HORSE_ARMOR);
            IdList.put("420:0" , Material.LEAD);
            IdList.put("421:0" , Material.NAME_TAG);
            IdList.put("422:0" , Material.COMMAND_BLOCK_MINECART);
            IdList.put("423:0" , Material.MUTTON);
            IdList.put("424:0" , Material.COOKED_MUTTON);
            IdList.put("425:0" , Material.WHITE_BANNER);
            IdList.put("426:0" , Material.END_CRYSTAL);
            IdList.put("427:0" , Material.SPRUCE_DOOR);
            IdList.put("428:0" , Material.BIRCH_DOOR);
            IdList.put("429:0" , Material.JUNGLE_DOOR);
            IdList.put("430:0" , Material.ACACIA_DOOR);
            IdList.put("431:0" , Material.DARK_OAK_DOOR);
            IdList.put("432:0" , Material.CHORUS_FRUIT);
            IdList.put("433:0" , Material.POPPED_CHORUS_FRUIT);
            IdList.put("434:0" , Material.BEETROOT);
            IdList.put("435:0" , Material.BEETROOT_SEEDS);
            IdList.put("436:0" , Material.BEETROOT_SOUP);
            IdList.put("437:0" , Material.DRAGON_BREATH);
            IdList.put("438:0" , Material.SPLASH_POTION);
            IdList.put("439:0" , Material.SPECTRAL_ARROW);
            IdList.put("440:0" , Material.TIPPED_ARROW);
            IdList.put("441:0" , Material.LINGERING_POTION);
            IdList.put("442:0" , Material.SHIELD);
            IdList.put("443:0" , Material.ELYTRA);
            IdList.put("444:0" , Material.SPRUCE_BOAT);
            IdList.put("445:0" , Material.BIRCH_BOAT);
            IdList.put("446:0" , Material.JUNGLE_BOAT);
            IdList.put("447:0" , Material.ACACIA_BOAT);
            IdList.put("448:0" , Material.DARK_OAK_BOAT);
            IdList.put("449:0" , Material.TOTEM_OF_UNDYING);
            IdList.put("450:0" , Material.SHULKER_SHELL);
            IdList.put("452:0" , Material.IRON_NUGGET);
            IdList.put("453:0" , Material.KNOWLEDGE_BOOK);

        // Just added all the Materials with a ID
        // If you want, you can edit the IDs and subIDs

    }

    // And the important getter for the Materials
    public Material getMaterial(Integer Id, Integer subId) {

        if(subId == null) {
            subId = 0;
        }

        if(Id == null) {
            Id = 0;
        }


        Integer gid = Id;

        if (IdList.containsKey(gid)) {

            return IdList.get(gid);

        }

        return null;

    }

    public Material getMaterial(Integer Id) {

        if (IdList.containsKey(""+Id+":0")) {

            return IdList.get(Id);

        }

        return null;

    }

    public Material getMaterial(String idwithsub) {

        String[] ids = idwithsub.split(":");

        if(ids.length == 2) {

            if(StringUtils.isNumeric(ids[0]) && StringUtils.isNumeric(ids[1])) {

                Integer Id = Integer.valueOf(ids[0]);
                Integer subId = Integer.valueOf(ids[1]);

                double id = Double.valueOf(Id + "." + 0);
                double gid = Double.valueOf(Id + "." + subId);

                if (IdList.containsKey(gid)) {

                    return IdList.get(gid);

                } else {

                    if(IdList.containsKey(id)) {

                        return IdList.get(id);

                    }

                }

            }

        }

        return null;

    }

    public Material getMaterial(String idwithsub, String splitChar) {

        String[] ids = idwithsub.split(splitChar);

        if(ids.length == 2) {

            if(StringUtils.isNumeric(ids[0]) && StringUtils.isNumeric(ids[1])) {

                Integer Id = Integer.valueOf(ids[0]);
                Integer subId = Integer.valueOf(ids[1]);

                double id = Double.valueOf(Id + "." + 0);
                double gid = Double.valueOf(Id + "." + subId);

                if (IdList.containsKey(gid)) {

                    return IdList.get(gid);

                } else {

                    if(IdList.containsKey(id)) {

                        return IdList.get(id);

                    }

                }

            }

        }

        return null;

    }

    public double getID(Material material) {

        if(IdList.containsValue(material)) {
            return idCheck(material);
        }

        return 0.0;

    }

    public double getID(String materialName) {

        Material mat = Material.getMaterial(materialName);

        if(IdList.containsValue(mat)) {
            return idCheck(mat);
        }

        return 0.0;

    }

    private double idCheck(Material mat) {

        Double[] db = IdList.keySet().toArray(new Double[0]);

        for(double d : db) {

            if(mat == IdList.get(d)) {

                return d;

            }

        }

        return 0.0;

    }

}
