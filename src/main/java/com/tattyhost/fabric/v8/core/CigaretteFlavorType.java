package com.tattyhost.fabric.v8.core;

public enum CigaretteFlavorType {
    NONE,
    MENTHOL_CRYSTALS,
    VANILLA_EXTRACT,
    APPLE_FLAVOR,
    CARROT_FLAVOR,
    MELON_FLAVOR,
    POTATO_FLAVOR,
    PUMPKIN_FLAVOR,
    GLOWBERRY_FLAVOR,
    SWEETBERRY_FLAVOR,
    MUSHROOM_FLAVOR,
    HONEY_FLAVOR;

    @Override
    public String toString() {
        return name().toLowerCase();
    }


    public static CigaretteFlavorType fromString(String string) {
        return valueOf(string.toUpperCase());
    }

}
