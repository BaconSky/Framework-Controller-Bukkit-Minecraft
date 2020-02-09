package com.baconsky.features.selectKitByMob;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.baconsky.core.CoreFeature;
import com.baconsky.core.TheMiniGameController;
import com.baconsky.features.lobbyScoreboard.LobbyScoreboard;
import com.baconsky.features.twoPhaseCountdown.LifeCycle;
import com.baconsky.lobby.main.MiniGameLobbyPhase;
import com.baconsky.minigame.config.MiniGameControllerConfig;
import com.baconsky.minigame.interfaces.FeatureType;
import com.baconsky.minigame.interfaces.Phase;
import com.baconsky.minigame.main.MiniGameController;

public class SelectKitByMobFeature extends CoreFeature {
	private KitManagerSelect kitManager;
//	private Phase phase;

	public SelectKitByMobFeature(Phase phase) {
		super(phase);
//		this.phase = phase;
	}

	@Override
	public FeatureType getFeatureType() {
		return FeatureType.SELECT_KIT;
	}

	@Override
	public void onStartFeature() {
		// Kit System
		MiniGameController miniGameController = TheMiniGameController.getTheMiniGameController().getInstance();

		String pluginDir = miniGameController.getDataFolder().getParentFile().getName();
		String pathToKitsConfig = miniGameController.getMiniGameControllerConfig().getKitsConfigPath();
		String pathToLobbyConfig = miniGameController.getMiniGameControllerConfig().getLobbyConfigPath();
		String lobbyConfigName = miniGameController.getMiniGameControllerConfig().getLobbyConfigName();
		kitManager = new KitManagerSelect(pluginDir + File.separator + pathToKitsConfig, miniGameController);
		MiniGameControllerConfig miniGameControllerConfig = new MiniGameControllerConfig("minigame.yml", miniGameController);
		MobPositionList mobPositionList = new MobPositionList(pluginDir + File.separator + pathToLobbyConfig, lobbyConfigName, miniGameController);
		kitManager.assignMobPositions(mobPositionList);

		
		int numPlayers = Bukkit.getServer().getOnlinePlayers().size();
		if (numPlayers > 0) {
Bukkit.getLogger().info("#######++++++++++ MiniGameLobby onEnable numPlayers="+numPlayers+" calling placeMobs");	
		kitManager.placeMobs(true);
		}
	}

	@Override
	public void onStopFeature() {
		// TODO Auto-generated method stub
	}

	//----------------------------- Listeners --------------------------------
	
	@EventHandler
	public void onEntityCombust(EntityCombustEvent event) {
		 event.setCancelled(true);
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		// %%%& ToDo cancel event only if the damaged entity is a zombie
		event.setCancelled(true);
	}

	@EventHandler
	public void onMobLeftClick(EntityDamageByEntityEvent e) {
//Bukkit.getLogger().info("EntityDamageByEntityEvent pos1");
		if(!(e.getEntity() instanceof LivingEntity)) return;
		LivingEntity entity = (LivingEntity) e.getEntity();
//		if (!(entity.getType() == EntityType.ZOMBIE)) return;
		
//Bukkit.getLogger().info("damaged zombie");
		
		if(e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
//Bukkit.getLogger().info("zombie damaged by player - in hand = "+player.getItemInHand());
			String mobName = entity.getCustomName();
			if (kitManager.isKit(mobName)) {
				KitSelect selectedKit = kitManager.getKit(mobName);
				kitManager.setKit(player, selectedKit);
				
				
Bukkit.getLogger().info("MobDamageByEntity Player "+player.getName()+" selected Kit:"+mobName);
				kitManager.savePlayerKits();
			}
		}
	} // onMobLeftClick

	
	
	@EventHandler
	public void mobRightClick(PlayerInteractEntityEvent event) {
        if((event.getRightClicked() instanceof LivingEntity) && (event.getHand() == EquipmentSlot.HAND)) {
        	LivingEntity entity = (LivingEntity) event.getRightClicked();
			String superpower = entity.getCustomName();
        	KitSelect kit = kitManager.getKit(superpower);
        	if (kit == null) return;
			Player player = event.getPlayer();
			String title = kit.getKitMetadata().getDescriptionTitle();
			player.sendMessage(title);
			List<String> description = kit.getKitMetadata().getDescription();
			for (String desc : description) {
				player.sendMessage(desc);
			} // for
        } // if
	} // mobRightClick

	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
Bukkit.getLogger().info("SelectKitFeature PlayerJoinEvent");
		event.getPlayer().setCollidable(false);
		kitManager.placeMobs(false);
	}
	
	
} // SelectKitFeature
