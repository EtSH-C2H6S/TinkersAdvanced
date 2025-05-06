package com.c2h6s.tinkers_advanced.content.effect;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.effect.base.EtSTBaseEffect;
import com.c2h6s.tinkers_advanced.registery.TiAcEffects;
import com.c2h6s.tinkers_advanced.util.CommonConstants;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TinkersAdvanced.MODID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ProtoPoison extends EtSTBaseEffect {
    public ProtoPoison() {
        super(MobEffectCategory.HARMFUL, 0xD7FF6B);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEffectApply(MobEffectEvent.Applicable event){
        if (event.getEffectInstance().getEffect()== TiAcEffects.PROTO_POISON.get()) event.setResult(Event.Result.ALLOW);
    }
    @SubscribeEvent
    public static void onHeal(LivingHealEvent event){
        LivingEntity living = event.getEntity();
        MobEffectInstance instance = living.getEffect(TiAcEffects.PROTO_POISON.get());
        if (instance!=null){
            event.setAmount(event.getAmount()*(1-(0.2f+instance.getAmplifier()*0.2f)));
        }
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        AttributeInstance instance = living.getAttribute(Attributes.MAX_HEALTH);
        if (instance!=null&&!(living instanceof Player)){
            if (instance.getModifier(CommonConstants.PROTO_POISON_UUID)==null){
                instance.addPermanentModifier(new AttributeModifier(CommonConstants.PROTO_POISON_UUID,Attributes.MAX_HEALTH.getDescriptionId(),-0.25, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
    }
}
