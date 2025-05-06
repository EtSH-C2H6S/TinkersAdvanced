package com.c2h6s.tinkers_advanced.mixin.AEMixin;

import appeng.items.tools.powered.AbstractPortableCell;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AbstractPortableCell.class,remap = false)
public interface AbstractPortableCellAccessor {
    @Accessor
    MenuType<?> getMenuType();
}
