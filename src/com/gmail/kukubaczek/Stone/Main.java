package com.gmail.kukubaczek.Stone;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;

public class Main extends JavaPlugin{
    
    private static Plugin plugin;
    
    @Override
    public void onEnable() {
        plugin = this;
        registerEvents(this, new onBreak());
        registerEvents(this, new onPlace());
        
        /*
         *  Rejestracja Configów
         */
        
        Config.registerConfig("config", "config.yml", this);
        Config.loadAll();
        
        /*
         *  Rejestracja craftingu
         */
                
        ItemStack ender = new ItemStack(Material.ENDER_STONE, 1);
        ItemMeta meta = ender.getItemMeta();
        meta.setLore(Arrays.asList("§7Postaw na ziemi,", "§7po sekundzie wygeneruje", "§7się nad nim stone!", "§7Po wykopaniu stone", "§7zregeneruje się!"));
        meta.setDisplayName("§aStoniarka");
        ender.setItemMeta(meta);
        
        ShapedRecipe rodSPAWN = new ShapedRecipe(ender).shape(new String[] { 
        	      "DAD", 
        	      "ABA", 
        	      "DCD" })
        	      .setIngredient('A', Material.IRON_INGOT)
        	      .setIngredient('B', Material.STONE)
        	      .setIngredient('C', Material.PISTON_BASE)
        		  .setIngredient('D', Material.REDSTONE);
        	    getServer().addRecipe(rodSPAWN);
        
    }
    
    @Override
    public void onDisable() {
        Config.saveAll();
    }
    
    
    //Much eaisier then registering events in 10 diffirent methods
    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
    
    //To access the plugin variable from other classes
    public static Plugin getPlugin() {
        return plugin;
    }
    
    public static long getTicks(String type){
    	if(type.equalsIgnoreCase("regenerate")){
    		long tick = (long) Config.getConfig("config").getInt("ticksToRenegerate");
    		return tick;
    	}else if(type.equalsIgnoreCase("generate")){
    		long tick = (long) Config.getConfig("config").getInt("ticksToGenerate");
    		return tick;
    	}else{
    		long error = (long) 0;
    		return error;
    	}
    }
}