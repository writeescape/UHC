package net.upd4ting.uhcreloaded.configuration.configs;

import net.upd4ting.uhcreloaded.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldCreator;

import net.upd4ting.uhcreloaded.configuration.Configuration;
import net.upd4ting.uhcreloaded.exception.InvalidConfigException;
import net.upd4ting.uhcreloaded.util.Util;

public class TimerConfig extends Configuration {
	
	// Other
	private Integer timeToStart;
	private Integer timeToStartFull;
	private Boolean noHunger;
	private Integer pvpStart;
	private Integer timeToShutdown;
	private Integer timeInvincible;
	
	// Worldborder
	private Boolean wbEnabled;
	private Boolean wbShrinkingEnabled;
	private Integer wbSizeStart;
	private Integer wbSizeEnd;
	private Integer wbTimeStart;
	private Integer wbShrinkTime;
	private Integer wbDamage;
	
	// DeathMatch
	private Boolean dmEnabled;
	private Integer dmTimeStart;
	private String 	dmWorldName;
	private Boolean dmShrinkingEnabled;
	private Integer dmSizeStart;
	private Integer dmSizeEnd;
	private Integer dmTimeStartShrinking;
	private Integer dmShrinkTime;
	private Integer dmDamage;
	
	// Speed
	private Boolean speedEnabled;
	private Integer speedTime;
	private Integer speedValue;
	private Boolean particleEffect;
	
	// Heal
	private Boolean healEnabled;
	private Integer healTimeStart;
	private Integer healRegenAmplificator;
	private Integer healRegenTime;
	
	public TimerConfig() {
		super(ConfType.STARTUP, "timer.yml");
	}

	@Override
	public void loadData() throws InvalidConfigException {
		
		// Heal
		healEnabled = conf.getBoolean("Heal.enabled");
		healTimeStart = conf.getInt("Heal.timeStart");
		healRegenAmplificator = conf.getInt("Heal.regen-amplificator");
		healRegenTime = conf.getInt("Heal.regen-time");
		
		// Other
		timeToStart = conf.getInt("time-to-start");
		timeToStartFull = conf.getInt("time-to-start-full");
		noHunger = conf.getBoolean("nohunger-when-pvp-disabled");
		pvpStart = conf.getInt("pvp-start");
		timeToShutdown = conf.getInt("time-to-shutdown");
		timeInvincible = conf.getInt("time-invincible");
		
		// Speed
		speedEnabled = conf.getBoolean("Speed.enabled");
		speedTime = conf.getInt("Speed.time");
		speedValue = conf.getInt("Speed.speed-value");
		particleEffect = conf.getBoolean("Speed.particleEffect");
		
		// Worldborder
		wbEnabled = conf.getBoolean("WorldBorder.enabled");
		wbShrinkingEnabled = conf.getBoolean("WorldBorder.shrinking-enabled");
		wbSizeStart = conf.getInt("WorldBorder.sizeStart");
		wbSizeEnd = conf.getInt("WorldBorder.sizeEnd");
		wbTimeStart = conf.getInt("WorldBorder.timeStart");
		wbShrinkTime = conf.getInt("WorldBorder.shrink-time");
		wbDamage = conf.getInt("WorldBorder.damage");
		
		// DeathMatch
		dmEnabled = conf.getBoolean("DeathMatch.enabled");
		dmTimeStart = conf.getInt("DeathMatch.timeStart");
		dmWorldName = conf.getString("DeathMatch.worldName");
		
		dmShrinkingEnabled = conf.getBoolean("DeathMatch.shrinking-enabled");
		dmSizeStart = conf.getInt("DeathMatch.sizeStart");
		dmSizeEnd = conf.getInt("DeathMatch.sizeEnd");
		dmTimeStartShrinking = conf.getInt("DeathMatch.time-start-shrinking");
		dmShrinkTime = conf.getInt("DeathMatch.shrink-time");
		dmDamage = conf.getInt("DeathMatch.damage");
	}
	
	@Override
	public void doTaskEnable() {
		if (dmEnabled && !dmWorldName.equals("world") && Bukkit.getWorld(dmWorldName) == null) {
			Logger.log(Logger.LogLevel.INFO, ChatColor.GREEN + "Loading death-match world");
			Bukkit.getServer().createWorld(new WorldCreator(dmWorldName));
		}
	}
	
	// Heal
	public Boolean isHealEnabled() { return healEnabled; }
	public Integer getHealTimeStart() { return healTimeStart; }
	public Integer getHealRegenAmplificator() { return healRegenAmplificator; }
	public Integer getHealRegenTime() { return healRegenTime; }
	
	// Other
	public Integer getTimeToStart() { return timeToStart; }
	public Integer getTimeToStartFull() { return timeToStartFull; }
	public Boolean isNoHunger() { return noHunger; }
	public Integer getPvpStart() { return pvpStart; }
	public Integer getTimeToShutdown() { return timeToShutdown; }
	public Integer getTimeInvincible() { return timeInvincible; }
	
	// Speed
	public Boolean isSpeedEnabled() { return speedEnabled; }
	public Integer getSpeedTime() { return speedTime; }
	public Integer getSpeedValue() { return speedValue; }
	public Boolean getParticleEffect() { return particleEffect; }
	
	// WorldBorder
	public Boolean isWbEnabled() { return wbEnabled; }
	public Boolean isWbShrinkingEnabled() { return wbShrinkingEnabled; }
	public Integer getWbSizeStart() { return wbSizeStart; }
	public Integer getWbSizeEnd() { return wbSizeEnd; }
	public Integer getWbTimeStart() { return wbTimeStart; }
	public Integer getWbShrinkTime() { return wbShrinkTime; }
	public Integer getWbDamage() { return wbDamage; }
	
	// DeathMatch
	public Boolean isDmEnabled() { return dmEnabled; }
	public Integer getDmTimeStart() { return dmTimeStart; }
	public String getDmWorldName() { return dmWorldName; }
	public Boolean isDmShrinkingEnabled() { return dmShrinkingEnabled; }
	public Integer getDmSizeStart() { return dmSizeStart; }
	public Integer getDmSizeEnd() { return dmSizeEnd; }
	public Integer getDmTimeStartShrinking() { return dmTimeStartShrinking; }
	public Integer getDmShrinkTime() { return dmShrinkTime; }
	public Integer getDmDamage() { return dmDamage; }
}
