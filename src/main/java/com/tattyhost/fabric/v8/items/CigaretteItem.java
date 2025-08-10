package com.tattyhost.fabric.v8.items;

import com.tattyhost.fabric.v8.core.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.consume.UseAction;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

// com.tattyhost.fabric.v8.items.CigaretteItem
public class CigaretteItem extends Item {

    private static final int DEFAULT_SECONDS = 30;
    private static final double MAX_QUALITY = 20.0;

    public CigaretteItem(Settings settings) { super(settings); }

    private static void ensureDefaults(ItemStack stack) {
        if (!stack.contains(ModDataComponents.TOBACCO_STATS))
            stack.set(ModDataComponents.TOBACCO_STATS, TobaccoStats.DEFAULT);
        if (!stack.contains(ModDataComponents.CIGARETTE_CORE))
            stack.set(ModDataComponents.CIGARETTE_CORE, CigaretteCore.DEFAULT);
        if (!stack.contains(ModDataComponents.CIGARETTE_BURN)) {
            int t = DEFAULT_SECONDS * 20;
            stack.set(ModDataComponents.CIGARETTE_BURN, new CigaretteBurn(t, t));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext ctx, List<Text> tip, TooltipType type) {
        ensureDefaults(stack);
        var tob = stack.get(ModDataComponents.TOBACCO_STATS);
        var core = stack.get(ModDataComponents.CIGARETTE_CORE);
        var burn = stack.get(ModDataComponents.CIGARETTE_BURN);

        tip.add(Text.literal("Quality: " + (int)(core.quality() * 100 / MAX_QUALITY) + "%").formatted(Formatting.GOLD));
        if (tob.flavor() != CigaretteFlavorType.NONE)
            tip.add(Text.literal("Flavor: " + tob.flavor()).formatted(Formatting.GRAY));

        tip.add(Text.literal("Tobacco: " + tob.condition() + " + " + tob.form()).formatted(Formatting.GRAY));
        tip.add(Text.literal("Moisture: " + (int)tob.moisture() + "%").formatted(Formatting.DARK_GRAY));
        tip.add(Text.literal("Tar: " + tob.tar() + "mg").formatted(Formatting.DARK_GRAY));
        tip.add(Text.literal("Carbon Monoxide: " + tob.carbon() + "mg").formatted(Formatting.DARK_GRAY));
        tip.add(Text.literal("Nicotine: " + tob.nicotine() + "mg").formatted(Formatting.DARK_GRAY));
        tip.add(Text.literal("Time left: " + Math.max(0, burn.remainingTicks()/20) + "s").formatted(Formatting.GRAY));
        if (core.filtered())
            tip.add(Text.literal("Filtered (" + Math.round(core.filterEfficiency()*100) + "%)").formatted(Formatting.DARK_GRAY));
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        ensureDefaults(stack);
        return stack.get(ModDataComponents.CIGARETTE_BURN).remainingTicks();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) { return UseAction.SPYGLASS; }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ensureDefaults(user.getStackInHand(hand));
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient || !(entity instanceof PlayerEntity p)) return;
        if (!(p.isUsingItem() && p.getActiveItem() == stack)) return;

        ensureDefaults(stack);
        var tob  = stack.get(ModDataComponents.TOBACCO_STATS);
        var core = stack.get(ModDataComponents.CIGARETTE_CORE);
        var burn = stack.get(ModDataComponents.CIGARETTE_BURN);

        int base = tob.burnDecBasePerTick();
        float filterMod = core.filterEfficiency(); // 0.9 → etwas langsamer
        int dec = Math.max(1, Math.round(base * filterMod));

        int left = Math.max(0, burn.remainingTicks() - dec);
        boolean burnedOutNow = left == 0;

        // Wert zurückschreiben
        stack.set(ModDataComponents.CIGARETTE_BURN, new CigaretteBurn(burn.maxTicks(), left));

        if (burnedOutNow) {
            // Nutzung beenden
            p.stopUsingItem();

            // 5% Chance auf Magic Ash – nur serverseitig
            if (!world.isClient && world.random.nextFloat() < 0.05f) {
                p.giveOrDropStack(new ItemStack(ModItems.MAGIC_ASH_ITEM));
            }

            // Zigarette verbrauchen (kein Doppel-Consume mit finishUsing)
            if (!p.isCreative()) {
                stack.decrement(1);
            }
        }
    }

    // Durability-Leiste unten
    @Override public boolean isItemBarVisible(ItemStack stack){ return stack.contains(ModDataComponents.CIGARETTE_BURN); }

    @Override public int getItemBarStep(ItemStack stack){
        CigaretteBurn burn = stack.getOrDefault(ModDataComponents.CIGARETTE_BURN, new CigaretteBurn(1,1));
        return Math.round(13f * burn.remainingTicks() / (float)burn.maxTicks());
    }
    @Override public int getItemBarColor(ItemStack stack){
        CigaretteBurn burn = stack.getOrDefault(ModDataComponents.CIGARETTE_BURN, new CigaretteBurn(1,1));
        float frac = (burn.remainingTicks() / (float)burn.maxTicks()); // 0=voll → rot
        return MathHelper.hsvToRgb(frac/3f, 1f, 1f);
    }

    /** Beim Crafting/Maschine aufrufen, um die Komponenten zu setzen. */
    public static void applyCraftResult(ItemStack result, TobaccoStats tob, CigaretteCore core, int totalSeconds) {
        result.set(ModDataComponents.TOBACCO_STATS, tob);
        result.set(ModDataComponents.CIGARETTE_CORE, core);
        int t = Math.max(1, totalSeconds) * 20;
        result.set(ModDataComponents.CIGARETTE_BURN, new CigaretteBurn(t, t));
    }
}
