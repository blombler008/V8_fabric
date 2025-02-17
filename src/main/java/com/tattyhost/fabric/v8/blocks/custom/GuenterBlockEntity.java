package com.tattyhost.fabric.v8.blocks.custom;

import com.tattyhost.fabric.v8.blocks.ModBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class GuenterBlockEntity extends BlockEntity {

    public GuenterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public GuenterBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(ModBlockEntityTypes.GUENTER_BLOCK_ENTITY_TYPE, blockPos, blockState);
    }



}

