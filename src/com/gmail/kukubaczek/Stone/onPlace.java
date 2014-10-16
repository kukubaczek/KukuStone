package com.gmail.kukubaczek.Stone;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class onPlace implements Listener{

	@EventHandler
	public void onBreak(BlockPlaceEvent event) {
		final Block blok = event.getBlock();
		Location loc = blok.getLocation();
		final Location loc1 = new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ());
		if(blok.getType() == Material.ENDER_STONE){
			if(loc1.getBlock().getType() == Material.AIR){
				Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable(){
					public void run() {
						loc1.getBlock().setType(Material.STONE);
					}
				}, Main.getTicks("generate"));
			}
		}
	}
}
