package com.tattyhost.fabric.v8.blocks.custom.plants;

import com.tattyhost.fabric.v8.items.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class TobaccoPlant extends CropBlock {
    public static final int MAX_AGE = 5; // Anzahl der Wachstumsstufen

    public static IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);

    public TobaccoPlant(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.TOBACCO_SEEDS; // Das Item für neue Pflanzen
    }

    @Override
    protected IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isOf(Blocks.FARMLAND);
    }
}
