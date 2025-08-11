package com.tattyhost.fabric.v8.blocks.custom;

import com.mojang.logging.LogUtils;
import com.tattyhost.fabric.v8.V8;
import com.tattyhost.fabric.v8.blocks.ModBlockEntityTypes;
import com.tattyhost.fabric.v8.items.ModItems;
import com.tattyhost.fabric.v8.screens.GuenterScreenHandler;
import com.tattyhost.fabric.v8.screens.HighTempFurnaceScreenHandler;
import com.tattyhost.fabric.v8.screens.InventoryImplementation;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class GuenterBlockEntity extends LockableContainerBlockEntity implements InventoryImplementation, NamedScreenHandlerFactory {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(3, ItemStack.EMPTY);

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT1_SLOT = 1;
    public static final int OUTPUT2_SLOT = 2;

    private int progress = 0;
    private int maxProgress = 20 * 5; // 5 seconds * 20 ticks
    private Random random = new Random();
    private boolean canProgress = false;

    private final PropertyDelegate propertyDelegate = new ArrayPropertyDelegate(2) {
        @Override
        public int get(int index) {
            switch (index) {
                case 0: return GuenterBlockEntity.this.getProgress();
                case 1: return GuenterBlockEntity.this.getMaxProgress();
                default: return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0: GuenterBlockEntity.this.setProgress(value); break;
                case 1: GuenterBlockEntity.this.setMaxProgress(value); break;
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };

    public GuenterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public GuenterBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(ModBlockEntityTypes.GUENTER_BLOCK_ENTITY_TYPE, blockPos, blockState);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }


    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, GuenterBlockEntity blockEntity) {


        if(blockEntity.canProgress()) {
            blockEntity.increaseProgress();

            if(blockEntity.progressFinished()) {
                blockEntity.craftRecipe();
                blockEntity.resetProgress();
            }

        }
        if(blockEntity.hasRecipe() && !blockEntity.canProgress()) {
            blockEntity.takeItem();
            blockEntity.increaseProgress();
        }
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    private boolean progressFinished() {
        return getProgress() >= getMaxProgress();
    }

    private boolean hasRecipe() {
        if (items.get(INPUT_SLOT).isEmpty()) {
            return false;
        }

        if(items.get(OUTPUT1_SLOT).getCount() > 63) {
            return false;
        }

        if (items.get(OUTPUT2_SLOT).getCount() > 63) {
            return false;
        }

        return true;
    }

    private boolean canProgress() {
        return canProgress;
    }


    private void increaseProgress() {
        setProgress(getProgress() + 1);
    }


    private void craftRecipe() {
        // craft recipe
        ItemStack slot1 = getStack(OUTPUT1_SLOT);

        if(slot1.isEmpty()) {
            items.set(OUTPUT1_SLOT, new ItemStack(Items.COAL));
        } else {
            slot1.increment(1);
        }


        if(random.nextInt(100) > 10)
           return;

        ItemStack slot2 = items.get(OUTPUT2_SLOT);
        if(slot2.isEmpty()) {
            items.set(OUTPUT2_SLOT, new ItemStack(ModItems.MAGIC_ASH_ITEM));
            return;
        }
        slot2.increment(1);

    }


    private void resetProgress() {
        progress = 0;
        canProgress = false;
    }

    private void takeItem() {
        // take item from input slot
        items.get(INPUT_SLOT).decrement(1);

        canProgress = true;
    }


    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, items, registryLookup);
        progress = nbt.getInt("Progress");
        canProgress = nbt.getBoolean("CanProgress");
    }

    @Override
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putInt("Progress", progress);
        nbt.putBoolean("CanProgress", canProgress);
        Inventories.writeNbt(nbt, items, registryLookup);
        super.writeNbt(nbt, registryLookup);
    }

        @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {


        return new GuenterScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return items;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            items.set(i, inventory.get(i));
        }
    }
}

