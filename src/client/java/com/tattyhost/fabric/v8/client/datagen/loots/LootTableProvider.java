package com.tattyhost.fabric.v8.client.datagen.loots;

import com.tattyhost.fabric.v8.blocks.ModBlocks;
import com.tattyhost.fabric.v8.blocks.ModMachines;
import com.tattyhost.fabric.v8.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class LootTableProvider extends FabricBlockLootTableProvider {

    protected LootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public static void addLoots(FabricDataGenerator.Pack pack) {
        pack.addProvider(LootTableProvider::new);
        pack.addProvider(PlantLootTableProvider::new);
    }

    @Override

    public void generate() {

        addDrop(ModBlocks.V8_BLOCK, multiOreLikeDrops(ModBlocks.V8_BLOCK, ModItems.V8_ITEM, 2, 5));
        addDrop(ModBlocks.AMERITE_BLOCK, multiOreLikeDrops(ModBlocks.AMERITE_BLOCK, ModItems.V8_ITEM, 5, 8));
        addDrop(ModMachines.GUENTER, multiOreLikeDrops(ModMachines.GUENTER, Items.COAL, 1, 3));
        addDrop(ModMachines.HIGH_TEMP_FURNACE);
        addDrop(ModMachines.DEDLEF);


    }


    public LootTable.Builder multiOreLikeDrops(Block drop, Item item, float min, float max) {
        RegistryWrapper.Impl<Enchantment> impl = this.registries.getOrThrow(RegistryKeys.ENCHANTMENT);
        return this.dropsWithSilkTouch(
                drop,
                this.applyExplosionDecay(
                        drop,
                        ItemEntry.builder(item)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(min, max)))
                                .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                )
        );



    }

    public String getName() {
        return "Generic Loot Tables";
    }
}
