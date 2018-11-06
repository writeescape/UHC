package net.upd4ting.uhcreloaded.task.tasks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.upd4ting.uhcreloaded.Game;
import net.upd4ting.uhcreloaded.UHCPlayer;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.configuration.configs.KitConfig;
import net.upd4ting.uhcreloaded.configuration.configs.MySQLConfig;

import org.bukkit.OfflinePlayer;

public class UpdateSQLTask extends Thread {
	
	private MySQLConfig config;
	private Integer updateTime;
	private Connection conn = null;

	public UpdateSQLTask(Integer updateTime) {
		this.config = UHCReloaded.getMysqlConfiguration();
		this.updateTime = updateTime;
		
		connect();
		loadTable();
		
		this.setDaemon(true);
		this.start();
	}
	
	@Override
	public void run() {
		Game game = UHCReloaded.getGame();
		while(true) {
			if (game.isInGame())
				save();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) { e.printStackTrace(); }
			
			disconnect();
			connect();
			
			try {
				Thread.sleep(updateTime * 1000);
			} catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
	
	private void connect() {
		MySQLConfig config = UHCReloaded.getMysqlConfiguration();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connectionString = "jdbc:mysql://" + config.getHost() + ":" + config.getPort() + "/" + config.getBase() + "?user=" + config.getUsername() + "&password=" + config.getPassword() + "&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
			conn = DriverManager.getConnection(connectionString);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void loadTable() {
		KitConfig kitConfig = UHCReloaded.getKitConfiguration();
		
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS "+ config.getTable() + " "+
				"(uuid VARCHAR(150) NOT NULL,"+
				"death INT UNSIGNED NOT NULL DEFAULT '0',"+
				"kills INT UNSIGNED NOT NULL DEFAULT '0',"+
				"win INT UNSIGNED NOT NULL DEFAULT '0',"+
				"lose INT UNSIGNED NOT NULL DEFAULT '0',"+
				"gamePlayed INT UNSIGNED NOT NULL DEFAULT '0',"+
				"PRIMARY KEY (uuid));";

	    querySendWithoutResponse(sqlCreate);
	    
	    if (kitConfig.isKitPurchaseEnabled()) {
	    	String sqlCreate2 = "CREATE TABLE IF NOT EXISTS "+ kitConfig.getKitPurchaseTable() + " "+
					"(uuid VARCHAR(150) NOT NULL,"+
					"kitId INT UNSIGNED NOT NULL);";
	    	
	    	querySendWithoutResponse(sqlCreate2);
	    }
	}
	
	private void save() {
		try {
			Statement query = conn.createStatement();
			Game game = UHCReloaded.getGame();
			for (OfflinePlayer player : game.getPlayers()) {
				UHCPlayer uhcp = UHCPlayer.instanceOf(player.getUniqueId());
				String request = "INSERT INTO "+ config.getTable()+" (`uuid`,`death`,`kills`,`win`,`lose`,`gamePlayed`) "
						+ "VALUES ('" + uhcp.getUUID().toString() + "','" + uhcp.getDeath() +  "','" + uhcp.getKill() +  "','" + uhcp.getWin()
						+ "','" + uhcp.getLose() + "','" + uhcp.getGamePlayed() + "') "
						+ "ON DUPLICATE KEY UPDATE "
						+ "death='"+ uhcp.getDeath()+"', "
						+ "kills='"+ uhcp.getKill()+"', "
						+ "win='"+ uhcp.getWin()+"', "
						+ "lose='"+ uhcp.getLose()+"', "
						+ "gamePlayed='"+ uhcp.getGamePlayed()+"';";
				query.addBatch(request);
			}
			query.executeBatch();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void close() {
		if (isConnected()) {
			save();
			disconnect();
		}
	}
	
	private void disconnect(){
		try {
			conn.close();
		} catch (SQLException e) { e.printStackTrace(); }

	}

	public ResultSet querySend(String query){
		try {
			Statement _state = conn.createStatement();
			ResultSet _result = _state.executeQuery(query);			
			return _result;
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}

	public void querySendWithoutResponse(String query){
		try {
			Statement _state = conn.createStatement();
			_state.executeUpdate(query);			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public Boolean isConnected() { try { return conn != null && !conn.isClosed(); } 
		catch (SQLException e) { e.printStackTrace(); return false; } }
}
