package me.anfereon;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;

import au.com.mineauz.minigames.Minigames;

public class SignListener implements Listener{
	
	private MinigamesCountSigns plugin;
	
	public SignListener(MinigamesCountSigns pluginMain) { //konstruktor
		this.plugin = pluginMain;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler 	// Player creates a sign
    public void onSignChange(SignChangeEvent event){
		
		Player player = event.getPlayer();
		
		//checks what player wrote on the first line
		if (ChatColor.stripColor(event.getLine(0)).equalsIgnoreCase("[MGCSigns]")) {
			
			//checks if provided minigame exists
			if(Minigames.plugin.mdata.hasMinigame(ChatColor.stripColor(event.getLine(1)))) {
				if (player.hasPermission("minigamesCountSigns.create")) {
				//create countSign
					plugin.MGCSignsData.addMGCSignToData(ChatColor.stripColor(event.getLine(1)), event.getBlock().getLocation());

					event.setLine(0, ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + ChatColor.stripColor(event.getLine(1)));
					event.setLine(1, "Igralcev:");
					event.setLine(2, ChatColor.GOLD + "0");
					player.sendMessage("MGCSign created");
					Bukkit.getLogger().info("MGCSign created at " + event.getBlock().getLocation()); //message to the console
					return;
				}
				//Player does not have the permission to create countSigns
				else {
					player.sendMessage("You do not have the permission to do that");
					event.setCancelled(true);
					return;
				}
			}
			else {
				//minigame with that name does not exist
				player.sendMessage("That minigame does not exist");
				event.setCancelled(true);
				return;
			}
		}
		else {
			return;
		}
	}
	
	@EventHandler 	// Player creates a sign
    public void onSignBreak(BlockBreakEvent event){
		if (event.getBlock().getType() == Material.SIGN || event.getBlock().getType() == Material.SIGN_POST){//if material is a sign
			Sign sign = (Sign) event.getBlock().getState();
			String mgname = ChatColor.stripColor(sign.getLine(0));
			if (plugin.MGCSignsData.checkIfMGCSignIsInData( mgname, event.getBlock().getLocation())) {
				plugin.MGCSignsData.removeMGCSignFromData(mgname, event.getBlock().getLocation());
			}
		}
	}
}
