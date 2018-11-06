package net.upd4ting.uhcreloaded.configuration.configs;

import net.upd4ting.uhcreloaded.configuration.Configuration;
import net.upd4ting.uhcreloaded.exception.InvalidConfigException;

public class MySQLConfig extends Configuration {
	
	private Boolean enabled;
	private Integer updateTime;
	private String username;
	private String password;
	private String base;
	private String table;
	private String host;
	private Integer port;
	
	public MySQLConfig() {
		super(ConfType.STARTUP, "MySQL.yml");
	}

	@Override
	public void loadData() throws InvalidConfigException {
		enabled = conf.getBoolean("enabled");
		updateTime = conf.getInt("update-time");
		username = conf.getString("username");
		password = conf.getString("password");
		base = conf.getString("base");
		host = conf.getString("host");
		port = conf.getInt("port");
		table = conf.getString("table");
	}
	
	public Boolean isEnabled() { return enabled; }
	public Integer getUpdateTime() { return updateTime; }
	public String getUsername() { return username; }
	public String getPassword() { return password; }
	public String getBase() { return base; }
	public String getTable() { return table; }
	public String getHost() { return host; }
	public Integer getPort() { return port; }
}
