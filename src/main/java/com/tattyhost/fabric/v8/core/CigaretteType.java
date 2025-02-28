package com.tattyhost.fabric.v8.core;

public enum CigaretteType {
    NORMAL,      // Standard-Zigarette
    BLACK_MARKED,     // Menthol-Zigarette
    ROLLED,     // Vanille-Zigarette
    HANDMADE,    // Selbstgedrehte Zigarette
    INDUSTRIAL;  // Industriell gefertigte Zigarette

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
