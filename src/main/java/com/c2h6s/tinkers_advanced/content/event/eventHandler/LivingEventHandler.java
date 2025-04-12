package com.c2h6s.tinkers_advanced.content.event.eventHandler;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.modifier.combat.IonizedModifier;
import com.c2h6s.tinkers_advanced.registery.TiAcEffects;
import com.c2h6s.tinkers_advanced.registery.TiAcParticleTypes;
import com.c2h6s.tinkers_advanced.util.CommonUtil;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = TinkersAdvanced.MODID)
public class LivingEventHandler {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        MobEffectInstance instance = event.getEntity().getEffect(TiAcEffects.TETANUS.get());
        if (instance!=null&&instance.getDuration()>0){
            event.setAmount(event.getAmount()*1.2f);
        }
    }
    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event){
        LivingEntity living = event.getEntity();
        if (living!=null){
            var nbt = living.getPersistentData();
            var level = living.level();

            Entity attackerLegacy = null;
            if (nbt.contains(CommonUtil.KEY_ATTACKER,Tag.TAG_INT)) attackerLegacy = level.getEntity(nbt.getInt(CommonUtil.KEY_ATTACKER));
            if (level instanceof ServerLevel serverLevel) {

                if (nbt.contains(IonizedModifier.KEY_IONIZED, Tag.TAG_INT)) {
                    var ionizeStrength = living.getPersistentData().getInt(IonizedModifier.KEY_IONIZED);
                    living.hurt(LegacyDamageSource.any(living.damageSources().generic().typeHolder(),attackerLegacy).setBypassInvulnerableTime().setBypassArmor().setBypassMagic().setBypassEnchantment().setBypassShield().setMsgId("plasma"), 1 + (ionizeStrength / 1000f));
                    living.invulnerableTime = 0;
                    if (living.getEffect(TiAcEffects.IONIZED.get())!=null) living.removeEffect(TiAcEffects.IONIZED.get());
                    living.addEffect(new MobEffectInstance(TiAcEffects.IONIZED.get(),ionizeStrength/20,ionizeStrength/200,false,false));
                    serverLevel.sendParticles((SimpleParticleType)TiAcParticleTypes.ELECTRIC.get(),living.getX(),living.getY()+0.5*living.getBbHeight(),living.getZ(),10,0,0,0,(living.getBbHeight()+living.getBbWidth())/4);
                    AttributeInstance attributeInstance;
                    double i =0;
                    attributeInstance = living.getAttribute(Attributes.ARMOR);
                    if (attributeInstance!=null){
                        var modifier = attributeInstance.getModifier(IonizedModifier.IONIZED_UUID);
                        if (modifier!=null){
                            i = modifier.getAmount();
                            attributeInstance.removeModifier(IonizedModifier.IONIZED_UUID);
                        }
                        attributeInstance.addTransientModifier(new AttributeModifier(IonizedModifier.IONIZED_UUID.toString(),i-0.5, AttributeModifier.Operation.ADDITION));
                    }
                    attributeInstance = living.getAttribute(Attributes.MOVEMENT_SPEED);
                    if (attributeInstance!=null){
                        var modifier = attributeInstance.getModifier(IonizedModifier.IONIZED_UUID);
                        if (modifier!=null){
                            i = modifier.getAmount();
                            attributeInstance.removeModifier(IonizedModifier.IONIZED_UUID);
                        }
                        attributeInstance.addTransientModifier(new AttributeModifier(IonizedModifier.IONIZED_UUID.toString(),i-0.025, AttributeModifier.Operation.ADDITION));
                    }
                    attributeInstance = living.getAttribute(Attributes.JUMP_STRENGTH);
                    if (attributeInstance!=null){
                        var modifier = attributeInstance.getModifier(IonizedModifier.IONIZED_UUID);
                        if (modifier!=null){
                            i = modifier.getAmount();
                            attributeInstance.removeModifier(IonizedModifier.IONIZED_UUID);
                        }
                        attributeInstance.addTransientModifier(new AttributeModifier(IonizedModifier.IONIZED_UUID.toString(),i-0.025, AttributeModifier.Operation.ADDITION));
                    }
                    attributeInstance = living.getAttribute(Attributes.FLYING_SPEED);
                    if (attributeInstance!=null){
                        var modifier = attributeInstance.getModifier(IonizedModifier.IONIZED_UUID);
                        if (modifier!=null){
                            i = modifier.getAmount();
                            attributeInstance.removeModifier(IonizedModifier.IONIZED_UUID);
                        }
                        attributeInstance.addTransientModifier(new AttributeModifier(IonizedModifier.IONIZED_UUID.toString(),i-0.025, AttributeModifier.Operation.ADDITION));
                    }
                    attributeInstance = living.getAttribute(Attributes.MAX_HEALTH);
                    if (attributeInstance!=null){
                        var modifier = attributeInstance.getModifier(IonizedModifier.IONIZED_UUID);
                        if (modifier!=null){
                            i = modifier.getAmount();
                            attributeInstance.removeModifier(IonizedModifier.IONIZED_UUID);
                        }
                        attributeInstance.addTransientModifier(new AttributeModifier(IonizedModifier.IONIZED_UUID.toString(),i-0.2, AttributeModifier.Operation.ADDITION));
                    }
                    nbt.putInt(IonizedModifier.KEY_IONIZED,ionizeStrength-1);
                    if (ionizeStrength<=1){
                        nbt.remove(IonizedModifier.KEY_IONIZED);
                        List<Attribute> list = List.of(Attributes.ARMOR,Attributes.MOVEMENT_SPEED,Attributes.JUMP_STRENGTH,Attributes.FLYING_SPEED,Attributes.MAX_HEALTH);
                        for (Attribute attribute:list){
                            attributeInstance = living.getAttribute(attribute);
                            if (attributeInstance!=null){
                                attributeInstance.removeModifier(IonizedModifier.IONIZED_UUID);
                            }
                        }
                    }
                }


            }
        }
    }
}
