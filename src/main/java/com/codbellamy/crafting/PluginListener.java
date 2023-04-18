package com.codbellamy.crafting;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.ItemStack;

public class PluginListener implements Listener {

    private final float EXP_MULTIPLIER = 1.5f;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(e.getBlock().getType() == Material.SPAWNER) {
            if (e.getPlayer().getType() == EntityType.PLAYER) {
                if(e.getPlayer().hasPermission("cr.mine")) {

                    ItemStack equipped = e.getPlayer().getInventory().getItemInMainHand();
                    if (equipped.getType() == Material.DIAMOND_PICKAXE ||
                            equipped.getType() == Material.GOLDEN_PICKAXE ||
                            equipped.getType() == Material.NETHERITE_PICKAXE) {

                        if (equipped.containsEnchantment(Enchantment.SILK_TOUCH)) {
                            e.setCancelled(true);

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
    public void onPrepareItemCraft(PrepareItemCraftEvent e){
        if(e.getRecipe().getResult() != null) {
            Material result = e.getRecipe().getResult().getType();
            boolean flag = false;
            for (Material[] m : Main.CRAFTING) {
                flag |= result == m[1];
            }
            if (flag) {
                if (!e.getView().getPlayer().hasPermission("cr.craft")) {
                    e.getInventory().setResult(new ItemStack(Material.AIR));
                }
            }
        }
    }

    @EventHandler
    public void onExpChangeEvent(PlayerExpChangeEvent e){
        if(e.getPlayer().hasPermission("cr.exp")){
            e.setAmount((int)((float)e.getAmount() * EXP_MULTIPLIER));
        }
    }
}
