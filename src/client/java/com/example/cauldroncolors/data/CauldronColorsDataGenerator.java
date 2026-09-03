package com.example.cauldroncolors.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CauldronColorsDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack =
                fabricDataGenerator.createPack();

        pack.addProvider(CauldronColorsModelProvider::new);
        pack.addProvider(CauldronColorsRecipeProvider::new);
        pack.addProvider(CauldronColorsLootTableProvider::new);

    }
}
