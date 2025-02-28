package com.tattyhost.fabric.v8.core;

public enum TobaccoType {
    DRIED,              // Getrockneter Tabak
    CUT,                // Geschnittener Tabak
    SHREDDED,           // Geschredderter Tabak
    CURED;              // Gereifter Tabak


    public static TobaccoType fromString(String string) {
        return valueOf(string.toUpperCase());
    }

    @Override
    public String toString() {
        return name().substring(0, 1).toUpperCase() + name().toLowerCase().substring(1);
    }


}
