package com.tattyhost.fabric.v8.blocks;

import com.tattyhost.fabric.v8.blocks.custom.*;
import net.minecraft.block.Block;

import static com.tattyhost.fabric.v8.blocks.ModRegisters.register;
import static com.tattyhost.fabric.v8.utils.Strings.BLOCK_GUENTER_NAME;
import static com.tattyhost.fabric.v8.utils.Strings.BLOCK_HIGH_TEMP_FURNACE_NAME;

public class ModMachines {

    public static final Block GUENTER = register(BLOCK_GUENTER_NAME, GuenterBlock::new, true, true );
    public static final Block HIGH_TEMP_FURNACE = register(BLOCK_HIGH_TEMP_FURNACE_NAME, HighTempFurnaceBlock::new, true, true );

    public static final Block DEDLEF = register("dedlef", DedlefBlock::new, true, true );
    public static void initialize() {

    }

}
