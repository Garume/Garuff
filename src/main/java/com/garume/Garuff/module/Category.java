package com.garume.Garuff.module;

public enum Category {
	PVP("PvP"), EXPLOITS("Exploits"),RENDER("Render"),PLAYER("Player"),CLIENT("Client"),HUD("Hud");

	public String name;
	public int moduleIndex;

	Category(String name){
		this.name = name;
	}

}
