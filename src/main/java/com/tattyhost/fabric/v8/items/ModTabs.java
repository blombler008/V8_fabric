package com.tattyhost.fabric.v8.items;

import com.tattyhost.fabric.v8.V8;
import com.tattyhost.fabric.v8.utils.Strings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModTabs {

    public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(V8.MOD_ID, "item_group"));
    private static Item GROUP_ICON = ModItems.V8_ITEM;
    public static ItemGroup CUSTOM_ITEM_GROUP  = FabricItemGroup.builder()
            .icon(() -> new ItemStack(GROUP_ICON))
            .displayName(Text.translatable(Strings.ITEM_GROUP_V8_TRANSLATION_KEY))
            .entries((displayContext, entries) -> ModItems.VISIBLE_ITEMS.forEach(entries::add))
            .build();


    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);

    }
}
