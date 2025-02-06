package com.c2h6s.tinkers_advanced.content.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class crushing extends EtSTBaseEffect{
    public static final UUID CRUSHING_UUID =UUID.fromString("0617bfe2-0467-1ec8-ec1d-2077daa57e51");
    protected crushing() {
        super(MobEffectCategory.HARMFUL, 0x747680);
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        super.applyEffectTick(living, amplifier);
        AttributeInstance instance =living.getAttribute(Attributes.ARMOR);
        if (instance!=null) {
            if (living.getAttributes().hasModifier(Attributes.ARMOR, CRUSHING_UUID)) {
                instance.removeModifier(CRUSHING_UUID);
            }
            instance.addTransientModifier(new AttributeModifier(CRUSHING_UUID,Attributes.ARMOR.getDescriptionId(),-(amplifier*2), AttributeModifier.Operation.ADDITION));
        }
    }
}
