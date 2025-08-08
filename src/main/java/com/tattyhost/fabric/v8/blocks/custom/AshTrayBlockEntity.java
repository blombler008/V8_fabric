package com.tattyhost.fabric.v8.blocks.custom;

import com.tattyhost.fabric.v8.blocks.ModBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AshTrayBlockEntity extends BlockEntity {
    public AshTrayBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.ASH_TRAY_BLOCK_ENTITY_TYPE, pos, state);
    }

    public int getAshLevel() {
        return getCachedState().getOrEmpty(AshTrayBlock.LEVEL).orElse(0);
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, AshTrayBlockEntity blockEntity) {

    }

}
