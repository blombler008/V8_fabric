package com.tattyhost.fabric.v8;

import com.tattyhost.fabric.v8.blocks.ModBlockEntityTypes;
import com.tattyhost.fabric.v8.blocks.ModBlocks;
import com.tattyhost.fabric.v8.items.ModItems;
import com.tattyhost.fabric.v8.items.ModTabs;
import com.tattyhost.fabric.v8.screens.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class V8 implements ModInitializer {

    public static final String MOD_ID = "v8";
    public static Logger LOGGER = Logger.getLogger(MOD_ID);


    @Override
    public void onInitialize() {
        ModItems.initialize();
        ModBlocks.initialize();
        ModTabs.initialize();
        ModBlockEntityTypes.initialize();
        ModScreenHandlers.initialize();

    }
}
