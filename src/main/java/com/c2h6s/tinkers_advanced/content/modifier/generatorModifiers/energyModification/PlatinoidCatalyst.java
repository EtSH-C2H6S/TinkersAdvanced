package com.c2h6s.tinkers_advanced.content.modifier.generatorModifiers.energyModification;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.content.modifierHooks.ModifyGenerationModifierHook;
import com.c2h6s.tinkers_advanced.content.modifierHooks.TiAcModifierHooks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class PlatinoidCatalyst extends EtSTBaseModifier implements ModifyGenerationModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, TiAcModifierHooks.MODIFY_GENERATION);
    }
    public static float getBonus(int modifierLevel){
        return (float) (TiAcConfig.COMMON.PLATINOID_CATALYST_BONUS.get()*modifierLevel);
    }

    @Override
    public long modifyTotalGeneration(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, ItemStack stack, long baseAmount, long amount) {
        return (long) (amount<0? amount+baseAmount*getBonus(entry.getLevel()):amount-baseAmount/(1+getBonus(entry.getLevel())));
    }

    @Override
    public int modifyTotalPerTick(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, ItemStack stack,int baseAmount, int amount) {
        return (int) (amount+baseAmount*getBonus(entry.getLevel()));
    }

}
