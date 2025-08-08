package com.tattyhost.fabric.v8.blocks.custom;

import com.mojang.serialization.MapCodec;
import com.tattyhost.fabric.v8.blocks.ModBlockEntityTypes;
import com.tattyhost.fabric.v8.items.ModItems;
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
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AshTrayBlock extends BlockWithEntity {

    // shape sorted: x z y
    // voxel: 10x10x4
    // voxel offset: 3, 3, 0
    public static final VoxelShape VOXEL_SHAPE = Block.createCuboidShape(3, 0, 3, 13, 4, 13);
    public static final IntProperty LEVEL = Properties.LEVEL_8;


    public AshTrayBlock(AbstractBlock.Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(LEVEL, 0));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        ItemStack stack = ctx.getStack();
        int level = 0;

        if (stack.isOf(ModItems.ASH_TRAY_FULL)) {
            level = 8;
        }
        return this.getDefaultState().with(LEVEL, level);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return VOXEL_SHAPE;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        // Make sure to check world.isClient if you only want to tick only on serverside.
        if(world.isClient) {
            return null;
        }

        if(state.get(LEVEL) == 8) {
            return null;
        }

        return validateTicker(type, ModBlockEntityTypes.ASH_TRAY_BLOCK_ENTITY_TYPE, AshTrayBlockEntity::tick);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        // check Ash tray level on place
        // e.g. when full ashtray is placed make the level to be 8

    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
//            // This will call the createScreenHandlerFactory method from BlockWithEntity, which will return our blockEntity casted to
//            // a namedScreenHandlerFactory. If your block class does not extend BlockWithEntity, it needs to implement createScreenHandlerFactory.
//            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
//
//            if (screenHandlerFactory != null) {
//                // With this call the server will request the client to open the appropriate Screenhandler
//                player.openHandledScreen(screenHandlerFactory);
//            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
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
