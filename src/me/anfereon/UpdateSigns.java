package me.anfereon;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class UpdateSigns {

	private MinigamesCountSigns plugin;

	public UpdateSigns(MinigamesCountSigns pluginMain) {//constructor
		
		this.plugin = pluginMain;
	}
	
	public boolean UpdateSignsOf(String minigameName, int SteviloIgralcev){//returns true if sign was updated
		ArrayList<Integer> arrayList;
		arrayList = plugin.MGCSignsData.getMGCSignsMatchingIndexes(minigameName);
		if (arrayList != null) {
			for (int i = 0; i < arrayList.size(); i++)
				try {
					Block b = plugin.getServer().getWorlds().get(0).getBlockAt(plugin.MGCSignsData.getMGCSignsLocationByIndex(arrayList.get(i)));
					Sign sign = (Sign) b.getState();
					if (SteviloIgralcev == 0) {
						sign.setLine(2, ChatColor.GOLD + "0");
					}
					else {
						sign.setLine(2, ChatColor.GREEN + String.valueOf(SteviloIgralcev));
					}
					sign.update();
				}
				catch (Exception e) {
					Bukkit.getLogger().info("[MGCSIgns] Error updating signs for " + minigameName + "removing sign from data");
					//remove sign from data, because probably it does not exist any more
					plugin.MGCSignsData.removeMGCSignFromData(minigameName, plugin.MGCSignsData.getMGCSignsLocationByIndex(arrayList.get(i)));
					return false;
				}
			return true;
		}
		else {
			Bukkit.getLogger().info("Problem getting ArrayList of intgers from MGCSignsData");
			return false;
		}
	}
	
	public boolean IncrementSignsOf(String minigameName, int DodatnoSteviloIgralcev){//returns true if sign was updated
		ArrayList<Integer> arrayList;
		arrayList = plugin.MGCSignsData.getMGCSignsMatchingIndexes(minigameName);
		if (arrayList != null) {
			for (int i = 0; i < arrayList.size(); i++)
				try {
					Block b = plugin.getServer().getWorlds().get(0).getBlockAt(plugin.MGCSignsData.getMGCSignsLocationByIndex(arrayList.get(i)));
					Sign sign = (Sign) b.getState();
					Integer in = new Integer(ChatColor.stripColor(sign.getLine(2)));
					sign.setLine(2, ChatColor.GREEN + String.valueOf(in + DodatnoSteviloIgralcev));
					sign.update();
				}
				catch (Exception e) {
					Bukkit.getLogger().info("[MGCSIgns] Error incrementing signs for " + minigameName + "removing sign from data");
					//remove sign from data, because probably it does not exist any more
					plugin.MGCSignsData.removeMGCSignFromData(minigameName, plugin.MGCSignsData.getMGCSignsLocationByIndex(arrayList.get(i)));
					return false;
				}
			return true;
		}
		else {
			Bukkit.getLogger().info("Problem getting ArrayList of intgers from MGCSignsData");
			return false;
		}
	}
	public boolean DecrementSignsOf(String minigameName, int OdvzetoSteviloIgralcev){//returns true if sign was updated
		ArrayList<Integer> arrayList;
		arrayList = plugin.MGCSignsData.getMGCSignsMatchingIndexes(minigameName);
		if (arrayList != null) {
			for (int i = 0; i < arrayList.size(); i++)
				try {
					Block b = plugin.getServer().getWorlds().get(0).getBlockAt(plugin.MGCSignsData.getMGCSignsLocationByIndex(arrayList.get(i)));
					Sign sign = (Sign) b.getState();
					Integer in = new Integer(ChatColor.stripColor(sign.getLine(2)));
					if (in > 0) {//is 0 after minigame ends - this prevents counter to go into negtive numbers
						if (in - OdvzetoSteviloIgralcev == 0) {
							sign.setLine(2, ChatColor.GOLD + "0");
						}
						else {
							sign.setLine(2, ChatColor.GREEN + String.valueOf(in - OdvzetoSteviloIgralcev));
						}
						sign.update();
						Bukkit.getLogger().info("[MGCSIgns] decrementing playercount for " + minigameName);
					}
				}
				catch (Exception e) {
					Bukkit.getLogger().info("[MGCSIgns] Error updating signs for " + minigameName);
					return false;
				}
			return true;
		}
		else {
			Bukkit.getLogger().info("Problem getting ArrayList of intgers from MGCSignsData");
			return false;
		}
	}
}
