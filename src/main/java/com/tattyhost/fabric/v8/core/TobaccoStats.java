package com.tattyhost.fabric.v8.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record TobaccoStats(
        TobaccoType tobaccoType,   // Typ des Tabaks (Enum)
        CigaretteFlavorType flavor, // Geschmacksrichtung
        float moisture,            // Feuchtigkeitsgehalt (0-100%)
        float nicotine,            // Nikotinanteil in mg
        int quality                // Qualitätsstufe (0-20)
) {
    public static final TobaccoStats DEFAULT = new TobaccoStats(TobaccoType.DRIED, CigaretteFlavorType.NONE, 50.0F, 20.0F, 15);


    public static final Codec<TobaccoStats> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.xmap(TobaccoType::fromString, TobaccoType::name).optionalFieldOf("tobaccoType", DEFAULT.tobaccoType).forGetter(TobaccoStats::tobaccoType),
            Codec.STRING.xmap(CigaretteFlavorType::fromString, CigaretteFlavorType::name).optionalFieldOf("flavor", DEFAULT.flavor).forGetter(TobaccoStats::flavor),
            Codec.FLOAT.optionalFieldOf("moisture", DEFAULT.moisture).forGetter(TobaccoStats::moisture),
            Codec.FLOAT.optionalFieldOf("nicotine", DEFAULT.nicotine).forGetter(TobaccoStats::nicotine),
            Codec.INT.optionalFieldOf("quality", DEFAULT.quality).forGetter(TobaccoStats::quality)
    ).apply(instance, TobaccoStats::new));


}