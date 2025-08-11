package com.tattyhost.fabric.v8.blocks.custom;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.tattyhost.fabric.v8.blocks.ModBlockEntityTypes;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

import static com.tattyhost.fabric.v8.blocks.custom.HighTempFurnaceBlockEntity.INPUT_SLOT;
import static com.tattyhost.fabric.v8.blocks.custom.HighTempFurnaceBlockEntity.OUTPUT_SLOT;

public class HighTempFurnaceBlock extends BlockWithEntity  {

    public static final BooleanProperty LIT = Properties.LIT;
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final Property<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;

    private static final VoxelShape VOXEL_SHAPE_LOWER = Block.createCuboidShape(0, 0, 0, 16, 16, 16);
    private static final VoxelShape VOXEL_SHAPE_UPPER = Block.createCuboidShape(0, 0, 0, 16, 8, 16);

    public HighTempFurnaceBlock(AbstractBlock.Settings settings) {
        super(settings.strength(1.0f,2.0f).nonOpaque().requiresTool());

        setDefaultState(getStateManager().getDefaultState().with(LIT, false).with(FACING, Direction.NORTH).with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(HighTempFurnaceBlock::new);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {

        if(state.get(HALF) == DoubleBlockHalf.UPPER) {
            return;
        }
        if(pos.up().getY() < 256 && world.getBlockState(pos.up()).isReplaceable()) {
            world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), 3);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        boolean notBrocken = state.isOf(newState. getBlock());
        if(notBrocken) {
            return;
        }

        ItemScatterer.spawn(world, pos, (HighTempFurnaceBlockEntity)world.getBlockEntity(pos));
        world.updateComparators(pos,this);
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if(state.get(HALF) == DoubleBlockHalf.LOWER) {
            if(world.getBlockState(pos.up()).getBlock() == this) {
                world.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 3);
            }
            return;
        }

        if(world.getBlockState(pos.down()).getBlock() == this) {
            world.setBlockState(pos.down(), Blocks.AIR.getDefaultState(), 3);
        }

    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        DoubleBlockHalf half = state.get(HALF);
        return switch (half) {
            case LOWER -> VOXEL_SHAPE_LOWER;
            case UPPER -> VOXEL_SHAPE_UPPER;
            default -> VoxelShapes.fullCube();
        };
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.LIT, Properties.HORIZONTAL_FACING, Properties.DOUBLE_BLOCK_HALF);

    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HighTempFurnaceBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        // Make sure to check world.isClient if you only want to tick only on serverside.

        if(world.isClient) {
            return null;
        }

        if(state.get(HALF) == DoubleBlockHalf.UPPER) {
            return null;
        }
        return validateTicker(type, ModBlockEntityTypes.HIGH_TEMP_FURNACE_BLOCK_ENTITY_TYPE, HighTempFurnaceBlockEntity::tick);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            BlockPos blockPos = pos;
            if (state.get(HALF) == DoubleBlockHalf.UPPER) {
                blockPos = pos.down();
                state = world.getBlockState(blockPos);
            }
            // This will call the createScreenHandlerFactory method from BlockWithEntity, which will return our blockEntity casted to
            // a namedScreenHandlerFactory. If your block class does not extend BlockWithEntity, it needs to implement createScreenHandlerFactory.
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, blockPos);

            if (screenHandlerFactory != null) {
                // With this call the server will request the client to open the appropriate Screenhandler
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

}
