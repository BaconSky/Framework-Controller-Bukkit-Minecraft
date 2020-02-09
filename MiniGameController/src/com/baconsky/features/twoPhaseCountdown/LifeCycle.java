package com.baconsky.features.twoPhaseCountdown;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import com.baconsky.core.TheMiniGameController;
import com.baconsky.lobby.config.MiniGameConfig;
import com.baconsky.minigame.interfaces.Phase;
import com.baconsky.minigame.interfaces.ScoreboardCommand;
import com.baconsky.minigame.main.MiniGameController;

public class LifeCycle {
	public static int startCountDownId = 0;

	//%%%& ToDo:  Take care that the thread is cancelled sooner or later. Otherwise we will have a memory league. 
	

	private MiniGameController miniGameController;
	private GameState gameState;

	private CountdownThread countdownThread;
	private MiniGameConfig miniGameConfig;
	private Phase phase;
	
	public LifeCycle(Phase phase) {
		this.phase = phase;
		miniGameController = TheMiniGameController.getTheMiniGameController().getInstance();
		this.miniGameConfig = miniGameController.getMiniGameConfig();
		gameState = GameState.BEFORE_COUNTDOWN;
	}
	

	
	
	//------------------------------------- Countdown State Machine -------------------------------------
		
		public enum GameState { BEFORE_COUNTDOWN, COUNTDOWN_1, COUNTDOWN_2, START_GAME, IN_GAME };
		
	/*
	* 	Countdown State Machine
	* 
	* 	BEFORE_COUNTDOWN
	* 		More or Equal MinNumPlayers logged in -> COUNTDOWN_1
	* 		More or Equal TypicalNumPlayers logged in -> COUNTDOWN_2
	* 		
	* 
	* 
	* 	COUNTDOWN_1 (start countdown with countdow1 sec)
	* 		More or Equal TypicalNumPlayers logged in -> COUNTDOWN_2
	* 		Less than MinNumPlayers -> BEFORE_COUNTDOWN
	* 		Less Than Countdown2 sec before Game starts -> COUNTDOWN_2
	* 
	* 
	* 	COUNTDOWN_2 (assign teams, if countdow > countdown2 sec then countdown = countdown2 sec)
	* 		Less than MinNumPlayers -> BEFORE_COUNTDOWN
	* 		0 sec before Game starts -> START_GAME 
	* 
	* 
	* 	START_GAME (cancel countdownThread, deactivate MiniGameLobby plugin, Activate Game plugin)
	* 		-> goto IN_GAME
	* 	
	* 	IN_GAME
	* 		when winner is defined -> BEFORE_COUNTDOWN
	* 	
	*/

		

	public GameState stateMachine() {
//		Bukkit.getLogger().info("LifeCycle stateMachine pos1 gameState="+gameState);

		
		int timeUntilStart;
		if (countdownThread == null) timeUntilStart = miniGameController.getLobbyConfig().getCountdown1() + 1;
		else timeUntilStart = this.countdownThread.getTimeUntilStart();
		
		String cnt = Integer.toString(timeUntilStart);
		List<String> args = new ArrayList<String>();
		args.add(cnt);
		miniGameController.getLobby().updateScoreboard(null, ScoreboardCommand.UPDATE_COUNTDOWN, args);
		
		switch (gameState) {
		case BEFORE_COUNTDOWN :
			if (miniGameConfig.countdown1Condition()) {
//Bukkit.getLogger().info("LifeCycle stateMachine pos2 countdown1Condition=true");
				
				startCountdown();
				gameState = GameState.COUNTDOWN_1;
			}
		case COUNTDOWN_1 :
			if (miniGameConfig.beforeCountdownCondition()) {
//Bukkit.getLogger().info("LifeCycle stateMachine pos3 beforeCountdownCondition=true");
				stopCountdown();
				gameState = GameState.BEFORE_COUNTDOWN;
			}
			if ((miniGameConfig.countdown2Condition()) ||
					(timeUntilStart < miniGameController.getLobbyConfig().getCountdown2()))
			{
//Bukkit.getLogger().info("LifeCycle stateMachine pos4 countdown2Condition=true||timeUntilStart<");
				gameState = GameState.COUNTDOWN_2;
			}

			break;
		case COUNTDOWN_2 :
			if (miniGameConfig.beforeCountdownCondition()) {
//Bukkit.getLogger().info("LifeCycle stateMachine pos5 beforeCountdownCondition=true");
				stopCountdown();
				gameState = GameState.BEFORE_COUNTDOWN;
			}
			if (timeUntilStart < 1)	{
//Bukkit.getLogger().info("LifeCycle stateMachine pos6 timeUntilStart<1");
				gameState = GameState.START_GAME;
			}
			break;

		case START_GAME :
			Bukkit.getLogger().info("LifeCycle stateMachine pos7 START_GAME calling onStopPhase");			
			phase.onStopPhase();
//			miniGameController.startNextPhase();
			gameState = GameState.IN_GAME;
			break;
		case IN_GAME :

			break;

		} // switch

//Bukkit.getLogger().info("LifeCycle stateMachine pos9 new gameState="+gameState);
		return gameState; 
	} // stateMachine

		
	
	
	//----------------------------------- Countdown ------------------------------------
	
	
	public void startCountdown() {
//Bukkit.getLogger().info("startCountdown");
		this.countdownThread = new CountdownThread(miniGameController, this);
		CountdownThread.timeUntilStart = miniGameController.getLobbyConfig().getCountdown1();
		startCountDownId = miniGameController.getServer().getScheduler().scheduleSyncRepeatingTask(miniGameController, this.countdownThread, 20l, 20l);
	}


	public void stopCountdown() {
		if (startCountDownId == 0) return;
		Bukkit.getLogger().info("LiveCycle.stopCountdown going to cancel Task Tread");
		Bukkit.getScheduler().cancelTask(startCountDownId);
		startCountDownId = 0;
	}
	
	
	public void restartCountdown() {
//Bukkit.getLogger().info("restartCountdown");
		stopCountdown();
		gameState = GameState.COUNTDOWN_1;
		startCountdown();
	}


	
	public void resetGameState() {
//Bukkit.getLogger().info("resetGameState");
		gameState = GameState.BEFORE_COUNTDOWN;
	} 

	
	
	
	
	
}
