package com.baconsky.features.chests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

public class BlockInventoryConfig {

	private List<ChestItemProperties> chestItemProperties = new ArrayList<ChestItemProperties>();
	private int minNumItems;
	private int maxNumItems;
	private String mobName;
	private EntityType mobType;
	

	public BlockInventoryConfig(String inventoryConfigPath, String inventoryConfigName) {

		// check if folder inventoryConfigPath exists - otherwise create it
		File dir = new File(inventoryConfigPath);
		if (!dir.exists()) {
			try {
				dir.getParentFile().mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // ! configDir exists

		File ymlFile = new File(inventoryConfigPath, inventoryConfigName);
		if (!ymlFile.exists()) {
			try {
				ymlFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// load the yml file and read data
		FileConfiguration inventoryConfig = YamlConfiguration.loadConfiguration(ymlFile);
		ConfigurationSection chests = inventoryConfig.getConfigurationSection("chests");

		minNumItems = chests.getInt("min_num_items");
		maxNumItems = chests.getInt("max_num_items");
		mobName = chests.getString("mob_name");
		String typeStr = chests.getString("mob_type");
		mobType = EntityType.valueOf(typeStr);
		ConfigurationSection items = inventoryConfig.getConfigurationSection("chests.items");

		for (String itemKey : items.getKeys(false)) {
			ConfigurationSection itemConfig = inventoryConfig.getConfigurationSection("chests.items."+itemKey);
			String name = itemConfig.getString("name");
			int probability = itemConfig.getInt("probability");
			int min_num = itemConfig.getInt("min_num");
			int max_num = itemConfig.getInt("max_num");
			ChestItemProperties properties = new ChestItemProperties(name, probability, min_num, max_num);
			chestItemProperties.add(properties);
		}
	}
	
	
	//------------------------------ settings ------------------------------
	
	public int getMinNumItems() {
		return minNumItems;
	}

	public int getMaxNumItems() {
		return maxNumItems;
	}


	public List<ChestItemProperties> getAllItems() {
		return chestItemProperties;
	}

	public ChestItemProperties getItem(int i) {
		return chestItemProperties.get(i);
	}

	
	public int getNumItems() {
		return chestItemProperties.size();
	}


	public String getMobName() {
		return mobName;
	}


	public EntityType getMobType() {
		return mobType;
	}
	
	
	
}
/*

chests:
  min_num_items: 3
  max_num_items: 5
  mob_name: "Pass"
  mob_type: "ZOMBIE"
  items:
    item1:
      name: "DIAMOND_SWORD"
      probability: 3
      min_num: 1
      max_num: 1
    item2:
      name: "STONE_AXE"
      probability: 5
      min_num: 1
      max_num: 2
    item3:
      name: "WOOD_AXE"
      probability: 6
      min_num: 1
      max_num: 5
    item4:
      name: "DIAMOND_AXE"
      probability: 3
      min_num: 2
      max_num: 7
    item5:
      name: "STICK"
      probability: 5
      min_num: 6
      max_num: 9
    item6:
      name: "FLINT"
      probability: 6
      min_num: 2
      max_num: 5
 */
