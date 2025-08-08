package com.tattyhost.fabric.v8.blocks;

import com.tattyhost.fabric.v8.blocks.custom.AshTrayBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;

import static com.tattyhost.fabric.v8.blocks.ModRegisters.register;

public class ModBlocks {

    public static final Block V8_BLOCK = register("v8_block", AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).hardness(1.0f).resistance(6.0f), true, true );
    public static final Block AMERITE_BLOCK = register("amerite_block", AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).hardness(2.0f).resistance(2.0f), true, true );


    private static final AbstractBlock.Settings ASH_TRAY_SETTINGS = AbstractBlock.Settings.create()
            .pistonBehavior(PistonBehavior.DESTROY)
            .mapColor(MapColor.STONE_GRAY)
            .nonOpaque()
            .breakInstantly()
            .noBlockBreakParticles()
            .allowsSpawning(Blocks::never)
            .solidBlock(Blocks::never)
            .suffocates(Blocks::never)
            .blockVision(Blocks::never);

    public static final Block ASH_TRAY = register("ash_tray", AshTrayBlock::new, ASH_TRAY_SETTINGS, false, false );

    public static void initialize() {}
}
