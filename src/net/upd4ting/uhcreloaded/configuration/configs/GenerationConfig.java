package net.upd4ting.uhcreloaded.configuration.configs;

import net.upd4ting.uhcreloaded.GenRule;
import net.upd4ting.uhcreloaded.configuration.Configuration;
import net.upd4ting.uhcreloaded.exception.InvalidConfigException;

public class GenerationConfig extends Configuration {
	
	private Boolean ruleEnabled;
	private Boolean sugarCaneGeneratorEnabled;
	private Integer sugarCaneGeneratorPercent;
	
	public GenerationConfig() {
		super(ConfType.STARTUP, "generation.yml");
	}

	@Override
	public void loadData() throws InvalidConfigException {
		ruleEnabled = conf.getBoolean("enableRule");
		
		for (String s : conf.getStringList("rules"))
			GenRule.unparse(s);
		
		sugarCaneGeneratorEnabled = conf.getBoolean("SugarCaneGenerator.enabled");
		sugarCaneGeneratorPercent = conf.getInt("SugarCaneGenerator.percent");
	}
	
	public Boolean isRuleEnabled() { return ruleEnabled; }
	public Boolean isSugarCaneGeneratorEnabled() { return sugarCaneGeneratorEnabled; }
	public Integer getSugarCaneGeneratorPercent() { return sugarCaneGeneratorPercent; }
}
