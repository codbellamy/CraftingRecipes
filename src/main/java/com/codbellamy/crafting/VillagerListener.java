package com.codbellamy.crafting;

import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class VillagerListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Villager v){
            // Allow nitwits to die... easily
            if(v.getProfession() == Villager.Profession.NITWIT){
                v.setHealth(0);
                return;
            }
            // Don't let villagers take any damage
            v.setInvulnerable(true);
            v.setHealth(20);
            e.setCancelled(true);
        }
    }
}
