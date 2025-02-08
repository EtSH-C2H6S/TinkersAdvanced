package com.c2h6s.tinkers_advanced.eventHandler;

import com.c2h6s.tinkers_advanced.registery.TiAcEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;

public class LivingEventHandler {
    public LivingEventHandler(){
        MinecraftForge.EVENT_BUS.addListener(this::onLivingHurt);
    }

    private void onLivingHurt(LivingHurtEvent event) {
        MobEffectInstance instance = event.getEntity().getEffect(TiAcEffects.TETANUS.get());
        if (instance!=null&&instance.getDuration()>0){
            event.setAmount(event.getAmount()*1.2f);
        }
    }
}
