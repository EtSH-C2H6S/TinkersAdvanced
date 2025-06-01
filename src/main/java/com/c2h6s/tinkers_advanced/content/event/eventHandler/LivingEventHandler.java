package com.c2h6s.tinkers_advanced.content.event.eventHandler;

import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.registery.TiAcEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = TinkersAdvanced.MODID)
public class LivingEventHandler {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        MobEffectInstance instance = event.getEntity().getEffect(TiAcEffects.TETANUS.get());
        if (instance!=null&&instance.getDuration()>0){
            event.setAmount((float) (event.getAmount()*TiAcConfig.COMMON.EFFECT_TETANUS_DAMAGE_MULTIPLIER.get()));
        }
    }
    @SubscribeEvent
    public static void onLivingHeal(LivingHealEvent event){
        LivingEntity living = event.getEntity();
        MobEffectInstance instance = living.getEffect(TiAcEffects.PROTO_POISON.get());
        if (instance!=null){
            event.setAmount((float) (event.getAmount()*(1-(TiAcConfig.COMMON.EFFECT_PROTO_POISON_REGENERATION_DECREASE.get() +instance.getAmplifier()*TiAcConfig.COMMON.EFFECT_PROTO_POISON_REGENERATION_DECREASE.get()))));
        }
        instance = living.getEffect(TiAcEffects.PLAGUE.get());
        if (instance!=null){
            event.setAmount((float) (event.getAmount()*(1-(instance.getAmplifier()+1)*TiAcConfig.COMMON.EFFECT_PLAGUE_REGENERATION_DECREASE.get())));
        }
    }
}
