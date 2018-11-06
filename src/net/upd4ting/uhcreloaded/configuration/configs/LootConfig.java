package net.upd4ting.uhcreloaded.configuration.configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import net.upd4ting.uhcreloaded.configuration.Configuration;
import net.upd4ting.uhcreloaded.exception.InvalidConfigException;
import net.upd4ting.uhcreloaded.schematic.Schematic.BlockInfo;
import net.upd4ting.uhcreloaded.util.Interval;

public class LootConfig extends Configuration {
	private Boolean entityLoot;
	private Boolean blockLoot;
	private HashMap<BlockInfo, Loot> blocksLoots = new HashMap<>();
	private HashMap<EntityType, Loot> entitiesLoots = new HashMap<>();
	
	public LootConfig() {
		super(ConfType.STARTUP, "loot.yml", "Entities", "Blocks");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void loadData() throws InvalidConfigException {
		entityLoot = conf.getBoolean("loot-entities");
		blockLoot = conf.getBoolean("loot-block");
		
		if (entityLoot) {
			for (String s : conf.getConfigurationSection("Entities").getKeys(false)) {
				String target = "Entities." + s + ".";
				
				String entity  = conf.getString(target + "id");
				
				// -- Check de l'entitée
				EntityType type = EntityType.valueOf(entity);
				
				if (type == null)
					throw new InvalidConfigException("[loot.yml] Invalid entity name: " + entity);
				
				Integer exp = conf.getInt(target + "exp");
				
				if (exp < 0)
					throw new InvalidConfigException("[loot.yml] Invalid exp value: " + exp + " ( must be >= 0 )");
				
				List<LootItem> loots = getLootsItem(conf.getConfigurationSection(target + "loots"));
				
				entitiesLoots.put(type, new Loot(exp, loots));
			}
		}
		
		if (blockLoot) {
			for (String s : this.conf.getConfigurationSection("Blocks").getKeys(false)) {
				String target = "Blocks." + s + ".";
				
				Integer id = conf.getInt(target + "id");
				byte data = (byte) conf.getInt(target + "data");
				
				// -- Check du matérial
				Material mat = Material.getMaterial(id);
				
				if (mat == null)
					throw new InvalidConfigException("[loot.yml] Invalid block id: " + id);
				
				Integer exp = this.conf.getInt(target + "exp");
				
				if (exp < 0)
					throw new InvalidConfigException("[loot.yml] Invalid exp value: " + exp + " ( must be >= 0 )");
				
				List<LootItem> loots = getLootsItem(this.conf.getConfigurationSection(target + "loots"));
				
				blocksLoots.put(new BlockInfo(mat, data), new Loot(exp, loots));
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private List<LootItem> getLootsItem(ConfigurationSection section) throws InvalidConfigException{
		
		List<LootItem> loots = new ArrayList<>();
		
		for (String key : section.getKeys(false)) {
			String target = key + ".";
			
			Integer id = section.getInt(target + "id");
			
			if (id < 0)
				throw new InvalidConfigException("[loot.yml] Invalid loot item id ( must be > 0 )");
			
			byte data = (byte) section.getInt(target + "data");
			
			if (data < 0)
				throw new InvalidConfigException("[loot.yml] Invalid loot item data ( must be > 0 )");
			
			String amount = section.getString(target + "amount");
			Interval<Integer> interval = null;
			
			if (!amount.contains("-")) {
				Integer min = stringToInteger(amount);
				interval = new Interval<>(min, min);
			} else {
				String[] splitted = amount.split("-");
				Integer min = stringToInteger(splitted[0]);
				Integer max = stringToInteger(splitted[1]);
				interval = new Interval<>(min, max);
			}
			
			Double percent = section.getDouble(target + "chance");
			
			ItemStack item = new ItemStack(Material.getMaterial(id), 1, data);
			loots.add(new LootItem(item, percent, interval));
		}
		
		return loots;
	}
	
	private Integer stringToInteger(String s) throws InvalidConfigException {
		Integer value = null;
		
		try {
			value = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new InvalidConfigException("[loot.yml] Invalid amount format: " + s + " , must be a number or an interval: 1-3");
		}
		
		return value;
	}
	
	public Boolean isOverridingEntitiesLoot() { return entityLoot; }
	public Boolean isOverridingBlocksLoot() { return blockLoot; }
	public HashMap<BlockInfo, Loot> getBlockLoots() { return blocksLoots; }
	public HashMap<EntityType, Loot> getEntitiesLoots() { return entitiesLoots; }
	
	public Boolean containsBlockLoot(Material mat, byte data) {
		for (BlockInfo info : blocksLoots.keySet())
			if (info.material == mat && info.data == data)
				return true;
		return false;
	}
	
	public Loot getBlockLoot(Material mat, byte data) {
		for (BlockInfo info : blocksLoots.keySet())
			if (info.material == mat && info.data == data)
				return blocksLoots.get(info);
		return null;
	}
	
	public class LootItem {
		private ItemStack item;
		private Interval<Integer> amounts;
		private Double percent;

		public LootItem(ItemStack item, Double percent, Interval<Integer> amounts) {
			this.item = item;
			this.percent = percent;
			this.amounts = amounts;
		}

		public ItemStack getLootItem() {
			Double ran = new Random().nextDouble() * 100;
			if (ran <= percent) {
				item.setAmount(amounts.getAsRandomInt());
				return item.clone();
			} else
				return null;
		}
	}
	
	public class Loot {
		private List<LootItem> loots;
		private Integer exp;
		
		public Loot(Integer exp, List<LootItem> loots) {
			this.loots = loots;
			this.exp = exp;
		}
		
		public List<LootItem> getLoots() { return loots; }
		public Integer getExp() { return exp; }
	}
}
