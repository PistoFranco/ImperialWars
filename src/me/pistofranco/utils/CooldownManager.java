package me.pistofranco.utils;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CooldownManager {
    private Map<Cooldown,String> cooldowns = new HashMap<>();

    public void newCooldownQueue(String item, Integer seconds){
        if(!exists(item)) {
            cooldowns.put(new Cooldown(seconds), item);
        }
        else System.out.print("Error while adding cooldown with name: "+item+" , this name already exists.");
    }

    public Cooldown getQueue(String item) {
        for (Map.Entry<Cooldown, String> entry : cooldowns.entrySet()) {
            if (entry.getValue().equals(item)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private boolean exists(String item){
        for (Map.Entry<Cooldown,String> entry : cooldowns.entrySet()){
            if(entry.getValue().equals(item)){
                return true;
            }
        }
        return false;
    }

}

