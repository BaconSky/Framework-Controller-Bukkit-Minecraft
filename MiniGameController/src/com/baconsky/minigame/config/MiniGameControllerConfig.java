package com.baconsky.minigame.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class MiniGameControllerConfig {
	private String gameConfigName;
	private String gameConfigPath;
	private String lobbyConfigName;
	private String lobbyConfigPath;
	private String kitsConfigPath;
	private String currentArena;

	private String miniGameControllerConfigName;
	FileConfiguration miniGameControllerConfig;
	File configFile;
	
//---------------------- ctor -----------------------------------
	
	public MiniGameControllerConfig(Plugin plugin) {
		this("minigame.yml", plugin);
	}
	
	
	public MiniGameControllerConfig(String miniGameControllerConfigName, Plugin plugin) {
		this.miniGameControllerConfigName = miniGameControllerConfigName;
		
		File configDir = plugin.getDataFolder().getParentFile();
//Bukkit.getLogger().info("MiniGameConfig ctor configDir="+configDir.getName());
		configFile = new File(configDir, this.miniGameControllerConfigName);
		
//Bukkit.getLogger().info("MiniGameConfig ctor configFile="+configFile.getName());

		miniGameControllerConfig = YamlConfiguration.loadConfiguration(configFile);
		gameConfigName = miniGameControllerConfig.getString("gameconfig_name");
		gameConfigPath = miniGameControllerConfig.getString("gameconfig_path");
		lobbyConfigName = miniGameControllerConfig.getString("lobbyconfig_name");
		lobbyConfigPath = miniGameControllerConfig.getString("lobbyconfig_path");
		kitsConfigPath = miniGameControllerConfig.getString("kitsconfig_path");

		currentArena = (miniGameControllerConfig.contains("arena")) ? miniGameControllerConfig.getString("arena") : null;
//Bukkit.getLogger().info("MiniGameConfig ctor gameConfigName="+gameConfigName+" kitsConfigPath="+kitsConfigPath);

		
	}
	
//----------------------- getter & setter ---------------------------
	
	
	public String getGameConfigName() {
		return gameConfigName;
	}

	public String getGameConfigPath() {
		return gameConfigPath;
	}

	public String getLobbyConfigName() {
		return lobbyConfigName;
	}

	public String getLobbyConfigPath() {
		return lobbyConfigPath;
	}

	public String getKitsConfigPath() {
		return kitsConfigPath;
	}

	public String getMinigameConfigName() {
		return miniGameControllerConfigName;
	}
	
	public void setCurrentArena(String arena) {
		miniGameControllerConfig.set("arena", arena);
		try {
			miniGameControllerConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getCurrentArenaName() {
		return currentArena;
	}
	
	public String getCurrentArenaPath() {
		return gameConfigPath + File.separator + "arena";
	}
	
	public String getPlayerKitsConfigName() {
		return "player_kits.yml";
		//%%%& ToDo The PlayerKits should not be a file any more. It should be a variable in memory
	}
	
	
}

/*
 
gameconfig_name: game.yml
gameconfig_path: games/hungergames
lobbyconfig_name: lobby.yml
lobbyconfig_path: lobby
kitsconfig_path: games/hungergames/kits
arena: arena_2
 
 
*/