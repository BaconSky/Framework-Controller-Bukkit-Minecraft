package com.baconsky.hungergames.main;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.baconsky.core.GamePhase;
import com.baconsky.core.TheMiniGameController;
import com.baconsky.features.chests.ChestManager;
import com.baconsky.hungergames.handler.LifeCycle;
import com.baconsky.minigame.config.MiniGameControllerConfig;
import com.baconsky.minigame.interfaces.MiniGameScoreboard;
import com.baconsky.minigame.interfaces.ScoreboardCommand;
import com.baconsky.minigame.main.MiniGameController;


public class HungerGamesPhase extends GamePhase {
	private MiniGameController miniGameController;
	private LifeCycle lifeCycle;
	
	private String inventoryConfigName;
	private String pathToInventoryConfig;
	private ChestManager chestManager;
	private MiniGameControllerConfig miniGameControllerConfig;

	//------------------------------- ctor --------------------------------------
	
	
	public HungerGamesPhase(MiniGameController miniGameController) {
		this.miniGameController = miniGameController;
/*
		miniGameControllerConfig = new MiniGameControllerConfig("minigame.yml", miniGameController);
		inventoryConfigName = "block_inventory.yml";
		pathToInventoryConfig = miniGameControllerConfig.getGameConfigPath();
*/		
	}
	
	// -------------------------------------- Overrides Phase ------------------------
	

	@Override
	public String getName() {
		return "HungerGames";
	}


	
	@Override
	public void beforeStartPhase() {
		MiniGameController miniGameController = TheMiniGameController.getTheMiniGameController().getInstance();
		miniGameControllerConfig = new MiniGameControllerConfig("minigame.yml", miniGameController);
		inventoryConfigName = "block_inventory.yml";
		pathToInventoryConfig = miniGameControllerConfig.getGameConfigPath();
	}


	
	@Override
	public void onStartPhase() {
		super.onStartPhase();
		//TeamHandler

		this.lifeCycle = new LifeCycle(this);
		
Bukkit.getLogger().info("========== HungerGames onStart onlinePlayers="+Bukkit.getServer().getOnlinePlayers()+" size="+Bukkit.getServer().getOnlinePlayers().size()); 
		Bukkit.getLogger().info("HungerGames was started");
		
	} //startPhase
	
	@Override
	public void onStopPhase() {
		Bukkit.getLogger().info("HungerGames.onStopPhase pos1");
	} // onStopPhase
	

	@Override
	public MiniGameController getGame() {
		return miniGameController;
	}

	
	@Override
	public void setScoreboard(MiniGameScoreboard scoreboard) {
		// %%%& ToDo Auto-generated method stub
		
	}


	@Override
	public void updateScoreboard(Player player, ScoreboardCommand command, List<String> args) {
		// TODO Auto-generated method stub
		
	}


	
	//---------------------------- Override GamePhase

	
	@Override
	public void checkGameState() {
		lifeCycle.checkGameState();
	}

	
	//------------------------------------ Utilities -------------------------------
	

	@Override
	public String toString() {
		return "HungerGamesPhase";
	}

	
	
	
}
