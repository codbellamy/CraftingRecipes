package com.codbellamy.crafting;

import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.ArrayList;
import java.util.List;

public class Recipes {

    public static final String[] KEYS = {
            "spider_egg",
            "zombie_egg",
            "creeper_egg",
            "cave_spider_egg",
            "villager_egg",
            "blaze_egg",
            "chicken_egg",
            "cow_egg",
            "pig_egg",
            "ghast_egg",
            "wither_egg",
            "skeleton_egg",
            "wither_skeleton_egg",
            "magma_cube_egg",
            "evoker_egg",
            "slime_egg",
            "iron_golem_egg",
            "phantom_egg",
            "drowned_egg",
            "sheep_egg",
            "shulker_egg",
            "rabbit_egg"
    };
    public static final Material[][] CRAFTING = {
            {Material.SPIDER_EYE,               Material.SPIDER_SPAWN_EGG},
            {Material.ROTTEN_FLESH,             Material.ZOMBIE_SPAWN_EGG},
            {Material.GUNPOWDER,                Material.CREEPER_SPAWN_EGG},
            {Material.FERMENTED_SPIDER_EYE,     Material.CAVE_SPIDER_SPAWN_EGG},
            {Material.EMERALD,                  Material.VILLAGER_SPAWN_EGG},
            {Material.BLAZE_ROD,                Material.BLAZE_SPAWN_EGG},
            {Material.CHICKEN,                  Material.CHICKEN_SPAWN_EGG},
            {Material.BEEF,                     Material.COW_SPAWN_EGG},
            {Material.PORKCHOP,                 Material.PIG_SPAWN_EGG},
            {Material.GHAST_TEAR,               Material.GHAST_SPAWN_EGG},
            {Material.NETHER_STAR,              Material.WITHER_SPAWN_EGG},
            {Material.BONE,                     Material.SKELETON_SPAWN_EGG},
            {Material.WITHER_SKELETON_SKULL,    Material.WITHER_SKELETON_SPAWN_EGG},
            {Material.MAGMA_CREAM,              Material.MAGMA_CUBE_SPAWN_EGG},
            {Material.TOTEM_OF_UNDYING,         Material.EVOKER_SPAWN_EGG},
            {Material.SLIME_BALL,               Material.SLIME_SPAWN_EGG},
            {Material.IRON_BLOCK,               Material.IRON_GOLEM_SPAWN_EGG},
            {Material.PHANTOM_MEMBRANE,         Material.PHANTOM_SPAWN_EGG},
            {Material.TRIDENT,                  Material.DROWNED_SPAWN_EGG},
            {Material.MUTTON,                   Material.SHEEP_SPAWN_EGG},
            {Material.SHULKER_SHELL,            Material.SHULKER_SPAWN_EGG},
            {Material.RABBIT_FOOT,              Material.RABBIT_SPAWN_EGG}
    };
    public static List<ShapelessRecipe> eggs = new ArrayList<>();
    public static ShapedRecipe spawner;

    public static List<Material> getCraftingColumn(int col){
        List<Material> out = new ArrayList<>();
        for (Material[] materials : CRAFTING) {
            out.add(materials[col]);
        }
        return out;
    }
}
