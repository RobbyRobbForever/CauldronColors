package com.example.cauldroncolors.data;

import com.example.cauldroncolors.CauldronColors;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class CauldronColorsRecipeProvider extends FabricRecipeProvider {

    public CauldronColorsRecipeProvider(
            FabricDataOutput output,
            CompletableFuture<HolderLookup.Provider> registriesFuture
    ) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(
            HolderLookup.Provider registryLookup,
            RecipeOutput output
    ) {
        return new RecipeProvider(registryLookup, output) {

            @Override
            public void buildRecipes() {
                addRecipe(CauldronColors.TRUE_BLUE_EGG, CauldronColors.BLUE_EGG_BLOCK);
                addRecipe(CauldronColors.TRUE_BROWN_EGG, CauldronColors.BROWN_EGG_BLOCK);
                addRecipe(CauldronColors.MAGENTA_EGG, CauldronColors.MAGENTA_EGG_BLOCK);
                addRecipe(CauldronColors.LIGHT_BLUE_EGG, CauldronColors.LIGHT_BLUE_EGG_BLOCK);
                addRecipe(CauldronColors.YELLOW_EGG, CauldronColors.YELLOW_EGG_BLOCK);
                addRecipe(CauldronColors.LIME_EGG, CauldronColors.LIME_EGG_BLOCK);
                addRecipe(CauldronColors.PINK_EGG, CauldronColors.PINK_EGG_BLOCK);
                addRecipe(CauldronColors.GRAY_EGG, CauldronColors.GRAY_EGG_BLOCK);
                addRecipe(CauldronColors.LIGHT_GRAY_EGG, CauldronColors.LIGHT_GRAY_EGG_BLOCK);
                addRecipe(CauldronColors.CYAN_EGG, CauldronColors.CYAN_EGG_BLOCK);
                addRecipe(CauldronColors.PURPLE_EGG, CauldronColors.PURPLE_EGG_BLOCK);
                addRecipe(CauldronColors.GREEN_EGG, CauldronColors.GREEN_EGG_BLOCK);
                addRecipe(CauldronColors.RED_EGG, CauldronColors.RED_EGG_BLOCK);
                addRecipe(CauldronColors.ORANGE_EGG, CauldronColors.ORANGE_EGG_BLOCK);
                addRecipe(CauldronColors.BLACK_EGG, CauldronColors.BLACK_EGG_BLOCK);
                addRecipe(CauldronColors.WHITE_EGG, CauldronColors.WHITE_EGG_BLOCK);
                addCopperCauldronRecipe();
            }

            private void addCopperCauldronRecipe() {
                shaped(
                        RecipeCategory.DECORATIONS,
                        CauldronColors.COPPER_CAULDRON.asItem()
                )
                        .pattern("C C")
                        .pattern("C C")
                        .pattern("CCC")
                        .define('C', Items.COPPER_INGOT)
                        .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                        .save(output);
            }

            private void addRecipe(Item egg, Block eggBlock) {
                shaped(
                        RecipeCategory.BUILDING_BLOCKS,
                        eggBlock.asItem()
                )
                        .pattern("EEE")
                        .pattern("E E")
                        .pattern("EEE")
                        .define('E', egg)
                        .unlockedBy("has_egg", has(egg))
                        .save(output);
            }
        };
    }

    @Override
    public String getName() {
        return "Cauldron Colors Recipes";
    }
}
