package com.codbellamy.crafting;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ShapelessRecipe;

@SuppressWarnings({"ConstantValue", "DataFlowIssue"})
public class RecipeListener implements Listener {
    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent e){
        if(e.getEntity() instanceof HumanEntity human) {
            Material item = e.getItem().getItemStack().getType();
            checkRecipes(human, item);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getCursor() != null){
            if(e.getCursor().getType() != null){
                HumanEntity human = e.getView().getPlayer();
                Material cursor_item = e.getCursor().getType();
                checkRecipes(human, cursor_item);
            }
        }
        if(e.isShiftClick()){
            HumanEntity human = e.getView().getPlayer();
            if(e.getClickedInventory().getItem(e.getSlot()) == null) return;
            if(e.getClickedInventory().getItem(e.getSlot()).getType() == null) return;
            Material item = e.getClickedInventory().getItem(e.getSlot()).getType();
            checkRecipes(human, item);
        }
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent e){
        HumanEntity human = e.getView().getPlayer();
        if(e.getRecipe() == null) return;
        if(e.getRecipe().getResult() == null) return;
        if(e.getRecipe().getResult().getType() == null) return;
        Material item = e.getRecipe().getResult().getType();
        checkRecipes(human, item);
    }

    private void checkRecipes(HumanEntity human, Material item) {
        if(item == Material.SPAWNER || item == Material.IRON_BARS || item == Material.ENDER_EYE){
            if(!human.hasDiscoveredRecipe(Recipes.spawner.getKey())){
                human.discoverRecipe(Recipes.spawner.getKey());
            }
            return;
        }
        if(item == Material.EGG || item == Material.ECHO_SHARD){
            for(ShapelessRecipe s : Recipes.eggs){
                if(human.hasDiscoveredRecipe(s.getKey())) continue;
                human.discoverRecipe(s.getKey());
            }
        }
        if(Recipes.getCraftingColumn(1).contains(item) || Recipes.getCraftingColumn(0).contains(item)){
            for(ShapelessRecipe s : Recipes.eggs){
                if(s.getResult().getType() == item && !human.hasDiscoveredRecipe(s.getKey())){
                    human.discoverRecipe(s.getKey());
                    return;
                }
            }
        }
    }
}
