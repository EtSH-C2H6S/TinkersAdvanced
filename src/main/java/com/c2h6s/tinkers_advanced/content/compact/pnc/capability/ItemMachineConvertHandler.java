package com.c2h6s.tinkers_advanced.content.compact.pnc.capability;

import it.unimi.dsi.fastutil.floats.FloatPredicate;
import me.desht.pneumaticcraft.api.PNCCapabilities;
import me.desht.pneumaticcraft.api.tileentity.IAirHandlerItem;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemMachineConvertHandler implements IItemMachineConvertHandler , ICapabilityProvider {
    public final ItemStack stack;
    private final IAirHandlerItem handlerItem;
    private final LazyOptional<IItemMachineConvertHandler> holder = LazyOptional.of(() -> this);

    public ItemMachineConvertHandler(ItemStack stack) {
        this.stack = stack;
        LazyOptional<IAirHandlerItem> handlerItem = stack.getCapability(PNCCapabilities.AIR_HANDLER_ITEM_CAPABILITY);
        this.handlerItem = handlerItem.orElse(null);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap== PNCCapabilities.AIR_HANDLER_ITEM_CAPABILITY) return holder.cast();
        if (cap== PNCCapabilities.AIR_HANDLER_CAPABILITY) return holder.cast();
        if (cap== PNCCapabilities.AIR_HANDLER_MACHINE_CAPABILITY) return holder.cast();
        return LazyOptional.empty();
    }

    @NotNull
    @Override
    public ItemStack getContainer() {
        return stack;
    }

    @Override
    public float getPressure() {
        if (handlerItem!=null){
            return handlerItem.getPressure();
        }
        return 0;
    }

    @Override
    public int getAir() {
        if (handlerItem!=null){
            return handlerItem.getAir();
        }
        return 0;
    }

    @Override
    public void addAir(int i) {
        if (handlerItem!=null){
            handlerItem.addAir(i);
        }
    }

    @Override
    public int getBaseVolume() {
        if (handlerItem!=null){
            return handlerItem.getBaseVolume();
        }
        return 0;
    }

    @Override
    public void setBaseVolume(int i) {
        if (handlerItem!=null){
            handlerItem.setBaseVolume(i);
        }
    }

    @Override
    public int getVolume() {
        if (handlerItem!=null){
            return handlerItem.getVolume();
        }
        return 0;
    }

    @Override
    public float maxPressure() {
        if (handlerItem!=null){
            return handlerItem.maxPressure();
        }
        return 0;
    }

    @Override
    public float getDangerPressure() {
        return Integer.MAX_VALUE;
    }

    @Override
    public float getCriticalPressure() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void setPressure(float v) {
        if (handlerItem!=null) {
            int newAir = (int) (v * handlerItem.getVolume());
            newAir-=handlerItem.getAir();
            handlerItem.addAir(newAir);
        }
    }

    @Override
    public void setVolumeUpgrades(int i) {
    }
    @Override
    public void enableSafetyVenting(FloatPredicate floatPredicate, Direction direction) {
    }
    @Override
    public void disableSafetyVenting() {
    }
    @Override
    public void tick(BlockEntity blockEntity) {
    }
    @Override
    public void setSideLeaking(@Nullable Direction direction) {
    }
    @Nullable
    @Override
    public Direction getSideLeaking() {
        return null;
    }
    @Override
    public List<Connection> getConnectedAirHandlers(BlockEntity blockEntity) {
        return List.of();
    }
    @Override
    public void setConnectedFaces(List<Direction> list) {
    }
    @Override
    public void printManometerMessage(Player player, List<Component> list) {
    }
    @Override
    public CompoundTag serializeNBT() {
        return new CompoundTag();
    }
    @Override
    public void deserializeNBT(CompoundTag nbt) {
    }
}
