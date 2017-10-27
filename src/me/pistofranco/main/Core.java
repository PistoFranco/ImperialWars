package me.pistofranco.main;

import me.pistofranco.atributes.Level;
import me.pistofranco.events.InteractEvent;
import me.pistofranco.events.KillEvent;
import me.pistofranco.events.OnJoin;
import me.pistofranco.items.StickOfAgility;
import me.pistofranco.items.SuperBow;
import me.pistofranco.utils.CooldownManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Core extends JavaPlugin{

    //Instantiation
    private static Core plugin;
    public static Economy economy;
    private static CooldownManager cooldown;

    public SuperBow superBow;
    public StickOfAgility stickOfAgility;

    public Map<Player,Level> levels = new HashMap<>();

    public void onEnable(){
        plugin = this;
        cooldown = new CooldownManager();
        setupConfiguration();
        if(registerProvider())
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"ImperialWars Economy setup correctly");
        registerEvents();
        registerItems();
        registerCooldowns();
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"ImperialWars has been loaded");
    }

    private void setupConfiguration() {
        if(!getConfig().contains("players")){
            getConfig().addDefault("players.91.level",0);
            getConfig().addDefault("players.91.xpNeeded",125);
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }

    private void registerCooldowns() {
        cooldown.newCooldownQueue(superBow.getItemMeta().getDisplayName(),2);
        cooldown.newCooldownQueue(stickOfAgility.getItemMeta().getDisplayName(),2);
    }

    public void onDisable(){
        plugin = null;
        for(Map.Entry<Player,Level> entry : levels.entrySet()){
            entry.getValue().saveLevel();
        }
    }

    public static Core getPlugin(){
        return plugin;
    }

    //TODO: Conexion con vault
    public boolean registerProvider(){
        return true;
    }

    public static CooldownManager getCooldown() {
        return cooldown;
    }

    private void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new InteractEvent(),this);
        pm.registerEvents(new KillEvent(),this);
        pm.registerEvents(new OnJoin(),this);
    }

    private void registerItems(){
        superBow = new SuperBow();
        stickOfAgility = new StickOfAgility();
    }

    public Level getLevel(Player player){
        for(Map.Entry<Player,Level> entry : levels.entrySet()){
            if(entry.getKey() == player){
                return entry.getValue();
            }
        }
        return null;
    }

}
