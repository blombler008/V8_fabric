package com.tattyhost.fabric.v8.client;

import com.tattyhost.fabric.v8.blocks.ModBlockEntityTypes;
import com.tattyhost.fabric.v8.blocks.ModPlants;
import com.tattyhost.fabric.v8.client.model.AshTrayModel;
import com.tattyhost.fabric.v8.client.screens.GuenterScreen;
import com.tattyhost.fabric.v8.client.screens.HighTempFurnaceScreen;
import com.tattyhost.fabric.v8.client.renderer.AshTrayBER;
import com.tattyhost.fabric.v8.screens.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class V8Client implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // Register Block Render Layers
        BlockRenderLayerMap.INSTANCE.putBlock(ModPlants.TOBACCO_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModPlants.COTTON_PLANT, RenderLayer.getCutout());

        //
        BlockEntityRendererFactories.register(ModBlockEntityTypes.ASH_TRAY_BLOCK_ENTITY_TYPE, AshTrayBER::new);
        EntityModelLayerRegistry.registerModelLayer(AshTrayModel.LAYER, AshTrayModel::getTexturedModelData);


        // Register Screens
        HandledScreens.register(ModScreenHandlers.HIGH_TEMP_FURNACE_SCREEN_HANDLER, HighTempFurnaceScreen::new);
        HandledScreens.register(ModScreenHandlers.GUENTER_SCREEN_HANDLER, GuenterScreen::new);
    }
}
