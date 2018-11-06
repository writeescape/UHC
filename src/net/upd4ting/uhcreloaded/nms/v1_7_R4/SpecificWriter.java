package net.upd4ting.uhcreloaded.nms.v1_7_R4;

import net.upd4ting.uhcreloaded.nms.common.SpecificWriterHandler;

import com.comphenix.protocol.events.PacketContainer;

public class SpecificWriter implements SpecificWriterHandler {

	@Override
	public void write(PacketContainer container, SpecificWriterType type) {
		if (type == SpecificWriterType.DISPLAY) {
			container.getStrings().writeSafely(2, "integer");
		} else if (type == SpecificWriterType.ACTIONCHANGE) {
			container.getIntegers().write(1, 0);
		} else if (type == SpecificWriterType.ACTIONREMOVE) {
			container.getIntegers().write(1, 1);
		}
	}
}
