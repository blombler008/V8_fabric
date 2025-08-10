package com.tattyhost.fabric.v8.core;

public enum TobaccoType {


    DRIED(1.30f),      // trocken → brennt am schnellsten
    SHREDDED(1.15f),   // geschreddert → deutlich schneller (mehr Oberfläche)
    CUT(1.05f),        // geschnitten → leicht schneller als Basis
    CURED(1.00f),      // „normal“/gereift → Basis
    FLAVORED(0.95f),   // leicht feuchter durch Aroma → etwas langsamer
    FRESH(0.85f);      // feucht → am langsamsten

    public final float burnRate;
    TobaccoType(float burnRate) { this.burnRate = burnRate; }
    public static TobaccoType fromString(String string) {
        return valueOf(string.toUpperCase());
    }

    @Override
    public String toString() {
        return name().substring(0, 1).toUpperCase() + name().toLowerCase().substring(1);
    }


}

