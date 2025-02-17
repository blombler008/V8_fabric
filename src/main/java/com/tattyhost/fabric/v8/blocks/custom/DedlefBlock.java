package com.tattyhost.fabric.v8.blocks.custom;


import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DedlefBlock extends Block {
    public DedlefBlock(AbstractBlock.Settings settings) {
        super(settings.requiresTool().strength(5.0f, 12.0f));

    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (player.preferredHand == Hand.MAIN_HAND) {
            if (!world.isClient) {
                player.giveItemStack(new ItemStack(Items.BREAD));
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}