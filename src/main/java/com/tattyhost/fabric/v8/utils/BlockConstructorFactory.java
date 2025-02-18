package com.tattyhost.fabric.v8.utils;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;

import java.util.function.Function;

public interface BlockConstructorFactory<T extends Block> {
    T create(AbstractBlock.Settings settings);

    static <T extends Block> BlockConstructorFactory<T> of(Function<AbstractBlock.Settings, T> constructor) {
        return constructor::apply;
    }
}
