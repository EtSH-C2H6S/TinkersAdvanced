package com.c2h6s.tinkers_advanced.content.modifier.generatorModifiers.energyModification;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.content.modifierHooks.ModifyGenerationModifierHook;
import com.c2h6s.tinkers_advanced.content.modifierHooks.TiAcModifierHooks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.ToolEnergyCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class EnergyBin extends EtSTBaseModifier implements ModifyGenerationModifierHook {
    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, TiAcModifierHooks.MODIFY_GENERATION);
    }

    @Override
    public long modifyTotalGeneration(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, ItemStack stack, long baseAmount, long amount) {
        return amount;
    }

    @Override
    public int modifyTotalPerTick(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, ItemStack stack, int baseAmount, int amount) {
        return amount;
    }

    @Override
    public void onGeneratorTick(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, ItemStack stack, int generatedAmount) {
        if (ToolEnergyCapability.getEnergy(tool)>0){
            ToolEnergyCapability.setEnergy(tool,0);
        }
    }
}
