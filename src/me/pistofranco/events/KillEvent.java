package me.pistofranco.events;

import me.pistofranco.atributes.Level;
import me.pistofranco.main.Core;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class KillEvent implements Listener {
    private Core plugin = Core.getPlugin();
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event)
    {
        Entity e = event.getEntity();
        ArrayList<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(plugin.stickOfAgility));
        event.setDroppedExp(0);
        event.getDrops().clear();
        event.getDrops().addAll(drops);
        if(e.getLastDamageCause() instanceof EntityDamageByEntityEvent)
        {
            EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) e.getLastDamageCause();
            if(nEvent.getDamager() instanceof Player)
            {
                Player player = (Player) nEvent.getDamager();
                Level level = plugin.getLevel(player);
                level.substractXpNeeded(125);
            }
        }
    }
}
