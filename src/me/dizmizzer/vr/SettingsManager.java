package me.dizmizzer.vr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class SettingsManager {

	private SettingsManager() { }
	
	private static SettingsManager instance = new SettingsManager();
	
	public static SettingsManager getInstance() {
		return instance;
	}
	File file;
	YamlConfiguration config;
	public void setup(Plugin p) throws IOException, InvalidConfigurationException {
		if (!(p.getDataFolder().exists())) {
			p.getDataFolder().mkdir();
		}
		
		 file = new File(p.getDataFolder(), "config.yml");

		if (!(file.exists())) {
			file.createNewFile();
			config = YamlConfiguration.loadConfiguration(file);
			config.set("x1", -100);
			config.set("x2", 100);
			config.set("z1", -100);
			config.set("z2", 100);
			config.set("areax1", -10);
			config.set("areax2", 10);
			config.set("areaz1", -10);
			config.set("areaz2", 10);
			config.set("rewards.diamond_block", 0);
			

			config.set("timer", 1000);
			config.set("world", "world");

			config.set("enabledonstart", false);
			config.save(file);
		}
		
		config = YamlConfiguration.loadConfiguration(file);
		
		
	}
	
	public int GetTimer() {
		
		return config.getInt("timer");
	}
	
	public void setTime(int i) throws IOException {
		config.set("timer", i);
		saveConfig();
		reloadConfig();
	}
	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(file);
		
	}

	public void saveConfig() throws IOException {

		config.save(file);
	}

	public boolean GetEnabled() {
		return config.getBoolean("enabledonstart");
	}
	public String getWorldName() {
		return config.getString("world");
	}
	
	public Location getLoc(World w) {
		Random r = new Random();
		
		int x1 = config.getInt("x1");
		int x2 = config.getInt("x2");
		int z1 = config.getInt("z1");
		int z2 = config.getInt("z2");

		int x = r.nextInt(x2 +1 - x1) + x1;
		int z = r.nextInt(z2 +1 - z1) + z1;

		int y = w.getHighestBlockAt(x, z).getY() + 1;
		
		Location loc = new Location(w, x, y, z);
		return loc;
	}
	
	public boolean isInArea(Player player) {
		int x1;
		int x2;
		int z1;
		int z2;
		
		if (config.getInt("areax1") < config.getInt("areax2")) {
			x1 = config.getInt("areax1");
			x2 = config.getInt("areax2");
		}
		else {
			x1 = config.getInt("areax2");
			x2 = config.getInt("areax1");
		}

		if (config.getInt("areaz1") < config.getInt("areaz2")) {
			z1 = config.getInt("areaz1");
			z2 = config.getInt("areaz2");
		}
		else {
			z1 = config.getInt("areaz2");
			z2 = config.getInt("areaz1");
		}
		
		Location loc = player.getLocation();
		if (loc.getX() < x2 && loc.getX() > x1) {
			if (loc.getZ() < z2 && loc.getZ() > z1) {
				return true;
			}
		}

		return false;
	}

	public ArrayList<ItemStack> getRewards() {
		Collection<String> items = config.getConfigurationSection("rewards").getKeys(false);
		ArrayList<ItemStack> itemreturns = new ArrayList<ItemStack>();
		for (String item : items) {
			Material m = Material.getMaterial(item);
			ItemStack i = new ItemStack(m, 1);
			itemreturns.add(i);
		}
		
		return itemreturns;
	}

}
