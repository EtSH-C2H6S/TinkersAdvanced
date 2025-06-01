package com.c2h6s.tinkers_advanced.content.entity.tickers;

import com.c2h6s.etstlib.content.misc.entityTicker.LivingEntityTicker;
import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.tinkers_advanced.registery.TiAcParticleTypes;
import com.c2h6s.tinkers_advanced.util.CommonUtil;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class IonizedEntityTicker extends LivingEntityTicker {
    public static final UUID IONIZED_UUID = UUID.fromString("f839d735-470b-5120-7257-68aa4bdde97c");

    @Override
    public boolean livingTick(int duration, int level, LivingEntity entity) {
        var nbt = entity.getPersistentData();
        var world = entity.level();
        Entity attackerLegacy = null;
        if (nbt.contains(CommonUtil.KEY_ATTACKER, Tag.TAG_INT))
            attackerLegacy = world.getEntity(nbt.getInt(CommonUtil.KEY_ATTACKER));
        if (world instanceof ServerLevel serverLevel) {
            entity.hurt(LegacyDamageSource.any(entity.damageSources().generic().typeHolder(), attackerLegacy).setBypassInvulnerableTime().setBypassArmor().setBypassMagic().setBypassEnchantment().setBypassShield().setMsgId("plasma"), level);
            entity.invulnerableTime = 0;
            serverLevel.sendParticles((SimpleParticleType) TiAcParticleTypes.ELECTRIC.get(), entity.getX(), entity.getY() + 0.5 * entity.getBbHeight(), entity.getZ(), 5, 0, 0, 0, (entity.getBbHeight() + entity.getBbWidth()) / 6);
        }
        if (world.getGameTime() % 20 == 0) {

            AttributeInstance attributeInstance;
            double i = 0;
            attributeInstance = entity.getAttribute(Attributes.ARMOR);
            if (attributeInstance != null) {
                var modifier = attributeInstance.getModifier(IONIZED_UUID);
                if (modifier != null) {
                    i = modifier.getAmount();
                    attributeInstance.removeModifier(IONIZED_UUID);
                }
                attributeInstance.addTransientModifier(new AttributeModifier(IONIZED_UUID.toString(), i - 10, AttributeModifier.Operation.ADDITION));
            }
            attributeInstance = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attributeInstance != null) {
                var modifier = attributeInstance.getModifier(IONIZED_UUID);
                if (modifier != null) {
                    i = modifier.getAmount();
                    attributeInstance.removeModifier(IONIZED_UUID);
                }
                attributeInstance.addTransientModifier(new AttributeModifier(IONIZED_UUID.toString(), i - 0.2, AttributeModifier.Operation.ADDITION));
            }
            attributeInstance = entity.getAttribute(Attributes.JUMP_STRENGTH);
            if (attributeInstance != null) {
                var modifier = attributeInstance.getModifier(IONIZED_UUID);
                if (modifier != null) {
                    i = modifier.getAmount();
                    attributeInstance.removeModifier(IONIZED_UUID);
                }
                attributeInstance.addTransientModifier(new AttributeModifier(IONIZED_UUID.toString(), i - 0.2, AttributeModifier.Operation.ADDITION));
            }
            attributeInstance = entity.getAttribute(Attributes.FLYING_SPEED);
            if (attributeInstance != null) {
                var modifier = attributeInstance.getModifier(IONIZED_UUID);
                if (modifier != null) {
                    i = modifier.getAmount();
                    attributeInstance.removeModifier(IONIZED_UUID);
                }
                attributeInstance.addTransientModifier(new AttributeModifier(IONIZED_UUID.toString(), i - 0.2, AttributeModifier.Operation.ADDITION));
            }
            attributeInstance = entity.getAttribute(Attributes.MAX_HEALTH);
            if (attributeInstance != null) {
                var modifier = attributeInstance.getModifier(IONIZED_UUID);
                if (modifier != null) {
                    i = modifier.getAmount();
                    attributeInstance.removeModifier(IONIZED_UUID);
                }
                attributeInstance.addTransientModifier(new AttributeModifier(IONIZED_UUID.toString(), i - 10, AttributeModifier.Operation.ADDITION));
            }
        }

        return true;
    }
}
