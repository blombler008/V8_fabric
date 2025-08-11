package com.tattyhost.fabric.v8.core;

import com.tattyhost.fabric.v8.V8;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModDataComponents {

    // Registriere die Zigaretten-Statistik als eine Datenkomponente
    public static final ComponentType<TobaccoStats> TOBACCO_STATS = Registry.register(
            Registries.DATA_COMPONENT_TYPE, V8.id("tobacco_stats"),
            ComponentType.<TobaccoStats>builder().codec(TobaccoStats.CODEC).build()
    );

    public static final ComponentType<CigaretteCore> CIGARETTE_CORE = Registry.register(
            Registries.DATA_COMPONENT_TYPE, V8.id("cigarette_core"),
            ComponentType.<CigaretteCore>builder().codec(CigaretteCore.CODEC).build()
    );

    public static final ComponentType<CigaretteBurn> CIGARETTE_BURN = Registry.register(
            Registries.DATA_COMPONENT_TYPE, V8.id("cigarette_burn"),
            ComponentType.<CigaretteBurn>builder().codec(CigaretteBurn.CODEC).build()
    );

    public static <T> ComponentType<T> register(String id, ComponentType<T> componentType) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(V8.MOD_ID, id), componentType);
    }

    public static void initialize() {}
}
