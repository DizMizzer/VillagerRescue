package me.dizmizzer.vr;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

import net.md_5.bungee.api.ChatColor;

public class DropVillager {
	

	public void Drop(Location loc) {
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&c&l--------------------------------------------"));
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&lVillager Dropped at @x, @y, @z")
				.replace("@x",(int) loc.getX() + "")
				.replace("@y", (int) loc.getY() + "")
				.replace("@z",(int) loc.getZ() + "")
				);
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&c&l--------------------------------------------"));
		
		Villager v = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
		v.setCustomNameVisible(true);
		v.setAdult();
		v.setCustomName(ChatColor.translateAlternateColorCodes('&', "&cBring me back!"));
	}

}
