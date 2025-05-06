package com.c2h6s.tinkers_advanced.content.misc;

import appeng.menu.locator.MenuLocator;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import appeng.api.implementations.menuobjects.IMenuItem;
import appeng.api.implementations.menuobjects.ItemMenuHost;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.IModDataView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import javax.annotation.Nullable;

import static slimeknights.tconstruct.library.tools.capability.inventory.InventoryModule.GET_COMPOUND_LIST;

public record PocketCellLocator(int itemIndex, @Nullable BlockPos blockPos) implements MenuLocator {
    public <T> T locate(Player player, Class<T> hostInterface) {
        ItemStack stack = null;
        ItemStack legging = player.getItemBySlot(EquipmentSlot.LEGS);
        if (legging.getItem() instanceof IModifiable){
            IItemHandler handler = legging.getCapability(ForgeCapabilities.ITEM_HANDLER).filter(cap->cap instanceof IItemHandlerModifiable).orElse(null);
            if (handler!=null&&handler.getSlots()>itemIndex){
                stack = handler.getStackInSlot(itemIndex);
            }
        }
        if (stack==null) return null;

        if (!stack.isEmpty() && stack.getItem() instanceof IMenuItem guiItem) {
            ItemMenuHost menuHost = guiItem.getMenuHost(player, itemIndex, stack, blockPos);
            if (hostInterface.isInstance(menuHost)) {
                return hostInterface.cast(menuHost);
            }
        }

        return null;
    }

    public void writeToPacket(FriendlyByteBuf buf) {
        buf.writeInt(itemIndex);
        buf.writeBoolean(blockPos != null);
        if (blockPos != null) {
            buf.writeBlockPos(blockPos);
        }
    }

    public static PocketCellLocator readFromPacket(FriendlyByteBuf buf) {
        var itemIndex = buf.readInt();
        BlockPos blockPos = null;
        if (buf.readBoolean()) {
            blockPos = buf.readBlockPos();
        }
        return new PocketCellLocator(itemIndex, blockPos);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("PocketCell");
        result.append('{');
        result.append("slot=").append(itemIndex);
        if (blockPos != null) {
            result.append(',').append("pos=").append(blockPos);
        }
        result.append('}');
        return result.toString();
    }
}
