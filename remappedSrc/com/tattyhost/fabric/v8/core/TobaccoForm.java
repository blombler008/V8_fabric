package com.tattyhost.fabric.v8.core;

public enum TobaccoForm {
    CUT(1.05f),        // leicht schneller
    SHREDDED(1.15f);   // deutlich schneller

    public final float factor;
    TobaccoForm(float f) { this.factor = f; }

    public static TobaccoForm fromString(String s) {
        try { return TobaccoForm.valueOf(s.toUpperCase(java.util.Locale.ROOT)); }
        catch (Exception e) { return CUT; } // Fallback
    }
}