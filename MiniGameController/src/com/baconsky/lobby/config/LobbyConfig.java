package com.baconsky.lobby.config;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class LobbyConfig {
	
	private String lobbyConfigPath;
	private String lobbyConfigName;
	private String pathToWorld;
	private String world;
	private int countdown1;
	private int countdown2;

//---------------------- ctor ------------------------------
	
	
	
public LobbyConfig(String lobbyConfigPath, String lobbyConfigName) {
	this.pathToWorld = lobbyConfigPath + File.separator + "world";
//Bukkit.getLogger().info("LobbyConfig ctor pos1");
	this.lobbyConfigPath = lobbyConfigPath;
	this.lobbyConfigName = lobbyConfigName;
	
	// check if folder lobbyConfigPath exists - otherwise create it
	File dir = new File(lobbyConfigPath);
	if (!dir.exists()) {
		try {
			dir.getParentFile().mkdirs();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // ! configDir exists
	
	File ymlFile = new File(lobbyConfigPath, lobbyConfigName);
	if (!ymlFile.exists()) {
		try {
			Bukkit.getLogger().info("LobbyConfig ctor create config file "+ymlFile+" in folder "+lobbyConfigPath);
			ymlFile.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// load the yml file and read data
//Bukkit.getLogger().info("LobbyConfig ctor: Start loading configfile "+ymlFile.getName()+" from folder "+lobbyConfigPath);
		FileConfiguration lobbyConfig = YamlConfiguration.loadConfiguration(ymlFile);
		this.countdown1 = lobbyConfig.getInt("countdown1");
		this.countdown2 = lobbyConfig.getInt("countdown2");
		this.world = lobbyConfig.getString("world");
//Bukkit.getLogger().info("LobbyConfig ctor: Finished loading configfile "+ymlFile.getName()+" from folder "+lobbyConfigPath);
//Bukkit.getLogger().info("LobbyConfig ctor: countdown1="+countdown1+" countdown2="+countdown2+" world="+world);
}

	
	
//-------------- getter & setter  --------------

	public String getLobbyConfigPath() {
		return lobbyConfigPath;
	}

	public String getLobbyConfigName() {
		return lobbyConfigName;
	}

	public String getPathToWorld() {
		return pathToWorld;
	}

	public void setPathToWorld(String pathToWorld) {
		this.pathToWorld = pathToWorld;
	}

	public int getCountdown1() {
		return countdown1;
	}

	public int getCountdown2() {
		return countdown2;
	}

	public String getWorld() {
		return world;
	}

	
}

/*

countdown1: 10
countdown2: 5
world: "lobby_1"

*/