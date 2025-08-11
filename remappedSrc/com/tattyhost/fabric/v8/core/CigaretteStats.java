package com.tattyhost.fabric.v8.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record CigaretteStats(
        int quality,                 // Qualität
        CigaretteFlavorType flavor,      // Geschmacksrichtung
        float tar,             // Teer-Gehalt in mg
        float carbon  // Kohlenmonoxid-Gehalt in mg
) {
    public static final CigaretteStats DEFAULT = new CigaretteStats(15, CigaretteFlavorType.NONE, 20.0F, 15.0F);
    // Codec für die Serialisierung
    public static final Codec<CigaretteStats> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("quality", DEFAULT.quality).forGetter(CigaretteStats::quality),
            Codec.STRING.xmap(CigaretteFlavorType::valueOf, CigaretteFlavorType::name).optionalFieldOf("flavor", DEFAULT.flavor).forGetter(CigaretteStats::flavor),
            Codec.FLOAT.optionalFieldOf("tar", DEFAULT.tar).forGetter(CigaretteStats::tar),
            Codec.FLOAT.optionalFieldOf("carbon", DEFAULT.carbon).forGetter(CigaretteStats::carbon)

    ).apply(instance, CigaretteStats::new));

}

