package com.baconsky.features.spectatorTeams;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import com.baconsky.core.CoreFeature;
import com.baconsky.core.GamePhase;
import com.baconsky.core.TheMiniGameController;
import com.baconsky.features.selectKitByMob.KitManagerSelect;
import com.baconsky.features.selectKitByMob.MobPositionList;
import com.baconsky.minigame.config.MiniGameControllerConfig;
import com.baconsky.minigame.interfaces.FeatureType;
import com.baconsky.minigame.main.MiniGameController;

public class SpectatorTeamFeature  extends CoreFeature {

	private SpectatorTeamManager teamManager;
	private GamePhase gamePhase;
	
	public SpectatorTeamFeature(GamePhase gamePhase) {
		super(gamePhase);
		this.gamePhase = gamePhase;
	}

	@Override
	public FeatureType getFeatureType() {
		return FeatureType.SPECTATOR_TEAM;
	}

	@Override
	public void onStartFeature() {
//		MiniGameController miniGameController = TheMiniGameController.getTheMiniGameController().getInstance();
		teamManager = new SpectatorTeamManager(gamePhase);
		teamManager.initialPlayerTeamAssignment();
		if (teamManager.getNumberTeams() < 1) return;
		Bukkit.getLogger().info("SpectatorTeamFeature onStartFeature: after initialPlayerTeamAssignment teamManager="+teamManager);
	} // onStartFeature

	@Override
	public void onStopFeature() {
		// TODO Auto-generated method stub
	}

	//----------------------------- Listeners --------------------------------
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player)) return; // the projectile did not hit a player but something else
		Player player = (Player) event.getEntity(); 
		
		// if the damage will kill the player: we put him into the spectator team 
		// avoiding the necessity to respawn after playerDeathEvent
		if (player.getHealth() - event.getDamage() < 1) {
			if (teamManager.getNumberTeams() < 1) return;
Bukkit.getLogger().info("onEntityDamage pos3 teamManager="+teamManager);
Bukkit.getLogger().info("onEntityDamage: pos4 player died player="+player.getName()+" teamManager="+teamManager);
			player.sendMessage("You DIED!");
			teamManager.getTeam(player).removePlayer(player);
Bukkit.getLogger().info("onEntityDamage: pos5");
			teamManager.getSpectatorTeam().addPlayer(player);
			event.setCancelled(true);
			gamePhase.decrementPlayersAlive();
			gamePhase.checkGameState();

			return;
		}
		
		if (teamManager.getTeam(player) != null) return; // player is not a spectator
		if (!(event.getDamager() instanceof Projectile)) return;    
		Projectile projectile = (Projectile) event.getDamager();
		event.setCancelled(true);
			
			
		Vector velocity = projectile.getVelocity();
		double speed = velocity.distance(new Vector(0,0,0));
		Vector normalizedVelocity = velocity.normalize();
		Location oldLocation = projectile.getLocation();
		Location newLocation = oldLocation.add(normalizedVelocity);
	
		if (projectile instanceof Arrow) {
			projectile.getWorld().spawnArrow(newLocation, velocity, (float) speed, 1.0F);
			
		} else {
	        Entity newProjectile = projectile.getWorld().spawnEntity(newLocation, projectile.getType());
	        newProjectile.setVelocity(velocity);
		}
		
		projectile.remove();
Bukkit.getLogger().info("onEntityDamage: pos99");
		
	} // onEntityDamage


	

	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		gamePhase.checkGameState();
	} // onPlayerQuit

	
	
	
}
