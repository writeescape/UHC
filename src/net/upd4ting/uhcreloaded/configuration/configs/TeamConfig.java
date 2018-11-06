package net.upd4ting.uhcreloaded.configuration.configs;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.Configuration;
import net.upd4ting.uhcreloaded.exception.InvalidConfigException;
import net.upd4ting.uhcreloaded.schematic.Schematic.BlockInfo;
import net.upd4ting.uhcreloaded.team.Team;

public class TeamConfig extends Configuration {
	
	private Boolean enabled;
	private Boolean autoFill;
	private Boolean guiEnabled;
	private Integer teamSize;
	private Integer expireTime;
	private Integer guiItemId;
	private Integer guiSlotItem;
	
	public TeamConfig() {
		super(ConfType.ENABLE, "team.yml");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void loadData() throws InvalidConfigException {
		enabled = conf.getBoolean("enabled");
		autoFill = conf.getBoolean("auto-fill");
		guiEnabled = conf.getBoolean("gui-enabled");
		teamSize = conf.getInt("team-size");
		expireTime = conf.getInt("expire-time");
		guiItemId = conf.getInt("gui-item");
		guiSlotItem = conf.getInt("slot-item");
		
		if (this.guiEnabled) {
			for (String s : conf.getStringList("preconfigurated-team")) {
				if (!s.contains(":"))
					throw new InvalidConfigException("[team.yml] Invalid line: " + s);
				
				String[] splitted = s.split(":");
				
				if (splitted.length != 4)
					throw new InvalidConfigException("[team.yml] Invalid line: " + s);
				
				Integer id = Integer.parseInt(splitted[0]);
				byte data = (byte) Integer.parseInt(splitted[1]);
				String displayName = ChatColor.translateAlternateColorCodes('&', splitted[2]);
				String nameMenu = ChatColor.translateAlternateColorCodes('&', splitted[3]);
				
				BlockInfo info = new BlockInfo(Material.getMaterial(id), data);
				Team.createTeam(nameMenu, info, displayName);
			}
			
			if (Team.getNumber() < 
					Math.ceil(UHCReloaded.getMainConfiguration().getMaxPlayer() / this.teamSize))
				throw new InvalidConfigException("[team.yml] Not enough team !! Only " + Team.getNumber() + 
						" preconfigurated team of " + this.teamSize + " player for " + UHCReloaded.getMainConfiguration().getMaxPlayer() + " players !");
		}
	}
	
	public Boolean isEnabled() { return enabled; }
	public Boolean isAutoFillActived() { return autoFill; }
	public Boolean isGUIEnabled() { return guiEnabled; }
	public Integer getTeamSize() { return teamSize; }
	public Integer getExpireTime() { return expireTime; }
	public Integer getGuiItemID() { return guiItemId; }
	public Integer getGuiSlotItem() { return guiSlotItem; }
}
