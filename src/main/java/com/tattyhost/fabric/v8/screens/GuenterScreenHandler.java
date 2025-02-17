package com.tattyhost.fabric.v8.screens;

import com.tattyhost.fabric.v8.blocks.ModBlocks;
import com.tattyhost.fabric.v8.items.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class GuenterScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    private static final int INVENTORY_SLOTS = 3;

    // This constructor gets called on the client when the server wants it to open the screenHandler,
    // The client will call the other constructor with an empty Inventory and the screenHandler will automatically
    // sync this empty inventory with the inventory on the server.
    public GuenterScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(INVENTORY_SLOTS));
    }

    // This constructor gets called from the BlockEntity on the server without calling the other constructor first, the server knows the inventory of the container
    // and can therefore directly provide it as an argument. This inventory will then be synced to the client.
    public GuenterScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.GUENTER_SCREEN_HANDLER, syncId);
        enableSyncing();
        checkSize(inventory, INVENTORY_SLOTS);
        this.inventory = inventory;
        // some inventories do custom logic when a player opens it.
//        inventory.onOpen(playerInventory.player);

        // This will place the slot in the correct locations for a 3x3 Grid. The slots exist on both server and client!
        // This will not render the background of the slots however, this is the Screens job
        int m;
        int l;
        int slotX;
        int slotY;
        int slotIndex;

        int slotsY = 21;

        int inventroySlot0X = 55;
        int inventroySlot0Y = slotsY;

        int inventroySlot1X = 92;
        int inventroySlot1Y = slotsY;

        int inventroySlot2X = inventroySlot1X + 18;
        int inventroySlot2Y = slotsY;

        this.addSlot(new InputSlot(inventory, 0, inventroySlot0X, inventroySlot0Y));
        this.addSlot(new OutputSlot(inventory, 1, inventroySlot1X, inventroySlot1Y));
        this.addSlot(new OutputSlot(inventory, 2, inventroySlot2X, inventroySlot2Y));


        addPlayerSlots(playerInventory, 8, 59);

    }
    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        if (inventory == this.inventory) {
            this.sendContentUpdates();
        }
    }
    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    private static class OutputSlot extends Slot {
        public OutputSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            // Prevent setting items in this slot
            return false;
        }
    }


    private static class InputSlot extends Slot {
        public InputSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            // Prevent other items than Cigarettes in this slot
            return stack.isOf(ModItems.CIGARETTE_ITEM);
        }
    }

}