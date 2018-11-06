package net.upd4ting.uhcreloaded.configuration.configs;

import net.upd4ting.uhcreloaded.configuration.Configuration;
import net.upd4ting.uhcreloaded.exception.InvalidConfigException;

public class MainConfig extends Configuration{
	
	private Integer maxPlayer;
	private Integer minPlayer;
	private String bypassMax;
	private Boolean eternalDay;
	private Integer chunkPerInterval;
	private Integer interval;
	private String commandRestartServer;
	private Boolean rejoinEnabled;
	private Integer rejoinTimeMax;
	private Integer rejoinMaxRejoin;
	private Boolean spectatorJoinEnabled;
	private String spectatorJoinPermission;
	private Integer defaultHealthPlayer;
	private Boolean friendlyFire;
	private Boolean spectatorEnabled;
	private String spectatorPermission;
	private Integer spectatorDistance;
	private Boolean spectatorOnlymates;
	private Boolean spectatorOld;
	private Boolean goldenHeadEnabled;
	private Integer goldenHeadRegenAmplificator;
	private Integer goldenHeadRegenTime;
	private Boolean fallDamageEnabled;
	private Boolean treecutterEnabled;
	private Boolean craftIncreasedEnabled;
	private Boolean lobbyEnabled;
	private String lobbyName;
	private Integer lobbyItemId;
	private Boolean schematicEnabled;
	private Integer schematicBlockId;
	private Integer goldenNormalTime;
	private Integer goldenNotchTime;
	private Boolean obsidianFaster;
	private Boolean placeholderapi;
	private String displayStyle;
	
	public MainConfig() {
		super(ConfType.STARTUP, "config.yml");
	}

	@Override
	public void loadData() throws InvalidConfigException {
		maxPlayer = conf.getInt("maxplayer");
		minPlayer = conf.getInt("minplayer");
		bypassMax = conf.getString("bypass-max");
		eternalDay = conf.getBoolean("eternal-day");
		chunkPerInterval = conf.getInt("ChunkLoader.chunk-per-interval");
		interval = conf.getInt("ChunkLoader.interval");
		schematicEnabled = conf.getBoolean("Schematic.enabled");
		schematicBlockId = conf.getInt("Schematic.block");
		commandRestartServer = conf.getString("command-restart-server");
		rejoinEnabled = conf.getBoolean("Rejoin.enabled");
		rejoinTimeMax = conf.getInt("Rejoin.time-max");
		rejoinMaxRejoin = conf.getInt("Rejoin.maxRejoin");
		spectatorJoinEnabled = conf.getBoolean("SpectatorJoin.enabled");
		spectatorJoinPermission = conf.getString("SpectatorJoin.permission");
		defaultHealthPlayer = conf.getInt("health-player");
		friendlyFire = conf.getBoolean("friendly-fire");
		spectatorEnabled = conf.getBoolean("Spectator.enabled");
		spectatorPermission = conf.getString("Spectator.permission");
		spectatorDistance = conf.getInt("Spectator.maxDistance");
		spectatorOnlymates = conf.getBoolean("Spectator.onlySpecTeammates");
		spectatorOld = conf.getBoolean("Spectator.old");
		goldenHeadEnabled = conf.getBoolean("GoldenHeads.enabled");
		goldenHeadRegenAmplificator = conf.getInt("GoldenHeads.regen-amplificator");
		goldenHeadRegenTime = conf.getInt("GoldenHeads.regen-time");
		fallDamageEnabled = conf.getBoolean("fall-damage");
		treecutterEnabled = conf.getBoolean("treecutter");
		craftIncreasedEnabled = conf.getBoolean("craft-increased");
		lobbyEnabled = conf.getBoolean("Lobby.enabled");
		lobbyName = conf.getString("Lobby.name-in-bungeeconfig");
		lobbyItemId = conf.getInt("Lobby.item-id");
		goldenNormalTime = conf.getInt("GoldenRegen.golden-apple");
		goldenNotchTime = conf.getInt("GoldenRegen.golden-notch");
		obsidianFaster = conf.getBoolean("obsidian-faster");
		placeholderapi = conf.getBoolean("placeholderapi");
		displayStyle = conf.getString("tab-health-display-style");
	}
	
	public Integer getMinPlayer() { return minPlayer; }
	public Integer getMaxPlayer() { return maxPlayer; }
	public String getBypassMax() { return bypassMax; }
	public Boolean isEternalDayEnabled() { return eternalDay; }
	public Integer getChunkPerInterval() { return chunkPerInterval; }
	public Integer getInterval() { return interval; }
	public Boolean isSchematicEnabled() { return schematicEnabled; }
	public Integer getSchematicBlock() { return schematicBlockId; }
	public String getCommandRestartServer() { return commandRestartServer; }
	public Boolean isRejoinEnabled() { return rejoinEnabled; }
	public Integer getRejoinTimeMax() { return rejoinTimeMax; }
	public Integer getRejoinMaxRejoin() { return rejoinMaxRejoin; }
	public Boolean isSpectatorJoinEnabled() { return spectatorJoinEnabled; }
	public String getSpectatorJoinPermission() { return spectatorJoinPermission; }
	public Integer getDefaultHealthPlayer() { return defaultHealthPlayer; }
	public Boolean isFriendlyFireEnabled() { return friendlyFire; }
	public Boolean isSpectatorEnabled() { return spectatorEnabled; }
	public String getSpectatorPermission() { return spectatorPermission; }
	public Integer getSpectatorDistance() { return spectatorDistance; }
	public Boolean isSpectatorOnlyMates() { return spectatorOnlymates; }
	public Boolean isSpectatorOld() { return spectatorOld; }
	public Boolean isGoldenHeadEnabled() { return goldenHeadEnabled; }
	public Integer getGoldenHeadRegenAmplificator() { return goldenHeadRegenAmplificator; }
	public Integer getGoldenHeadRegenTime() { return goldenHeadRegenTime; }
	public Boolean isFallDamageEnabled() { return fallDamageEnabled; }
	public Boolean isTreeCutterEnabled() { return treecutterEnabled; }
	public Boolean isCraftIncreasedEnabled() { return craftIncreasedEnabled; }
	public Boolean isLobbyEnabled() { return lobbyEnabled; }
	public String getLobbyName() { return lobbyName; }
	public Integer getLobbyItemId() { return lobbyItemId; }
	public Integer getGoldenNormalTime() { return goldenNormalTime; }
	public Integer getGoldenNotchTime() { return goldenNotchTime; }
	public Boolean isObsidianFasterEnabled() { return obsidianFaster; }
	public Boolean getPlaceholderApi() { return placeholderapi; }
	public String getDisplayStyle() { return displayStyle; }
}
