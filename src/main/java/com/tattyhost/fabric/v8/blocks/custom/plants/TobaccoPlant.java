package com.tattyhost.fabric.v8.blocks.custom.plants;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.tattyhost.fabric.v8.items.ModItems;
import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class TobaccoPlant extends CropBlock {
    public static final int MAX_AGE = 9; // Anzahl der Wachstumsstufen

    private static final double HEIGHTMAX = 16.0D;
    private static final double HEIGHTMIN = 2.0D;
    private static final boolean LINIAR = false;

    private static final VoxelShape[] AGE_TO_SHAPE = shapeOnHeights();

    private static VoxelShape[] shapeOnHeights() {

        VoxelShape[] shapes = new VoxelShape[MAX_AGE+1];
        for(int j = 0; j <= MAX_AGE; ++j) {
            double height = HEIGHTMIN;
            double lin = LINIAR ? (HEIGHTMIN + (HEIGHTMAX - HEIGHTMIN) * (double)j / (double)MAX_AGE) : (HEIGHTMAX * (double)j / (double)MAX_AGE);
            if(lin > HEIGHTMIN) {
                height = lin;
            }

            shapes[j] = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, height, 16.0D);
            LogUtils.getLogger().info("Shape " + j + " = " + shapes[j]);
        }
        return shapes;
    }

    public static final MapCodec<TobaccoPlant> CODEC = createCodec(TobaccoPlant::new);

    public static final IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);

    public TobaccoPlant(Settings settings) {
        super(settings);

    }

    @Override
    public MapCodec<TobaccoPlant> getCodec() {
        return CODEC;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
//        super.appendProperties(builder);
        builder.add(TobaccoPlant.AGE);
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
        return TobaccoPlant.AGE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isOf(Blocks.FARMLAND);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.FARMLAND);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[getAge(state)];
    }
}
