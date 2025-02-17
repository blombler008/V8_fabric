package com.tattyhost.fabric.v8.client;

import com.tattyhost.fabric.v8.client.screens.HighTempFurnaceScreen;
import com.tattyhost.fabric.v8.screens.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class V8Client implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.HIGH_TEMP_FURNACE_SCREEN_HANDLER, HighTempFurnaceScreen::new);
    }
}
