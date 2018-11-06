package net.upd4ting.uhcreloaded.customevent;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BlockBreakLootEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	 
	@Override
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	private Player p;
	private Block b;
	private boolean cancelled;

	public BlockBreakLootEvent(Player p, Block b) {
		this.p = p;
		this.b = b;
		this.cancelled = false;
	}

	public Player getPlayer() {
		return p;
	}
	
	public Block getBlock() {
		return b;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}