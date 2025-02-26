package com.tattyhost.fabric.v8.client.datagen;

import com.tattyhost.fabric.v8.blocks.ModBlocks;
import com.tattyhost.fabric.v8.blocks.ModMachines;
import com.tattyhost.fabric.v8.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                this.createShaped(RecipeCategory.MISC, ModBlocks.V8_BLOCK, 4)
                        .pattern("###")
                        .pattern("# #")
                        .pattern(" # ")
                        .input('#', ModItems.V8_ITEM)
                        .criterion(hasItem(Items.CRAFTING_TABLE), conditionsFromItem(Items.CRAFTING_TABLE))
                        .offerTo(exporter);


                this.createShaped(RecipeCategory.MISC, ModBlocks.AMERITE_BLOCK, 4)
                        .pattern("###")
                        .pattern("@# ")
                        .pattern(" @ ")
                        .input('#', ModItems.V8_ITEM)
                        .input('@', Items.IRON_INGOT)
                        .criterion(hasItem(Items.CRAFTING_TABLE), conditionsFromItem(Items.CRAFTING_TABLE))
                        .offerTo(exporter);

                this.createShaped(RecipeCategory.MISC, ModMachines.DEDLEF, 1)
                        .pattern("BBB")
                        .pattern("BIB")
                        .pattern("BBB")
                        .input('B', Items.BRICKS)
                        .input('I', Items.BREAD)
                        .criterion("has_bread", conditionsFromItem(Items.BREAD))
                        .offerTo(exporter);

            }
        };
    }


    @Override
    public String getName() {
        return "Recipe Provider";
    }
}
