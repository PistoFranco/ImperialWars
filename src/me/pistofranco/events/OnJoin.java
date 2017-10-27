package me.pistofranco.events;

import me.pistofranco.atributes.Level;
import me.pistofranco.main.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {
    Core plugin = Core.getPlugin();

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Level level = new Level(player);
        player.getInventory().setItem(0, plugin.superBow);
        player.getInventory().setItem(1, plugin.stickOfAgility);
    }
}