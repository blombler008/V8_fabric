package com.tattyhost.fabric.v8.client.datagen.models;

import com.tattyhost.fabric.v8.V8;
import com.tattyhost.fabric.v8.blocks.ModBlocks;
import com.tattyhost.fabric.v8.blocks.ModMachines;
import com.tattyhost.fabric.v8.blocks.custom.HighTempFurnaceBlock;
import com.tattyhost.fabric.v8.items.ModItems;
import com.tattyhost.fabric.v8.utils.Strings;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.property.select.DisplayContextProperty;
import net.minecraft.item.Item;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.List;

public class ModelGenerator extends FabricModelProvider {
    public ModelGenerator(FabricDataOutput output) {
        super(output);
    }

    public static void addModels(FabricDataGenerator.Pack pack) {
        pack.addProvider(ModelGenerator::new);
        pack.addProvider(PlantModelGenerator::new);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        generator.registerSimpleCubeAll(ModBlocks.V8_BLOCK);
        generator.registerSimpleCubeAll(ModBlocks.AMERITE_BLOCK);
        generator.registerSimpleCubeAll(ModMachines.GUENTER);
        generator.registerSimpleCubeAll(ModMachines.DEDLEF);
        highTempFurnace(generator);
        // Custom block model on lower half of block suffix with _lower and upper half of block suffix with _upper
        // on High Temp Furnace
        // also register facing property for block state


    }
    private void highTempFurnace(BlockStateModelGenerator generator) {
        HighTempFurnaceBlock block = (HighTempFurnaceBlock) ModMachines.HIGH_TEMP_FURNACE;

        VariantsBlockStateSupplier supplier = VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateVariantMap.create(Properties.LIT, Properties.HORIZONTAL_FACING, Properties.DOUBLE_BLOCK_HALF)
                        .register((lit, face, half) -> {
                            String halfStr = half == DoubleBlockHalf.LOWER ? "_lower" : "_upper";
//                            int rot = ((face.getHorizontalQuarterTurns() + 2) % 4) * 90; // Rotation berechnen
                            VariantSettings.Rotation rotation = getRotationForFace(face);
                            final String s = Strings.BLOCK_HIGH_TEMP_FURNACE_NAME + halfStr;
                            Identifier modelId = Identifier.of(V8.MOD_ID, "block/" + s);

                            return BlockStateVariant.create()
                                    .put(VariantSettings.MODEL, modelId)
                                    .put(VariantSettings.Y, rotation);
                        })
                );

        generator.blockStateCollector.accept(supplier);
        final String s = Strings.BLOCK_HIGH_TEMP_FURNACE_NAME + "_item";
        Identifier itemModelId = Identifier.of(V8.MOD_ID, "block/" + s);
        generator.registerParentedItemModel(ModMachines.HIGH_TEMP_FURNACE, itemModelId);
    }


    public static ItemModel.Unbaked createModelWithInHandAndGroundVariant(ItemModel.Unbaked model, ItemModel.Unbaked inHandModel, @SuppressWarnings("unused") ItemModel.Unbaked inUseModel) {
        DisplayContextProperty displayContextProperty = new DisplayContextProperty();
//        ItemModels.usingItemProperty();

        return ItemModels.select(displayContextProperty, inHandModel,
                ItemModels.switchCase(List.of(ModelTransformationMode.GUI, ModelTransformationMode.FIXED),
                        model) // GUI und FIXED uses normal model

        );
    }

    public final void registerWithInHandModel(ItemModelGenerator generator, Item item) {
        ItemModel.Unbaked unbaked = ItemModels.basic(generator.upload(item, Models.GENERATED));
        ItemModel.Unbaked unbakedInHand = ItemModels.basic(ModelIds.getItemSubModelId(item, "_in_hand"));
//        ItemModel.Unbaked unbakedInUse = ItemModels.basic(ModelIds.getItemSubModelId(item, "_in_use"));

        generator.output.accept(item, createModelWithInHandAndGroundVariant(unbaked, unbakedInHand, null/*unbakedInUse*/));
    }

    // Methode zur Berechnung der Rotation
    public VariantSettings.Rotation getRotationForFace(Direction face) {
        return switch (face) {
            case NORTH -> VariantSettings.Rotation.R0;
            case EAST -> VariantSettings.Rotation.R90;
            case SOUTH -> VariantSettings.Rotation.R180;
            case WEST -> VariantSettings.Rotation.R270;
            default -> VariantSettings.Rotation.R0; // Default falls was schiefgeht
        };
    }
    @Override
    public void generateItemModels(ItemModelGenerator generator) {

        generator.register(ModItems.V8_ITEM, Models.GENERATED);
        registerWithInHandModel(generator, ModItems.CIGARETTE_ITEM);

        for (Item item : ModItems.NORMAL_VISIBLE_ITEMS) {
            generator.register(item, Models.GENERATED);
        }

//        generator.register(ModBlocks.V8_BLOCK.asItem(), Models.CUBE_ALL);
    }

    @Override
    public String getName() {
        return "General Model Definitions";
    }


}
