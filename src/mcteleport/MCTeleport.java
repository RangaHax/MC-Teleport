package mcteleport;

import java.util.Scanner;


import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
 
public final class MCTeleport extends JavaPlugin {
 
	
	public void onEnable(){
		Spawn spawnExecutor = new Spawn(this.getConfig(), this);
		getCommand("setspawn").setExecutor(spawnExecutor);
		getCommand("setspawntimer").setExecutor(spawnExecutor);
		getCommand("spawn").setExecutor(spawnExecutor);
	}
	public void onDisable(){}
	
	/**
	 * @return You will get a string like "3 days 2 hours 5 minutes and 6 seconds
	 */
	public static String secondsToString(long s) {
		boolean invert = false;
		//1st sort out the edge case:
		if(s == 0) {
			return "0 seconds";
		}
		if(s < 0) {
			invert = true;
		}
		
		//calculate how many of each unit is required
		int days = (int)(s/(60*60*24));
		s = s-(days*24*60*60);
		int hours = (int)(s/(60*60));
		s = s-(hours*60*60);
		int mins = (int)(s/60);
		int seconds = (int)(s-(mins*60));
		
		//work out the number of units used
		boolean and = false;
		int numberOfUnits = 0;
		if(days != 0) numberOfUnits++;
		if(hours != 0) numberOfUnits++;
		if(mins != 0) numberOfUnits++;
		if(seconds != 0) numberOfUnits++;
		if(numberOfUnits>1) and = true;
		
		//format the string
		String result = "";
		if(seconds != 0) {
			if(seconds == 1) result = String.valueOf(seconds)+" second "+result;
			else result = String.valueOf(seconds)+" seconds "+result;
			if(and) {
				result = "and "+result;
				and = false;
			}
		}
		if(mins != 0) {
			if(mins == 1) result = String.valueOf(mins)+" mintute "+result;
			else result = String.valueOf(mins)+" mintutes "+result;
			if(and) {
				result = "and "+result;
				and = false;
			}
		}
		if(hours != 0) {
			if(hours == 1) result = String.valueOf(hours)+" hour "+result;
			else result = String.valueOf(hours)+" hours "+result;
			if(and) {
				result = "and "+result;
				and = false;
			}
		}
		if(days != 0) {
			if(days == 1) result = String.valueOf(hours)+" hour "+result;
			else result = String.valueOf(hours)+" hours "+result;
		}
		if(invert) result = result+"ago";
		return result;
	}
	public static String secondsToString(int s) {
		return secondsToString(new Integer(s).longValue());
	}
}
