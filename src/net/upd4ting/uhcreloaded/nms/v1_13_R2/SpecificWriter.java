package net.upd4ting.uhcreloaded.nms.v1_13_R2;

import com.comphenix.protocol.events.PacketContainer;

import net.minecraft.server.v1_13_R2.IScoreboardCriteria.EnumScoreboardHealthDisplay;
import com.bergerkiller.generated.net.minecraft.server.PacketPlayOutScoreboardScoreHandle.EnumScoreboardActionHandle;
import net.upd4ting.uhcreloaded.nms.common.SpecificWriterHandler;

public class SpecificWriter implements SpecificWriterHandler {

	@Override
	public void write(PacketContainer container, SpecificWriterType type) {
		if (type == SpecificWriterType.DISPLAY) {
			container.getSpecificModifier(EnumScoreboardHealthDisplay.class)
            .write(0, EnumScoreboardHealthDisplay.INTEGER);
		} else if (type == SpecificWriterType.ACTIONCHANGE) {
			container.getSpecificModifier(EnumScoreboardActionHandle.class)
            .write(0, EnumScoreboardActionHandle.CHANGE);
		} else if (type == SpecificWriterType.ACTIONREMOVE) {
			container.getSpecificModifier(EnumScoreboardActionHandle.class)
            .write(0, EnumScoreboardActionHandle.REMOVE);
		}
	}
}
