package me.pistofranco.atributes;

import me.pistofranco.main.Core;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Level {
    private Core plugin = Core.getPlugin();

    private float necesaryXp;
    private float xp;
    private final int minXP = 125;
    private int level;
    private Player player;

    public Level(Player player){
        this.player = player;
        if(plugin.getConfig().contains("players."+player.getName())) {
            level = plugin.getConfig().getInt("players." + player.getName() + ".level");
            xp = plugin.getConfig().getInt("players." + player.getName() + ".xp");
            necesaryXp = minXP * level/1.6f;
        }else {
            plugin.getConfig().set("players." + player.getName() + ".level",1);
            plugin.getConfig().set("players." + player.getName() + ".xp",0);
            plugin.saveConfig();
            xp = 125;
            level = 1;
            necesaryXp = minXP;
            plugin.levels.put(player,this);
        }
    }

    public void substractXpNeeded(float amount){
        if(xp + amount == necesaryXp){
            newLevel();
            return;
        }
        if(xp + amount > necesaryXp){
            newLevel((xp+amount)-necesaryXp);
            return;
        }
        xp = xp+amount;
    }

    private void newLevel() {
        level++;
        necesaryXp = minXP * level/1.6f;
        player.sendMessage(ChatColor.GREEN+"Congratulations you level up!");
        player.sendMessage(ChatColor.GREEN+"Now you are level: "+getLevel());
        player.sendMessage(ChatColor.GREEN+"XP to next level: "+(int)xp+" / "+(int)necesaryXp);

    }
    private void newLevel(float amount) {
        level++;
        necesaryXp = minXP * level/1.6f;
        substractXpNeeded(amount);
        player.sendMessage(ChatColor.GREEN+"Congratulations you level up!");
        player.sendMessage(ChatColor.GREEN+"Now you are level: "+getLevel());
        player.sendMessage(ChatColor.GREEN+"XP to next level: "+(int)xp+" / "+(int)necesaryXp);

    }
    public void saveLevel(){
        plugin.getConfig().set("players."+player.getName()+".level",level);
        plugin.getConfig().set("players."+player.getName()+".xpNeeded",necesaryXp);
        plugin.getConfig().set("players."+player.getName()+".xp",xp);
        plugin.saveConfig();
    }
    public int getLevel(){
        return level;
    }
}
