package com.baconsky.minigame.interfaces;

import java.util.List;

import org.bukkit.entity.Player;

public interface MiniGameScoreboard {

	/**
	 * 
	 * @param player player = null means: update all players
	 * @param command
	 * @param args
	 */
	public void updateScoreboard(Player player, ScoreboardCommand command, List<String> args);

	public void updateScoreboard();

}
