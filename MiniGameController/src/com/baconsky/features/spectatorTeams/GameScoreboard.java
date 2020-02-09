package com.baconsky.features.spectatorTeams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import com.baconsky.core.GamePhase;
import com.baconsky.hungergames.main.HungerGamesPhase;
import com.baconsky.minigame.interfaces.Phase;

public class GameScoreboard {
	
	private ScoreboardManager scoreboardManager;
	private Scoreboard scoreboard;
	private Objective scoreboardObjective;
	private GamePhase gamePhase;
	
	public GameScoreboard(GamePhase gamePhase) {
		this.gamePhase = gamePhase;
		this.scoreboardManager = Bukkit.getScoreboardManager();
		this.scoreboard = scoreboardManager.getNewScoreboard();
		scoreboardObjective = this.scoreboard.registerNewObjective("Teams", "dummy");
		scoreboardObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}


//-------------------------------------------------------

	// this function is always called when a new team is created
	// %%%& ToDo. We have probably a bug: What happens when a team is removed
	public Team registerTeamInScoreboard(String teamName, ChatColor color) {
		Team team = scoreboard.registerNewTeam(teamName);
		team.setDisplayName(color + teamName);
		team.setPrefix(color + "");
		return team;
	}


	// this method is always called when a player joins the game
	public void updateScoreboard() {
		setupHungerGamesScoreboard();
		for(Player online : Bukkit.getOnlinePlayers()) {
			online.setScoreboard(scoreboard);
		}
	}

	
	public void resetScoreboard() {
		this.scoreboard = scoreboardManager.getNewScoreboard(); 
		scoreboardObjective = this.scoreboard.registerNewObjective("Teams", "dummy");
		scoreboardObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
			
//----------------------------------------- private methods ----------------------------
			


	private void setupHungerGamesScoreboard() {
		scoreboardObjective.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "HungerGames");
		Score score1 = scoreboardObjective.getScore(" ");
		score1.setScore(16);
		Score score2 = scoreboardObjective.getScore(ChatColor.GREEN + "" + ChatColor.BOLD +"Tributes");
		score2.setScore(15);
Bukkit.getLogger().info("========== setupHungerGamesScoreboard onlinePlayers="+Bukkit.getServer().getOnlinePlayers()+" size="+Bukkit.getServer().getOnlinePlayers().size()+"  getPlayersAlive="+gamePhase.getPlayersAlive()); 
		Score score3 = scoreboardObjective.getScore(ChatColor.WHITE + "" + gamePhase.getPlayersAlive());
		score3.setScore(15);
		Score score4 = scoreboardObjective.getScore("  ");
		score4.setScore(14);
		Score score5 = scoreboardObjective.getScore(ChatColor.BLUE + "" + ChatColor.BOLD + "Time since started: " + ChatColor.WHITE + "2.0       ");
		score5.setScore(13);
		Score score6 = scoreboardObjective.getScore("-----------------------");
		score6.setScore(12);
	}


			
			
//------------------------------------------ old stuff ----------------------------------			
	
	
/*	
	public ScoreboardMG() {
//Bukkit.getLogger().info("ScoreboardMG create scoreboardManager & scoreboard");		
		this.scoreboardManager = Bukkit.getScoreboardManager();
		this.scoreboard = scoreboardManager.getNewScoreboard();
//Bukkit.getLogger().info("ScoreboardMG create objective");		
		scoreboardObjective = this.scoreboard.registerNewObjective("Teams", "dummy");
		scoreboardObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
		scoreboardObjective.setDisplayName("Teams");
	}
	
//-------------------------------------------------------
	
	// this function is always called when a new team is created
	// %%%& ToDo. We have probably a bug: What happens when a team is removed
	public Team registerTeamInScoreboard(String teamName, ChatColor color) {
//		Scoreboard board = teamManager.getScoreboardMG().getScoreboard();
		Team team = scoreboard.registerNewTeam(teamName);
		team.setDisplayName(color + teamName);
		team.setPrefix(color + " ");
		return team;
	}
	
	
	// this method is always called when a player joins the game
	public void updateScoreboard() {
		int playerSlot = 1;
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			Score score = scoreboardObjective.getScore(player);
			score.setScore(playerSlot);
			playerSlot++;
		}
		
		for(Player online : Bukkit.getOnlinePlayers()) {
		online.setScoreboard(scoreboard);
		}
	}
	
*/
	
	
	
	
	
//------------------------getter&setter-----------------------
	
	

	public ScoreboardManager getScoreboardManager() {
		return scoreboardManager;
	}



	public Objective getScoreboardObjective() {
		return scoreboardObjective;
	}



	public Scoreboard getScoreboard() {
		return scoreboard;
	}



	

}
