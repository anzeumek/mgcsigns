package me.anfereon;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MinigamesCountSigns extends JavaPlugin{
	
	//seperate config for data storage
    File newConfig;
    FileConfiguration newConfigz;
    //initialize stuff
	public MGCSignsData MGCSignsData;
	public SignListener signListener;
	public EventListener eventListener;
	public UpdateSigns updateSigns;
	//creates permissions!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public Permission createSignPermission = new Permission("minigamesCountSigns.create");
	
	@Override
	public void onEnable() {
		newConfig = new File(getDataFolder(), "newconfig.yml"); // set the file location for data config
		newConfigz = YamlConfiguration.loadConfiguration(newConfig); // this will give you all the functions such as .getInt, getString ect.
		saveNewConfig();
		
		signListener = new SignListener(this);
		eventListener = new EventListener(this);
		PluginManager pm = getServer().getPluginManager(); //for listener and permissions
		pm.addPermission(createSignPermission); //adds permision
		//creates new data storage for MGCSigns
		MGCSignsData = new MGCSignsData(this, newConfigz);

		updateSigns = new UpdateSigns(this);

		
		
		getLogger().info("[MinigamesCountSigns] plugin enabled");
	}
	
	@Override
	public void onDisable() {
		
		saveNewConfig();
		getLogger().info("[MinigamesCountSigns] plugin disabled");
	}
	
	//config for data storage
    public void saveNewConfig(){
    	try {
			newConfigz.save(newConfig);
		} catch (Exception e) {
			//error saving data config
			e.printStackTrace();
		}
    }
}
