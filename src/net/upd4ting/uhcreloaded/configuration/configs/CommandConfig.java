package net.upd4ting.uhcreloaded.configuration.configs;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.upd4ting.uhcreloaded.configuration.Configuration;
import net.upd4ting.uhcreloaded.exception.InvalidConfigException;

public class CommandConfig extends Configuration {
	
	private Boolean winCommand;
	private List<String> winCommands;
	private Boolean killCommand;
	private List<String> killCommands;

	public CommandConfig() {
		super(ConfType.STARTUP, "commands.yml");
	}

	@Override
	public void loadData() throws InvalidConfigException {
		winCommand = conf.getBoolean("enable-win-command");
		winCommands = getStringListConfig("win-command");
		killCommand = conf.getBoolean("enable-kill-command");
		killCommands = getStringListConfig("kill-command");
	}
	
	public void checkExecuteCommand(Player p) {
		if (winCommand)
			executeCommand(p, winCommands);
		if (killCommand)
			executeCommand(p, killCommands);
	}
	
	private void executeCommand(Player p, List<String> commands) {
		for (String s : commands)
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replaceAll("%p", p.getName()));
	}
	
	public Boolean isWinCommandEnabled() { return winCommand; }
	public List<String> getWinCommands() { return winCommands; }
	public Boolean iskillCommandEnabled() { return killCommand; }
	public List<String> getKillCommands() { return killCommands; }
}
