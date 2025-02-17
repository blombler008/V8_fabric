package com.tattyhost.fabric.v8.client.datagen;

import com.tattyhost.fabric.v8.blocks.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class BlockTagProvider extends FabricTagProvider<Block> {

    public static final TagKey<Block> NEEDS_WOODEN_TOOL = TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla("needs_tool_level_0"));
    public static final TagKey<Block> NEEDS_STONE_TOOL = BlockTags.NEEDS_STONE_TOOL;
    public static final TagKey<Block> NEEDS_IRON_TOOL = BlockTags.NEEDS_IRON_TOOL;
    public static final TagKey<Block> NEEDS_DIAMOND_TOOL = BlockTags.NEEDS_DIAMOND_TOOL;
    public static final TagKey<Block> NEEDS_NETHERITE_TOOL = TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla("needs_tool_level_4"));


    public BlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BLOCK, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        mineableTag(ModBlocks.V8_BLOCK, BlockTags.PICKAXE_MINEABLE, NEEDS_IRON_TOOL);
        mineableTag(ModBlocks.AMERITE_BLOCK, BlockTags.PICKAXE_MINEABLE, NEEDS_IRON_TOOL);
        mineableTag(ModBlocks.HIGH_TEMP_FURNACE, BlockTags.PICKAXE_MINEABLE, NEEDS_IRON_TOOL);
        mineableTag(ModBlocks.GUENTER, BlockTags.PICKAXE_MINEABLE, NEEDS_DIAMOND_TOOL);
        mineableTag(ModBlocks.DEDLEF, BlockTags.PICKAXE_MINEABLE, NEEDS_NETHERITE_TOOL);
    }


    private void mineableTag(Block block, TagKey<Block> toolTag, TagKey<Block> materialTag) {
        if(materialTag == NEEDS_NETHERITE_TOOL) {
            getOrCreateTagBuilder(toolTag).add(block);
            this.getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(block);
//            this.getOrCreateTagBuilder(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).add(block);
            this.getOrCreateTagBuilder(BlockTags.INCORRECT_FOR_DIAMOND_TOOL).add(block);
            this.getOrCreateTagBuilder(BlockTags.INCORRECT_FOR_IRON_TOOL).add(block);
            this.getOrCreateTagBuilder(BlockTags.INCORRECT_FOR_STONE_TOOL).add(block);
            this.getOrCreateTagBuilder(BlockTags.INCORRECT_FOR_GOLD_TOOL).add(block);
            this.getOrCreateTagBuilder(BlockTags.INCORRECT_FOR_WOODEN_TOOL).add(block);

            return;
        }

        if (materialTag == NEEDS_WOODEN_TOOL) {
            getOrCreateTagBuilder(toolTag).add(block);
            this.getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(block);
            this.getOrCreateTagBuilder(BlockTags.INCORRECT_FOR_STONE_TOOL).add(block);
            return;
        }


        getOrCreateTagBuilder(toolTag).add(block);
        getOrCreateTagBuilder(materialTag).add(block);
    }

}
