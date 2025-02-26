package com.tattyhost.fabric.v8.client.datagen.loots;

import com.tattyhost.fabric.v8.blocks.ModPlants;
import com.tattyhost.fabric.v8.blocks.custom.plants.CottonPlant;
import com.tattyhost.fabric.v8.blocks.custom.plants.TobaccoPlant;
import com.tattyhost.fabric.v8.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.state.property.Property;

import java.util.concurrent.CompletableFuture;

public class PlantLootTableProvider extends FabricBlockLootTableProvider {

    protected PlantLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        LootCondition.Builder builder = getPlantStage(ModPlants.TOBACCO_PLANT, TobaccoPlant.AGE, 9);
        this.addDrop(ModPlants.TOBACCO_PLANT, super.cropDrops(ModPlants.TOBACCO_PLANT, ModItems.TOBACCO_LEAF, ModItems.TOBACCO_SEEDS, builder));

        addCottonPlantDrops();
    }

    private void addCottonPlantDrops() {
        this.addDrop(ModPlants.COTTON_PLANT, this.addCropCottonDrops());
    }


    private LootCondition.Builder getPlantStage(Block block, Property<Integer> property, int age) {
        return BlockStatePropertyLootCondition.builder(block)
                .properties(StatePredicate.Builder.create().exactMatch(property, age));
    }

    private ConditionalLootFunction.Builder<?> getPlantFortuneExtra() {
        RegistryWrapper.Impl<Enchantment> impl = this.registries.getOrThrow(RegistryKeys.ENCHANTMENT);
        return ApplyBonusLootFunction.binomialWithBonusCount(impl.getOrThrow(Enchantments.FORTUNE), 0.5714286F, 3);
    }

    private LootPool.Builder fortunePoolBuilder(Item item, Block block, Property<Integer> property, int age) {
        return LootPool.builder()
                .with(leafBuilder(item, block, property, age)
                        .apply(getPlantFortuneExtra()));
    }

    private LootPool.Builder poolBuilder(Item item, Block block, Property<Integer> property, int age) {
        return LootPool.builder().with(leafBuilder(item, block, property, age));
    }

    private LeafEntry.Builder<?> leafBuilder(Item item, Block block, Property<Integer> property, int age) {
        return ItemEntry.builder(item).conditionally(getPlantStage(block, property, age));
    }

    private LootTable.Builder addCropCottonDrops() {
        Item seeds = ModItems.COTTON_SEEDS;
        Item product = ModItems.COTTON;
        Block crop = ModPlants.COTTON_PLANT;
        Property<Integer> property = CottonPlant.AGE;
        return this.applyExplosionDecay(
                crop,
                LootTable.builder()
                        .pool(poolBuilder(seeds, crop, property, 0))
                        .pool(poolBuilder(seeds, crop, property, 1))
                        .pool(fortunePoolBuilder(seeds, crop, property, 2))
                        .pool(fortunePoolBuilder(product, crop, property, 3))
                );
    }


    public String getName() {
        return "Plant Loot Tables";
    }
}
