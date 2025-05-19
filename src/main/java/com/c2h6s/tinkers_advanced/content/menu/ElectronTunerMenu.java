package com.c2h6s.tinkers_advanced.content.menu;

import com.c2h6s.tinkers_advanced.registery.TiAcMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.inventory.EmptyItemHandler;
import slimeknights.tconstruct.tools.menu.ReadOnlySlot;

public class ElectronTunerMenu extends AbstractContainerMenu {
    private ItemStack toolItem;
    public final Inventory playerInventory;
    public final int slotIndex;
    public static final int lastInputSlot = 7;
    public static final int lastOutputSlot = 15;
    public final IItemHandler handler;
    public int toolSlot;
    public ElectronTunerMenu(@Nullable MenuType<?> pMenuType, Inventory playerInventory, int pContainerId, ItemStack toolItem,IItemHandler handler,int slotIndex) {
        super(pMenuType, pContainerId);
        this.toolItem = toolItem;
        this.slotIndex = slotIndex;
        this.playerInventory = playerInventory;
        this.handler = handler;
        int y = 7;
        for (int i = 0; i <= lastInputSlot; i++) {
            int j = i % 2;
            this.addSlot(new SlotItemHandler(handler, i, 180 + j * 18, y));
            if (j == 1) {
                y += 18;
            }
        }
        for (int i = 8; i <= lastOutputSlot; i++) {
            int j = i % 2;
            this.addSlot(new SlotItemHandler(handler, i, 180 + j * 18, y) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return false;
                }
            });
            if (j == 1) {
                y += 18;
            }
        }

        this.addPlayerInventory(playerInventory);
        this.addPlayerHotbar(playerInventory);
    }

    public ItemStack getToolItem() {return toolItem;}

    public void updateToolSlot(ItemStack toolItem){
        this.toolItem = toolItem;
        IItemHandlerModifiable handler = (IItemHandlerModifiable) toolItem.getCapability(ForgeCapabilities.ITEM_HANDLER).filter(cap -> cap instanceof IItemHandlerModifiable).orElse(EmptyItemHandler.INSTANCE);
        for (int i=0;i<handler.getSlots();i++){
            this.getSlot(i).set(handler.getStackInSlot(i));
        }
    }

    public ElectronTunerMenu(int id, Inventory inventory, FriendlyByteBuf buffer) {
        super(TiAcMenus.ELECTRON_TUNER_MENU.get(),id);
        this.slotIndex = buffer.readVarInt();
        this.toolItem = inventory.getItem(this.slotIndex);
        this.playerInventory = inventory;
        this.handler = this.toolItem.getCapability(ForgeCapabilities.ITEM_HANDLER).filter(cap -> cap instanceof IItemHandlerModifiable).orElse(EmptyItemHandler.INSTANCE);
        int y = 7;
        for (int i = 0; i <= lastInputSlot; i++) {
            int j = i % 2;
            this.addSlot(new SlotItemHandler(this.handler, i, 180 + j * 18, y));
            if (j == 1) {
                y += 18;
            }
        }
        for (int i = 8; i <= lastOutputSlot; i++) {
            int j = i % 2;
            this.addSlot(new SlotItemHandler(this.handler, i, 180 + j * 18, y) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return false;
                }
            });
            if (j == 1) {
                y += 18;
            }
        }

        this.addPlayerInventory(playerInventory);
        this.addPlayerHotbar(playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            result = slotStack.copy();
            int end = this.slots.size();
            if (index >= 0&&index<=lastOutputSlot) {
                if (!this.moveItemStackTo(slotStack, lastOutputSlot+1, end, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index>lastOutputSlot){
                if (!this.moveItemStackTo(slotStack, 0, lastInputSlot, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (slotStack.getCount() == result.getCount()) {
                return ItemStack.EMPTY;
            }

        }
        return result;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return !toolItem.isEmpty()&&playerInventory.getItem(slotIndex)==toolItem;
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                int slot = j + 9 * i + 9;
                if (slot==this.slotIndex){
                    this.toolSlot = this.slots.size();
                    this.addSlot(new ReadOnlySlot(inventory, slot, j * 18 + 8, i * 18 + 89));
                }
                else this.addSlot(new Slot(inventory, slot, j * 18 + 8, i * 18 + 89));
            }
        }
    }

    private void addPlayerHotbar(Inventory inventory) {
        for (int i = 0; i < 9; i++) {
            if (i==this.slotIndex){
                this.toolSlot = this.slots.size();
                this.addSlot(new ReadOnlySlot(inventory, i, 8 + i * 18, 147));
            }
            else this.addSlot(new Slot(inventory, i, 8 + i * 18, 147));
        }
    }
}
