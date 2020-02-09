package com.baconsky.features.selectKitByMob;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
//import org.bukkit.craftbukkit.v1_9_R2.entity.CraftZombie;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.baconsky.lobby.main.MiniGameLobbyPhase;
import com.baconsky.minigame.main.MiniGameController;

public class MobThread extends BukkitRunnable {

	private World world;

	private boolean mobsSpawned = false;
	private MiniGameController miniGameController;
	private Map<String, KitSelect> allKits;

	LivingEntity mob1, mob2, mob3, mob4;
	Location loc1, loc2, loc3, loc4;

	public MobThread(MiniGameController miniGameController2, Map<String, KitSelect> allKits) {
		Bukkit.getLogger().info("MobThread ctor pos1");	
		this.miniGameController = miniGameController2;
		this.allKits = allKits;
	} // ctor

	@Override
	public void run() {

		if (!mobsSpawned) {
			mobsSpawned = true;
			world = miniGameController.getLobby().getLobbyWorld();
			for(Entity e : world.getEntities()) {
				if (e.getType() == EntityType.ZOMBIE) e.remove();
			}
			Location mobLoc = null;
			LivingEntity mob = null;
			for (Map.Entry<String, KitSelect> entry : allKits.entrySet()) {
				KitSelect kit = entry.getValue();
				KitMetadataSelect kitMetadata = kit.getKitMetadata(); 

				MobPosition position = kitMetadata.getMobPosition();
//Bukkit.getLogger().info("+++++**** MobThread position="+position);
				if (position == null) continue;

				mobLoc = position.getPos(world); 
				float yaw = position.getYew();
				mobLoc.setYaw(yaw);
				
				EntityType mobType = kitMetadata.getMobType();
				ItemStack helmet = kitMetadata.getMobHelmet();
				ItemStack chestplate = kitMetadata.getMobChestplate();
				ItemStack leggings = kitMetadata.getMobLeggings();
				ItemStack boots = kitMetadata.getMobBoots();
				ItemStack leftArm = kitMetadata.getMobLeftHand();
				ItemStack rightArm = kitMetadata.getMobRightHand();

				mob = (LivingEntity) world.spawnEntity(mobLoc, mobType);
				mob.setAI(false);
				mob.setCollidable(false);
				mob.setRemoveWhenFarAway(false);
//				mob.setInvulnerable(true);
				
				mob.getEquipment().setHelmet(helmet);
				mob.getEquipment().setChestplate(chestplate);
				mob.getEquipment().setLeggings(leggings);
				mob.getEquipment().setBoots(boots);
				mob.getEquipment().setItemInMainHand(rightArm);
				mob.getEquipment().setItemInOffHand(leftArm);
//Bukkit.getLogger().info("MobThread setCustomName="+kitMetadata.getSuperpower());

				mob.setCustomName(kitMetadata.getSuperpower());
				mob.setCustomNameVisible(true);
			} // for
		} // not mobsSpawned
	} // run
	
}
