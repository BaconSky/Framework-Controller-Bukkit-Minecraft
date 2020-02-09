package com.baconsky.features.chests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ChestConfig {


	private Plugin plugin;
	private String chestConfigPath;
	private String chestConfigName;
	private List<ChestPosition> allChests = new ArrayList<ChestPosition>();
	
	
	public ChestConfig (String chestConfigPath, String chestConfigName, Plugin plugin) {
		this.plugin = plugin;
		this.chestConfigPath = chestConfigPath;
		this.chestConfigName = chestConfigName;
		
		// check if folder chestConfigPath exists - otherwise create it
		File dir = new File(chestConfigPath);
		if (!dir.exists()) {
			try {
				dir.getParentFile().mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // ! dir exists
		
		File ymlFile = new File(chestConfigPath, chestConfigName);
		if (!ymlFile.exists()) {
			try {
				Bukkit.getLogger().info("chestConfig ctor create config file "+ymlFile+" in folder "+chestConfigPath);
				ymlFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// load the yml file and read data
//Bukkit.getLogger().info("chestConfig ctor: Start loading configfile "+ymlFile.getName()+" from folder "+chestConfigPath);
		FileConfiguration chestConfig = YamlConfiguration.loadConfiguration(ymlFile);
		ConfigurationSection allChestsConfig = chestConfig.getConfigurationSection("chests");
		for (String key : allChestsConfig.getKeys(false)) {
			ConfigurationSection curChestPosConfig = allChestsConfig.getConfigurationSection(key);
			ChestPosition chestPos = new ChestPosition(curChestPosConfig);
			allChests.add(chestPos);
		} // for

//Bukkit.getLogger().info("chestConfig ctor: Finished loading configfile "+ymlFile.getName()+" from folder "+chestConfigPath);
Bukkit.getLogger().info("chestConfig ctor: allChests="+allChests);
	} // ctor

	
//-------------------------------------- getter & setter --------------------
	
	public List<ChestPosition> getAllChests() {
		return allChests;
	}

	
	
	
}


/*

chests:
  chest1:
    x: 20
    y: 33
    z: 44
    o: "SOUTH"
  chest2:
    x: 10
    y: 13
    z: 14
    o: "EAST"


*/
