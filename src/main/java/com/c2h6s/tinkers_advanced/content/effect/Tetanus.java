package com.c2h6s.tinkers_advanced.content.effect;

import com.c2h6s.tinkers_advanced.content.effect.base.EtSTBaseEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class Tetanus extends EtSTBaseEffect {
    public static final UUID TETANUS_UUID =UUID.fromString("0617bfe2-0467-1ec8-ec1d-2077daa57e51");
    public Tetanus() {
        super(MobEffectCategory.HARMFUL, 0x747680);
        super.addAttributeModifier(Attributes.ARMOR,TETANUS_UUID.toString(),-1, AttributeModifier.Operation.ADDITION);
    }
}
