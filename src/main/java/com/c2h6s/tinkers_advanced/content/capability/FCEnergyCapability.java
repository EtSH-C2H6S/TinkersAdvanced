package com.c2h6s.tinkers_advanced.content.capability;

import com.c2h6s.tinkers_advanced.content.item.toolItem.FluxContainerItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.function.Supplier;

import static slimeknights.tconstruct.library.tools.capability.ToolEnergyCapability.*;

public record FCEnergyCapability(Supplier<? extends IToolStackView> tool) implements IEnergyStorage {

    public FCEnergyCapability(Supplier<? extends IToolStackView> tool) {
        this.tool = tool;
    }

    public static int getMaxEnergy(IToolStackView tool) {
        return tool.getStats().getInt(MAX_STAT);
    }

    public static int getEnergy(IToolStackView tool) {
        return tool.getPersistentData().getInt(ENERGY_KEY);
    }

    private static void setEnergyRaw(IToolStackView tool, int energy) {
        if (energy == 0) {
            tool.getPersistentData().remove(ENERGY_KEY);
        } else {
            tool.getPersistentData().putInt(ENERGY_KEY, energy);
        }

    }

    public static void setEnergy(IToolStackView tool, int energy) {
        setEnergyRaw(tool, Mth.clamp(energy, 0, getMaxEnergy(tool)));
    }

    public static void checkEnergy(IToolStackView tool) {
        int energy = getEnergy(tool);
        if (energy < 0) {
            setEnergyRaw(tool, 0);
        } else {
            int capacity = getMaxEnergy(tool);
            if (energy > capacity) {
                setEnergyRaw(tool, capacity);
            }
        }

    }

    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (maxReceive <= 0) {
            return 0;
        } else {
            IToolStackView tool = (IToolStackView)this.tool.get();
            int current = getEnergy(tool);
            int filled = Math.min(getMaxEnergy(tool) - current, maxReceive);
            filled = FluxContainerItem.getReceiveAmount(tool,filled,simulate);
            if (!simulate) {
                setEnergyRaw(tool, current + filled);
            }

            return filled;
        }
    }

    public int extractEnergy(int maxExtract, boolean simulate) {
        if (maxExtract <= 0) {
            return 0;
        } else {
            IToolStackView tool = (IToolStackView)this.tool.get();
            int current = getEnergy(tool);
            if (current <= 0) {
                return 0;
            } else {
                int drained = maxExtract;
                if (current < drained) {
                    drained = current;
                }

                if (!simulate) {
                    setEnergyRaw(tool, current - drained);
                }

                return drained;
            }
        }
    }

    public int getEnergyStored() {
        return getEnergy((IToolStackView)this.tool.get());
    }

    public int getMaxEnergyStored() {
        return getMaxEnergy((IToolStackView)this.tool.get());
    }

    public boolean canExtract() {
        return true;
    }

    public boolean canReceive() {
        return true;
    }

    public Supplier<? extends IToolStackView> tool() {
        return this.tool;
    }


    public static class Provider implements ToolCapabilityProvider.IToolCapabilityProvider {
        private final LazyOptional<IEnergyStorage> energyCap;

        public Provider(Supplier<? extends IToolStackView> toolStack) {
            this.energyCap = LazyOptional.of(() -> {
                return new FCEnergyCapability(toolStack);
            });
        }

        public <T> LazyOptional<T> getCapability(IToolStackView tool, Capability<T> cap) {
            return cap == ForgeCapabilities.ENERGY && tool.getStats().getInt(MAX_STAT) > 0 ? this.energyCap.cast() : LazyOptional.empty();
        }
    }
}