package net.upd4ting.uhcreloaded.nms.v1_7_R4;

import net.minecraft.server.v1_7_R4.EnumClientCommand;
import net.minecraft.server.v1_7_R4.PacketPlayInClientCommand;
import net.upd4ting.uhcreloaded.nms.common.RespawnHandler;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Respawn implements RespawnHandler {
	@Override
	public void respawn(Player player) {
        if (player != null && player.isDead() && player.isOnline()) {
            PacketPlayInClientCommand packet = new PacketPlayInClientCommand((EnumClientCommand.PERFORM_RESPAWN));
            ((CraftPlayer)player).getHandle().playerConnection.a(packet);
        }
	}
}
