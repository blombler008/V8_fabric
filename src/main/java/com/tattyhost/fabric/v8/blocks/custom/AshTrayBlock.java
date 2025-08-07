package com.tattyhost.fabric.v8.blocks.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class AshTrayBlock extends BlockWithEntity {

    // shape sorted: x z y
    // voxel: 10x10x4
    // voxel offset: 3, 3, 0
    public static final VoxelShape VOXEL_SHAPE = Block.createCuboidShape(3, 0, 3, 13, 4, 13);

    public AshTrayBlock(AbstractBlock.Settings settings) {
        super(settings);
    }



    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return VOXEL_SHAPE;
    }



    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(AshTrayBlock::new);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AshTrayBlockEntity(pos, state);
    }
}
