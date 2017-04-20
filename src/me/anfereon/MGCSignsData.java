package me.anfereon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

//arrays of all mgcsigns in world
public class MGCSignsData {
	
	private MinigamesCountSigns plugin;
	private List<String> mgcsNames;
	private List<Location> mgcsLocations;
	private Map<String,Object> map;
	
	public MGCSignsData(MinigamesCountSigns pluginMain, FileConfiguration newConfigz) {//Constructor
		this.plugin = pluginMain;
		mgcsNames = new ArrayList<String>();
		mgcsLocations = new ArrayList<Location>();
		map = newConfigz.getValues(false); //gets map from data configa - DATA IN CONFIG IS SAVED AS: location: minigamename
		//gets location and minigame name from map
		if (!map.isEmpty()) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				int comma1 = entry.getKey().indexOf(",", 0);
				int comma2 = entry.getKey().indexOf(",", comma1+1);
				int comma3 = entry.getKey().indexOf(",", comma2+1);
				String tx = entry.getKey().substring(0, comma1);
				String ty = entry.getKey().substring(comma1+1, comma2);
				String tz = entry.getKey().substring(comma2+1, comma3);
				String tworld = entry.getKey().substring(comma3+1);
				Integer ax = new Integer(tx);
				Integer ay = new Integer(ty);
				Integer az = new Integer(tz);
				World aworld = plugin.getServer().getWorld(tworld);
				Location aloc = new Location(aworld, ax, ay, az);
				String aminigamename = (String)(entry.getValue());
				//writes minigame name and location to lists
				mgcsNames.add(aminigamename);
				mgcsLocations.add(aloc);
			}
		} 
		
		plugin.getLogger().info("[MGCS] Data Arraylists created");
	}
	
	public void addMGCSignToData(String minigameName, Location location) {
		mgcsNames.add(minigameName);
		mgcsLocations.add(location);
		
		//Also add data to config
		//Turns location into string and stores it to config as key. Minigamename is value
		String xadd = String.valueOf(location.getBlockX());
		String yadd = String.valueOf(location.getBlockY());
		String zadd = String.valueOf(location.getBlockZ());
		String tworldadd = String.valueOf(location.getWorld().getName());
		
		plugin.newConfigz.set(xadd+","+yadd+","+zadd+","+tworldadd, minigameName);;
		plugin.saveNewConfig();
	}
	
	//Removes MGCSign from data. Returns if data was removed or not
	public boolean removeMGCSignFromData(String minigameName, Location location) {
		
		if (!mgcsNames.isEmpty() && mgcsNames.contains(minigameName)) { //if list is not empty and minigamename is in list
			
			int i = 0;
			for (String entry : mgcsNames) { //goes through all list elements
				if (entry.equals(minigameName) && mgcsLocations.get(i).equals(location)) { //if MGCSign with correct location is found in data
					
					mgcsNames.remove(i);
					mgcsLocations.remove(i);
					
					//Also REMOVES data FROM conFIG
					String xremove = String.valueOf(location.getBlockX());
					String yremove = String.valueOf(location.getBlockY());
					String zremove = String.valueOf(location.getBlockZ());
					String tworldremove = String.valueOf(location.getWorld().getName());
					
					plugin.newConfigz.set(xremove+","+yremove+","+zremove+","+tworldremove, null);;
					plugin.saveNewConfig();
					
					return true; //data was removed
				}				
				i++;
			}	
		}
		return false; //data could not be removed
		
	}
	
	//get name by index
	public String getMGCSignsNameByIndex(int i){
		if (i < mgcsNames.size()) {
			return mgcsNames.get(i);
		}
		else {
			return null;	
		}
	}
	
	//get location by index
	public Location getMGCSignsLocationByIndex(int i){
		if (i < mgcsLocations.size()) {
			return mgcsLocations.get(i);
		}
		else {
			return null;	
		}
	}
	
	//get array of indexes that hold values of provided minigame
	public ArrayList<Integer> getMGCSignsMatchingIndexes(String minigameName){
		ArrayList<Integer> array = new ArrayList<Integer>();;
		int i = 0;
		for (String entry : mgcsNames) { //goes through all list elements
			if (entry.equals(minigameName)) { //if MGCSign with correct location is found in data
				array.add(new Integer(i));
			}				
			i++;
		}
		return array;
	}
	
	//check if ArrayList mgcsNames contains the sign
	public boolean checkIfMGCSignIsInData(String minigameName, Location location){
		if (mgcsNames.contains(minigameName) && mgcsLocations.contains(location)) {
			if (mgcsNames.get((mgcsLocations.indexOf(location))).equals(minigameName)) {//checks if correct minigame name at correct location
				return true;
			}
		}
		return false;
	}
	
	
	//get list size
	public int getMGCSignsListSize(){
		return mgcsNames.size();
	}
}

