package com.gmail.kukubaczek.Stone;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
 
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
 
public class Config {
 
    private static List<RConfig> configs = new ArrayList<RConfig>();
 
    public static boolean registerConfig(String id, String fileName, JavaPlugin plugin){
        File file = new File(plugin.getDataFolder(), fileName);
 
        if(!file.exists()){
            file.getParentFile().mkdirs();
            try{ copy(plugin.getResource(fileName), file); } catch (Exception e) {}
        }
 
        RConfig c = new RConfig(id, file);
        for(RConfig x : configs){
            if(x.equals(c))return false;
        }
 
        configs.add(c);
        return true;
    }
 
    public static boolean unregisterConfig(String id){
        return configs.remove(getConfig(id));
    }
 
    public static RConfig getConfig(String id){
        for(RConfig c : configs){
            if(c.getConfigId().equalsIgnoreCase(id))return c;
        }
        return null;
    }
 
    public static boolean save(String id){
        RConfig c = getConfig(id);
        if(c == null)return false;
        try {
            c.save();
        } catch (Exception e) {
            print("An error occurred while saving a config with id "+id);
            e.printStackTrace();
            return false;
        }
        return true;
    }
 
    public static boolean saveAll(){
        try{
            for(RConfig c : configs){
                c.save();
            }
        }catch(Exception e){
            print("An error occurred while saving all configs");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean load(String id){
        RConfig c = getConfig(id);
        if(c == null)return false;
        try {
                c.load();
        } catch (Exception e) {
                print("An error occurred while loading a config with id "+id);
                e.printStackTrace();
                return false;
        }
        return true;
    }
    public static boolean loadAll(){
        try{
                for(RConfig c : configs){
                        c.load();
                }
        }catch(Exception e){
                print("An error occurred while loading all configs");
                e.printStackTrace();
                return false;
        }
        return true;
    }
 
    public static void clear(String id){
        RConfig c = getConfig(id);
        if(c == null)return;
 
        configs.remove(c);
        configs.add(new RConfig(c.getConfigId(), c.getFile()));
    }
 
    private static void print(String msg){
        System.out.println("Config: "+msg);
    }
 
    public static class RConfig extends YamlConfiguration {
        private String id;
        private File file;
 
        public String getConfigId(){return id;}
        public File getFile(){return file;}
 
        private RConfig(String id, File file){
            super();
            this.id = id;
            this.file = file;
        }
 
        public void save() throws IOException{
            save(file);
        }
 
        public void load() throws InvalidConfigurationException, FileNotFoundException, IOException{
            load(file);
        }
 
        public boolean equals(RConfig c){
            return c.getConfigId().equalsIgnoreCase(this.id);
        }
    }
 
    private static void copy(InputStream in, File file) throws IOException {
        OutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len;
 
        while((len = in.read(buf)) > 0){
            out.write(buf, 0, len);
        }
 
        out.close();
        in.close();
    }
}
 