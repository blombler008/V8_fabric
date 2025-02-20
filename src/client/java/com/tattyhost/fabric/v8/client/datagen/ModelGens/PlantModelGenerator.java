package com.tattyhost.fabric.v8.client.datagen.ModelGens;

import com.tattyhost.fabric.v8.blocks.ModBlocks;
import com.tattyhost.fabric.v8.blocks.custom.plants.TobaccoPlant;
import com.tattyhost.fabric.v8.items.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;

public class PlantModelGenerator extends FabricModelProvider {
    public PlantModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        registerCrop(blockStateModelGenerator, ModBlocks.TOBACCO_PLANT, TobaccoPlant.AGE, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    public final void registerCrop(BlockStateModelGenerator blockStateModelGenerator, Block crop, Property<Integer> ageProperty, int... ageTextureIndices) {
        if (ageProperty.getValues().size() != ageTextureIndices.length) {
            throw new IllegalArgumentException();
        } else {
            Int2ObjectMap<Identifier> int2ObjectMap = new Int2ObjectOpenHashMap<>();
            BlockStateVariantMap blockStateVariantMap = BlockStateVariantMap.create(ageProperty).register((integer) -> {
                int i = ageTextureIndices[integer];
                Identifier identifier = int2ObjectMap.computeIfAbsent(i, (j) -> {

                    // changed from Texturemap::crop to Texturemap::cross
                    return blockStateModelGenerator.createSubModel(crop, "_stage" + i, Models.CROSS, TextureMap::cross);
                });
                return BlockStateVariant.create().put(VariantSettings.MODEL, identifier);
            });
            blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(crop).coordinate(blockStateVariantMap));
        }
    }


    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.TOBACCO_SEEDS, Models.GENERATED);
    }


    @Override
    public String getName() {
        return "Plant Model Definitions";
    }
}
