package com.baconsky.minigame.main;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.baconsky.core.TheMiniGameController;
import com.baconsky.features.chests.ChestsFeature;
import com.baconsky.features.lobbyScoreboard.LobbyScoreboardFeature;
import com.baconsky.features.selectKitByMob.SelectKitByMobFeature;
import com.baconsky.features.twoPhaseCountdown.TwoPhaseCountdownFeature;
import com.baconsky.hungergames.main.HungerGamesPhase;
import com.baconsky.lobby.config.LobbyConfig;
import com.baconsky.lobby.config.MiniGameConfig;
import com.baconsky.lobby.main.MiniGameLobbyPhase;
import com.baconsky.minigame.config.MiniGameControllerConfig;
import com.baconsky.minigame.interfaces.Feature;
import com.baconsky.minigame.interfaces.Phase;


public class MiniGameController extends JavaPlugin {
	private static TheMiniGameController theMiniGameController;

	private MiniGameControllerConfig miniGameControllerConfig;

	Phase currentPhase;
	
	MiniGameLobbyPhase lobbyPhase; // the phase where we start the game
	HungerGamesPhase hungerGamesPhase;
	
	private LobbyConfig lobbyConfig;

	private MiniGameConfig miniGameConfig;


	
	public MiniGameController() {
//		Bukkit.getLogger().info("MiniGameController ctor pos1");

		miniGameControllerConfig = new MiniGameControllerConfig("minigame.yml", this);
	
		String pluginDir = getDataFolder().getParentFile().getName();
		String pathToLobbyConfig = miniGameControllerConfig.getLobbyConfigPath();
		String lobbyConfigName = miniGameControllerConfig.getLobbyConfigName();
		String pathToGameConfig = miniGameControllerConfig.getGameConfigPath();
		String gameConfigName = miniGameControllerConfig.getGameConfigName();
		lobbyConfig = new LobbyConfig(pluginDir + File.separator + pathToLobbyConfig, lobbyConfigName);
		miniGameConfig = new MiniGameConfig(pluginDir + File.separator + pathToGameConfig, gameConfigName, this);
		//pathToKitsConfig = miniGameControllerConfig.getKitsConfigPath();
	} // ctor


	@Override
	public void onEnable() {
//Bukkit.getLogger().info("MiniGameController onEnable pos1");
		theMiniGameController = new TheMiniGameController(this);  // initializes static TheMiniGameController. 

		//----------------- init lobbyPhase
		lobbyPhase = new MiniGameLobbyPhase(this);
		setCurrentPhase(lobbyPhase);
		
		// countdown
		Feature countdownFeature = new TwoPhaseCountdownFeature(lobbyPhase);
		lobbyPhase.addFeature(countdownFeature);
	
		// scoreboard
//Bukkit.getLogger().info("MiniGameController onEnable adding lobbyScoreboard");
		LobbyScoreboardFeature lobbyScoreboardFeature = new LobbyScoreboardFeature(lobbyPhase);
		lobbyPhase.setScoreboard(lobbyScoreboardFeature);
		lobbyPhase.addFeature(lobbyScoreboardFeature);
//Bukkit.getLogger().info("MiniGameController onEnable after adding lobbyScoreboard. Going to start phase Lobby");

		// select kits
		Feature selectKitsFeature = new SelectKitByMobFeature(lobbyPhase);
		lobbyPhase.addFeature(selectKitsFeature);
		
		lobbyPhase.onStartPhase();
		
		//------------------ init HungerGames
	
		hungerGamesPhase = new HungerGamesPhase(this);
		
		// chests
		ChestsFeature chestsFeature = new ChestsFeature(hungerGamesPhase);
		lobbyPhase.addFeature(chestsFeature);
		
		lobbyPhase.setNextPhase(hungerGamesPhase);

		hungerGamesPhase.setNextPhase(lobbyPhase);

	} // onEnable

	
	
	@Override
	public void onDisable() {
		Bukkit.getLogger().info("MiniGameController.onDisable JavaPlugin was disabled");
	}

	
	public void setCurrentPhase(Phase phase) {
		this.currentPhase = phase;
	}

//-------------------------------------------------------------------------
	
	// %%%& check which of these methods is still necessary
	public MiniGameLobbyPhase getLobby() {
		return lobbyPhase;
	}


	public LobbyConfig getLobbyConfig() {
		return lobbyConfig;
	}


	public MiniGameConfig getMiniGameConfig() {
		return miniGameConfig;
	}


	
	public MiniGameControllerConfig getMiniGameControllerConfig() {
		return miniGameControllerConfig;
	}



	
	
//----------------------------------------------------------------
	
	@Override
	public String toString() {
		return "MiniGameController [theLobby=" + lobbyPhase + "]";
	}

	
	
	
} // MiniGameController
