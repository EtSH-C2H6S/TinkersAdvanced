package com.c2h6s.tinkers_advanced.content.modifier.compat.pnc;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import me.desht.pneumaticcraft.common.core.ModSounds;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.DamageBlockModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.armor.ModifyDamageModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import static com.c2h6s.etstlib.tool.modifiers.capabilityProvider.PnCIntegration.AirStorageProvider.*;

public class AerialProtection extends EtSTBaseModifier implements DamageBlockModifierHook, ModifyDamageModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.DAMAGE_BLOCK,ModifierHooks.MODIFY_DAMAGE);
    }

    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public boolean isDamageBlocked(IToolStackView tool, ModifierEntry modifierEntry, EquipmentContext equipmentContext, EquipmentSlot equipmentSlot, DamageSource damageSource, float damage) {
        if (!damageSource.is(DamageTypeTags.BYPASSES_ARMOR)&&damage<=5&&getAir(tool)>=(int) (-damage*400)&&!equipmentContext.getLevel().isClientSide){
            LivingEntity entity = equipmentContext.getEntity();
            addAir(tool, (int) (-damage*400));
            entity.level().playSound(null,entity.blockPosition(), ModSounds.SHORT_HISS.get(), entity.getSoundSource(),1,1);
            return true;
        }
        return false;
    }


    @Override
    public float modifyDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (!source.is(DamageTypeTags.BYPASSES_ARMOR)&&getAir(tool)>0&&!context.getLevel().isClientSide){
            float reduce = Math.min(4.5f+amount*0.1f,getAir(tool)/400f);
            if (reduce>0){
                addAir(tool, (int) (-400*reduce));
                amount-=reduce;
                LivingEntity entity = context.getEntity();
                entity.level().playSound(null,entity.blockPosition(), ModSounds.SHORT_HISS.get(), entity.getSoundSource(),1,1);
            }
        }
        return amount;
    }
}
