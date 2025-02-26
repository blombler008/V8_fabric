package com.tattyhost.fabric.v8.client;

import com.tattyhost.fabric.v8.blocks.ModPlants;
import com.tattyhost.fabric.v8.client.screens.GuenterScreen;
import com.tattyhost.fabric.v8.client.screens.HighTempFurnaceScreen;
import com.tattyhost.fabric.v8.screens.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

public class V8Client implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModPlants.TOBACCO_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModPlants.COTTON_PLANT, RenderLayer.getCutout());
        HandledScreens.register(ModScreenHandlers.HIGH_TEMP_FURNACE_SCREEN_HANDLER, HighTempFurnaceScreen::new);
        HandledScreens.register(ModScreenHandlers.GUENTER_SCREEN_HANDLER, GuenterScreen::new);
    }
}
