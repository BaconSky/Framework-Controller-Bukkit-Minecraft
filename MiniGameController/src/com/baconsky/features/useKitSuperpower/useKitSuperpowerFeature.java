package com.baconsky.features.useKitSuperpower;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.baconsky.core.CoreFeature;
import com.baconsky.core.TheMiniGameController;
import com.baconsky.minigame.config.MiniGameControllerConfig;
import com.baconsky.minigame.interfaces.FeatureType;
import com.baconsky.minigame.interfaces.Phase;
import com.baconsky.minigame.main.MiniGameController;

public class useKitSuperpowerFeature extends CoreFeature {

	private KitManagerUse kitManager;
	
	public useKitSuperpowerFeature(Phase phase) {
		super(phase);
	}

	@Override
	public FeatureType getFeatureType() {
		return FeatureType.USE_KIT;
	}

	@Override
	public void onStartFeature() {
		MiniGameController miniGameController = TheMiniGameController.getTheMiniGameController().getInstance();
		MiniGameControllerConfig miniGameControllerConfig = miniGameController.getMiniGameControllerConfig(); 
		String playerKitsConfigName = miniGameControllerConfig.getPlayerKitsConfigName();
		kitManager = new KitManagerUse(playerKitsConfigName);
	}

	@Override
	public void onStopFeature() {
		// TODO Auto-generated method stub
	}

	//----------------------------- Listeners --------------------------------

	
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event) {
		if ((event.getAction() == Action.LEFT_CLICK_AIR) ||
			(event.getAction() == Action.LEFT_CLICK_BLOCK) ||
			(event.getAction() == Action.RIGHT_CLICK_AIR) ||
			(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		) {
//			Bukkit.getLogger().info("HungerGames onPlayerClick FOUND CLICK event: name="+event.getEventName()
//			+" hand="+event.getHand()+" player="+event.getPlayer()+" class="+event.getClass()+" action"+event.getAction());
			KitUse kit = kitManager.getPlayerKit(event.getPlayer().getName());
			kit.handleEvent(event);
		}
		
	} // onPlayerClick


	
}
