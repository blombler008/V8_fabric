package com.tattyhost.fabric.v8.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.tattyhost.fabric.v8.blocks.ModRegisters.register;

public class ModBlocks {

    public static final Block V8_BLOCK = register("v8_block", AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).hardness(1.0f).resistance(6.0f), true, true );
    public static final Block AMERITE_BLOCK = register("amerite_block", AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).hardness(2.0f).resistance(2.0f), true, true );

    public static void initialize() {}
}
