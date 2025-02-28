package com.tattyhost.fabric.v8.items;

import com.tattyhost.fabric.v8.core.CigaretteFlavorType;
import com.tattyhost.fabric.v8.core.CigaretteStats;
import com.tattyhost.fabric.v8.core.ModDataComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.consume.UseAction;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class CigaretteItem extends Item {


    @SuppressWarnings("ALL")
    private double maxQuantity = 20;
    @SuppressWarnings("ALL")
    private int burnTime = 0;
    @SuppressWarnings("ALL")
    private int maxBurnTime;


    public CigaretteItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (stack.contains(ModDataComponents.CIGARETTE_STATS)) {
            CigaretteStats stats = stack.get(ModDataComponents.CIGARETTE_STATS);

            assert stats != null;

            boolean isFlavored = stats.flavor() != CigaretteFlavorType.NONE;

            tooltip.add(Text.literal("Quality: " + (stats.quality() * 100 / this.maxQuantity) + "%").formatted(Formatting.GOLD));
            if(isFlavored) {
                tooltip.add(Text.literal("Flavor: " + stats.flavor()).formatted(Formatting.GRAY));
            }
            tooltip.add(Text.literal("Tobacco Type: " + stats.tobaccoType()).formatted(Formatting.GRAY));
            tooltip.add(Text.literal("Tar: " + stats.tar() + "mg").formatted(Formatting.GRAY));
            tooltip.add(Text.literal("Carbon Monoxide: " + stats.carbon() + "mg").formatted(Formatting.GRAY));
            tooltip.add(Text.literal("Nicotine: " + stats.nicotine() + "mg").formatted(Formatting.GRAY));
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 20*60;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPYGLASS;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
//        user.playSound(SoundEvents.ITEM_SPYGLASS_USE, 1.0F, 1.0F);
        user.incrementStat(Stats.USED.getOrCreateStat(this));

        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
//        this.playStopUsingSound(user);

        stack.setCount(stack.getCount() - 1);

        Random random = new Random();
        if (random.nextInt(100) < 10)
            user.giveOrDropStack(new ItemStack(ModItems.MAGIC_ASH_ITEM));

        return stack.isEmpty() ? new ItemStack(Items.AIR) : stack;
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
//        this.playStopUsingSound(user);
        return true;
    }

}
