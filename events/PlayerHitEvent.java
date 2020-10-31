package me.obby.rocketspleef.events;

import me.obby.rocketspleef.Rocketspleef;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerHitEvent implements Listener {

    private Rocketspleef plugin;
    public PlayerHitEvent(Rocketspleef plugin) {this.plugin = plugin;}

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();
            Player victim = (Player) e.getEntity();
            plugin.getAttackhistory().put(victim, damager);
        }
    }
}
