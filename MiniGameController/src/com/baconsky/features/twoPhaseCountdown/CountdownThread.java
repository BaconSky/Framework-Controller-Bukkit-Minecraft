package com.baconsky.features.twoPhaseCountdown;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownThread extends BukkitRunnable {
	
	Plugin plugin;
	LifeCycle liveCycle;
	
	public CountdownThread(Plugin plugin, LifeCycle liveCycle) {
		this.plugin = plugin;
		this.liveCycle = liveCycle;
	}
	
	
	public static int timeUntilStart;
	
	public int getTimeUntilStart() {
		return timeUntilStart;
	}
	
	public void setTimeUntilStart(int timeUntilStart){
		this.timeUntilStart = timeUntilStart;
	}
	
	
	@Override
	public void run() {
		if (timeUntilStart == 0) {
//			Bukkit.getLogger().info("Game starts");
/*			
			if (!Game.canStart()){
				plugin.restartCountdown();
				ChatUtilities.broadcast("Cannot start game. Restarting countdown!");
				return;
			}
			Game.start();
*/			
		}
		
		if ((timeUntilStart % 10 == 0 || timeUntilStart < 10) && (timeUntilStart >= 0)) {
			Bukkit.getLogger().info(String.valueOf(timeUntilStart) + "seconds until the games starts");
		}
		timeUntilStart--;
		
//Bukkit.getLogger().info("StartCountdown calling stateMachine");		
		this.liveCycle.stateMachine();
		

	}
}
