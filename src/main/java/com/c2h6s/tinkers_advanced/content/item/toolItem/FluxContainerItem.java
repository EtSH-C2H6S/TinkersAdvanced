package com.c2h6s.tinkers_advanced.content.item.toolItem;

import com.c2h6s.tinkers_advanced.content.capability.FCEnergyCapability;
import com.c2h6s.tinkers_advanced.content.item.tinkering.TiAcToolDefinitions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;


public class FluxContainerItem extends ModifiableItem {
    public FluxContainerItem(Properties properties) {
        super(properties, TiAcToolDefinitions.FLUX_CONTAINER);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ToolCapabilityProvider(stack){
            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
                if (cap== ForgeCapabilities.ENERGY){
                   return LazyOptional.of(()-> new FCEnergyCapability(()->ToolStack.from(stack))).cast();
                }
                return super.getCapability(cap);
            }
        };
    }

    public static int getReceiveAmount(IToolStackView tool, int amount, boolean simulate){
        return amount;
    }

}
