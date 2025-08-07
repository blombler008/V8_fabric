package com.tattyhost.fabric.v8.blocks.custom;

import com.tattyhost.fabric.v8.blocks.ModBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class AshTrayBlockEntity extends BlockEntity {
    public AshTrayBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.ASH_TRAY_BLOCK_ENTITY_TYPE, pos, state);
    }


}
