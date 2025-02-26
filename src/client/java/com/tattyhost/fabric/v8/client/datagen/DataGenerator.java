package com.tattyhost.fabric.v8.client.datagen;

import com.tattyhost.fabric.v8.client.datagen.loots.LootTableProvider;
import com.tattyhost.fabric.v8.client.datagen.models.ModelGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();




        pack.addProvider(ItemTagProvider::new);
        pack.addProvider(BlockTagProvider::new);
        LootTableProvider.addLoots(pack);
        pack.addProvider(RecipeProvider::new);

        ModelGenerator.addModels(pack);
    }

}