package me.pistofranco.events;

import me.pistofranco.main.Core;
import me.pistofranco.utils.Cooldown;
import me.pistofranco.utils.CooldownManager;
import net.minecraft.server.v1_12_R1.EntityArrow;
import org.apache.commons.lang.UnhandledException;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;


public class InteractEvent implements Listener {

    private Core plugin = Core.getPlugin();
    private CooldownManager cooldownManager = Core.getCooldown();

    @EventHandler
    public void interactEvent(PlayerInteractEvent event) {
        //Player to Player
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand() == null){
            return;
        }
        String item = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
        if (item == plugin.superBow.getItemMeta().getDisplayName()) {
            Cooldown cooldown = cooldownManager.getQueue(plugin.superBow.getItemMeta().getDisplayName());
            if (event.getAction() == Action.LEFT_CLICK_AIR) {
                if (cooldown.hasCooldown(player)) {
                    event.setCancelled(true);
                    player.sendMessage(plugin.superBow.getPrefix() + ChatColor.RED + "Cooldown left: " + cooldown.getSeconds(player));
                } else {
                    new BukkitRunnable(){
                        int i = 0;
                        List<Arrow> arrows = new ArrayList<>();

                        @Override
                        public void run() {
                            if(i<= 10) {
                                double pitch = ((player.getLocation().getPitch() + 90) * Math.PI) / 180;
                                double yaw = ((player.getLocation().getYaw() + 90) * Math.PI) / 180;
                                double x = Math.sin(pitch) * Math.cos(yaw);
                                double y = Math.sin(pitch) * Math.sin(yaw);
                                double z = Math.cos(pitch);
                                Vector vector = new Vector(x, z, y);
                                Arrow arrow = player.getWorld().spawn(player.getEyeLocation(), Arrow.class);
                                arrows.add(arrow);
                                arrow.setShooter(player);
                                arrow.setVelocity(vector.multiply(3));
                            }
                            if(i == 20){
                                for(Arrow arrow : arrows){
                                    arrow.remove();
                                }
                                arrows.clear();
                                cancel();
                            }
                            i++;
                        }
                    }.runTaskTimer(plugin,10l,10l);

                    cooldown.addPlayer(player);
                    player.sendMessage(plugin.superBow.getPrefix() + ChatColor.RED + "Cooldown left: " + cooldown.getSeconds(player));
                }
            }
        }


        if (item == plugin.stickOfAgility.getItemMeta().getDisplayName()) {
            Cooldown cooldown = cooldownManager.getQueue(plugin.stickOfAgility.getItemMeta().getDisplayName());
            if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                if (cooldown.hasCooldown(player)) {
                    event.setCancelled(true);
                    player.sendMessage(plugin.stickOfAgility.getPrefix() + ChatColor.RED + "Cooldown left: " + cooldown.getSeconds(player));
                } else {
                    double pitch = ((player.getLocation().getPitch() + 90) * Math.PI) / 180;
                    double yaw = ((player.getLocation().getYaw() + 90) * Math.PI) / 180;
                    double x = Math.sin(pitch) * Math.cos(yaw);
                    double y = Math.sin(pitch) * Math.sin(yaw);
                    double z = Math.cos(pitch);
                    Vector vector = new Vector(x, z, y);
                    player.setVelocity(vector.multiply(10));
                    cooldown.addPlayer(player);
                    player.sendMessage(plugin.stickOfAgility.getPrefix() + ChatColor.RED + "Cooldown left: " + cooldown.getSeconds(player));
                }
            }
        }

    }

}
