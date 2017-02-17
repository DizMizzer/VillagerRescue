package me.dizmizzer.vr;

import java.io.IOException;
import java.util.InputMismatchException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class ForceStart implements CommandExecutor {
	
	private Main plugin;
	public ForceStart(Main p) {
		this.plugin = p;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		if (!(sender.hasPermission("villagerrescue.help"))) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[&3VillagerRescue&9]&3 No permission to do this!"));
			return true;
		}
		
		if (args.length < 1) {
			HelpList(sender);
			return true;
		}
		
		if (args[0].equalsIgnoreCase("forcestart")) {
			if (!(sender.hasPermission("villagerrescue.forcestart"))) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[&3VillagerRescue&9]&3 No permission to do this!"));

				return true;
			}

			DropVillager dv = new DropVillager();
			dv.Drop(SettingsManager.getInstance().getLoc(Bukkit.getWorld(SettingsManager.getInstance().getWorldName())));
		}
		else if (args[0].equalsIgnoreCase("disable")) {
			if (!(sender.hasPermission("villagerrescue.toggle"))) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[&3VillagerRescue&9]&3 No permission to do this!"));

				return true;
			}

			if (plugin.enabled) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[&3VillagerRescue&9]&3 Village Rescue has been disabled!"));
				plugin.enabled = false;
			}
			else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[&3VillagerRescue&9]&3 Village Rescue already has been disabled!"));
			}
		}
		else if (args[0].equalsIgnoreCase("enable")) {
			if (!(sender.hasPermission("villagerrescue.toggle"))) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[&3VillagerRescue&9]&3 No permission to do this!"));

				return true;
			}

			if (plugin.enabled) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[&3VillagerRescue&9]&3 Village Rescue already has been enabled!"));
			}
			else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[&3VillagerRescue&9]&3 Village Rescue has been enabled!"));
				plugin.enabled = true;
			}
		}
		else if (args[0].equalsIgnoreCase("reload")) {
			if (!(sender.hasPermission("villagerrescue.reload"))) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[&3VillagerRescue&9]&3 No permission to do this!"));

				return true;
			}
			
			SettingsManager.getInstance().reloadConfig();

		}

		else if (args.length > 1 || args[0].equalsIgnoreCase("time")) {
			if (!(sender.hasPermission("villagerrescue.time"))) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[&3VillagerRescue&9]&3 No permission to do this!"));

				return true;
			}
			
			int Time = Integer.parseInt(args[1]);
			
			try {
				SettingsManager.getInstance().setTime(Time);
			} catch (InputMismatchException e) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[&3VillagerRescue&9]&3 Please insert a number."));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else {
			HelpList(sender);
		}
		return true;
	}

	private void HelpList(CommandSender sender) {

		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3--------------&9[&3VillagerRescue&9]&3--------------"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9/vr forcestart: &7This will spawn a villager to be rescued."));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9/vr disable: &7This will disable the autospawner"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9/vr enable: &7This will enable the autospawner"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9/vr time (seconds): &7You can change the delay with this"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9/vr reload: &7Reload the config."));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3------------------------------------------"));

		
		return;
	}

}
