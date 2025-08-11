package com.tattyhost.fabric.v8.utils;

import net.minecraft.item.Item;

import java.util.function.Function;

public interface ItemConstructorFactory<T extends Item> {
    T create(Item.Settings settings);

    static <T extends Item> ItemConstructorFactory<T> of(Function<Item.Settings, T> constructor) {
        return constructor::apply;
    }
}
