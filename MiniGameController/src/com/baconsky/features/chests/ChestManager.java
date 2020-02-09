package com.baconsky.features.chests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Chest;

public class ChestManager {
	
	private BlockInventoryConfig blockInventoryConfig;
	private int minNumItems, maxNumItems, numAllItems;
	private List<Integer> probability;
	private List<ChestItemProperties> itemProperties;
	private List<Location> allChests;
    private Random randomGenerator;
	private String mobName;
	private EntityType mobType;
	private World world;

//--------------------------------- ctor ----------------------------
	
	public ChestManager(String inventoryConfigPath, String inventoryConfigName, World world) {
		blockInventoryConfig = new BlockInventoryConfig(inventoryConfigPath, inventoryConfigName);
		probability = new ArrayList<Integer>();
		itemProperties = new ArrayList<ChestItemProperties>();
		allChests = new ArrayList<Location>();
		this.world = world;
		mobName = getBlockInventoryConfig().getMobName();
		mobType = getBlockInventoryConfig().getMobType();
		
		long now = System.nanoTime();
		randomGenerator = new Random(now);
		numAllItems = blockInventoryConfig.getNumItems();
		minNumItems = blockInventoryConfig.getMinNumItems();
		maxNumItems = blockInventoryConfig.getMaxNumItems();
		for (int i=0; i<numAllItems; i++) {
			ChestItemProperties item = blockInventoryConfig.getItem(i);
			itemProperties.add(item);
			for (int j=0; j<item.getProbability(); j++) probability.add(i);
		}
	}

//------------------------------------- create ----------------------
	
	public List<ItemStack> createChest() {
		int numItemsInChest = getRandomItemNumber(minNumItems, maxNumItems);
		List<ItemStack> result = new ArrayList<ItemStack>();
		for (int i=0; i<numItemsInChest; i++) {
			int itemIdx = getRandomItem();
			String itemName = itemProperties.get(itemIdx).getName();
			int minCnt = itemProperties.get(itemIdx).getMinNum();
			int maxCnt = itemProperties.get(itemIdx).getMaxNum();
			int cnt = getRandomItemNumber(minCnt, maxCnt);
			Material mat = Material.valueOf(itemName);
			ItemStack itemStack = new ItemStack(mat, cnt);
			result.add(itemStack);
		}
		return result;
	}
	
//--------------------------- place and fill the chests --------------------
	
	private BlockFace getMobOrientation(CraftEntity mob) {
		float mobRotation = mob.getHandle().getHeadRotation();
		if ((mobRotation >= - 135.0) && (mobRotation <= -45.0)) return BlockFace.EAST;
		else if ((mobRotation >= - 45.0) && (mobRotation <= 45.0)) return BlockFace.SOUTH;
		else if ((mobRotation >=  45.0) && (mobRotation <= 135.0)) return BlockFace.WEST;
		else return BlockFace.NORTH;
	}
	
	private List<Entity> getAllZombiesWithName() {
		List<Entity> importantMobList = new ArrayList<Entity>();
		for (Entity mob : world.getEntities()) {
			if ((mob.getType() == mobType) && (mob.getCustomName() != null)) {
				if (mob.getCustomName().equalsIgnoreCase(mobName)) {
				importantMobList.add(mob);
				}
			}
		}
		return importantMobList;
	}
	
	public void setChests() {
		List<Entity> importantMobList = getAllZombiesWithName();
		for (Entity mob : importantMobList) {
			// Place the empty chest at the location and orietation of the mob
			Location blockLocation = mob.getLocation().getBlock().getLocation();
Bukkit.getLogger().info("ChestManager setChest place empty chest at location:"+blockLocation);			
			allChests.add(blockLocation);
			Block chestBlock = blockLocation.getBlock();	
			chestBlock.setType(Material.CHEST);
			BlockState state = chestBlock.getState();
			Chest chest = new Chest(getMobOrientation((CraftEntity) mob));
			state.setData(chest);
			state.update();
			// Fill the chest
		}	
	} // setChest
	
	public void fillChest() {
		for (Location chestLocation : allChests) {
			if (!(chestLocation.getBlock().getType() == Material.CHEST)) continue;
			List<ItemStack> itemsForNewChest = createChest();			
Bukkit.getLogger().info("ChestManager fillChest itemsForNewChest="+itemsForNewChest);
			org.bukkit.block.Chest curChest = (org.bukkit.block.Chest) chestLocation.getBlock().getState();
			for (ItemStack itemStack : itemsForNewChest) {
				boolean freeSpace = false;
				ItemStack[] chestContent = curChest.getInventory().getContents();
				for (int i=0; i<27; i++) {
					if (chestContent[i] == null) { freeSpace = true; break; }
				}
				if (!freeSpace) break;
				int cnt=0; 
				int slot = -1;
				while (freeSpace && cnt<1000) {
					cnt++;
					// find free slot
					slot = getRandomItemNumber(0, 26);
					if (chestContent[slot] != null) continue;
					curChest.getInventory().setItem(slot, itemStack);
					break;
				} // while
				if (slot < 0) Bukkit.getLogger().severe("ChestSystem internal error 835542 : fill chest");
			} // for itemStack
		} // for allChests
	} // fillChest

	
	
	
//-------------------------------- random ---------------------------
	
	public int getRandomItemNumber(int min, int max) {
		int randomInt = randomGenerator.nextInt(max - min + 1);
		return min + randomInt;
	}
	
	public int getRandomItem() {
		int idx = randomGenerator.nextInt(probability.size());
		return probability.get(idx);
	}

//----------------------------------- getter & setter ---------------

	public BlockInventoryConfig getBlockInventoryConfig() {
		return blockInventoryConfig;
	}	
}
