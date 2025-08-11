package com.tattyhost.fabric.v8.blocks;

import com.tattyhost.fabric.v8.blocks.custom.plants.CottonPlant;
import com.tattyhost.fabric.v8.blocks.custom.plants.TobaccoPlant;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;

import static com.tattyhost.fabric.v8.blocks.ModRegisters.register;

public class ModPlants {


    public static final Block TOBACCO_PLANT = register("tobacco", TobaccoPlant::new, AbstractBlock.Settings.create()

            .mapColor(state -> state.get(TobaccoPlant.AGE) >= TobaccoPlant.MAX_AGE-1 ? MapColor.YELLOW : MapColor.DARK_GREEN)
            .noCollision()
            .nonOpaque()
            .ticksRandomly()
            .breakInstantly()
            .sounds(BlockSoundGroup.CROP)
            .pistonBehavior(PistonBehavior.DESTROY), false, false );

    public static final Block COTTON_PLANT = register("cotton", CottonPlant::new, AbstractBlock.Settings.create()
            .mapColor(state -> {
                int age = state.get(CottonPlant.AGE);
                boolean fullyGrown = (age == CottonPlant.MAX_AGE);
                boolean isFlowering = (age <= 1);
                boolean isSeeding = (age == 2);
                return (fullyGrown ? MapColor.WHITE : (isFlowering ? MapColor.YELLOW : (isSeeding ? MapColor.LIGHT_BLUE : MapColor.GREEN)));
            })
            .noCollision()
            .nonOpaque()
            .ticksRandomly()
            .breakInstantly()
            .sounds(BlockSoundGroup.CROP)
            .pistonBehavior(PistonBehavior.DESTROY), false, false );

    public static void initialize() {

    }

}
