package com.baconsky.features.chests;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class ChestPosition {
	private int x;
	private int y;
	private int z;
	private String o;
	
	public ChestPosition(ConfigurationSection chestConfig) {
		this.x = chestConfig.getInt("x");
		this.y = chestConfig.getInt("y");
		this.z = chestConfig.getInt("z");
		this.o = chestConfig.getString("o");
	}

//------------------------------------- getter & setter --------------------------
	
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public String getO() {
		return o;
	}
	
	public Location getPos(World world) {
		return new Location(world, x, y, z);
	}

//------------------------------------ toString ------------------------------
	
	@Override
	public String toString() {
		return "ChestPosition [x=" + x + ", y=" + y + ", z=" + z + ", o=" + o + "]";
	}
	
	
	
}
