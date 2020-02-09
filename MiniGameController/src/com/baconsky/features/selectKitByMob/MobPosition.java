package com.baconsky.features.selectKitByMob;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class MobPosition {
	private int x;
	private int y;
	private int z;
	private float yaw;
	private String name;
	
	public MobPosition(String name, ConfigurationSection chestConfig) {
		this.name = name;
		this.x = chestConfig.getInt("x");
		this.y = chestConfig.getInt("y");
		this.z = chestConfig.getInt("z");
		this.yaw = Float.valueOf(chestConfig.getString("yaw"));
	}

//------------------------------------- getter & setter --------------------------

	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public float getYew() {
		return yaw;
	}
	
	public Location getPos(World world) {
		return new Location(world, x, y, z);
	}

//------------------------------------ toString ------------------------------
	
	@Override
	public String toString() {
		return name+"[x=" + x + ", y=" + y + ", z=" + z + ", yaw=" + yaw + "]";
	}
	
	
	
}
