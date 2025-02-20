package com.tattyhost.fabric.v8.items;

import com.tattyhost.fabric.v8.V8;
import com.tattyhost.fabric.v8.blocks.ModBlocks;
import com.tattyhost.fabric.v8.utils.ItemConstructorFactory;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static final List<Item> ITEMS = new ArrayList<>();
    public static final List<Item> VISIBLE_ITEMS = new ArrayList<>();



    public static final Item CIGARETTE_ITEM = register("cigarette", CigaretteItem::new, true);
    public static final Item V8_ITEM = register("v8", false);
    public static final Item MAGIC_ASH_ITEM = register("magic_ash", true);

    public static final Item TOBACCO_SEEDS = register("tobacco_seeds", createBlockItemWithUniqueName(ModBlocks.TOBACCO_PLANT), true);


    public static Item register(String key, ItemConstructorFactory<Item> constructor, boolean visible) {
        RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(V8.MOD_ID, key));
        Item.Settings settings = new Item.Settings().registryKey(registryKey);

        return register(constructor.create(settings), registryKey, visible);
    }

    public static Item register(Item key, RegistryKey<Item> registryKey, boolean visible) {
        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, registryKey.getValue(), key);

        //V8.LOGGER.info("Registered item: " + registeredItem);
        // If the item is visible, add it to the list of visible items.
        if (visible) {
            //V8.LOGGER.info("Adding item to visible items: " + registeredItem);
            VISIBLE_ITEMS.add(registeredItem);
        }
        // Add the item to the list of items.

        ITEMS.add(registeredItem);
        // Return the registered item!

        return registeredItem;
    }


    public static Item register(Item.Settings itemSettings, RegistryKey<Item> registryKey, boolean visible) {
        // Register the item.
        Item item = new Item(itemSettings);
        return register(item, registryKey, visible);
    }

    public static Item register(String name, boolean visible) {
        RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(V8.MOD_ID, name));
        Item.Settings settings = new Item.Settings().registryKey(registryKey);

        return register(settings, registryKey, visible);
    }


    private static ItemConstructorFactory<Item> createBlockItemWithUniqueName(Block block) {
        return settings -> new BlockItem(block, settings.useItemPrefixedTranslationKey());
    }

    public static void initialize() {
    }

}
