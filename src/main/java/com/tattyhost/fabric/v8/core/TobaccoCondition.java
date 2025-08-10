package com.tattyhost.fabric.v8.core;

// Feuchte-/Reifezustand des Tabaks
public enum TobaccoCondition {
    FRESH(0.85f),      // feucht → langsam
    FLAVORED(0.95f),   // etwas feuchter wegen Aroma
    CURED(1.00f),      // Standard
    DRIED(1.30f);      // sehr trocken → schnell

    public final float factor;
    TobaccoCondition(float f) { this.factor = f; }

    public static TobaccoCondition fromString(String s) {
        try { return TobaccoCondition.valueOf(s.toUpperCase(java.util.Locale.ROOT)); }
        catch (Exception e) { return CURED; } // Fallback
    }
}