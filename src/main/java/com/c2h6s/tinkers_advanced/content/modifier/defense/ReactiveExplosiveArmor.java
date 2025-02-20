package com.c2h6s.tinkers_advanced.content.modifier.defense;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.util.FakeExplosionUtil;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.ModifyDamageModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class ReactiveExplosiveArmor extends EtSTBaseModifier implements ModifyDamageModifierHook {
    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.MODIFY_HURT);
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
        if (source.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS)||!direct){
            return amount*0.75f;
        }
        FakeExplosionUtil.fakeExplode(amount, context.getEntity(), context.getLevel(),context.getEntity().position().add(0,context.getEntity().getBbHeight()/2,0),new IntOpenHashSet(context.getEntity().getId()),true);
        return amount*0.75f;
    }
}
