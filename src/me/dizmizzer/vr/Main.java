package me.dizmizzer.vr;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;


public class Main extends JavaPlugin implements Listener {
	
	public boolean enabled = false;
	public void onEnable() {
		
		
		this.getCommand("vr").setExecutor(new ForceStart(this));
		try {
			SettingsManager.getInstance().setup(this);
		} catch (IOException | InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		enabled = SettingsManager.getInstance().GetEnabled();
		int time = SettingsManager.getInstance().GetTimer();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				if (enabled) {
					Location loc = SettingsManager.getInstance().getLoc(Bukkit.getWorld("world"));
					DropVillager dv = new DropVillager();
					dv.Drop(loc);
				}

			}
			
			
		}, time * 20, time * 20); 
		
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onEntityClick(PlayerInteractEntityEvent e) {
		if (e.getRightClicked().getType() != EntityType.VILLAGER) {
			return;
		}
		else if (!(e.getRightClicked().getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&cBring me back!")))) {
			return;
		}
		
		if (e.getRightClicked().isInsideVehicle()) {
			e.getRightClicked().getVehicle().remove();
		}
		Player player = e.getPlayer();
		
		Bat b = (Bat) player.getWorld().spawnEntity(player.getLocation(), EntityType.BAT);
		b.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1, false));
		b.setPassenger(e.getRightClicked());
		
		player.setPassenger(b);
		

		e.setCancelled(true);
		
	}
	
	@EventHandler
	public void onEntityDamager(EntityDamageEvent e) {
		if (e.getEntity().getType() != EntityType.VILLAGER && e.getEntity().getType() != EntityType.BAT) {
			return;
		}
		
		if (e.getEntity().getCustomName() == null && e.getEntity().getType() != EntityType.BAT) {

			return;
		}
		if (e.getEntity().getPassenger() != null && e.getEntity().getType() == EntityType.BAT) {
			if (e.getEntity().getPassenger().getCustomName() != null) {
				if (e.getEntity().getPassenger().getCustomName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&cBring me back!"))) {

					e.setCancelled(true);
					return;
				}

			}
		}
		if (e.getEntity().getCustomName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&cBring me back!"))) {
			if (e.getDamage() > 0.2) {

				e.setCancelled(true);
			}
		}
			
	}
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		
		if (e.getPlayer().getPassenger() == null) {
			return;
		}
		if (e.getPlayer().getPassenger().getType() != EntityType.BAT) {
			return;
		}
		Entity entity = e.getPlayer().getPassenger();
		
		if (entity.getPassenger() == null) {
			return;
		}
		
		if (entity.getPassenger().getType() != EntityType.VILLAGER) {
			return;
		}
		if (entity.getPassenger().getCustomName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&cBring me back!"))) {
			if (SettingsManager.getInstance().isInArea(e.getPlayer())) {
				entity.getPassenger().remove();
				entity.remove();
				SendReward(e.getPlayer());
			}
		}
	}

	private void SendReward(Player player) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[&3VillagerRescue&9]&3 You brought the Villager back! Here is a reward!"));
		ArrayList<ItemStack> rewards = SettingsManager.getInstance().getRewards();
		for (ItemStack i : rewards) {
			player.getInventory().addItem(i);
		}
	}
	
	

}
