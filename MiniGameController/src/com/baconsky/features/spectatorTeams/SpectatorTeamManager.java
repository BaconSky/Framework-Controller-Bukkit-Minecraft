package com.baconsky.features.spectatorTeams;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.baconsky.core.GamePhase;
import com.baconsky.hungergames.main.HungerGamesPhase;
import com.baconsky.minigame.interfaces.Phase;

public class SpectatorTeamManager {
	
	private Map<String, GameTeam> allTeams;
	private GameTeam spectatorTeam;
	private GameScoreboard scoreboardMG;
	private final String SPECTATOR_TEAM_NAME = "Spectator";
	private final ChatColor SPECTATOR_TEAM_COLOR = ChatColor.GRAY;
	
	public SpectatorTeamManager(GamePhase gamePhase) {
Bukkit.getLogger().info("TeamManager ctor pos1");		
		this.allTeams = new HashMap<String, GameTeam>();
		this.scoreboardMG = new GameScoreboard(gamePhase);
		this.spectatorTeam = new GameTeam(SPECTATOR_TEAM_NAME, SPECTATOR_TEAM_COLOR, this);
Bukkit.getLogger().info("TeamManager ctor pos9");		
	} // ctor()
	

	public void initialPlayerTeamAssignment() {
		// for hunger games each player is a team of itself
Bukkit.getLogger().info("initialPlayerTeamAssignment pos1");		
		removeAllTeams();
Bukkit.getLogger().info("initialPlayerTeamAssignment pos2");		
		
		for (Player player :  Bukkit.getOnlinePlayers()) {
Bukkit.getLogger().info("initialPlayerTeamAssignment pos3 found player="+player.getName());		
			// team name = player name
			GameTeam team = createTeam(player.getName(), ChatColor.DARK_BLUE);
			team.addPlayer(player);
		}
Bukkit.getLogger().info("initialPlayerTeamAssignment pos9 new TeamManager="+toString());		
		
	}

	
	
	public GameTeam createTeam(String name, ChatColor color) {
		if (allTeams.containsKey(name)) return null;
		GameTeam team = new GameTeam(name, color, this);
		allTeams.put(name, team);
		return team;
	}
	
	public boolean removeTeam(String name) {
		if (!(allTeams.containsKey(name))) return false;
		allTeams.remove(name);
		return true;
	}	
	
	public GameTeam getTeam(Player player) {
		for (String teamName : allTeams.keySet()) {
			GameTeam team = allTeams.get(teamName);
			if (team.isMember(player)) return team;
		}
		return null;
	}

	
	public int getNumberTeams() {
		return allTeams.size();
	}
	
	public void removeAllTeams() {
		this.allTeams = new HashMap<String, GameTeam>();
		this.scoreboardMG.resetScoreboard();
	}
	
	
//------------------------------- getter & setter ------------------------------
	
	
	
	public GameTeam getSpectatorTeam() {
		return spectatorTeam;
	}


	public String getSpectatorTeamName() {
		return SPECTATOR_TEAM_NAME;
	}


	public GameScoreboard getScoreboardMG() {
		return scoreboardMG;
	}

	
	
//------------------------------------ utilities ---------------------------------	

	@Override
	public String toString() {
		String str = "TeamManager:";
		for (String teamName : allTeams.keySet()) {
			GameTeam team = allTeams.get(teamName);
			str += teamName + ": " + team.toString() + "\n";
		}
		return str;
	}



	
}
