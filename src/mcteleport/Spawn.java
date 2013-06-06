package mcteleport;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import timer.BroadcastListener;
import timer.BroadcastTimer;

public class Spawn implements CommandExecutor, BroadcastListener {

	private FileConfiguration config;
	private MCTeleport masterPlugin;
	
	public Spawn(FileConfiguration config, MCTeleport master) {
		this.config = config;
		masterPlugin = master;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String lbl, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("spawn")) {
			//check all parameters are met 1st
			//ie: actually a player, no parameters 
			if(!(sender instanceof Player)) {
				sender.sendMessage("This command must be run by a player");
				return false;
			}
			if(args.length != 0) {
				sender.sendMessage("This command takes no arguments");
				return false;
			}
			Player player = (Player) sender;
			if(player.hasPermission(""))
				countdownComplete(player);
			else {
				int spawnTime = Integer.parseInt(String.valueOf(config.get("nznetwork.hardcore.spawn.timer")));
				new BroadcastTimer(player, "Teleporting to spawn in #t", spawnTime, this);
			}
			return true;
		}
		else if(cmd.getName().equalsIgnoreCase("setspawn")) {
			//check all parameters are met 1st
			//ie: actually a player, no parameters 
			if(!(sender instanceof Player)) {
				sender.sendMessage("This command must be run by a player");
				return false;
			}
			if(args.length != 0) {
				sender.sendMessage("This command takes no arguments");
				return false;
			}
			Player player = (Player) sender;
			Location loc = player.getLocation();
			config.set("nznetwork.hardcore.spawn.world", loc.getWorld().getName());
			config.set("nznetwork.hardcore.spawn.X", loc.getX());
			config.set("nznetwork.hardcore.spawn.Y", loc.getY());
			config.set("nznetwork.hardcore.spawn.Z", loc.getZ());
			config.set("nznetwork.hardcore.spawn.Yaw", loc.getYaw());
			config.set("nznetwork.hardcore.spawn.Pitch", loc.getPitch());
			masterPlugin.saveConfig();
			sender.sendMessage("Spawn has been moved");
			return true;
		}
		else if(cmd.getName().equalsIgnoreCase("setspawntimer")) {
			if(args.length != 1) {
				sender.sendMessage("This must have 1 time paramenter (seconds)");
				return false;
			}
			try {
				int time = Integer.parseInt(args[0]);
				config.set("nznetwork.hardcore.spawn.timer", time);
				masterPlugin.saveConfig();
				sender.sendMessage("Spawn timer set to "+MCTeleport.secondsToString(time));
				return true;
			}
			catch (NumberFormatException e) {
				sender.sendMessage("This must have 1 time paramenter (seconds)");
				return false;
			}
		}
		return false;
	}

	@Override
	public void countdownComplete(Player player) {
		//this could easily be moved to the constructor
		//so config is not read every time
		//you would just have to update after .setspawn is called
		World world = Bukkit.getWorld(String.valueOf(config.get("nznetwork.hardcore.spawn.world")));
		double X = Double.parseDouble(String.valueOf(config.get("nznetwork.hardcore.spawn.X")));
		double Y = Double.parseDouble(String.valueOf(config.get("nznetwork.hardcore.spawn.Y")));
		double Z = Double.parseDouble(String.valueOf(config.get("nznetwork.hardcore.spawn.Z")));
		float Yaw = Float.parseFloat(String.valueOf(config.get("nznetwork.hardcore.spawn.Yaw")));
		float Pitch = Float.parseFloat(String.valueOf(config.get("nznetwork.hardcore.spawn.Pitch")));
		Location loc = new Location(world, X, Y, Z, Yaw, Pitch);
		player.teleport(loc);
		player.sendMessage("Teleported to spawn");
	}

}
