package net.upd4ting.uhcreloaded.configuration.configs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import net.upd4ting.uhcreloaded.configuration.Configuration;
import net.upd4ting.uhcreloaded.exception.InvalidConfigException;

public class BoardConfig extends Configuration {
	
	private Boolean enabled;
	private String title;
	private ArrayList<String> waiting = new ArrayList<>();
	private ArrayList<String> ingame = new ArrayList<>();
	private ArrayList<String> spectator = new ArrayList<>();
	
	public BoardConfig() {
		super(ConfType.STARTUP, "scoreboard.yml");
	}

	@Override
	public void loadData() throws InvalidConfigException {
		enabled = conf.getBoolean("enabled");
		title = ChatColor.translateAlternateColorCodes('&', conf.getString("title"));
		waiting = getListColor(conf.getStringList("waiting"));
		ingame = getListColor(conf.getStringList("ingame"));
		spectator = getListColor(conf.getStringList("spectator"));
	}
	
	private ArrayList<String> getListColor(List<String> l) {
		ArrayList<String> list = new ArrayList<>();
		
		for (String s : l) {
			list.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		
		return list;
	}
	
	public Boolean isEnabled() { return enabled; }
	public String getTitle() { return title; }
	public ArrayList<String> getWaiting() { return waiting; }
	public ArrayList<String> getInGame() { return ingame; }
	public ArrayList<String> getSpectator() { return spectator; }
}
