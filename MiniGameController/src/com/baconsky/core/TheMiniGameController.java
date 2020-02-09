package com.baconsky.core;

import com.baconsky.minigame.main.MiniGameController;

public class TheMiniGameController {

	private final MiniGameController instance;
	private static TheMiniGameController theMiniGameController;

	
	public static TheMiniGameController getTheMiniGameController() {
		return theMiniGameController;
	}


	public TheMiniGameController(final MiniGameController instance) {
		this.instance = instance;
		TheMiniGameController.theMiniGameController = this; 
	}

	public MiniGameController getInstance() {
		return instance;
	}
}
