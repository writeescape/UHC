package net.upd4ting.uhcreloaded.nms.v1_11_R1;

import com.comphenix.protocol.events.PacketContainer;

import net.minecraft.server.v1_11_R1.IScoreboardCriteria.EnumScoreboardHealthDisplay;
import net.minecraft.server.v1_11_R1.PacketPlayOutScoreboardScore.EnumScoreboardAction;
import net.upd4ting.uhcreloaded.nms.common.SpecificWriterHandler;

public class SpecificWriter implements SpecificWriterHandler {

	@Override
	public void write(PacketContainer container, SpecificWriterType type) {
		if (type == SpecificWriterType.DISPLAY) {
			container.getSpecificModifier(EnumScoreboardHealthDisplay.class)
            .write(0, EnumScoreboardHealthDisplay.INTEGER);
		} else if (type == SpecificWriterType.ACTIONCHANGE) {
			container.getSpecificModifier(EnumScoreboardAction.class)
            .write(0, EnumScoreboardAction.CHANGE);
		} else if (type == SpecificWriterType.ACTIONREMOVE) {
			container.getSpecificModifier(EnumScoreboardAction.class)
            .write(0, EnumScoreboardAction.REMOVE);
		}
	}
}
