package com.tattyhost.fabric.v8.items;

import com.tattyhost.fabric.v8.V8;
import com.tattyhost.fabric.v8.blocks.ModPlants;
import com.tattyhost.fabric.v8.core.CigaretteStats;
import com.tattyhost.fabric.v8.core.ModDataComponents;
import com.tattyhost.fabric.v8.core.TobaccoStats;
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
@SuppressWarnings("unused")
public class ModItems {

    public static final List<Item> ITEMS = new ArrayList<>();
    public static final List<Item> VISIBLE_ITEMS = new ArrayList<>();
    public static final List<Item> NORMAL_VISIBLE_ITEMS = new ArrayList<>();

    public static final Item V8_ITEM = register("v8", false);

    // Magic Ash, ein spezielles Item, das durch das Rauchen von Zigaretten entsteht.
    public static final Item MAGIC_ASH_ITEM = register("magic_ash", true);

    // Tabakblätter, die von Tabakpflanzen geerntet werden.
    public static final Item TOBACCO_LEAF = register("tobacco_leaf", true);

    // Baumwolle, die von Baumwollpflanzen geerntet wird.
    public static final Item COTTON = register("cotton", true);

    // Getrocknete Tabakblätter, die nach dem Trocknungsprozess entstehen und weiterverarbeitet werden müssen.
    public static final Item TOBACCO_DRIED_LEAF = register("tobacco_dried_leaf", true);



    // TODO: texture
    // Allgemeiner getrockneter Tabak, Basis für weitere Verarbeitungsschritte.
    public static final Item TOBACCO = register(
            "tobacco",
            TobacooItem::new,
            new Item.Settings().component(ModDataComponents.TOBACCO_STATS, TobaccoStats.DEFAULT),
            true
    );

    // Zigarette, ein spezielles Item, das geraucht werden kann.
    public static final Item CIGARETTE_ITEM = register(
            "cigarette",
            CigaretteItem::new,
            new Item.Settings().component(ModDataComponents.CIGARETTE_STATS, CigaretteStats.DEFAULT),
            true
    );

    // TODO: texture
    // Zigarettenpapier, wird für das Drehen von Zigaretten benötigt.
    public static final Item CIGARETTE_PAPER = register("cigarette_paper", true);

    // TODO: texture
    // Standard-Zigarettenfilter.
    public static final Item CIGARETTE_FILTER = register("cigarette_filter", true);

    // TODO: texture
    // Leere Zigarettenhülse ohne Filter.
    public static final Item CIGARETTE_TUBE_EMPTY = register("cigarette_tube_empty", true);

    // TODO: texture
    // Vorgefertigte Zigarettenhülse.
    public static final Item CIGARETTE_TUBE = register("cigarette_tube", true);

    // TODO: texture
    // Leere Zigarettenhülse mit Filter.
    public static final Item CIGARETTE_TUBE_EMPTY_FILTER = register("cigarette_tube_empty_filter", true);

    // TODO: texture
    // Zigarettenhülse mit Filter.
    public static final Item CIGARETTE_TUBE_FILTER = register("cigarette_tube_filter", true);

    // TODO: texture ... maybe block? block model?
    // Aschenbecher zum Sammeln von Asche.
    public static final Item ASH_TRAY = register("ash_tray", AshTreyItem::new, true);

    // TODO: texture ... maybe block? block model?
    // Voller Aschenbecher mit gesammelter Asche.
    public static final Item ASH_TRAY_FULL = register("ash_tray_full", AshTreyItem::new, true);

    // TODO: texture
    // Eine Zigarettenschachtel als Container für 21 Zigaretten.
    public static final Item CIGARETTE_PACK = register("cigarette_pack", true);

    // TODO: texture
    // Leere Zigarettenschachtel.
    public static final Item CIGARETTE_PACK_EMPTY = register("cigarette_pack_empty", true);

    // TODO: texture
    // Ein Zigarettenkarton, der 10 Zigarettenschachteln enthält.
    public static final Item CIGARETTE_CARTON = register("cigarette_carton", true);

    // Aromen
    public static final Item MENTHOL_CRYSTALS = register("menthol_crystals", true);

    public static final Item VANILLA_EXTRACT = register("vanilla_extract", true);
    public static final Item APPLE_FLAVOR = register("apple_flavor", true);
    public static final Item CARROT_FLAVOR = register("carrot_flavor", true);
    public static final Item MELON_FLAVOR = register("melon_flavor", true);
    public static final Item POTATO_FLAVOR = register("potato_flavor", true);
    public static final Item PUMPKIN_FLAVOR = register("pumpkin_flavor", true);
    public static final Item GLOWBERRY_FLAVOR = register("glowberry_flavor", true);
    public static final Item SWEETBERRY_FLAVOR = register("sweetberry_flavor", true);
    public static final Item MUSHROOM_FLAVOR = register("mushroom_flavor", true);
    public static final Item HONEY_FLAVOR = register("honey_flavor", true);



    public static final Item TOBACCO_SEEDS = register("tobacco_seeds", createBlockItemWithUniqueName(ModPlants.TOBACCO_PLANT), true);
    public static final Item COTTON_SEEDS = register("cotton_seeds", createBlockItemWithUniqueName(ModPlants.COTTON_PLANT), true);


    public static Item register(String key, ItemConstructorFactory<Item> constructor, boolean visible) {
        RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(V8.MOD_ID, key));
        Item.Settings settings = new Item.Settings().registryKey(registryKey);

        return register(constructor.create(settings), registryKey, visible);
    }


    public static Item register(String key, ItemConstructorFactory<Item> constructor, Item.Settings settings, boolean visible) {
        RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(V8.MOD_ID, key));
        settings.registryKey(registryKey);

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

        Item item = register(settings, registryKey, visible);
        if(visible)
            NORMAL_VISIBLE_ITEMS.add(item);
        return item;
    }


    private static ItemConstructorFactory<Item> createBlockItemWithUniqueName(Block block) {
        return settings -> new BlockItem(block, settings.useItemPrefixedTranslationKey());
    }

    public static void initialize() {
    }

}
