package com.baconsky.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import com.baconsky.minigame.interfaces.Feature;
import com.baconsky.minigame.interfaces.FeatureType;
import com.baconsky.minigame.interfaces.Phase;
import com.baconsky.minigame.main.MiniGameController;

public abstract class CorePhase implements Phase {

	protected MiniGameController game;
	protected Phase	next;
	protected List<Feature>	features = new ArrayList<>();

	public CorePhase(final MiniGameController game, final Phase next) {
		this();
		Bukkit.getLogger().info("CorePhase ctor pos1 next:"+next);
		
		this.game = game;
		this.next = next;
	}

	public CorePhase() {
//		addFeature(new LeaveHandlerFeature(this));
//		addFeature(new JoinHandlerFeature(this));
	}

	@Override
	public void onStartPhase() {
Bukkit.getLogger().info("CorePhase.onStart pos1 this="+this+" next="+next);		

/*		
		if (!checkCombability()) {
			Core.getCore().getInstance().debug("Incombatibility found!");
			return;
		}
*/		

		for (final Feature f : features) {
	Bukkit.getLogger().info("CorePhase init Feature :"+f);

			f.beforeStartFeature(this);
		}

		
		Bukkit.getServer().getPluginManager().registerEvents(this, getGame());
//		Core.getCore().getCommandHandler().registerCommands(this);

		for (final Feature f : features) {
			Bukkit.getServer().getPluginManager().registerEvents(f, getGame());
//			Core.getCore().getCommandHandler().registerCommands(f);
			f.onStartFeature();
		}
//Bukkit.getLogger().info("+++++++++++++++++++++++CorePhase.onStartPhase pos99. returning");		
	} // onStartPhase


	
	
	@Override
	public void onStopPhase() {
Bukkit.getLogger().info("CorePhase.onStopPhase pos1 this="+this+" next="+next);		
		
/*		
		for (final Task t : Core.getCore().getTaskHandler().getTaskByPhase(this)) {
			Core.getCore().getTaskHandler().cancel(t);
		}
*/



		HandlerList.unregisterAll(this);
//		Core.getCore().getCommandHandler().unregisterCommands(this);
		
		if (getNextPhase() != null) {
			getNextPhase().beforeStartPhase();
		}

		for (final Feature f : features) {
			HandlerList.unregisterAll(f);
//			Core.getCore().getCommandHandler().unregisterCommands(f);
			f.onStopFeature();
		}

		getGame().setCurrentPhase(getNextPhase());
		if (getNextPhase() != null) {
			getNextPhase().onStartPhase();
		}

		try {
			finalize();
		}
		catch (final Throwable e) {
			e.printStackTrace();
		}
//	Bukkit.getLogger().info("+++++++++++++++++++++++CorePhase.onStopPhase pos99. returning");		
	} // onStopPhase

	
	
	@Override
	public MiniGameController getGame() {
		return game;
	}

	@Override
	public List<Feature> getFeatures() {
		return features;
	}
	
	@Override
	public Feature getFeature(final FeatureType feature) {
		for (final Feature f : features) {
			if (f.getFeatureType().equals(feature)) {
				return f;
			}
		}
		return null;
	}


	
	@Override
	public void setNextPhase(final Phase next) {
		this.next = next;
	}


	
	@Override
	public Phase getNextPhase() {
		return next;
	}


	

	@Override
	public void addFeature(final Feature f) {
		if (getFeature(f.getFeatureType()) == null) {
			features.add(f);
		}
	}

	@Override
	public void removeFeature(final FeatureType t) {
		if (getFeature(t) != null) {
			features.remove(getFeature(t));
		}
	}


	
	
}
