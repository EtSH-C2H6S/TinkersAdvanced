package com.c2h6s.tinkers_advanced.content.modifier.compat.mekanism;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.ToolDataNBT;

public class AtomGrade extends NoLevelsModifier implements VolatileDataModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.VOLATILE_DATA);
    }

    @Override
    public void addVolatileData(IToolContext iToolContext, ModifierEntry modifierEntry, ToolDataNBT toolDataNBT) {
        if (iToolContext.hasTag(TinkerTags.Items.ARMOR)){
            toolDataNBT.addSlots(SlotType.DEFENSE,1);
        } else toolDataNBT.addSlots(SlotType.UPGRADE,1);
    }
}
