package com.codbellamy.crafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

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
            "magma_cube"
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
            {Material.MAGMA_CREAM,              Material.MAGMA_CUBE_SPAWN_EGG}
    };
    @Override
    public void onEnable(){

        ShapedRecipe spawner =
                new ShapedRecipe(
                        new NamespacedKey(this, "spawner"),
                        new ItemStack(Material.SPAWNER, 1)
                );
        spawner.setCategory(CraftingBookCategory.MISC);
        spawner.setGroup("spawner");
        spawner = spawner.shape("xxx","xox","xxx")
                .setIngredient('o', Material.ENDER_EYE)
                .setIngredient('x', Material.IRON_BARS);
        this.getServer().addRecipe(spawner);

        Bukkit.getLogger().info("[" + this.getName() + "] " + "Loaded spawner recipe.");

        // Create shapeless recipes
        createEggRecipe();
        Bukkit.getLogger().info(String.format("[" + this.getName() + "] " + "Loaded %d egg recipes.", KEYS.length));

        // Register event handler
        getServer().getPluginManager().registerEvents(new PluginListener(), this);
        Bukkit.getLogger().info(String.format("[" + this.getName() + "] " + "Registered listener.", KEYS.length));
    }

    public void onDisable(){}

    public void createEggRecipe(){
        ShapelessRecipe egg;
        Material input, output;

        for (int i = 0; i < KEYS.length; i++){
            input = CRAFTING[i][0];
            output = CRAFTING[i][1];
            egg =
                    new ShapelessRecipe(
                            new NamespacedKey(this, KEYS[i]),
                            new ItemStack(output, 1)
                    );

            egg.setCategory(CraftingBookCategory.MISC);
            egg.setGroup("spawn_egg");
            egg = egg.addIngredient(1, input)
                    .addIngredient(1, Material.AMETHYST_SHARD)
                    .addIngredient(1, Material.EGG);
            this.getServer().addRecipe(egg);
        }
    }
}
