package com.codbellamy.crafting;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PluginListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(e.getBlock().getType() == Material.SPAWNER) {
            if (e.getPlayer().getType() == EntityType.PLAYER) {
                if(e.getPlayer().hasPermission("cr.craft.spawner")) {

                    ItemStack equipped = e.getPlayer().getInventory().getItemInMainHand();
                    if (equipped.getType() == Material.DIAMOND_PICKAXE ||
                            equipped.getType() == Material.GOLDEN_PICKAXE ||
                            equipped.getType() == Material.NETHERITE_PICKAXE) {

                        if (equipped.containsEnchantment(Enchantment.SILK_TOUCH)) {

                            e.getBlock().getWorld().dropItemNaturally(
                                    e.getBlock().getLocation(),
                                    new ItemStack(Material.SPAWNER, 1)
                            );

                            e.getBlock().setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent e){
        if(e.getEntity().getType() == EntityType.PLAYER){
            Material item = e.getItem().getItemStack().getType();
            if(item == Material.SPAWNER && !e.getEntity().hasPermission("cr.craft.spawner")){
                e.setCancelled(true);
                return;
            }
            if(isEgg(item) && !e.getEntity().hasPermission("cr.craft.egg")){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent e){
        if(e.getRecipe() == null) return;
        if(e.getRecipe().getResult() == null) return;
        Material result = e.getRecipe().getResult().getType();
        if(result == Material.SPAWNER && !e.getView().getPlayer().hasPermission("cr.craft.spawner")){
            e.getInventory().setResult(new ItemStack(Material.AIR));
            return;
        }
        if (isEgg(result) && !e.getView().getPlayer().hasPermission("cr.craft.egg")) {
            e.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onExpChangeEvent(PlayerExpChangeEvent e){
        // Eventually, let players with permissions to modify this
        final float EXP_MULTIPLIER = 1.5f;

        if(e.getPlayer().hasPermission("cr.exp")){
            e.setAmount((int)((float)e.getAmount() * EXP_MULTIPLIER));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(e.getClickedBlock().getType() == Material.SPAWNER){
            if(!e.getPlayer().hasPermission("cr.create")) e.setCancelled(true);
            return;
        }
        if(isEgg(e.getItem().getType()) && !e.getPlayer().hasPermission("cr.craft.egg")){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getCurrentItem().getType() == Material.SPAWNER && !e.getView().getPlayer().hasPermission("cr.craft.spawner")){
            e.setCancelled(true);
            return;
        }
        if(isEgg(e.getCurrentItem().getType()) && !e.getView().getPlayer().hasPermission("cr.craft.egg")){
            e.setCancelled(true);
        }
    }

    private boolean isEgg(Material material){
        boolean flag = false;
        for (Material[] m : Main.CRAFTING) {
            flag |= material == m[1];
        }
        return flag;
    }
}
