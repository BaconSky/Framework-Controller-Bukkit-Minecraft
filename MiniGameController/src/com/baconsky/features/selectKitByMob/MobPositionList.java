package com.baconsky.features.selectKitByMob;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class MobPositionList {
	private Map<String, MobPosition> allMobPositions = new HashMap<String, MobPosition>();
	
	
	public MobPositionList (String mobConfigPath, String mobConfigName, Plugin plugin) {
		// check if folder kitConfigPath exists - otherwise create it
		File dir = new File(mobConfigPath);
		if (!dir.exists()) {
			try {
				dir.getParentFile().mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // ! dir exists
		
		File ymlFile = new File(mobConfigPath, mobConfigName);
		if (!ymlFile.exists()) {
			try {
				Bukkit.getLogger().info("MobPositionList ctor create config file "+ymlFile+" in folder "+mobConfigPath);
				ymlFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// %%%& ToDo check if default position in mob is set in yml file
		
		// load the yml file and read data
		FileConfiguration kitConfig = YamlConfiguration.loadConfiguration(ymlFile);
		ConfigurationSection allKitsConfig = kitConfig.getConfigurationSection("kits");
		for (String key : allKitsConfig.getKeys(false)) {
			ConfigurationSection curKitPosConfig = allKitsConfig.getConfigurationSection(key);
			MobPosition kitPos = new MobPosition(key, curKitPosConfig);
			allMobPositions.put(key, kitPos);
		} // for

//Bukkit.getLogger().info("kitConfig ctor: Finished loading configfile "+ymlFile.getName()+" from folder "+kitConfigPath);
Bukkit.getLogger().info("kitConfig ctor: allKits="+allMobPositions);
	} // ctor

	
//-------------------------------------- getter & setter --------------------
	
	public Map<String, MobPosition> getAllPossibleMobPositions() {
		return allMobPositions;
	}

	
	
	
}


/*

kits:
  kit1:
    x: 10
    y: 70
    z: 20
    o: "-70.5"
  kit2:
    x: 5
    y: 69
    z: 34
    o: "103.9"


*/
