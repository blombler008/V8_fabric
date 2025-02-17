package com.tattyhost.fabric.v8.blocks;

import com.tattyhost.fabric.v8.V8;
import com.tattyhost.fabric.v8.blocks.custom.HighTempFurnaceBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntityTypes {
    public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(V8.MOD_ID, path), blockEntityType);
    }

    public static final BlockEntityType<HighTempFurnaceBlockEntity> HIGH_TEMP_FURNACE_BLOCK_ENTITY_TYPE = register(
            "high_temp_furnace",
            // For versions 1.21.2 and above,
            // replace `BlockEntityType.Builder` with `FabricBlockEntityTypeBuilder`.
            FabricBlockEntityTypeBuilder.create(HighTempFurnaceBlockEntity::new, ModBlocks.HIGH_TEMP_FURNACE).build()
    );


    public static final BlockEntityType<HighTempFurnaceBlockEntity> GUENTER_BLOCK_ENTITY_TYPE = register(
            "guenter",
            // For versions 1.21.2 and above,
            // replace `BlockEntityType.Builder` with `FabricBlockEntityTypeBuilder`.
            FabricBlockEntityTypeBuilder.create(HighTempFurnaceBlockEntity::new, ModBlocks.HIGH_TEMP_FURNACE).build()
    );

    public static void initialize() {
    }

}
