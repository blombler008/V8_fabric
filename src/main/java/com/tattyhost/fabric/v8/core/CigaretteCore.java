package com.tattyhost.fabric.v8.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record CigaretteCore(
        int quality,            // 0..20
        boolean filtered,       // hat Filter
        float filterEfficiency  // 0..1 (z. B. 0.9 = 10% langsamerer Abbrand)
) {
    public static final CigaretteCore DEFAULT = new CigaretteCore(15, true, 0.90f);

    public static final Codec<CigaretteCore> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.optionalFieldOf("quality", DEFAULT.quality).forGetter(CigaretteCore::quality),
            Codec.BOOL.optionalFieldOf("filtered", DEFAULT.filtered).forGetter(CigaretteCore::filtered),
            Codec.FLOAT.optionalFieldOf("filterEfficiency", DEFAULT.filterEfficiency).forGetter(CigaretteCore::filterEfficiency)
    ).apply(i, CigaretteCore::new));
}
