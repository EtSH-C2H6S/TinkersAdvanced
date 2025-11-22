package com.c2h6s.tinkers_advanced.core.library.capability;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public interface IExchangerTickable {
    void tick(ItemStack stack, Level level, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity);
}
