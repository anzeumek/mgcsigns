package me.anfereon;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import au.com.mineauz.minigames.events.EndMinigameEvent;
import au.com.mineauz.minigames.events.JoinMinigameEvent;
import au.com.mineauz.minigames.events.QuitMinigameEvent;
import au.com.mineauz.minigames.events.StartMinigameEvent;

public class EventListener implements Listener{
	
	private MinigamesCountSigns plugin;
	
	public EventListener(MinigamesCountSigns pluginMain) { //constructor
		this.plugin = pluginMain;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler 	// minigame ends
    public void onEndMinigame(EndMinigameEvent event){
		//Bukkit.getLogger().info("minigame ended");
		plugin.updateSigns.UpdateSignsOf(event.getMinigame().toString(), 0);
	}
	
	@EventHandler 	// minigame starts
    public void onStartMinigame(StartMinigameEvent event){
		plugin.updateSigns.UpdateSignsOf(event.getMinigame().toString(), event.getPlayers().size());
	}
	
	@EventHandler 	// player joins minigame
    public void onJoinMinigame(JoinMinigameEvent event){
		plugin.updateSigns.UpdateSignsOf(event.getMinigame().toString(), event.getMinigame().getPlayers().size()+1);
	}
	
	@EventHandler 	// player quits minigame
    public void onQuitMinigame(QuitMinigameEvent event){
		plugin.updateSigns.DecrementSignsOf(event.getMinigame().toString(), 1);
	}
}
