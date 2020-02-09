package com.baconsky.minigame.interfaces;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.baconsky.minigame.main.MiniGameController;

public interface Phase extends Listener {

	public String getName();

	//-------------------- live cycle -----------------------
	
	public void beforeStartPhase(); // called in the phase before to prepare for this phase 
	public void onStartPhase(); // called in JavaPlugin.onEnable
	public void onStopPhase(); // called when next phase starts - or - in JavaPlugin onDisable

	//------------------- getter, setter and others -------------
	
	public Phase getNextPhase();
	public void setNextPhase(Phase next);

	public MiniGameController getGame();

	// features
	public List<Feature> getFeatures();
	public Feature getFeature(FeatureType feature);
	public void addFeature(Feature f);
	public void removeFeature(FeatureType t);

	// scoreboard
	public void setScoreboard(MiniGameScoreboard scoreboard);
	public void updateScoreboard(Player player, ScoreboardCommand command, List<String> args);


}
