package com.baconsky.minigame.interfaces;

import org.bukkit.event.Listener;

public interface Feature extends Listener {

	public FeatureType getFeatureType();

	public void beforeStartFeature(final Phase phase);

	public void onStartFeature();
	
	public void onStopFeature();

	public Phase getPhase();


}
