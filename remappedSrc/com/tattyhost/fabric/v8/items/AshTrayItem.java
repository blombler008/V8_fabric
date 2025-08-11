package com.tattyhost.fabric.v8.items;

import com.tattyhost.fabric.v8.blocks.ModBlocks;
import com.tattyhost.fabric.v8.blocks.ModMachines;
import com.tattyhost.fabric.v8.utils.Strings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class AshTrayItem extends BlockItem {
    public AshTrayItem(net.minecraft.item.Item.Settings settings) {
        super(ModBlocks.ASH_TRAY, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        Text text = Text.translatable(Strings.ITEM_ASH_TREY_FULL_TOOLTIP_TRANSLATION_KEY);
        if (getTranslationKey().equals(Strings.ITEM_ASH_TREY_TRANSLATION_KEY)) {
            text = Text.translatable(Strings.ITEM_ASH_TREY_TOOLTIP_TRANSLATION_KEY);
        }

        createNLTooltipFromTranslatable(tooltip, text);
    }

    private static void createNLTooltipFromTranslatable(List<Text> tooltip, Text text) {
        OrderedText t = text.asOrderedText();
        StringBuilder sb = new StringBuilder();
        t.accept((index, style, charCode) -> {
            sb.append((char)charCode);
            return true;
        });
        String textString = sb.toString();
        if (textString.contains("\n")) {
            String[] lines = textString.split("\n");
            for (String line : lines) {
                Text tLine = Text.literal(line).formatted(Formatting.RESET);
                tooltip.add(tLine.copy().formatted(Formatting.GRAY));
            }
        }
    }


}
