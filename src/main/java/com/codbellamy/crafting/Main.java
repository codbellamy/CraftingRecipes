package com.codbellamy.crafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class Main extends JavaPlugin{
    @Override
    public void onEnable(){
        // Create shaped recipes
        createSpawnerRecipe();
        Bukkit.getLogger().info("[" + this.getName() + "] " + "Loaded spawner recipe.");

        // Create shapeless recipes
        createEggRecipe();
        Bukkit.getLogger().info(String.format("[" + this.getName() + "] " + "Loaded %d egg recipes.", Recipes.KEYS.length));

        // Register event handler
        getServer().getPluginManager().registerEvents(new SpawnerListener(), this);
        getServer().getPluginManager().registerEvents(new UntrustedListener(), this);
        getServer().getPluginManager().registerEvents(new VillagerListener(), this);
        getServer().getPluginManager().registerEvents(new RecipeListener(), this);
        Bukkit.getLogger().info(String.format("[" + this.getName() + "] " + "Registered listeners.", Recipes.KEYS.length));
    }

    public void onDisable(){}

    private void createSpawnerRecipe(){
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
        Recipes.spawner = spawner;
    }

    private void createEggRecipe(){
        ShapelessRecipe egg;
        Material input, output;

        for (int i = 0; i < Recipes.KEYS.length; i++){
            input = Recipes.CRAFTING[i][0];
            output = Recipes.CRAFTING[i][1];
            egg =
                    new ShapelessRecipe(
                            new NamespacedKey(this, Recipes.KEYS[i]),
                            new ItemStack(output, 1)
                    );

            egg.setCategory(CraftingBookCategory.MISC);
            egg.setGroup("spawn_egg");
            egg = egg.addIngredient(1, input)
                    .addIngredient(1, Material.ECHO_SHARD)
                    .addIngredient(1, Material.EGG);
            this.getServer().addRecipe(egg);
            Recipes.eggs.add(egg);
        }
    }
}
