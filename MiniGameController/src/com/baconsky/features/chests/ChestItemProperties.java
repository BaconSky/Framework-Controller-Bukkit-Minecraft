package com.baconsky.features.chests;

public class ChestItemProperties {
	
	private String name;
	private int probability;
	private int minNum;
	private int maxNum;
	
	public ChestItemProperties(String name, int probability, int minNum, int maxNum) {
		this.name = name;
		this.probability=probability;
		this.minNum = minNum;
		this.maxNum = maxNum;
	}
	
	@Override
	public String toString() {
		return "ChestItemProperties [name=" + name + ", probability=" + probability + ", minNum=" + minNum + ", maxNum=" + maxNum + "]";
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getProbability() {
		return probability;
	}
	
	public void setProbability(int probability) {
		this.probability = probability;
	}
	
	public int getMinNum() {
		return minNum;
	}
	
	public void setMinNum(int minNum) {
		this.minNum = minNum;
	}
	
	public int getMaxNum() {
		return maxNum;
	}
	
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
}
