package com.c2h6s.tinkers_advanced.content.capability;

import com.c2h6s.tinkers_advanced.content.block.ExchangerBlock;
import com.c2h6s.tinkers_advanced.content.block.blockEntity.ExchangerBlockEntity;
import com.c2h6s.tinkers_advanced.registery.TiAcBlocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;

public class ExchangerWrappedEnergyHandler implements IEnergyStorage {
    public final IEnergyStorage energyStorage;
    public final boolean disallowInsert;

    public ExchangerWrappedEnergyHandler(ExchangerBlockEntity exchangerBlockEntity) {
        this.energyStorage = exchangerBlockEntity.exchangingItem.getCapability(ForgeCapabilities.ENERGY).orElse(null);
        Level level = exchangerBlockEntity.getLevel();
        BlockState blockState = exchangerBlockEntity.getBlockState();
        if (level!=null&&blockState.is(TiAcBlocks.EXCHANGER.get())){
            this.disallowInsert=blockState.getValue(ExchangerBlock.ENERGY_OUTPUT);
        } else this.disallowInsert = false;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return !disallowInsert&&this.energyStorage!=null?this.energyStorage.receiveEnergy(maxReceive,simulate):0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return this.energyStorage!=null?this.energyStorage.receiveEnergy(maxExtract,simulate):0;
    }

    @Override
    public int getEnergyStored() {
        return this.energyStorage!=null?this.energyStorage.getEnergyStored():0;
    }

    @Override
    public int getMaxEnergyStored() {
        return this.energyStorage!=null?this.energyStorage.getMaxEnergyStored():0;
    }

    @Override
    public boolean canExtract() {
        return this.energyStorage!=null;
    }

    @Override
    public boolean canReceive() {
        return this.energyStorage!=null&&!this.disallowInsert;
    }
}
