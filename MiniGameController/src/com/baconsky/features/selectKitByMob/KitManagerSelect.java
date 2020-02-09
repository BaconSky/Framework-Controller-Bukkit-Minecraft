package com.baconsky.features.selectKitByMob;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.baconsky.lobby.main.MiniGameLobbyPhase;
import com.baconsky.minigame.config.MiniGameControllerConfig;
import com.baconsky.minigame.main.MiniGameController;


public class KitManagerSelect {
	private Map<String, KitSelect> allKits;
	private HashMap<String, KitSelect> playerKits;
	private boolean mobsSpawned = false;
	private MobThread mobThread;
	private MiniGameController miniGameController;

//------------------------ ctor ---------------------------
	
	
	
	public KitManagerSelect() {
		allKits = new HashMap<String, KitSelect>();
		playerKits = new HashMap<String, KitSelect>();
	}

	public KitManagerSelect(String pathToConfigFiles, MiniGameController miniGameController) {
		this.miniGameController = miniGameController;
		
		allKits = new HashMap<String, KitSelect>();
		playerKits = new HashMap<String, KitSelect>();
		// check if folder pathToConfigFiles exists - otherwise create it
		File dir = new File(pathToConfigFiles);
		if (!dir.exists()) {
			try {
				dir.getParentFile().mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // ! configDir exists
		// get all yml files in this foloder
		dir = new File(pathToConfigFiles);;
		File[] allYmlFiles = dir.listFiles(new FilenameFilter() { 
					public boolean accept(File configDir, String filename)
						{ return filename.endsWith(".yml"); }
				} );
		// load the yml files and contruct the Kits
		for (File ymlFile : allYmlFiles) {
//Bukkit.getLogger().info("KitManager: Start loading configfile "+ymlFile.getName()+" from folder "+pathToConfigFiles);
			FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(ymlFile);
			KitSelect kit = new KitSelect(fileConfig, this);
//Bukkit.getLogger().info("KitManager: Finished loading configfile "+ymlFile.getName()+" from folder "+pathToConfigFiles);
			// save kit in allKits
			allKits.put(kit.getSuperpower(), kit);
		}


		
	} // KitManager(pathToConfigFiles)

//---------------------------- assign positions and place mobs ----------------------------

	public void assignMobPositions(MobPositionList mobPositionList) {
		Map<String, MobPosition> tmpList = mobPositionList.getAllPossibleMobPositions();
		for (Map.Entry<String, KitSelect> entry : allKits.entrySet()) {
		    //String key = entry.getKey();
		    KitSelect kit = entry.getValue();
		    KitMetadataSelect kitMetadata = kit.getKitMetadata(); 
		    String defaultPos = kitMetadata.getMobPositionName();
		    // check if this position is available
		    if( (defaultPos != null) && (defaultPos.length() > 0) && (tmpList.containsKey(defaultPos))) {
		    	kitMetadata.setMobPosition(tmpList.get(defaultPos));
		    	tmpList.remove(defaultPos);
		    	kit.setKitMetadata(kitMetadata);
		    } else {
		    	// find empty position
		    	String tmpPos = null;
		    	for (String tmpKey : tmpList.keySet()) tmpPos = tmpKey;
		    	if (tmpPos != null) {
			    	kitMetadata.setMobPosition(tmpList.get(tmpPos));
			    	tmpList.remove(tmpPos);
			    	kit.setKitMetadata(kitMetadata);
		    	} else {
		    		Bukkit.getLogger().severe("Internal Error 834425: No mob position left. More mobs than positions.");
		    	}
		    }
		} // for
	} // assignMobPositions
	
	
	/**
	 * 
	 * @param world
	 * @param forcedSpawning : if forcedSpawning is true the mobs will be placed, no matter if mobsSpawned is false or not
	 */
	public void placeMobs(boolean forcedSpawning) {
		if ((!forcedSpawning) && (mobsSpawned)) return;
		mobsSpawned = true;

		this.mobThread = new MobThread(miniGameController, allKits);
//		Bukkit.getLogger().info("++++++++++****** KitManager placeMobs start scheduler");	
		miniGameController.getServer().getScheduler().scheduleSyncRepeatingTask(miniGameController, mobThread, 20l, 20l);
	} // placeMobs



//--------------------------------- public manager methods -----------------


	
	public KitSelect createKit(String name, List<String> items, KitMetadataSelect kitDescription) {
		if (allKits.containsKey(name)) {
			Bukkit.getLogger().severe("Kit with name "+name+" exists already. Kit not constructed");
			return null;
		}
		KitSelect k = new KitSelect(name, items, kitDescription, this);
		allKits.put(name, k);
		return k;
	}

	public boolean isKit(String name) {
		return (allKits.containsKey(name));
	}

	public KitSelect getKit(String name) {
		if (!allKits.containsKey(name)) return null;
		return allKits.get(name);
	}

	public void setKit(Player player, KitSelect kit) {
		playerKits.put(player.getName(), kit);
	}

	public KitSelect getKit(Player player) {
		return (playerKits.get(player.getName()) == null) ? allKits.get(0) : playerKits.get(player.getName());
	}


	public Map<String, KitSelect> getAllKits() {
		return allKits;
	}


	public void savePlayerKits() {
		File configDir = miniGameController.getDataFolder().getParentFile();
		//Bukkit.getLogger().info("MiniGameConfig ctor configDir="+configDir.getName());
		MiniGameControllerConfig miniGameControllerConfig = miniGameController.getMiniGameControllerConfig(); 
		String kitPlayerKitConfigName = miniGameControllerConfig.getPlayerKitsConfigName();

		File configFile = new File(configDir, kitPlayerKitConfigName);

		YamlConfiguration yamlFile = new YamlConfiguration();
		for (Map.Entry<String, KitSelect> entry : playerKits.entrySet()) {
		    String player = entry.getKey();
		    KitSelect kit = entry.getValue();
			yamlFile.set("kits."+player, kit.getSuperpower());
		}
		try {
			yamlFile.save(configFile);
		} catch(IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String toString() {
		return "KitManager [playerKits=" + playerKits + "]";
	}
	
	
	
	
}
