package com.baconsky.features.selectKitByMob;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class KitMetadataSelect {
	





	private ItemStack displayItem;
	private String superpower; // Name of superpower (like browler)
	private String descriptionTitle; // for example: title in entity meta
	private List<String> description; // goes into - > lore and goes into chat when click on mob
	
	// apearence of mob
	private EntityType mobType;
	private ItemStack mobHelmet;
	private ItemStack mobChestplate;
	private ItemStack mobLeggings;
	private ItemStack mobBoots;
	private ItemStack mobLeftHand;
	private ItemStack mobRightHand;
	private String mobPositionName;
	private MobPosition mobPosition;

	
// ------------------- ctor ---------------------------
	
	KitMetadataSelect(String superpower, ItemStack displayItem, String descriptionTitle, List<String> description, String position,
			EntityType mobType, ItemStack mobHelmet, ItemStack mobChestplate, ItemStack mobLeggings, ItemStack mobBoots,
			ItemStack mobLeftHand, ItemStack mobRightHand) 
	{		
		this.superpower = superpower;
		
		this.displayItem = displayItem;
		this.descriptionTitle = descriptionTitle;
		this.description = description;
		this.mobPositionName = position;

		this.mobType = mobType;
		this.mobHelmet = mobHelmet;
		this.mobChestplate = mobChestplate;
		this.mobLeggings = mobLeggings;
		this.mobBoots = mobBoots;
		this.mobLeftHand = mobLeftHand;
		this.mobRightHand = mobRightHand;
		
	}


	
	public KitMetadataSelect(FileConfiguration kitConfig) {
		Material mat;
		this.superpower = kitConfig.getString("superpower");
		mat = Material.valueOf(kitConfig.getString("displayitem"));
		this.displayItem = new ItemStack(mat, 1);
		this.descriptionTitle = kitConfig.getString("description_title");
		this.description = kitConfig.getStringList("description");
		this.mobPositionName = kitConfig.getString("position");

		// mob configuration
		ConfigurationSection mobConfig = kitConfig.getConfigurationSection("mob");
		this.mobType = EntityType.valueOf(mobConfig.getString("mob_type"));
		mat = Material.valueOf(mobConfig.getString("helmet"));
		this.mobHelmet = new ItemStack(mat, 1);
		mat = Material.valueOf(mobConfig.getString("chestplate"));
		this.mobChestplate = new ItemStack(mat, 1);
		mat = Material.valueOf(mobConfig.getString("leggins"));
		this.mobLeggings = new ItemStack(mat, 1);
		mat = Material.valueOf(mobConfig.getString("boots"));
		this.mobBoots = new ItemStack(mat, 1);
		mat = Material.valueOf(mobConfig.getString("left_hand"));
		this.mobLeftHand = new ItemStack(mat, 1);
		mat = Material.valueOf(mobConfig.getString("right_hand"));
		this.mobRightHand = new ItemStack(mat, 1);
	}


	//------------------------------------- getter & setter ----------------------



	public ItemStack getDisplayItem() {
		return displayItem;
	}



	public String getSuperpower() {
		return superpower;
	}



	public String getDescriptionTitle() {
		return descriptionTitle;
	}



	public List<String> getDescription() {
		return description;
	}


	public String getMobPositionName() {
		return mobPositionName;
	}



	public void setMobPositionName(String mobPositionName) {
		this.mobPositionName = mobPositionName;
	}



	public MobPosition getMobPosition() {
		return mobPosition;
	}



	public void setMobPosition(MobPosition mobPosition) {
		this.mobPosition = mobPosition;
	}



	public EntityType getMobType() {
		return mobType;
	}

	public ItemStack getMobHelmet() {
		return mobHelmet;
	}



	public ItemStack getMobChestplate() {
		return mobChestplate;
	}


	public ItemStack getMobLeggings() {
		return mobLeggings;
	}



	public ItemStack getMobBoots() {
		return mobBoots;
	}



	public ItemStack getMobLeftHand() {
		return mobLeftHand;
	}



	public ItemStack getMobRightHand() {
		return mobRightHand;
	}


//-------------------------------------------- toString -----------------------------
	
	
	@Override
	public String toString() {
		return "KitMetadata [displayItem=" + displayItem + ", superpower=" + superpower + ", descriptionTitle="
				+ descriptionTitle + ", description=" + description + ", mobHelmet=" + mobHelmet + ", mobChestplate="
				+ mobChestplate + ", mobLeggings=" + mobLeggings + ", mobBoots=" + mobBoots + ", mobLeftHand="
				+ mobLeftHand + ", mobRightHand=" + mobRightHand + ", mobPositionName=" + mobPositionName
				+ ", mobPosition=" + mobPosition + "]";
	}

	
	
	
}
