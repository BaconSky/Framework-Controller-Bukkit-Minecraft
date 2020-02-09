package com.baconsky.hungergames.handler;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.baconsky.hungergames.main.HungerGamesPhase;

public class LifeCycle {

	private HungerGamesPhase hgPlugin;
	
	public LifeCycle(HungerGamesPhase hgPlugin) {
		this.hgPlugin = hgPlugin;
	}
	
	
	public void checkGameState() {
		if (gameFinishedCondition()) {
			finishGame();
		}

	} // checkGameState
	
	
	private boolean gameFinishedCondition() {
		if (hgPlugin.getPlayersAlive() < 2) {
			return true;
		}
		return false;
	} // gameFinishedCondition

	
	public void finishGame() {
		Bukkit.getLogger().info("Game finished");
		Set<String> enabledPlugins = new HashSet<String>();
		enabledPlugins.add("MiniGameController");
		enablePlugin(enabledPlugins);
	}
	
	
	public void enablePlugin(Set<String> plugins) {
		for (Plugin pl : Bukkit.getPluginManager().getPlugins()) {
			if (plugins.contains(pl.getDescription().getName())) {
				Bukkit.getPluginManager().enablePlugin(pl);
				continue;
			} else Bukkit.getPluginManager().disablePlugin(pl);
		} // for
	} // enablePlugin

	
	
}
