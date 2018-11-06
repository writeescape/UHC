package net.upd4ting.uhcreloaded.nms.v1_9_R1;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import net.minecraft.server.v1_9_R1.BiomeBase;
import net.minecraft.server.v1_9_R1.MinecraftKey;
import net.minecraft.server.v1_9_R1.RegistryID;
import net.minecraft.server.v1_9_R1.RegistryMaterials;
import net.upd4ting.uhcreloaded.nms.common.Biome;
import net.upd4ting.uhcreloaded.nms.common.BiomeHandler;
import net.upd4ting.uhcreloaded.util.ReflectionUtils;

@SuppressWarnings({"unchecked","rawtypes"})
public class BiomeReplacer implements BiomeHandler {

	@Override
	public void swap(Biome from, Biome to) {
		BiomeRegistry registry = BiomeRegistry.getInstance();
		
        int oldbiomeid = Biome.valueOf(from.name()).getId();
        registry.register(oldbiomeid, new MinecraftKey(from.name().toLowerCase()), registry.getObject(new MinecraftKey(to.name().toLowerCase())));
	}

	@Override
	public void swap(Biome to) {
		BiomeRegistry registry = BiomeRegistry.getInstance();

		for (int i = 0; i < BiomeRegistry.allbiomes.length; i++)
			registry.register(i, new MinecraftKey(BiomeRegistry.allbiomes[i].toLowerCase()), registry.getObject(new MinecraftKey(to.name().toLowerCase())));
	}

	// Related class for biome changing
	public abstract interface IRegistry<K, V> extends Iterable<V> {
		public abstract void register(int paramInt, K paramK, V paramV);

		public abstract V getObject(K paramK);

		public abstract K getNameForObject(V paramV);

		public abstract boolean containsKey(K paramK);

		public abstract int getIDForObject(V paramV);

		public abstract V getObjectById(int paramInt);
	}

	public static class IDRegistry<T> implements Iterable<T> {
		private final IdentityHashMap<T, Integer> identityMap;
		private final List<T> objectList = Lists.newArrayList();

		public IDRegistry(int size) {
			this.identityMap = new IdentityHashMap(size);
		}

		public void put(T key, Integer value) {
			this.identityMap.put(key, value);
			while (this.objectList.size() <= value.intValue()) {
				this.objectList.add(null);
			}
			this.objectList.set(value.intValue(), key);
		}

		public void remove(T key) {
			put(key, null);
		}

		public int get(T key) {
			Integer integer = (Integer)this.identityMap.get(key);
			return integer == null ? -1 : integer.intValue();
		}

		public final T getByValue(int value) {
			return (T)((value >= 0) && (value < this.objectList.size()) ? this.objectList.get(value) : null);
		}

		public Iterator<T> iterator() {
			return Iterators.filter(this.objectList.iterator(), Predicates.notNull());
		}
	}

	public static class BiomeRegistry extends RegistryMaterials<MinecraftKey, BiomeBase> implements IRegistry<MinecraftKey, BiomeBase> {
		protected final Map<MinecraftKey, BiomeBase> registry = new HashMap();
		protected final IDRegistry<BiomeBase> idRegistry = new IDRegistry(256);
		public static String[] allbiomes = { "OCEAN", "PLAINS", "DESERT", "EXTREME_HILLS", "FOREST", "TAIGA", "SWAMPLAND", "RIVER", "HELL", "SKY", "FROZEN_OCEAN", "FROZEN_RIVER", "ICE_FLATS", "ICE_MOUNTAINS", "MUSHROOM_ISLAND", "MUSHROOM_ISLAND_SHORE", 
				"BEACHES", "DESERT_HILLS", "FOREST_HILLS", "TAIGA_HILLS", "SMALLER_EXTREME_HILLS", "JUNGLE", "JUNGLE_HILLS", "JUNGLE_EDGE", "DEEP_OCEAN", "STONE_BEACH", "COLD_BEACH", "BIRCH_FOREST", "BIRCH_FOREST_HILLS", 
				"ROOFED_FOREST", "TAIGA_COLD", "TAIGA_COLD_HILLS", "REDWOOD_TAIGA", "REDWOOD_TAIGA_HILLS", "EXTREME_HILLS_WITH_TREES", "SAVANNA", "SAVANNA_ROCK", "MESA", "MESA_ROCK", "MESA_CLEAR_ROCK" };

		public static BiomeRegistry getInstance() {
			if (!(BiomeBase.REGISTRY_ID instanceof BiomeRegistry)) {
				init();
			}
			return (BiomeRegistry)BiomeBase.REGISTRY_ID;
		}

		public void a(int id, MinecraftKey entry, BiomeBase value) {
			register(id, entry, value);
		}

		public void a(MinecraftKey entry, BiomeBase value) {
			Validate.notNull(entry);
			Validate.notNull(value);
			if (this.registry.containsKey(entry)) {
				Bukkit.getLogger().log(Level.FINE, "Adding duplicate key '" + entry + "' to registry");
			}
			this.registry.put(entry, value);
		}

		public void register(int id, MinecraftKey entry, BiomeBase value) {
			this.idRegistry.put(value, Integer.valueOf(id));
			a(entry, value);
		}

		public BiomeBase get(MinecraftKey var1) {
			return getObject(var1);
		}

		public MinecraftKey b(BiomeBase var1) {
			return getNameForObject(var1);
		}

		public boolean d(MinecraftKey var1) {
			return containsKey(var1);
		}

		public int a(BiomeBase var1) {
			return getIDForObject(var1);
		}

		public BiomeBase getId(int var1) {
			return getObjectById(var1);
		}

		public BiomeBase getObject(MinecraftKey name) {
			return (BiomeBase)this.registry.get(name);
		}

		public MinecraftKey getNameForObject(BiomeBase obj) {
			MinecraftKey result = null;
			for (Map.Entry<MinecraftKey, BiomeBase> entryset : this.registry.entrySet()) {
				if ((entryset.getValue() != null) && (((BiomeBase)entryset.getValue()).equals(obj)))
				{
					if (result != null)
					{
						Bukkit.getLogger().warning("DUPLICATE ENTRY FOR BIOME " + obj.getClass().getName());
						break;
					}
					result = (MinecraftKey)entryset.getKey();
				}
			}
			return result;
		}

		public boolean containsKey(MinecraftKey key) {
			return this.registry.containsKey(key);
		}

		public int getIDForObject(BiomeBase obj) {
			return this.idRegistry.get(obj);
		}

		public BiomeBase getObjectById(int id) {
			return (BiomeBase)this.idRegistry.getByValue(id);
		}

		public Iterator<BiomeBase> iterator() {
			return this.idRegistry.iterator();
		}

		public static boolean init() {
			try
			{
				BiomeRegistry registry = new BiomeRegistry();
				RegistryMaterials<MinecraftKey, BiomeBase> oldRegistry = BiomeBase.REGISTRY_ID;
				RegistryID<BiomeBase> oldIDRegistry = (RegistryID)ReflectionUtils.getValue(RegistryMaterials.class, oldRegistry, "a");
				Map<BiomeBase, MinecraftKey> oldDataRegistry = (Map)ReflectionUtils.getValue(RegistryMaterials.class, oldRegistry, "b");
				for (Map.Entry<BiomeBase, MinecraftKey> entry : oldDataRegistry.entrySet())
				{
					int id = oldIDRegistry.getId((BiomeBase)entry.getKey());
					if ((id != -1) && (entry.getKey() != null)) {
						registry.register(id, (MinecraftKey)entry.getValue(), (BiomeBase)entry.getKey());
					}
				}
				ReflectionUtils.setFinalStatic(BiomeBase.class.getDeclaredField("REGISTRY_ID"), registry);
			}
			catch (ReflectiveOperationException e)
			{
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}
}		
