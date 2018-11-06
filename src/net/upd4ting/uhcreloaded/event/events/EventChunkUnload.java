package net.upd4ting.uhcreloaded.event.events;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class EventChunkUnload implements Listener {
	
	public static Set<Chunk> keepChunk = new HashSet<>();
	
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent e) {
		if (keepChunk.contains(e.getChunk()))
			e.setCancelled(true);
	}
}
