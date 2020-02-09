package com.baconsky.features.twoPhaseCountdown;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.baconsky.core.CoreFeature;
import com.baconsky.minigame.interfaces.FeatureType;
import com.baconsky.minigame.interfaces.Phase;

public class TwoPhaseCountdownFeature extends CoreFeature {
	private LifeCycle lifeCycle;
//	private Phase phase;

	public TwoPhaseCountdownFeature(Phase phase) {
		super(phase);
//		this.phase = phase;
	}

	@Override
	public FeatureType getFeatureType() {
		return FeatureType.TWO_PHASE_COUNTDOWN;
	}

	@Override
	public void onStartFeature() {
		lifeCycle = new LifeCycle(phase);
		lifeCycle.resetGameState();
		lifeCycle.stateMachine();
	}

	@Override
	public void onStopFeature() {
		lifeCycle.stopCountdown();
	}
	
	//-------------------------------- public methods ------------------
/*	
	public void resetGameState() {
		lifeCycle.resetGameState();
	}
	
	public void stateMachine() {
		lifeCycle.stateMachine();
	}
*/
	//----------------------------- Listeners --------------------------------
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Bukkit.getLogger().info("TwoPhaseCountdownFeature PlayerJoinEvent");
		lifeCycle.stateMachine();
	}


	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Bukkit.getLogger().info("TwoPhaseCountdownFeature PlayerQuitEvent");
		lifeCycle.stateMachine();
	}


//---------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return "TwoPhaseCountdownFeature";
	}

	

}
