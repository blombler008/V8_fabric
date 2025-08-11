package com.tattyhost.fabric.v8.blocks.custom.plants;

import com.mojang.serialization.MapCodec;
import com.tattyhost.fabric.v8.items.ModItems;
import com.tattyhost.fabric.v8.utils.ModUtils;
import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;


public class CottonPlant extends CropBlock {
    public static final int MAX_AGE = 3; // Wachstumsstufen

    private static final double HEIGHTMAX = 14.0D;
    private static final double HEIGHTMIN = 2.0D;
    private static final boolean LINIAR = false;

    private static final VoxelShape[] AGE_TO_SHAPE = ModUtils.shapeOnHeights(HEIGHTMAX, HEIGHTMIN, MAX_AGE, LINIAR);


    public static final MapCodec<CottonPlant> CODEC = createCodec(CottonPlant::new);
    public static final IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);

    public CottonPlant(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<CottonPlant> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CottonPlant.AGE);
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.COTTON_SEEDS; // Das Item für neue Pflanzen
    }

    @Override
    protected IntProperty getAgeProperty() {
        return CottonPlant.AGE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isOf(Blocks.FARMLAND);
    }

//    For later use TODO: Implement custom soils
//    @Override
//    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
//        return floor.isOf(Blocks.FARMLAND);
//    }
    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[getAge(state)];
    }

}