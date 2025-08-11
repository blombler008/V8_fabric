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
        // sicher & ohne assert
        var stats = stack.getOrDefault(ModDataComponents.TOBACCO_STATS, TobaccoStats.DEFAULT);

        // Flavor nur zeigen, wenn vorhanden
        if (stats.flavor() != CigaretteFlavorType.NONE) {
            tooltip.add(Text.literal("Flavor: " + stats.flavor()).formatted(Formatting.GRAY));
        }

        tooltip.add(Text.literal("Tobacco: " + stats.form() + "/" + stats.condition()).formatted(Formatting.GRAY));
        tooltip.add(Text.literal("Moisture: " + (int) stats.moisture() + "%").formatted(Formatting.GRAY));
        tooltip.add(Text.literal("Nicotine: " + stats.nicotine() + "mg").formatted(Formatting.GRAY));
        tooltip.add(Text.literal("Tar: " + stats.tar() + "mg").formatted(Formatting.DARK_GRAY));
        tooltip.add(Text.literal("Carbon Monoxide: " + stats.carbon() + "mg").formatted(Formatting.DARK_GRAY));
    }
}
