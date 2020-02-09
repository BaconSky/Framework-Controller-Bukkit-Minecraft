package com.baconsky.lobby.main;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.baconsky.core.CorePhase;
import com.baconsky.features.lobbyScoreboard.LobbyScoreboard;
import com.baconsky.features.lobbyScoreboard.LobbyScoreboardFeature;
import com.baconsky.features.selectKitByMob.KitManagerSelect;
import com.baconsky.features.selectKitByMob.MobPositionList;
import com.baconsky.features.twoPhaseCountdown.LifeCycle;
import com.baconsky.lobby.config.LobbyConfig;
import com.baconsky.lobby.config.MiniGameConfig;
import com.baconsky.minigame.config.MiniGameControllerConfig;
import com.baconsky.minigame.interfaces.Feature;
import com.baconsky.minigame.interfaces.MiniGameScoreboard;
import com.baconsky.minigame.interfaces.Phase;
import com.baconsky.minigame.interfaces.ScoreboardCommand;
import com.baconsky.minigame.main.MiniGameController;


public class MiniGameLobbyPhase extends CorePhase {

	private MiniGameController miniGameController;
	
	private MiniGameControllerConfig miniGameControllerConfig;
	private LobbyConfig lobbyConfig;

	private World arena;
	private World lobby;

	private MiniGameScoreboard theScoreboard = null;

	
// ------------------------------ ctor --------------------------------
	
	public MiniGameLobbyPhase(MiniGameController miniGameController) {
		this.miniGameController = miniGameController;
		this.miniGameControllerConfig = miniGameController.getMiniGameControllerConfig();

		// select current arena
		String curArena =  miniGameController.getMiniGameConfig().selectArena();
		miniGameControllerConfig.setCurrentArena(curArena);
	} // ctor
	


	// ----------------------------------- Phase -----------------------------
	
	
	@Override
	public String getName() {
		return "Lobby Phase";
	}


	@Override
	public void beforeStartPhase() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onStartPhase() {
		super.onStartPhase();
		
		Bukkit.getLogger().info("MiniGameLobbyPhase startPhase");
		createLobbyWorld();
		removeArenaWorld();
	} // startPhase
	
	
	@Override
	public void onStopPhase() {
		super.onStopPhase();
//		Bukkit.getLogger().info("MiniGameLobbyPhase onStopPhase pos11 after returning from super.onStopPhase");
		
		String pathToGameConfig = miniGameController.getMiniGameControllerConfig().getGameConfigPath();
		MiniGameConfig miniGameConfig = miniGameController.getMiniGameConfig();
		String sourcePath = "." + File.separator + "plugins" + File.separator + pathToGameConfig + File.separator + "arenas" + File.separator + miniGameConfig.getWorld();

		// copy arena wolrd at its final place
Bukkit.getLogger().info("MiniGameLobbyPhase onStopPhase: create arena world sourcePath="+sourcePath);	
		String destinationPath = "Arena";
		String lobbyPath = "Lobby";
		try {
			File f = new File(destinationPath);
			if (f.exists()) {
				FileUtils.deleteDirectory(new File(destinationPath));
			}
			FileUtils.copyDirectory(new File(sourcePath), new File(destinationPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		// create arena world and teleport all player to their spawn location
		arena = miniGameController.getServer().createWorld(new WorldCreator(destinationPath));
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
//Bukkit.getLogger().info("MiniGameLobbyPhase onStopPhase: going to teleport player - ToDo %%%& take the coordinates from a config file: "+player.getName());		
			player.teleport(new Location(arena, 0, 7, 0));
		}
//Bukkit.getLogger().info("MiniGameLobbyPhase onStopPhase: finished teleporting players ");		

		// unload all other worlds
		for(World w: Bukkit.getServer().getWorlds()){
			if(!w.getName().equals(destinationPath)) {
//Bukkit.getLogger().info("MiniGameLobbyPhase onStopPhase: unload world "+w.getName());				
				Bukkit.getServer().unloadWorld(w, true);
			}
		} // for

		
		// delete lobby folder
		try {
			File f = new File(lobbyPath);
			if (f.exists()) {
				FileUtils.deleteDirectory(new File(lobbyPath));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//Bukkit.getLogger().info("MiniGameLobbyPhase onStopPhase pos99: finished => returning");		
	} // onStopPhase


	
	

	@Override
	public MiniGameController getGame() {
		return miniGameController;
	}

	
	//------------------------------- Utilities -----------------------------
	
	
	private void createLobbyWorld(){
//		String sourcePath = "." + File.separator + "worlds_repo" + File.separator + "lobby_1";
		String pluginDir = miniGameController.getDataFolder().getParentFile().getName();
		
		String pathToLobbyConfig = miniGameControllerConfig.getLobbyConfigPath();
		String lobbyConfigName = miniGameControllerConfig.getLobbyConfigName();
		miniGameControllerConfig.getLobbyConfigName();
		

		lobbyConfig = new LobbyConfig(pluginDir + File.separator + pathToLobbyConfig, lobbyConfigName);
		String sourcePath = "." + File.separator + "plugins" + File.separator + pathToLobbyConfig + File.separator + "world" + File.separator + lobbyConfig.getWorld();

		String destinationPath = "Lobby";
		try {
			File f = new File(destinationPath);
			if (f.exists()) {
				FileUtils.deleteDirectory(new File(destinationPath));
			}
Bukkit.getLogger().info("Minigamecontroller createLobbyWorld going to copy directory sourcePath="+sourcePath+" destinationPath="+destinationPath);
			FileUtils.copyDirectory(new File(sourcePath), new File(destinationPath));
Bukkit.getLogger().info("Minigamecontroller createLobbyWorld calling createWorld");
			lobby = miniGameController.getServer().createWorld(new WorldCreator(destinationPath));
Bukkit.getLogger().info("Minigamecontroller createLobbyWorld created World lobby="+lobby);
		} catch (IOException e) {
			Bukkit.getLogger().severe(" Minigamecontroller createLobbyWorld: Cannot delete Lobby world. Ignore ...");
			lobby = miniGameController.getServer().createWorld(new WorldCreator(destinationPath));
		}

/*		
		for (OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
Bukkit.getLogger().info("++++++******* Minigamecontroller offlinePlayer"+player.getName());
		}
*/
		
		// create lobby world and teleport all player to their spawn location
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			// %%%& take the coordinates from a config file
			boolean b = player.teleport(new Location(lobby, 0, 7, 0));
Bukkit.getLogger().info("++++++******* Minigamecontroller teleport onlinePlayer: "+player.getName()+" return="+b);
		}

		// unload all other worlds
		for(World w: Bukkit.getServer().getWorlds()){
            for (Chunk c : w.getLoadedChunks()) {
                c.unload(true);
            }

 //Bukkit.getLogger().info("Minigamecontroller createLobbyWorld found world "+w.getName());				
			if(!w.getName().equals(destinationPath)) {
				Bukkit.getServer().unloadWorld(w, true);
//Bukkit.getLogger().info("Minigamecontroller createLobbyWorld unload world "+w.getName());				
			}
		} // for

	} // createLobbyWorld

	
	private void removeArenaWorld() {
		String arenaPath = "Arena";
		try {
			File f = new File(arenaPath);
			if (f.exists()) {
				FileUtils.deleteDirectory(new File(arenaPath));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // removeArenaWorld
	
	
	//----------------------------- Listeners --------------------------
	
	
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Bukkit.getLogger().info("MiniGameLobbyPhase PlayerJoinEvent");

		// %%%& take the coordinates from a config file
		event.getPlayer().teleport(new Location(getLobbyWorld(), 5, 5, 5));
		return;
	}



	
	
	
	//-------------------------- Getter&Setter ----------------------	

	public World getLobbyWorld() {
		return lobby;
	}


	public String getMapName() {
		return "Temp%%%&Map";
	}
	
	public String getKitName(Player player) {
		return player.getName() + " - Kit";
	}

	@Override
	public String toString() {
		return "MiniGameLobbyPhase";
	}

	@Override
	public void setScoreboard(MiniGameScoreboard scoreboard) {
		theScoreboard = scoreboard;
	}


	@Override
	public void updateScoreboard(Player player, ScoreboardCommand command, List<String> args) {
		if (theScoreboard == null) return;
		theScoreboard.updateScoreboard(player, command, args);
	}


} // MiniGameLobbyPhase
