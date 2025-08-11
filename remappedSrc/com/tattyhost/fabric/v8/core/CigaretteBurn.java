package com.tattyhost.fabric.v8.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;


public record CigaretteBurn(int baseTicks, int effectiveTicks, int ticksRemaining) {

    // Creative-Default (neutral, wird bei Bedarf beim Erstellen „vorgerechnet“)
    public static final CigaretteBurn DEFAULT = new CigaretteBurn(400, 0, 0);

    public static final Codec<CigaretteBurn> CODEC = RecordCodecBuilder.create(b -> b.group(
            Codec.INT.optionalFieldOf("base", DEFAULT.baseTicks).forGetter(CigaretteBurn::baseTicks),
            Codec.INT.optionalFieldOf("effective", DEFAULT.effectiveTicks).forGetter(CigaretteBurn::effectiveTicks),
            Codec.INT.optionalFieldOf("ticks", DEFAULT.ticksRemaining).forGetter(CigaretteBurn::ticksRemaining)
    ).apply(b, (baseIn, effIn, ticksIn) -> new CigaretteBurn(Math.max(0, baseIn), Math.max(0, effIn), Math.max(0, ticksIn))));

    /** Berechnet effTicks aus baseTicks & burnRate und startet voll. (eff ≤ base) */
    public static CigaretteBurn computeByBurnRate(int baseTicks, float burnRate) {
        int base = Math.max(1, baseTicks);
        float rate = Math.max(0.001f, burnRate);
        int eff = Math.max(1, Math.round(base / rate));
        eff = Math.min(eff, base);
        return new CigaretteBurn(base, eff, eff);
    }

    public CigaretteBurn withTicksRemaining(int newTicksRemaining) {
        int rem = Math.max(0, Math.min(newTicksRemaining, this.effectiveTicks));
        return new CigaretteBurn(this.baseTicks, this.effectiveTicks, rem);
    }

    /** Für Itembar: gegen effTicks normalisieren. */
    public float fractionLeft() {
        return effectiveTicks <= 0 ? 0f : (ticksRemaining / (float) effectiveTicks);
    }
}