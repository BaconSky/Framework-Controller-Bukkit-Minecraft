package com.baconsky.core;

import com.baconsky.minigame.interfaces.Feature;
import com.baconsky.minigame.interfaces.Phase;

public abstract class CoreFeature implements Feature {

	protected Phase phase;

	public CoreFeature(final Phase phase) {
		this.phase = phase;
	}

	@Override
	public Phase getPhase() {
		return phase;
	}

	@Override
	public void beforeStartFeature(final Phase phase) {
		this.phase = phase;
	}

	@Override
	public void onStopFeature() {
		// Do nothing
	}


	
}

