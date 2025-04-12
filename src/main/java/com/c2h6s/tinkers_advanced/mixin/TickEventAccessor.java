package com.c2h6s.tinkers_advanced.mixin;

import net.minecraftforge.event.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = TickEvent.class,remap = false)
public interface TickEventAccessor {
    @Accessor("phase")
    TickEvent.Phase getTickPhase();
}
