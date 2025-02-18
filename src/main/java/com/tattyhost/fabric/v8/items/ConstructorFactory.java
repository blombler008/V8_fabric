package com.tattyhost.fabric.v8.items;

import net.minecraft.item.Item;

import java.util.function.Function;

public interface ConstructorFactory<T extends Item> {
    T create(Item.Settings settings);

    static <T extends Item> ConstructorFactory<T> of(Function<Item.Settings, T> constructor) {
        return constructor::apply;
    }
}
