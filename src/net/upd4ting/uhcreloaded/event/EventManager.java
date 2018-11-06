package net.upd4ting.uhcreloaded.event;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.event.events.EventBlockBreak;
import net.upd4ting.uhcreloaded.event.events.EventBlockPlace;
import net.upd4ting.uhcreloaded.event.events.EventChunkUnload;
import net.upd4ting.uhcreloaded.event.events.EventCreatureSpawn;
import net.upd4ting.uhcreloaded.event.events.EventDamage;
import net.upd4ting.uhcreloaded.event.events.EventEntityDeath;
import net.upd4ting.uhcreloaded.event.events.EventEntityRegainHealth;
import net.upd4ting.uhcreloaded.event.events.EventFoodLevelChange;
import net.upd4ting.uhcreloaded.event.events.EventInventory;
import net.upd4ting.uhcreloaded.event.events.EventInventoryClick;
import net.upd4ting.uhcreloaded.event.events.EventPlayerAsyncChat;
import net.upd4ting.uhcreloaded.event.events.EventPlayerConsume;
import net.upd4ting.uhcreloaded.event.events.EventPlayerInteract;
import net.upd4ting.uhcreloaded.event.events.EventPlayerItemPrepare;
import net.upd4ting.uhcreloaded.event.events.EventPlayerJoin;
import net.upd4ting.uhcreloaded.event.events.EventPlayerLogin;
import net.upd4ting.uhcreloaded.event.events.EventPlayerMove;
import net.upd4ting.uhcreloaded.event.events.EventPlayerPickupDrop;
import net.upd4ting.uhcreloaded.event.events.EventPlayerQuit;
import net.upd4ting.uhcreloaded.event.events.EventWorldInit;
import net.upd4ting.uhcreloaded.event.events.Frozen;
import net.upd4ting.uhcreloaded.event.events.ObsidianFaster;
import net.upd4ting.uhcreloaded.item.ItemListener;

public class EventManager {
	public static void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new EventBlockBreak(), UHCReloaded.getInstance());
		pm.registerEvents(new EventEntityDeath(), UHCReloaded.getInstance());
		pm.registerEvents(new EventPlayerJoin(), UHCReloaded.getInstance());
		pm.registerEvents(new EventPlayerQuit(), UHCReloaded.getInstance());
		pm.registerEvents(new EventWorldInit(), UHCReloaded.getInstance());
		pm.registerEvents(new EventEntityRegainHealth(), UHCReloaded.getInstance());
		pm.registerEvents(new EventDamage(), UHCReloaded.getInstance());
		pm.registerEvents(new EventPlayerInteract(), UHCReloaded.getInstance());
		pm.registerEvents(new EventPlayerLogin(), UHCReloaded.getInstance());
		pm.registerEvents(new EventPlayerAsyncChat(), UHCReloaded.getInstance());
		pm.registerEvents(new EventInventory(), UHCReloaded.getInstance());
		pm.registerEvents(new EventPlayerMove(), UHCReloaded.getInstance());
		pm.registerEvents(new EventFoodLevelChange(), UHCReloaded.getInstance());
		pm.registerEvents(new Frozen(), UHCReloaded.getInstance());
		pm.registerEvents(new EventPlayerConsume(), UHCReloaded.getInstance());
		pm.registerEvents(new EventCreatureSpawn(), UHCReloaded.getInstance());
		pm.registerEvents(new EventPlayerPickupDrop(), UHCReloaded.getInstance());
		pm.registerEvents(new EventChunkUnload(), UHCReloaded.getInstance());
		pm.registerEvents(new EventBlockPlace(), UHCReloaded.getInstance());
		pm.registerEvents(new EventInventoryClick(), UHCReloaded.getInstance());
		pm.registerEvents(new ItemListener(), UHCReloaded.getInstance());
		
		if (UHCReloaded.getMainConfiguration().isCraftIncreasedEnabled())
			pm.registerEvents(new EventPlayerItemPrepare(), UHCReloaded.getInstance());
		if (UHCReloaded.getMainConfiguration().isObsidianFasterEnabled())
			pm.registerEvents(new ObsidianFaster(), UHCReloaded.getInstance());
	}
}
