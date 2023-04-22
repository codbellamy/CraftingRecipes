package com.codbellamy.crafting;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

@SuppressWarnings("ConstantValue")
public class UntrustedListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if(!e.getPlayer().hasPermission("cr.trusted")){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        if(!e.getPlayer().hasPermission("cr.trusted")){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(!e.getPlayer().hasPermission("cr.trusted")){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if(!e.getPlayer().hasPermission("cr.trusted")){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickupItem(EntityPickupItemEvent e){
        if(e.getEntity() instanceof org.bukkit.entity.Player p) {
            if (!p.hasPermission("cr.trusted")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent e){
        if(!e.getPlayer().hasPermission("cr.trusted")){
            e.setAmount(0);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(e.getClickedBlock() == null) return;
        if(e.getClickedBlock().getType() == null) return;
        if(e.getItem() == null) return;
        if(!e.getPlayer().hasPermission("cr.trusted")){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getType() == null) return;
        if(!e.getView().getPlayer().hasPermission("cr.trusted")){
            e.setCancelled(true);
        }
    }


}
