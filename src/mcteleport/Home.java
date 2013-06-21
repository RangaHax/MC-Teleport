package mcteleport;

import mcteleport.timer.BroadcastListener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Bed;

public class Home implements CommandExecutor, BroadcastListener, Listener{

	private FileConfiguration config;
	private MCTeleport masterPlugin;
	
	public Home(MCTeleport master) {
		this.config = master.getConfig();
		masterPlugin = master;
		masterPlugin.getServer().getPluginManager().registerEvents(this, masterPlugin);
	}
	
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getClickedBlock().getType() == Material.BED_BLOCK) {
				if(event.getPlayer().isSneaking()) {
					return;
				}
				event.getPlayer().sendMessage(ChatColor.RED+"Right click bed");
				event.setCancelled(true);
			}
		}
	}
	
	
	
	@Override
	public void countdownComplete(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String lbl, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

}
