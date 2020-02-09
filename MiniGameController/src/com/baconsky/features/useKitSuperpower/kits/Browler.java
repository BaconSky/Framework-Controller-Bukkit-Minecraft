package com.baconsky.features.useKitSuperpower.kits;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerInteractEvent ;
import org.bukkit.plugin.Plugin;

import com.baconsky.features.useKitSuperpower.KitUse;
import com.baconsky.hungergames.main.HungerGamesPhase;

public class Browler implements KitUse {

	private Plugin plugin;
	
	public Browler(Plugin plugin) {
		this.plugin = plugin;
	}



	@Override
	public void handleEvent(PlayerInteractEvent  event) {
//		Bukkit.getLogger().info("Browler handleEvent");
		
		Bukkit.getLogger().info("Browler handleEvent event: name="+event.getEventName()
		+" hand="+event.getHand()+" player="+event.getPlayer()+" class="+event.getClass()+" action"+event.getAction());

	}

	@Override
	public String toString() {
		return "Browler Class";
	}

}
