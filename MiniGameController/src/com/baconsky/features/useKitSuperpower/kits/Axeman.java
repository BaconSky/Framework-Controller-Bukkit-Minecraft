package com.baconsky.features.useKitSuperpower.kits;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.baconsky.features.useKitSuperpower.KitUse;
import com.baconsky.hungergames.main.HungerGamesPhase;

public class Axeman implements KitUse {

	private Plugin plugin;
	
	public Axeman(Plugin plugin) {
		this.plugin = plugin;
	}

	
	@Override
	public void handleEvent(PlayerInteractEvent event) {
//		Bukkit.getLogger().info("Axeman handleEvent");
//		Bukkit.getLogger().info("Axeman handleEvent event: name="+event.getEventName()
//		+" hand="+event.getHand()+" player="+event.getPlayer()+" class="+event.getClass()+" action"+event.getAction());
		Bukkit.getLogger().info("Axeman handleEvent inHand: "+event.getPlayer().getInventory().getItemInMainHand().getType());
		Player player = event.getPlayer();
		Location playerLoc = player.getEyeLocation();
		ItemStack handItem = player.getInventory().getItemInMainHand();

		Set<Material> axes = new HashSet<Material>(); 
		axes.add(Material.DIAMOND_AXE);
		axes.add(Material.IRON_AXE);
		axes.add(Material.STONE_AXE);
		axes.add(Material.GOLD_AXE);
		axes.add(Material.WOOD_AXE);

		if ((event.getAction() != Action.RIGHT_CLICK_AIR) && (event.getAction() != Action.RIGHT_CLICK_BLOCK)) return;
		if (!(axes.contains(handItem.getType()))) return;


//		Item drop = player.getWorld().dropItem(playerLoc, new ItemStack(handItem.getType(), 1));
		Item drop = player.getWorld().dropItem(playerLoc, handItem);
		Vector currentVelocity = playerLoc.getDirection();
		drop.setVelocity(currentVelocity.multiply(1.5D));
		player.getInventory().setItemInMainHand(new ItemStack(Material.AIR, 1));
		
		
/*		
        new BukkitRunnable() {
        	 
            @Override
            public void run() {
          
	            for (int i=0; i<300; i++) {
	            	Bukkit.getLogger().info("Axeman run i="+i);
	            	List<Entity> nearbyEntities = drop.getNearbyEntities(1, 1, 1);
	            	if (nearbyEntities.size() > 0) {
	            		Bukkit.getLogger().info("found entities="+nearbyEntities);
	            	}
	            	try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
            	
            }                    
        }.runTaskLater(plugin, 50L);
*/
		
		
		
	}
	
	@Override
	public String toString() {
		return "Axeman Class";
	}
	
}
