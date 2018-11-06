package net.upd4ting.uhcreloaded.nms.common;

import java.util.List;

import org.bukkit.entity.Player;

public interface TitleHandler {
	public void sendTitle(Player player, String title, String subTitle, int fadeIn, int fadeOut, int time);
	public void sendTitle(Player player, String title, String subTitle);
	public void sendTitleToPlayers(Player[] players, String title, String subTitle);
	public void sendTitleToPlayers(Player[] players, String title, String subTitle, int fadeIn, int fadeOut, int time);
	public void sendTitleToPlayers(List<Player> players, String title, String subTitle, int fadeIn, int fadeOut, int time);
	public void sendTitleToAllPlayers(String title, String subTitle, int fadeIn, int fadeOut, int time);
	public void sendTitleToAllPlayers(String title, String subTitle);
	public void clearTitle(Player player);
}
