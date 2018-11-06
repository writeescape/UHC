package net.upd4ting.uhcreloaded.configuration.configs;

import net.upd4ting.uhcreloaded.configuration.Configuration;
import net.upd4ting.uhcreloaded.exception.InvalidConfigException;

public class ChatConfig extends Configuration {
	
	private String prefix;
	private String prefixAll;
	private String prefixTeam;
	private String prefixSpectator;
	private String format;
	private String prefixMessageGlobal;
	private Boolean formatingEnabled;
	
	public ChatConfig() {
		super(ConfType.STARTUP, "chat.yml");
	}

	@Override
	public void loadData() throws InvalidConfigException {
		prefix = getStringConfig("prefix");
		prefixAll = getStringConfig("prefix-all");
		prefixTeam = getStringConfig("prefix-team");
		prefixSpectator = getStringConfig("prefix-spectator");
		format = getStringConfig("format");
		prefixMessageGlobal = getStringConfig("prefix-message-global");
		formatingEnabled = conf.getBoolean("formating");
	}
	
	public String getPrefix() { return prefix + " "; }
	public String getPrefixAll() { return prefixAll + " "; }
	public String getPrefixTeam() { return prefixTeam + " "; }
	public String getPrefixSpectator() { return prefixSpectator + " "; }
	public String getFormat() { return format; }
	public String getPrefixMessageGlobal() { return prefixMessageGlobal; }
	public Boolean isFormatingEnabled() { return formatingEnabled; }
}
