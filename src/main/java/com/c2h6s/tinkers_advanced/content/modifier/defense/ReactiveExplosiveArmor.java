package com.c2h6s.tinkers_advanced.content.modifier.defense;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.util.FakeExplosionUtil;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.DamageBlockModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.armor.ModifyDamageModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class ReactiveExplosiveArmor extends EtSTBaseModifier implements ModifyDamageModifierHook, DamageBlockModifierHook {
    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.MODIFY_HURT,ModifierHooks.DAMAGE_BLOCK);
    }

    @Override
    public int getPriority() {
        return 200;
    }

    @Override
    public float modifyDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean direct) {
        if (source.is(DamageTypes.FELL_OUT_OF_WORLD)){
            return amount;
        }
        float multiplier = (float) (1- TiAcConfig.COMMON.REACTIVE_EXPLOSIVE_ARMOR_REDUCTION.get());
        if (multiplier>=1) return amount;
        if (source.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS)||!direct){
            return amount*multiplier;
        }
        FakeExplosionUtil.fakeExplode(amount, context.getEntity(), context.getLevel(),context.getEntity().position().add(0,context.getEntity().getBbHeight()/2,0),new IntOpenHashSet(context.getEntity().getId()),true);
        return amount*multiplier;
    }

    @Override
    public boolean isDamageBlocked(IToolStackView iToolStackView, ModifierEntry modifierEntry, EquipmentContext equipmentContext, EquipmentSlot equipmentSlot, DamageSource damageSource, float v) {
        return (damageSource.is(DamageTypeTags.IS_EXPLOSION) || damageSource.is(DamageTypeTags.IS_FIRE))&&TiAcConfig.COMMON.REACTIVE_EXPLOSIVE_ARMOR_IMMUNITY.get();
    }
}
