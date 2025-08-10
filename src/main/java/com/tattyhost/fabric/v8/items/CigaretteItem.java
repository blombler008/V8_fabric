package com.tattyhost.fabric.v8.items;

import com.tattyhost.fabric.v8.core.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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


    private static final double MAX_QUALITY = 20.0;

    public CigaretteItem(Settings settings) { super(settings); }

    private static float computeBurnRate(ItemStack stack) {
        var tob  = stack.getOrDefault(ModDataComponents.TOBACCO_STATS, TobaccoStats.DEFAULT);
        var core = stack.getOrDefault(ModDataComponents.CIGARETTE_CORE, CigaretteCore.DEFAULT);

        int basePerTick = tob.burnDecBasePerTick();
        float filterMul = core.filterEfficiency();
        return Math.max(0.001f, basePerTick * filterMul);
    }

    private static void ensureDefaults(ItemStack stack) {
        if (!stack.contains(ModDataComponents.TOBACCO_STATS))
            stack.set(ModDataComponents.TOBACCO_STATS, TobaccoStats.DEFAULT);
        if (!stack.contains(ModDataComponents.CIGARETTE_CORE))
            stack.set(ModDataComponents.CIGARETTE_CORE, CigaretteCore.DEFAULT);

        var burn = stack.getOrDefault(ModDataComponents.CIGARETTE_BURN, CigaretteBurn.DEFAULT);
        // Nur initialisieren, wenn keine sinnvollen Werte vorhanden (eff==0 oder Komponente fehlt)
        if (!stack.contains(ModDataComponents.CIGARETTE_BURN) || burn.effectiveTicks() <= 0) {
            float rate = computeBurnRate(stack);
            int baseTicks = burn.baseTicks() > 0 ? burn.baseTicks() : CigaretteBurn.DEFAULT.baseTicks();
            stack.set(ModDataComponents.CIGARETTE_BURN,
                    CigaretteBurn.computeByBurnRate(baseTicks, rate)); // <— NICHT ofSeconds(..)
        }
    }

    private static ItemStack findFlintAndSteel(PlayerEntity p) {
        var inv = p.getInventory();
        for (int i = 0; i < inv.size(); i++) {
            ItemStack s = inv.getStack(i);
            if (!s.isEmpty() && s.isOf(Items.FLINT_AND_STEEL)) return s;
        }
        return ItemStack.EMPTY;
    }


    @Override
    public void appendTooltip(ItemStack stack, TooltipContext ctx, List<Text> tip, TooltipType type) {
        var tob = stack.get(ModDataComponents.TOBACCO_STATS);
        var core = stack.get(ModDataComponents.CIGARETTE_CORE);
        var burn = stack.get(ModDataComponents.CIGARETTE_BURN);
        int secs = burn.ticksRemaining() / 20;

        tip.add(Text.literal("Quality: " + (int)(core.quality() * 100 / MAX_QUALITY) + "%").formatted(Formatting.GOLD));
        if (tob.flavor() != CigaretteFlavorType.NONE)
            tip.add(Text.literal("Flavor: " + tob.flavor()).formatted(Formatting.GRAY));

        tip.add(Text.literal("Tobacco: " + tob.condition() + " + " + tob.form()).formatted(Formatting.GRAY));
        tip.add(Text.literal("Moisture: " + (int)tob.moisture() + "%").formatted(Formatting.DARK_GRAY));
        tip.add(Text.literal("Tar: " + tob.tar() + "mg").formatted(Formatting.DARK_GRAY));
        tip.add(Text.literal("Carbon Monoxide: " + tob.carbon() + "mg").formatted(Formatting.DARK_GRAY));
        tip.add(Text.literal("Nicotine: " + tob.nicotine() + "mg").formatted(Formatting.DARK_GRAY));
        tip.add(Text.literal("Time left: " + secs + "s").formatted(Formatting.GOLD));
        if (core.filtered())
            tip.add(Text.literal("Filtered (" + Math.round(core.filterEfficiency()*100) + "%)").formatted(Formatting.DARK_GRAY));
    }
    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return stack.get(ModDataComponents.CIGARETTE_BURN).ticksRemaining();
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (world.isClient) return false;

        var burn = stack.get(ModDataComponents.CIGARETTE_BURN);
        int used = Math.max(0, burn.ticksRemaining() - remainingUseTicks);
        int left = Math.max(0, burn.ticksRemaining() - used);
        stack.set(ModDataComponents.CIGARETTE_BURN, burn.withTicksRemaining(left));
        return false;
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        // 5% Magic Ash (nur Server)
        if (!world.isClient && world.random.nextFloat() < 0.05f) {
            user.giveOrDropStack(new ItemStack(ModItems.MAGIC_ASH_ITEM));
        }

        // Burn auf 0, Licht aus
        var burn = stack.getOrDefault(ModDataComponents.CIGARETTE_BURN, CigaretteBurn.DEFAULT);
        stack.set(ModDataComponents.CIGARETTE_BURN, burn.withTicksRemaining(0));
        var core = stack.getOrDefault(ModDataComponents.CIGARETTE_CORE, CigaretteCore.DEFAULT);
        stack.set(ModDataComponents.CIGARETTE_CORE,
                new CigaretteCore(core.quality(), core.filtered(), core.filterEfficiency(), false));

        // Item verbrauchen (nicht in Creative)
        if (user instanceof PlayerEntity p && !p.isCreative()) {
            stack.decrement(1);
        }
        return stack.isEmpty() ? new ItemStack(Items.AIR) : stack;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) { return UseAction.SPYGLASS; }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        // Anzünden nötig?
        var core = stack.get(ModDataComponents.CIGARETTE_CORE);
        if (!core.lit()) {

            // Pruefen, ob spieler im gamemode creative ist
            if (user.isCreative()) {
                // Im Creative Mode wird die Zigarette direkt angezündet
                stack.set(ModDataComponents.CIGARETTE_CORE,
                        new CigaretteCore(core.quality(), core.filtered(), core.filterEfficiency(), true));
                world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1f, 1f);
                return ActionResult.SUCCESS; // sofort nutzbar
            }

            // Prüfen, ob Flint And Steel vorhanden

            ItemStack fas = findFlintAndSteel(user);
            if (fas.isEmpty()) {
                if (!world.isClient) {
                    user.sendMessage(Text.literal("You need a Flint and Steel to light it up!").formatted(Formatting.RED), true);
                }
                return ActionResult.FAIL; // nicht nutzbar ohne Feuer
            }
            // anzünden (nur Server)
            if (!world.isClient) {
                if (!user.isCreative()) {
                    fas.damage(1, user);
                }
                stack.set(ModDataComponents.CIGARETTE_CORE,
                        new CigaretteCore(core.quality(), core.filtered(), core.filterEfficiency(), true));
                world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1f, 1f);
            }
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        ensureDefaults(stack);
        if (!world.isClient) return;
        if (entity instanceof LivingEntity living && living.isUsingItem() && living.getActiveItem() == stack) {
            int remainingUse = living.getItemUseTimeLeft();
            var burn = stack.getOrDefault(ModDataComponents.CIGARETTE_BURN, CigaretteBurn.DEFAULT);
            if ((world.getTime() & 3L) == 0L && remainingUse != burn.ticksRemaining()) {
                stack.set(ModDataComponents.CIGARETTE_BURN, burn.withTicksRemaining(remainingUse));
            }
        }
    }
    // Durability-Leiste unten
    @Override public boolean isItemBarVisible(ItemStack stack){
        CigaretteCore core = stack.getOrDefault(ModDataComponents.CIGARETTE_CORE, CigaretteCore.DEFAULT);
        return core.lit();
    }

    @Override public int getItemBarStep(ItemStack stack){
        var burn = stack.getOrDefault(ModDataComponents.CIGARETTE_BURN, CigaretteBurn.DEFAULT);
        return Math.round(13f * burn.fractionLeft());
    }
    @Override public int getItemBarColor(ItemStack stack){
        var burn = stack.getOrDefault(ModDataComponents.CIGARETTE_BURN, CigaretteBurn.DEFAULT);
        float fractionLeft = burn.fractionLeft(); // 1=grün → 0=rot
        return MathHelper.hsvToRgb(fractionLeft/3f, 1f, 1f);
    }

    /** Beim Crafting/Maschine aufrufen, um die Komponenten zu setzen. */
    public static void applyCraftResult(ItemStack result, TobaccoStats tob, CigaretteCore core, int baseSeconds) {
        result.set(ModDataComponents.TOBACCO_STATS, tob);
        result.set(ModDataComponents.CIGARETTE_CORE, core);
        float rate = computeBurnRate(result);
        int baseTicks = Math.max(1, baseSeconds) * 20;
        result.set(ModDataComponents.CIGARETTE_BURN, CigaretteBurn.computeByBurnRate(baseTicks, rate));
    }

}
