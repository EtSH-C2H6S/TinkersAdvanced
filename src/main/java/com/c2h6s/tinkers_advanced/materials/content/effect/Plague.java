package com.c2h6s.tinkers_advanced.materials.content.effect;

import com.c2h6s.etstlib.content.effects.EtSTBaseEffect;
import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Plague extends EtSTBaseEffect {
    public Plague() {
        super(MobEffectCategory.HARMFUL,0x2C4D2A);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration%5==0;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        int cooldown = pLivingEntity.invulnerableTime;
        Random random = new Random();
        DamageSource source = List.of(
                new LegacyDamageSource(pLivingEntity.damageSources().wither().typeHolder(),null).setBypassInvulnerableTime(),
                new LegacyDamageSource(pLivingEntity.damageSources().magic().typeHolder(),null).setBypassInvulnerableTime()
        ).get(random.nextInt(2));
        pLivingEntity.hurt(source, (float) (pAmplifier* TiAcConfig.COMMON.EFFECT_PLAGUE_DAMAGE.get()));
        pLivingEntity.invulnerableTime = cooldown;
    }
}
