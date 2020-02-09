package com.baconsky.features.spectatorTeams;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class GameTeam {
	
	
	private Team scoreboardTeam;
	private ChatColor color;
	private SpectatorTeamManager teamManager;
	private boolean isSpectatorTeam;
	
	
//------------------------ctor--------------------------------
	
	public GameTeam(String teamName, ChatColor color, SpectatorTeamManager teamManager) {
		this.teamManager = teamManager;
		if (teamName.equals(teamManager.getSpectatorTeamName())) isSpectatorTeam = true;
		else {
			isSpectatorTeam = false;
			this.scoreboardTeam = teamManager.getScoreboardMG().registerTeamInScoreboard(teamName, color);
		}
		this.color = color;
	}

	
	
	
	
//------------------------getter&setter-----------------------

	
		public ChatColor getColor() {
			return color;
		}

		
		public String getTeamName() {
			return scoreboardTeam.getName();
		}
		
		
//------------------------------------------------------------	
	
	private void setPlayerToSpectatorMode(Player player) {
//		player.setGameMode(GameMode.SPECTATOR);
		player.setGameMode(GameMode.SURVIVAL);
		for (Player other : Bukkit.getOnlinePlayers()) {
			if ( other != player) other.hidePlayer(player);
		}
	}
		
		

	public void addPlayer(Player player) {
		if (player == null) return;
		if (isSpectatorTeam) {
			setPlayerToSpectatorMode(player);
			return;
		} else {
			scoreboardTeam.addPlayer(player);
			teamManager.getScoreboardMG().updateScoreboard();
		}
	}

	
	
	public void removePlayer(Player player) {
		if (player == null) return;
		scoreboardTeam.removePlayer(player);
		if (getMembers().isEmpty()) teamManager.removeTeam(getTeamName());
	}
	
	public boolean isMember(Player player) {
		if (getMembers().contains(player)) return true;
		return false;
	}
	
	public Set<OfflinePlayer> getMembers() {
		return scoreboardTeam.getPlayers();
	}


	@Override
	public String toString() {
		String str = "color = " + color + "\n";
		str += "scoreboardTeam: name="+scoreboardTeam.getName()+" displayNmae="+scoreboardTeam.getDisplayName()+" prefix="+scoreboardTeam.getPrefix()+" players="+scoreboardTeam.getPlayers();
		return str;
	}


}
