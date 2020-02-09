package com.baconsky.features.lobbyScoreboard;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.baconsky.lobby.main.MiniGameLobbyPhase;
import com.baconsky.minigame.interfaces.MiniGameScoreboard;
import com.baconsky.minigame.interfaces.ScoreboardCommand;
import com.baconsky.minigame.main.MiniGameController;

public class LobbyScoreboard {
	
	MiniGameController miniGameController;
	ScoreboardManager scoreboardManager;
	Scoreboard scoreboard;
	Objective scoreboardObjective;
	
	int timeUntilStart;
	
	public LobbyScoreboard(MiniGameController miniGameController){
		this.miniGameController = miniGameController;
		this.scoreboardManager = Bukkit.getScoreboardManager();
		this.scoreboard = scoreboardManager.getNewScoreboard();
		scoreboardObjective = this.scoreboard.registerNewObjective("Teams", "dummy");
		scoreboardObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	public void updateScoreboard() {
		setupLobbyScoreboard();
		if (Bukkit.getOnlinePlayers().size() == 0) return;
		for(Player player : Bukkit.getOnlinePlayers()) {
			// kit 
			String kitName = miniGameController.getLobby().getKitName(player);
			Score score7 = scoreboardObjective.getScore(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Kit: " + ChatColor.WHITE + kitName);
			score7.setScore(10);
			Score score8 = scoreboardObjective.getScore("    ");
			score8.setScore(9);
			try {
				player.setScoreboard(scoreboard);
			} catch (Exception e) {
				// Do nothing;
			}
		}
	}
	
	public void setTimeUntilStart(int timeUntilStart) {
//		Bukkit.getLogger().info("LobbyScoreboard setTimeUntilStart:"+timeUntilStart);

		this.timeUntilStart = timeUntilStart;
		updateScoreboard();
//		Bukkit.getLogger().info("timeUntilStart = " + timeUntilStart);
	}
	
//----------------------------------------- private methods ----------------------------
			


	public Scoreboard getScoreboard() {
		return scoreboard;
	}

	private void setupLobbyScoreboard() {
		if (timeUntilStart < 0) return;
	    for (String entry : scoreboard.getEntries()) {
//Bukkit.getLogger().info(" entry="+entry);
	    	scoreboard.resetScores(entry);
	    }
		int numPlayers = Bukkit.getOnlinePlayers().size();
		int maxNumPlayers = miniGameController.getMiniGameConfig().getMaxNumPlayer();
		int minNumPlayers = miniGameController.getMiniGameConfig().getMinNumPlayer();
		if (numPlayers < minNumPlayers) scoreboardObjective.setDisplayName(ChatColor.BOLD + "Waiting for players...");
		else scoreboardObjective.setDisplayName(ChatColor.BOLD + "Time Until Start: " + timeUntilStart);

		// Num players / maxNumPlayers;
		Score score1 = scoreboardObjective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "Tributes: " +  ChatColor.WHITE + numPlayers + "/" + maxNumPlayers);
		score1.setScore(16);
		Score score2 = scoreboardObjective.getScore(" ");
		score2.setScore(15);
		
		// Game
		String gameName = miniGameController.getMiniGameConfig().getName();
		Score score3 = scoreboardObjective.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + "Game: " + ChatColor.WHITE + gameName);
		score3.setScore(14);
		Score score4 = scoreboardObjective.getScore("  ");
		score4.setScore(13);
		
		// Map 
		String mapName = miniGameController.getLobby().getMapName();
		Score score5 = scoreboardObjective.getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Map: " + ChatColor.WHITE + mapName);
		score5.setScore(12);
		Score score6 = scoreboardObjective.getScore("   ");
		score6.setScore(11);
		
		/*
		Score score1 = scoreboardObjective.getScore(" ");
		score1.setScore(16);
		//scoreboard.resetScores(ChatColor.GREEN + "" + ChatColor.BOLD +"Time Until Start: " + ChatColor.WHITE + (timeUntilStart-1));
		Score score2 = scoreboardObjective.getScore(ChatColor.GREEN + "" + ChatColor.BOLD +"Time Until Start: " + ChatColor.WHITE + timeUntilStart);
		score2.setScore(15);
		Score score3 = scoreboardObjective.getScore("  ");
		score3.setScore(14);
		Score score4 = scoreboardObjective.getScore(ChatColor.BLUE + "" + ChatColor.BOLD + "Time since started: " + ChatColor.WHITE + "2.0       ");
		score4.setScore(13);
		Score score5 = scoreboardObjective.getScore("-----------------------");
		score5.setScore(12);
*/		
	}

/*	
	@Override
	public void updateScoreboard(Player player, ScoreboardCommand command, List<String> args) {
		if (command == ScoreboardCommand.UPDATE_COUNTDOWN) {
			int newCountdown = Integer.parseInt(args.get(0));
			setTimeUntilStart(newCountdown);
		}
	}
*/	
	
	
	
}

			
