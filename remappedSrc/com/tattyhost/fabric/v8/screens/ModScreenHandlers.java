package com.tattyhost.fabric.v8.screens;

import com.tattyhost.fabric.v8.V8;
import com.tattyhost.fabric.v8.utils.Strings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<HighTempFurnaceScreenHandler> HIGH_TEMP_FURNACE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,
            Identifier.of(V8.MOD_ID, Strings.BLOCK_HIGH_TEMP_FURNACE_NAME),
            new ScreenHandlerType<>(HighTempFurnaceScreenHandler::new, FeatureSet.empty())
    );

    public static final ScreenHandlerType<GuenterScreenHandler> GUENTER_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,
            Identifier.of(V8.MOD_ID, Strings.BLOCK_GUENTER_NAME),
            new ScreenHandlerType<>(GuenterScreenHandler::new, FeatureSet.empty())
    );




    public static void initialize() {
    }


}
