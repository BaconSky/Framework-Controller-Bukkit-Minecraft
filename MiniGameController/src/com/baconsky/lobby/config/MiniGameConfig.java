package com.baconsky.lobby.config;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class MiniGameConfig {

	private String gameConfigPath;
	private String gameConfigName;
	private Plugin plugin;

	private int numPlayerMin;
	private int numPlayerMax;
	private int numPlayerStandard;
	private String pluginName;
	private String gameName;
	private String selectedArena = null;
	
	static int rndCount = 0;
		
	
	public MiniGameConfig(String gameConfigPath, String gameConfigName, Plugin plugin) {
		this.plugin = plugin;
//Bukkit.getLogger().info("MiniGameConfig ctor pos1");
		this.gameConfigPath = gameConfigPath;
		this.gameConfigName = gameConfigName;
		
		
		// check if folder gameConfigPath exists - otherwise create it
		File dir = new File(gameConfigPath);
		if (!dir.exists()) {
			try {
				dir.getParentFile().mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // ! configDir exists
		
		File ymlFile = new File(gameConfigPath, gameConfigName);
		if (!ymlFile.exists()) {
			try {
				Bukkit.getLogger().info("GameConfig ctor create config file "+ymlFile+" in folder "+gameConfigPath);
				ymlFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// load the yml file and read data
//Bukkit.getLogger().info("GameConfig ctor: Start loading configfile "+ymlFile.getName()+" from folder "+gameConfigPath);
			FileConfiguration gameConfig = YamlConfiguration.loadConfiguration(ymlFile);
			this.numPlayerMin = gameConfig.getInt("num_player_min");
			this.numPlayerMax = gameConfig.getInt("num_player_standard");
			this.numPlayerStandard = gameConfig.getInt("num_player_max");
			this.gameName = gameConfig.getString("game_name");
			this.pluginName = gameConfig.getString("plugin_name");
//Bukkit.getLogger().info("GameConfig ctor: Finished loading configfile "+ymlFile.getName()+" from folder "+gameConfigPath);
//Bukkit.getLogger().info("GameConfig ctor: numPlayerMin="+numPlayerMin+" numPlayerMax="+numPlayerMax+" numPlayerStandard="+numPlayerStandard);

		
	}

	public String getName() { return gameName; } 

//------------------------ Conditions ----------------------
	
	
	public boolean beforeCountdownCondition() {
		return (getNumberPlayers() < getMinNumPlayer());
	}
	
	
	
	public boolean countdown1Condition() {
		return ((getNumberPlayers() >= getMinNumPlayer()) && (getNumberPlayers() < getStandardNumPlayer()));
	}
	

	
	public boolean countdown2Condition() {
		return (getNumberPlayers() >= getStandardNumPlayer());
	}
	
	
	
	public boolean startGameCondition(int timeUntilStart) {
		return ((getNumberPlayers() >= getMinNumPlayer()) && (timeUntilStart < 1));
	}
	
	
	
	
//--------------------- Settings ------------------------
	
	public int getMinNumPlayer() {
		return numPlayerMin;
	}
	

	public int getStandardNumPlayer() {
		return numPlayerMax;
	}

	public int getMaxNumPlayer() {
		return numPlayerStandard;
	}


	public String selectArena() {
		// get all arena dirs for this game		
		File dir = new File(gameConfigPath + File.separator + "arenas");
		File[] allArenaDirs = dir.listFiles();
		int numWorlds = allArenaDirs.length;
		
		rndCount++;
		if (rndCount >= numWorlds) rndCount = 0;
		selectedArena = allArenaDirs[rndCount].getName();
		return selectedArena;
	}
	
	
	public String getWorld() {
		if (selectedArena == null) return null;
		return selectedArena  + File.separator + "world";
	}

	
	public String getPluginName() {
		return pluginName;
	}

//------------------------------ utilities ----------------------
	
	private int getNumberPlayers () {
		return Bukkit.getServer().getOnlinePlayers().size();
	}
	

}


/*

num_player_min: 3
num_player_standard: 3
num_player_max: 30
plugin_name: "HungerGames"
*/
