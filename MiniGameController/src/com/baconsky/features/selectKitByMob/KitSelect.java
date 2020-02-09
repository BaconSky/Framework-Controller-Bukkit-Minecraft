package com.baconsky.features.selectKitByMob;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;

public class KitSelect {
	private final String PERMISSION_PREFIX = "MiniGame";
	private List<ItemStack> items = new ArrayList<ItemStack>();
 	private String superpower, permission;

	KitMetadataSelect kitDescription;

 	private KitManagerSelect kitManager;
 	
//------------------------------------------ ctor -------------------------------------
 	
 	
 	public KitSelect(String superpower, List<String> items, KitMetadataSelect kitDescription, KitManagerSelect kitManager) {
 		this.superpower = superpower;
 		this.permission = PERMISSION_PREFIX+".kit." + superpower;
 		this.kitDescription = kitDescription;
		this.kitManager = kitManager;
		 		
 		for(String s : items) {
 			int id = 0, amount = 1;
 			if (s.contains(":")){
 				String[] splitItem = s.split(":");
 				id = Integer.valueOf(splitItem[0].trim());
 				amount = Integer.valueOf(splitItem[1].trim());
 			} else 
 				id = Integer.valueOf(s.trim());
 			this.items.add(new ItemStack(id, amount));
 		}
 	}

 	
 	
 	
	public KitSelect(FileConfiguration fileConfig, KitManagerSelect kitManager) {
		this.kitManager = kitManager;
		this.kitDescription = new KitMetadataSelect(fileConfig);
		this.superpower = kitDescription.getSuperpower();
	}


 	
 	

//---------------------------------------- getter & setter ----------------------------------



	public List<ItemStack> getItems() {
		return items;
	}


	public void setItems(List<ItemStack> items) {
		this.items = items;
	}


	public String getSuperpower() {
		return superpower;
	}



	public String getPermission() {
		return permission;
	}



	public KitMetadataSelect getKitMetadata() {
		return kitDescription;
	}


	public void setKitMetadata(KitMetadataSelect kitDescription) {
		this.kitDescription = kitDescription;
	}
 	
 	public Permission getPermissionNode(){
 		return new Permission(permission);
 	}
 	
//---------------------------------------------------------------------------
 	
 	public void setKit(Player player) {
 		kitManager.setKit(player, this);
 	}

 	public void giveKit(Player player) {
 		kitManager.setKit(player, this);
 	}

 	
/*
 	public void giveKit(Player player) {
 		InventoryUtilities.clearInventory(player);
 		for(ItemStack is : items) player.getInventory().addItem(is);
 	}
*/ 	

 	
//---------------------------------------------------- toString ------------------------
 	
 	@Override
	public String toString() {
		return "Kit [items=" + items + ", superpower=" + superpower + ", kitDescription=" + kitDescription + "]";
	}



}
