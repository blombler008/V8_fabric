package com.tattyhost.fabric.v8.blocks;

import com.tattyhost.fabric.v8.V8;
import com.tattyhost.fabric.v8.items.ModItems;
import com.tattyhost.fabric.v8.utils.BlockConstructorFactory;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModRegisters {


    public static Block register(String key, BlockConstructorFactory<Block> constructor, AbstractBlock.Settings settings, boolean shouldRegisterItem, boolean visible) {
        Identifier identifier = Identifier.of(V8.MOD_ID, key);
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, identifier);

        return register(constructor.create(settings.registryKey(blockKey)), blockKey, shouldRegisterItem, visible);
    }

    public static Block register(String key, BlockConstructorFactory<Block> constructor, boolean shouldRegisterItem, boolean visible) {
        Identifier identifier = Identifier.of(V8.MOD_ID, key);
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, identifier);

        Block block = constructor.create(
                AbstractBlock.Settings.copy(Blocks.STONE)
                        .hardness(2.0f)
                        .resistance(2.0f)
                        .nonOpaque()
                        .registryKey(blockKey)
        );

        return register(block, blockKey, shouldRegisterItem, visible);
    }

    public static Block register(String key, AbstractBlock.Settings blockSettings, boolean shouldRegisterItem, boolean visible) {
        Identifier identifier = Identifier.of(V8.MOD_ID, key);
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, identifier);

        Block block = new Block(blockSettings.registryKey(blockKey));

        return register(block, blockKey, shouldRegisterItem, visible);
    }

    public static Block register(Block block, RegistryKey<Block> blockKey, boolean shouldRegisterItem, boolean visible) {

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            // Items need to be registered with a different type of registry key, but the ID
            // can be the same.
            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());

            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
            Registry.register(Registries.ITEM, itemKey, blockItem);

            ModItems.ITEMS.add(blockItem);
            if (visible) {
                ModItems.VISIBLE_ITEMS.add(blockItem);
            }
        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }


}
