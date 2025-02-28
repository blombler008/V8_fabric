package com.tattyhost.fabric.v8.core;

import com.tattyhost.fabric.v8.V8;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModDataComponents {

    // Registriere die Zigaretten-Statistik als eine Datenkomponente
    public static final ComponentType<CigaretteStats> CIGARETTE_STATS = register(
            "cigarette_stats", ComponentType.<CigaretteStats>builder().codec(CigaretteStats.CODEC).build()
    );

    public static final ComponentType<TobaccoStats> TOBACCO_STATS = register(
            "tobacco_stats", ComponentType.<TobaccoStats>builder().codec(TobaccoStats.CODEC).build()
    );

    public static <T> ComponentType<T> register(String id, ComponentType<T> componentType) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(V8.MOD_ID, id), componentType);
    }

    public static void initialize() {}
}
