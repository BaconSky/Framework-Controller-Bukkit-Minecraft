package com.baconsky.features.lobbyScoreboard;

import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.baconsky.core.CoreFeature;
import com.baconsky.core.TheMiniGameController;
import com.baconsky.features.twoPhaseCountdown.LifeCycle;
import com.baconsky.lobby.main.MiniGameLobbyPhase;
import com.baconsky.minigame.interfaces.FeatureType;
import com.baconsky.minigame.interfaces.MiniGameScoreboard;
import com.baconsky.minigame.interfaces.Phase;
import com.baconsky.minigame.interfaces.ScoreboardCommand;
import com.baconsky.minigame.main.MiniGameController;

public class LobbyScoreboardFeature extends CoreFeature implements MiniGameScoreboard {

	private LobbyScoreboard lobbyScoreboard;
//	private Phase phase;
	
	// ----------------------------------- ctor ----------------------------
	
	public LobbyScoreboardFeature(Phase phase) {
		super(phase);
//		this.phase = phase;
	}

	//---------------------------  MiniGameScoreboard ------------------------

	@Override
	public void updateScoreboard(Player player, ScoreboardCommand command, List<String> args) {
		if (lobbyScoreboard == null) return;
//Bukkit.getLogger().info("LobbyScoreboardFeature updateScoreboard command = "+command+"args ="+args);

		if (command == ScoreboardCommand.UPDATE_COUNTDOWN) {
//Bukkit.getLogger().info("LobbyScoreboardFeature updateScoreboard command = UPDATE_COUNTDOWN cnt="+args.get(0));
			int newCountdown = Integer.parseInt(args.get(0));
			setTimeUntilStart(newCountdown);
		}
	}

	@Override
	public void updateScoreboard() {
		if (lobbyScoreboard == null) return;
		lobbyScoreboard.updateScoreboard();
	}
	
	//---------------------------------------- CoreFeature ------------------------

	@Override
	public FeatureType getFeatureType() {
		return FeatureType.LOBBY_SCOREBOARD;
	}

	@Override
	public void onStartFeature() {
//		Bukkit.getLogger().info("LobbyScoreboardFeature start pos1");
		MiniGameController miniGameController = TheMiniGameController.getTheMiniGameController().getInstance();
		lobbyScoreboard = new LobbyScoreboard(miniGameController);

		if (super.phase instanceof MiniGameLobbyPhase) {
			MiniGameLobbyPhase miniGameLobby = (MiniGameLobbyPhase) phase;
			miniGameLobby.setScoreboard(this);	
		}

		lobbyScoreboard.updateScoreboard();
	}

	@Override
	public void onStopFeature() {
		// TODO Auto-generated method stub
	}

	//----------------------------- Listeners --------------------------------
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Bukkit.getLogger().info("LobbyScoreboardFeature PlayerJoinEvent");
		lobbyScoreboard.updateScoreboard();
	}

	

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Bukkit.getLogger().info("LobbyScoreboardFeature PlayerQuitEvent");
		lobbyScoreboard.updateScoreboard();
	}


	//------------------------------------- public methods ----------------------
	
/*	
	public void updateScoreboard() {
		lobbyScoreboard.updateScoreboard();
	}
*/

	public void setTimeUntilStart(int timeUntilStart) {
		lobbyScoreboard.setTimeUntilStart(timeUntilStart);
	}


	@Override
	public String toString() {
		return "LobbyScoreboardFeature";
	}

	
	
	

}
