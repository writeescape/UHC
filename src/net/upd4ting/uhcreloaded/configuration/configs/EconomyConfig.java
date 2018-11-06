package net.upd4ting.uhcreloaded.configuration.configs;

import net.upd4ting.uhcreloaded.configuration.Configuration;
import net.upd4ting.uhcreloaded.exception.InvalidConfigException;

public class EconomyConfig extends Configuration {
	
	private Boolean vaultEnabled;
	private Integer gainKill;
	private Integer gainWin;
	private Integer gainParticipate;
	
	public EconomyConfig() {
		super(ConfType.STARTUP, "economy.yml");
	}

	@Override
	public void loadData() throws InvalidConfigException {
		vaultEnabled = conf.getBoolean("vault");
		gainKill = conf.getInt("Gain.kill");
		gainWin = conf.getInt("Gain.win");
		gainParticipate = conf.getInt("Gain.participate");
	}
	
	public Boolean isVaultEnabled() { return vaultEnabled; }
	public Integer getGainKill() { return gainKill; }
	public Integer getGainWin() { return gainWin; }
	public Integer getGainParticipate() { return gainParticipate; }
}
