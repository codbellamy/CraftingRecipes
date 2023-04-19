package com.codbellamy.crafting;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings({"ConstantValue", "DataFlowIssue"})
public class PluginListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(e.getBlock().getType() == Material.SPAWNER) {
            if (e.getPlayer().getType() == EntityType.PLAYER) {
                if(e.getPlayer().hasPermission("cr.craft.spawner")) {

                    ItemStack equipped = e.getPlayer().getInventory().getItemInMainHand();
                    if (equipped.getType() == Material.DIAMOND_PICKAXE ||
                            equipped.getType() == Material.GOLDEN_PICKAXE ||
                            equipped.getType() == Material.IRON_PICKAXE ||
                            equipped.getType() == Material.NETHERITE_PICKAXE) {

                        if (equipped.containsEnchantment(Enchantment.SILK_TOUCH)) {

                            e.getBlock().getWorld().dropItemNaturally(
                                    e.getBlock().getLocation(),
                                    new ItemStack(Material.SPAWNER, 1)
                            );

                            e.getBlock().setType(Material.AIR);
                        }
                    }
                } else {
                    e.setCancelled(true);
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
        final int EXP_MULTIPLIER = 2;

        if(e.getPlayer().hasPermission("cr.exp")){
            e.setAmount(e.getAmount() * EXP_MULTIPLIER);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(e.getClickedBlock() == null) return;
        if(e.getClickedBlock().getType() == null) return;
        if(e.getClickedBlock().getType() == Material.SPAWNER){
            if(!e.getPlayer().hasPermission("cr.create")) e.setCancelled(true);
            return;
        }
        if(e.getItem() == null) return;
        if(isEgg(e.getItem().getType()) && !e.getPlayer().hasPermission("cr.craft.egg")){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getType() == null) return;
        Material item = e.getCurrentItem().getType();

        if (item == Material.SPAWNER || isEgg(item)) {
            if(!e.getView().getPlayer().hasPermission("cr.craft.egg")){
                if(e.getClickedInventory().getType() == InventoryType.PLAYER){
                    e.setCurrentItem(new ItemStack(Material.AIR));
                } else {
                    e.setCancelled(true);
                }
            }
            if(!e.getView().getPlayer().hasPermission("cr.craft.spawner")){
                if(e.getClickedInventory().getType() == InventoryType.PLAYER){
                    e.setCurrentItem(new ItemStack(Material.AIR));
                } else {
                    e.setCancelled(true);
                }
            }
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
