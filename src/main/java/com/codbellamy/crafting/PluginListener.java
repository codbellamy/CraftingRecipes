package com.codbellamy.crafting;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
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

                            // Drop spawner (and experience as it would naturally)
                            e.getBlock().getWorld().dropItemNaturally(
                                    e.getBlock().getLocation(),
                                    new ItemStack(Material.SPAWNER, 1)
                            );

                            // Replace target block with air (nothing)
                            e.getBlock().setType(Material.AIR);
                        }
                    }
                } else {
                    // If they dont have cr.craft.spawner, cancel event
                    // (Players cant destroy spawners)
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if(e.getBlock().getType() == Material.SPAWNER){
            if(e.getPlayer().getType() == EntityType.PLAYER){
                if(!e.getPlayer().hasPermission("cr.craft.spawner")){
                    // Deny players without perms from placing spawners
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
                // Deny players without perms from picking up spawners
                e.setCancelled(true);
                return;
            }
            if(isEgg(item) && !e.getEntity().hasPermission("cr.craft.egg")){
                // Deny players without perms from picking up craftable eggs
                // Note: players CAN pick up eggs that are not craftable.
                // Meaning, an egg spawned in (creative, /give, etc) that is not
                // in the list from this plugin can be picked up
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
            // Deny players without perms from crafting spawners
            e.getInventory().setResult(new ItemStack(Material.AIR));
            return;
        }
        if (isEgg(result) && !e.getView().getPlayer().hasPermission("cr.craft.egg")) {
            // Deny players without perms from crafting eggs (with recipes)
            e.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onExpChangeEvent(PlayerExpChangeEvent e){
        // Eventually, let players with permissions to modify this
        final int EXP_MULTIPLIER = 2;

        if(e.getPlayer().hasPermission("cr.exp")){
            // Players with this perm get xp multiplier
            e.setAmount(e.getAmount() * EXP_MULTIPLIER);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(e.getClickedBlock() == null) return;
        if(e.getClickedBlock().getType() == null) return;
        if(e.getClickedBlock().getType() == Material.SPAWNER){
            if(!e.getPlayer().hasPermission("cr.create")){
                // Deny players without perm from creating a mob spawner with an egg
                e.setCancelled(true);
                return;
            }
        }
        if(e.getItem() == null) return;
        if(isEgg(e.getItem().getType()) && !e.getPlayer().hasPermission("cr.craft.egg")){
            // Deny players without perm from using spawn eggs (craftable) at all
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
                    // Delete the eggs that are clicked on from their inventory
                    e.setCurrentItem(new ItemStack(Material.AIR));
                } else {
                    // Deny interacting with eggs in chests/containers
                    e.setCancelled(true);
                }
            }
            if(!e.getView().getPlayer().hasPermission("cr.craft.spawner")){
                if(e.getClickedInventory().getType() == InventoryType.PLAYER){
                    // Delete spawners that are clicked on from their inventory
                    e.setCurrentItem(new ItemStack(Material.AIR));
                } else {
                    // Deny interacting with spawners in chests/containers
                    e.setCancelled(true);
                }
            }
        }
    }

    // Returns true if 'material' has a defined recipe from within this plugin
    // Recipes located at Main.CRAFTING
    private boolean isEgg(Material material){
        boolean flag = false;

        for (Material[] m : Main.CRAFTING) {
            flag |= material == m[1];
        }
        return flag;
    }
}
