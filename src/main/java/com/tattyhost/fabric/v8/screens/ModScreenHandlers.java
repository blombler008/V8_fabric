package com.tattyhost.fabric.v8.screens;

import com.tattyhost.fabric.v8.blocks.ModBlocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static final ScreenHandlerType<HighTempFurnaceScreenHandler> HIGH_TEMP_FURNACE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,
            ModBlocks.HIGH_TEMP_FURNACE_ID,
            new ScreenHandlerType<>(HighTempFurnaceScreenHandler::new, FeatureSet.empty())
    );



    public static void initialize() {
    }


}
