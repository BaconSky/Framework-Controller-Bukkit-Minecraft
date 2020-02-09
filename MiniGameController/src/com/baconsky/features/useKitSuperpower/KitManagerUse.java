package com.baconsky.features.useKitSuperpower;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.baconsky.core.TheMiniGameController;
import com.baconsky.hungergames.main.HungerGamesPhase;
import com.baconsky.minigame.main.MiniGameController;

public class KitManagerUse {
	
	private Map<String, KitUse> playerKits = new HashMap<String, KitUse>();
	
	public KitManagerUse() {
		this("player_kits.yml");
	}
	

	public KitManagerUse(String playerKitConfigName) {
		MiniGameController miniGameController = TheMiniGameController.getTheMiniGameController().getInstance();

		File configDir = miniGameController.getDataFolder().getParentFile();
		File configFile = new File(configDir, playerKitConfigName);

		FileConfiguration KitManager = YamlConfiguration.loadConfiguration(configFile);
		ConfigurationSection kits = KitManager.getConfigurationSection("kits");
		for (String player : kits.getKeys(false)) {
			String kitName = kits.getString(player);
Bukkit.getLogger().info("KitManager ctor found player="+player+ " kit:"+kitName);
			String className = "com.baconsky.hungergames.handler.kitHandler.kits."+kitName;
			try {
//				Kit kit = (Kit) Class.forName(className).newInstance();
				Class<?> kitClass = Class.forName(className);
				Constructor<?> ctor = kitClass.getConstructor(Plugin.class);
				KitUse kit = (KitUse) ctor.newInstance(new Object[] { miniGameController });

				playerKits.put(player, kit);
			} catch (Exception e) {
				Bukkit.getLogger().severe("KitManager could not construct class "+className);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} // for

	} 

	
/*	
	public KitManager(String kitManagerName, Plugin plugin) {
		this.plugin = plugin;
		this.kitManagerName = kitManagerName;
		
		File configDir = plugin.getDataFolder().getParentFile();
//Bukkit.getLogger().info("KitManager ctor configDir="+configDir.getName());
		File configFile = new File(configDir, this.kitManagerName);
		
//Bukkit.getLogger().info("KitManager ctor configFile="+configFile.getName());

		FileConfiguration KitManager = YamlConfiguration.loadConfiguration(configFile);
		ConfigurationSection kits = KitManager.getConfigurationSection("kits");
		for (String player : kits.getKeys(false)) {
			String kitName = kits.getString(player);
Bukkit.getLogger().info("KitManager ctor found player="+player+ " kit:"+kitName);
			String className = "com.baconsky.hungergames.handler.kitHandler.kits."+kitName;
			try {
//				Kit kit = (Kit) Class.forName(className).newInstance();
				Class<?> kitClass = Class.forName(className);
				Constructor<?> ctor = kitClass.getConstructor(Plugin.class);
				Kit kit = (Kit) ctor.newInstance(new Object[] { plugin });

				playerKits.put(player, kit);
			} catch (Exception e) {
				Bukkit.getLogger().severe("KitManager could not construct class "+className);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} // for
	} // ctor
*/	
	
	public KitUse getPlayerKit(String player) {
		if (!(playerKits.containsKey(player))) return null;
		return playerKits.get(player);
	}
	

}
