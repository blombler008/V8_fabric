package com.tattyhost.fabric.v8.items;

import com.tattyhost.fabric.v8.core.CigaretteFlavorType;
import com.tattyhost.fabric.v8.core.ModDataComponents;
import com.tattyhost.fabric.v8.core.TobaccoStats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class TobacooItem extends Item {
    @SuppressWarnings("ALL")
    private double maxQuantity = 20;

    public TobacooItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (stack.contains(ModDataComponents.TOBACCO_STATS)) {
            TobaccoStats stats = stack.get(ModDataComponents.TOBACCO_STATS);
            assert stats != null;


            CigaretteFlavorType flavor = stats.flavor();
            boolean isFlavored = flavor != CigaretteFlavorType.NONE;

            tooltip.add(Text.literal("Quality: " + (stats.quality() * 100 / this.maxQuantity) + "%").formatted(Formatting.GOLD));
            if(isFlavored) {
                tooltip.add(Text.literal("Flavor: " + stats.flavor()).formatted(Formatting.GRAY));
            }
            tooltip.add(Text.literal("Tobacco Type: " + stats.tobaccoType()).formatted(Formatting.GRAY));
            tooltip.add(Text.literal("Moisture: " + stats.moisture() + "%").formatted(Formatting.GRAY));
            tooltip.add(Text.literal("Nicotine: " + stats.nicotine() + "mg").formatted(Formatting.GRAY));
        }
    }
}
