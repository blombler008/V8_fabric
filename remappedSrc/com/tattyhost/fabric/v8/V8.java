package com.tattyhost.fabric.v8;

import com.mojang.logging.LogUtils;
import com.tattyhost.fabric.v8.blocks.ModBlockEntityTypes;
import com.tattyhost.fabric.v8.blocks.ModBlocks;
import com.tattyhost.fabric.v8.blocks.ModMachines;
import com.tattyhost.fabric.v8.blocks.ModPlants;
import com.tattyhost.fabric.v8.items.ModItems;
import com.tattyhost.fabric.v8.items.ModTabs;
import com.tattyhost.fabric.v8.screens.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;


public class V8 implements ModInitializer {

    public static final String MOD_ID = "v8";
    public static Logger LOGGER = LogUtils.getLogger();

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing V8");
        ModItems.initialize();
        ModPlants.initialize();
        ModBlocks.initialize();
        ModMachines.initialize();
        ModTabs.initialize();
        ModBlockEntityTypes.initialize();
        ModScreenHandlers.initialize();
        LOGGER.info("V8 - Tobacco - initialized");
    }
}
