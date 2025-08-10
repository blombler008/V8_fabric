package com.tattyhost.fabric.v8.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.MathHelper;

public record TobaccoStats(
        TobaccoForm form,
        TobaccoCondition condition,
        CigaretteFlavorType flavor,
        float moisture,   // 0..100 (%)
        float nicotine,   // mg
        float tar,        // mg
        float carbon      // mg
) {
    public static final TobaccoStats DEFAULT =
            new TobaccoStats(TobaccoForm.CUT, TobaccoCondition.CURED, CigaretteFlavorType.NONE, 50f, 20f, 20f, 15f);

    public static final Codec<TobaccoStats> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.STRING.xmap(TobaccoForm::fromString, TobaccoForm::name)
                    .optionalFieldOf("form", DEFAULT.form).forGetter(TobaccoStats::form),
            Codec.STRING.xmap(TobaccoCondition::fromString, TobaccoCondition::name)
                    .optionalFieldOf("condition", DEFAULT.condition).forGetter(TobaccoStats::condition),
            Codec.STRING.xmap(CigaretteFlavorType::fromString, CigaretteFlavorType::name)
                    .optionalFieldOf("flavor", DEFAULT.flavor).forGetter(TobaccoStats::flavor),
            Codec.FLOAT.optionalFieldOf("moisture", DEFAULT.moisture).forGetter(TobaccoStats::moisture),
            Codec.FLOAT.optionalFieldOf("nicotine", DEFAULT.nicotine).forGetter(TobaccoStats::nicotine),
            Codec.FLOAT.optionalFieldOf("tar", DEFAULT.tar).forGetter(TobaccoStats::tar),
            Codec.FLOAT.optionalFieldOf("carbon", DEFAULT.carbon).forGetter(TobaccoStats::carbon)
    ).apply(i, TobaccoStats::new));

    /** Abbrand-„Stärke“ auf Basis des Tabaks. */
    public int burnDecBasePerTick() {
        float m = MathHelper.clamp(this.moisture() / 100f, 0f, 1f);
        float dryness = 1f - m;
        float drynessExtra = 2.0f; // voll trocken ≈ 3x so schnell wie feucht
        float perTick = 1f * form().factor * condition().factor * (1f + dryness * drynessExtra);
        return Math.max(1, Math.round(perTick));
    }
}