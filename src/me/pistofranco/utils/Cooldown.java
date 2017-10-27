package me.pistofranco.utils;

import me.pistofranco.main.Core;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class Cooldown {

    private Core plugin = Core.getPlugin();
    private Map<Player,Integer> cooldown = new HashMap<>();

    private Integer seconds;

    public Cooldown(Integer seconds){
        this.seconds = seconds;
        new BukkitRunnable(){
            @Override
            public void run() {
                for(Map.Entry<Player,Integer> entry : cooldown.entrySet()){
                    Integer seconds = entry.getValue();
                    Player player = entry.getKey();
                    if(seconds-1 == 0){
                        cooldown.remove(player);
                    }
                    entry.setValue(seconds-1);
                }
            }
        }.runTaskTimer(plugin,20L,20L);
    }

    public boolean hasCooldown(Player player){
        return cooldown.containsKey(player);
    }

    public float getSeconds(Player player){
        if(hasCooldown(player)){
            for(Map.Entry<Player,Integer> playerFloatMap : cooldown.entrySet()){
                if(playerFloatMap.getKey() == player){
                    return playerFloatMap.getValue();
                }
            }
        }
        return -1;
    }

    public void addPlayer(Player player){
        cooldown.put(player,seconds);
    }
}
