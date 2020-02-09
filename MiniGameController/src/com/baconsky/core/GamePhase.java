package com.baconsky.core;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public abstract class GamePhase extends CorePhase {
	
	private int playersAlive;
	

	@Override
	public void onStartPhase() {
		super.onStartPhase();
		playersAlive = Bukkit.getServer().getOnlinePlayers().size();

	}

	
	public int decrementPlayersAlive() {
		playersAlive--;
Bukkit.getLogger().info("GamePhase decrementPlayersAlive onlinePlayers="+Bukkit.getServer().getOnlinePlayers()+" size="+Bukkit.getServer().getOnlinePlayers().size()+" new value of playersAlive="+playersAlive); 
		return playersAlive;
	}

	public int getPlayersAlive() {
Bukkit.getLogger().info("GamePhase getPlayersAlive onlinePlayers="+Bukkit.getServer().getOnlinePlayers()+" size="+Bukkit.getServer().getOnlinePlayers().size()+" playersAlive="+playersAlive); 
		return playersAlive;
	}


	public abstract void checkGameState();

	
	//------------------------------------- Listeners ---------------------------------
	
	
	
	

	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Bukkit.getLogger().info("GamePhase PlayerQuitEvent "+event.getPlayer().getName() + " has quit the game!");
		decrementPlayersAlive();
	} // onPlayerQuit


	

}
