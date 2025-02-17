package com.tattyhost.fabric.v8.items;

import com.tattyhost.fabric.v8.V8;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SpyglassItem;
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

    public static final Identifier V8_ITEM_IDENTIFIER = Identifier.of(V8.MOD_ID, "v8");
    public static final RegistryKey<Item> V8_ITEM_KEY = RegistryKey.of(RegistryKeys.ITEM, V8_ITEM_IDENTIFIER);
    public static final Item.Settings V8_ITEM_SETTINGS = new Item.Settings().registryKey(V8_ITEM_KEY);
    public static final Item V8_ITEM = register(V8_ITEM_SETTINGS, V8_ITEM_KEY, false);


    public static final Identifier MAGIC_ASH_ITEM_IDENTIFIER = Identifier.of(V8.MOD_ID, "magic_ash");
    public static final RegistryKey<Item> MAGIC_ASH_ITEM_KEY = RegistryKey.of(RegistryKeys.ITEM, MAGIC_ASH_ITEM_IDENTIFIER);
    public static final Item.Settings MAGIC_ASH_ITEM_SETTINGS = new Item.Settings().registryKey(MAGIC_ASH_ITEM_KEY);
    public static final Item MAGIC_ASH_ITEM = register(MAGIC_ASH_ITEM_SETTINGS, MAGIC_ASH_ITEM_KEY, true);

    public static final Identifier CIGARETTE_ITEM_IDENTIFIER = Identifier.of(V8.MOD_ID, "cigarette");
    public static final RegistryKey<Item> CIGARETTE_ITEM_KEY = RegistryKey.of(RegistryKeys.ITEM, CIGARETTE_ITEM_IDENTIFIER);
    public static final Item.Settings CIGARETTE_ITEM_SETTINGS = new Item.Settings().registryKey(CIGARETTE_ITEM_KEY);
    public static final Item CIGARETTE_ITEM = registerCustom(new CigaretteItem(CIGARETTE_ITEM_SETTINGS), CIGARETTE_ITEM_KEY, true);

    public static Item registerCustom(Item item, RegistryKey<Item> registryKey, boolean visible) {
        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, registryKey.getValue(), item);

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
        return registerCustom(item, registryKey, visible);
    }


    public static void initialize() {
    }

}
