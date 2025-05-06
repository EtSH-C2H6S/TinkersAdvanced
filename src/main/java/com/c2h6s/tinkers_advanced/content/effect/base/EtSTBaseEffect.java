package com.c2h6s.tinkers_advanced.content.effect.base;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EtSTBaseEffect extends MobEffect {
    public EtSTBaseEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    @Override
    public void applyEffectTick(LivingEntity p_19467_, int p_19468_) {
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration>0;
    }
}
