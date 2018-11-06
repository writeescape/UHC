package net.upd4ting.uhcreloaded.configuration.configs;

import java.util.ArrayList;
import java.util.List;

import net.upd4ting.uhcreloaded.configuration.Configuration;
import net.upd4ting.uhcreloaded.exception.InvalidConfigException;

public class LangConfig extends Configuration {
	
	// Team
	private String teamPrefix;
	private String commandDisabled;
	private String wrongSyntax;
	private String playerNotOnline;
	private String sendInvitation;
	private String receivedInvitation;
	private String alreadyInvited;
	private String joinTeam;
	private String leaveTeam;
	private String teamFull;
	private String newTeamOwner;
	private String notOwner;
	private String notInTeam;
	private String alreadyInTeam;
	private String guiActived;
	private String teamNameMenu;
	private String teamNameItem;
	private List<String> helpMessage;
	
	// Kit
	private String kitNameMenu;
	private String kitNameItem;
	private String canSelectKit;
	private String costKit;
	private String dontHavePermissionKit;
	private String selectKit;
	private String selectedKit;
	private String notEnoughMoneyKit;
	private String buyKit;
	
	// Motd
	private String loading;
	private String waiting;
	private String ingame;
	private String finished;
	
	// Kick
	private String kickLoading;
	private String kickGamestarted;
	private String kickGameFinished;
	private String kickGameFull;
	
	// Gameplay
	private String join;
	private String leave;
	private String leaveIngame;
	private String rejoin;
	private String death;
	private String deathByPlayer;
	private String joinSpec;
	private String returnToHub;
	private String gameStart;
	private String gameTimeStart;
	private String teamWin;
	private String playerWin;
	private String lobbyTp;
	private String spectatorTp;
	private String noEnoughPlayer;
	private String leaderboard;
	private String receiveHeal;
	private String goldenHead;
	private String revived;
	
	// Timer
	private String damageTime;
	private String damageEnabled;
	private String pvpTime;
	private String pvpEnabled;
	private String dmTime;
	private String dmEnabled;
	private String wbTime;
	private String wbEnabled;
	private String healTime;
	private String healEnabled;
	
	// Scoreboard
	private String timerDelimitor;
	private String damage;
	private String border;
	private String pvp;
	private String deathmatch;
	private String heal;
	private String teammatesDelimitor;
	private String teamInfo;
	private String downArrow;
	private String upArrow;
	private String leftArrow;
	private String rightArrow;
	
	// Economy
	private String earnOnKill;
	private String earnOnWin;
	private String earnOnParticipate;
	
	// Stat command
	private List<String> statCommand = new ArrayList<>();
	private String statCommandPlayerNotExist;
	private String statCommandUsage;
	
	public LangConfig() {
		super(ConfType.STARTUP, "lang.yml");
	}

	@Override
	public void loadData() throws InvalidConfigException {
		// Stat commmand
		statCommand = getStringListConfig("StatCommand.info");
		statCommandPlayerNotExist = getStringConfig("StatCommand.playerNotExist");
		statCommandUsage = getStringConfig("StatCommand.usage");
		
		// Economy
		earnOnKill = getStringConfig("Economy.earnOnKill");
		earnOnWin = getStringConfig("Economy.earnOnWin");
		earnOnParticipate = getStringConfig("Economy.earnOnParticpate");
		
		// Scoreboard
		timerDelimitor = getStringConfig("Scoreboard.timerDelimitor");
		damage = getStringConfig("Scoreboard.damage");
		border = getStringConfig("Scoreboard.border");
		pvp = getStringConfig("Scoreboard.pvp");
		deathmatch = getStringConfig("Scoreboard.deathmatch");
		heal = getStringConfig("Scoreboard.heal");
		teammatesDelimitor = getStringConfig("Scoreboard.teammatesDelimitor");
		teamInfo = getStringConfig("Scoreboard.teamInfo");
		downArrow = getStringConfig("Scoreboard.downArrow");
		upArrow = getStringConfig("Scoreboard.upArrow");
		leftArrow = getStringConfig("Scoreboard.leftArrow");
		rightArrow = getStringConfig("Scoreboard.rightArrow");
		
		// Timer
		damageTime = getStringConfig("Timer.damageTime");
		damageEnabled = getStringConfig("Timer.damageEnabled");
		pvpTime = getStringConfig("Timer.pvpTime");
		pvpEnabled = getStringConfig("Timer.pvpEnabled");
		dmTime = getStringConfig("Timer.dmTime");
		dmEnabled = getStringConfig("Timer.dmEnabled");
		wbTime = getStringConfig("Timer.wbTime");
		wbEnabled = getStringConfig("Timer.wbEnabled");
		healTime = getStringConfig("Timer.healTime");
		healEnabled = getStringConfig("Timer.healEnabled");
		
		// Gameplay
		join = getStringConfig("Gameplay.join");
		leave = getStringConfig("Gameplay.leave");
		leaveIngame = getStringConfig("Gameplay.leaveIngame");
		rejoin = getStringConfig("Gameplay.rejoin");
		death = getStringConfig("Gameplay.death");
		deathByPlayer = getStringConfig("Gameplay.deathByPlayer");
		joinSpec = getStringConfig("Gameplay.joinSpec");
		returnToHub = getStringConfig("Gameplay.itemReturnLobby");
		gameStart = getStringConfig("Gameplay.gameStart");
		gameTimeStart = getStringConfig("Gameplay.gameTimeStart");
		teamWin = getStringConfig("Gameplay.teamWin");
		playerWin = getStringConfig("Gameplay.playerWin");
		lobbyTp = getStringConfig("Gameplay.lobbyTp");
		spectatorTp = getStringConfig("Gameplay.spectatorTp");
		noEnoughPlayer = getStringConfig("Gameplay.noEnoughPlayer");
		leaderboard = getStringConfig("Gameplay.leaderboard");
		receiveHeal = getStringConfig("Gameplay.receiveHeal");
		goldenHead = getStringConfig("Gameplay.goldenHead");
		revived = getStringConfig("Gameplay.revived");
		
		// Kick
		kickLoading = getStringConfig("Kick.loading");
		kickGamestarted = getStringConfig("Kick.gameStarted");
		kickGameFinished = getStringConfig("Kick.gameFinished");
		kickGameFull = getStringConfig("Kick.gameFull");
		
		// Motd
		loading = getStringConfig("Motd.loading");
		waiting = getStringConfig("Motd.waiting");
		ingame = getStringConfig("Motd.ingame");
		finished = getStringConfig("Motd.finished");
		
		// Kit
		kitNameMenu = getStringConfig("Kit.nameMenu");
		kitNameItem = getStringConfig("Kit.nameItem");
		canSelectKit = getStringConfig("Kit.canSelectKit");
		costKit = getStringConfig("Kit.costKit");
		dontHavePermissionKit = getStringConfig("Kit.dontHavePermission");
		selectKit = getStringConfig("Kit.selectKit");
		selectedKit = getStringConfig("Kit.selectedKit");
		notEnoughMoneyKit = getStringConfig("Kit.notEnoughMoney");
		buyKit = getStringConfig("Kit.buyKit");
		
		// Team messages
		teamPrefix = getStringConfig("Team.prefix");
		commandDisabled = getStringConfig("Team.disabled");
		wrongSyntax = getStringConfig("Team.wrongSyntaxCommand");
		playerNotOnline = getStringConfig("Team.playerNotOnline");
		sendInvitation = getStringConfig("Team.sendInvitation");
		receivedInvitation = getStringConfig("Team.receivedInvitation");
		alreadyInvited = getStringConfig("Team.alreadyInvited");
		alreadyInTeam = getStringConfig("Team.alreadyInTeam");
		joinTeam = getStringConfig("Team.joinTeam");
		leaveTeam = getStringConfig("Team.leaveTeam");
		teamFull = getStringConfig("Team.teamFull");
		newTeamOwner = getStringConfig("Team.newTeamOwner");
		notOwner = getStringConfig("Team.notOwner");
		notInTeam = getStringConfig("Team.notInTeam");
		guiActived = getStringConfig("Team.guiActived");
		teamNameMenu = getStringConfig("Team.nameMenu");
		teamNameItem = getStringConfig("Team.nameItem");
		helpMessage = getStringListConfig("Team.helpMessage");
	}
	
	// Getter StatCommand
	public List<String> getStatCommand() { return statCommand; }
	public String getStatCommandPlayerNotExist() { return statCommandPlayerNotExist; }
	public String getStatCommandUsage() { return statCommandUsage; }
	
	// Getter economy
	public String getEarnOnKill() { return earnOnKill; }
	public String getEarnOnWin() { return earnOnWin; }
	public String getEarnOnParticipate() { return earnOnParticipate; }
	
	// Getter scoreboard
	public String getTimerDelimitor() { return timerDelimitor; }
	public String getDamage() { return damage; }
	public String getBorder() { return border; }
	public String getPvp() { return pvp; }
	public String getDeathMatch() { return deathmatch; }
	public String getHeal() { return heal; }
	public String getTeammatesDelimitor() { return teammatesDelimitor; }
	public String getTeamInfo() { return teamInfo; }
	public String getLeftArrow() { return leftArrow; }
	public String getRightArrow() { return rightArrow; }
	public String getUpArrow() { return upArrow; }
	public String getDownArrow() { return downArrow; }
	
	// Getter timer
	public String getDamageTime() { return damageTime; }
	public String getDamageEnabled() { return damageEnabled; }
	public String getPvpTime() { return pvpTime; }
	public String getPvpEnabled() { return pvpEnabled; }
	public String getDmTime() { return dmTime; }
	public String getDmEnabled() { return dmEnabled; }
	public String getWbTime() { return wbTime; }
	public String getWbEnabled() { return wbEnabled; }
	public String getHealTime() { return healTime; }
	public String getHealEnabled() { return healEnabled; }
	
	// Getter gameplay
	public String getLeaveIngame() { return leaveIngame; }
	public String getJoin() { return join; }
	public String getLeave() { return leave; }
	public String getRejoin() { return rejoin; }
	public String getDeath() { return death; }
	public String getDeathByPlayer() { return deathByPlayer; }
	public String getJoinSpec() { return joinSpec; }
	public String getReturnTohub() { return returnToHub; }
	public String getGameStart() { return gameStart; }
	public String getGameTimeStart() { return gameTimeStart; }
	public String getTeamWin() { return teamWin; }
	public String getPlayerWin() { return playerWin; }
	public String getLobbyTp() { return lobbyTp; }
	public String getSpectatorTp() { return spectatorTp; }
	public String getNotEnoughPlayer() { return noEnoughPlayer; }
	public String getLeaderboard() { return leaderboard; }
	public String getReceiveHeal() { return receiveHeal; }
	public String getGoldenHead() { return goldenHead; }
	public String getRevived() { return revived; }
	
	// Getter Kick
	public String getKickLoading() { return kickLoading; }
	public String getKickGamestarted() { return kickGamestarted; }
	public String getKickGameFinished() { return kickGameFinished; }
	public String getKickGameFull() { return kickGameFull; }
	
	// Getter Motd
	public String getMotdFinished() { return finished; }
	public String getMotdIngame() { return ingame; }
	public String getMotdWaiting() { return waiting; }
	public String getMotdLoading() { return loading; }
	
	// Getter Kit
	public String getKitNameMenu() { return kitNameMenu; }
	public String getKitNameItem() { return kitNameItem; }
	public String getCanSelectKit() { return canSelectKit; }
	public String getCostKit() { return costKit; }
	public String getDontHavePermissionKit() { return dontHavePermissionKit; }
	public String getSelectKit() { return selectKit; }
	public String getSelectedKit() { return selectedKit; }
	public String getNotEnoughMoneyKit() { return notEnoughMoneyKit; }
	public String getBuyKit() { return buyKit; }
	
	// Getter team 
	public String getTeamPrefix() { return teamPrefix; }
	public String getCommandDisabled() { return commandDisabled; }
	public String getWrongSyntax() { return wrongSyntax; }
	public String getPlayerNotOnline() { return playerNotOnline; }
	public String getSendInvitation() { return sendInvitation; }
	public String getReceivedInvitation() { return receivedInvitation; }
	public String getAlreadyInvited() { return alreadyInvited; }
	public String getAlreadyInTeam() { return alreadyInTeam; }
	public String getJoinTeam() { return joinTeam; }
	public String getLeaveTeam() { return leaveTeam; }
	public String getTeamFull() { return teamFull; }
	public String getNewTeamOwner() { return newTeamOwner; }
	public String getNotOwner() { return notOwner; }
	public String getNotInTeam() { return notInTeam; }
	public String getGuiActived() { return guiActived; }
	public String getTeamNameMenu() { return teamNameMenu; }
	public String getTeamNameItem() { return teamNameItem; }
	public List<String> getHelpMessage() { return helpMessage; }
}
