package com.gmail.kukubaczek.Stone;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class onBreak implements Listener{

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		final Block blok = event.getBlock();
		Location loc = blok.getLocation();
		final Location loc1 = new Location(loc.getWorld(), loc.getX(), loc.getY() - 1, loc.getZ());
		if(blok.getType() == Material.STONE){
			if(loc1.getBlock().getType() == Material.ENDER_STONE){
				Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable(){
					public void run() {
						if(loc1.getBlock().getType() == Material.ENDER_STONE){
							blok.setType(Material.STONE);
						}
					}
				}, Main.getTicks("regenerate"));
			}
		}
	}
}
