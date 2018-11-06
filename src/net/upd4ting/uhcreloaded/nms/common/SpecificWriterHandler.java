package net.upd4ting.uhcreloaded.nms.common;

import com.comphenix.protocol.events.PacketContainer;

public interface SpecificWriterHandler {
	public enum SpecificWriterType { DISPLAY, ACTIONCHANGE, ACTIONREMOVE; }
	
	public void write(PacketContainer container, SpecificWriterType type);
}
