package com.tattyhost.fabric.v8.client.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModelGenerator::new);
        pack.addProvider(ItemTagProvider::new);
        pack.addProvider(BlockTagProvider::new);
        pack.addProvider(LootTableProvider::new);
        pack.addProvider(RecipeProvider::new);

    }

}