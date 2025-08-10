package com.tattyhost.fabric.v8.core;

public record CigaretteBurn(int maxTicks, int remainingTicks) {
    public static final CigaretteBurn DEFAULT = new CigaretteBurn(400, 400);


    public static final com.mojang.serialization.Codec<CigaretteBurn> CODEC =
            com.mojang.serialization.codecs.RecordCodecBuilder.create(b -> b.group(
                    com.mojang.serialization.Codec.INT.fieldOf("max").forGetter(CigaretteBurn::maxTicks),
                    com.mojang.serialization.Codec.INT.fieldOf("left").forGetter(CigaretteBurn::remainingTicks)
            ).apply(b, CigaretteBurn::new));

    public float pct() { return remainingTicks / (float) maxTicks; }
    public int secondsLeft() { return Math.max(0, remainingTicks / 20); }
}
