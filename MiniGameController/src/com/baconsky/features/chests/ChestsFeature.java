package com.baconsky.features.chests;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.baconsky.core.CoreFeature;
import com.baconsky.minigame.config.MiniGameControllerConfig;
import com.baconsky.minigame.interfaces.FeatureType;
import com.baconsky.minigame.interfaces.Phase;
import com.baconsky.minigame.main.MiniGameController;



public class ChestsFeature extends CoreFeature {
	private MiniGameController miniGameController;
	private String pathToChestConfig;
	private String inventoryConfigName;
	private String pathToInventoryConfig;
	private ChestManager chestManager;
	private MiniGameControllerConfig miniGameControllerConfig;
	private ChestConfig chestPosConfig;
	
	public ChestsFeature(Phase phase) {
		super(phase);
	}

	@Override
	public FeatureType getFeatureType() {
		return FeatureType.SCATTER_CHESTS;
	}


	@Override
	public void beforeStartFeature(final Phase phase) {
		super.beforeStartFeature(phase);

Bukkit.getLogger().info("ChestsFeature beforeStartFeature pos11");		

		// chestSystem
		// get chest positions
		this.miniGameController = phase.getGame();
		miniGameControllerConfig = miniGameController.getMiniGameControllerConfig(); 
		inventoryConfigName = "block_inventory.yml";
		pathToInventoryConfig = miniGameControllerConfig.getGameConfigPath();
		String pluginDir = phase.getGame().getDataFolder().getParentFile().getName();
		String pathToGameConfig = miniGameControllerConfig.getGameConfigPath();
		String curArena = miniGameControllerConfig.getCurrentArenaName();
		pathToChestConfig = pluginDir + File.separator + pathToGameConfig + File.separator + "arenas" + File.separator + curArena;
		chestPosConfig = new ChestConfig(pathToChestConfig, "arena.yml", phase.getGame());

		// get chest inventories
		World world = Bukkit.getWorld("world");
		chestManager = new ChestManager(pluginDir + File.separator + pathToInventoryConfig, inventoryConfigName, world);
		
//		chestManager.setChests(chestPosConfig);
		
		chestManager.setChests();

	} // beforeStart


	
	@Override
	public void onStartFeature() {
		Bukkit.getLogger().info("ChestsFeature onStartFeature pos1");		
		String pluginDir = miniGameController.getDataFolder().getParentFile().getName();
		World world = Bukkit.getWorld("world");
		chestManager = new ChestManager(pluginDir + File.separator + pathToInventoryConfig, inventoryConfigName, world);
		chestManager.setChests();
		chestManager.fillChest();
	}

	
	
	@Override
	public void onStopFeature() {
		// TODO Auto-generated method stub
	}


	
	

}
