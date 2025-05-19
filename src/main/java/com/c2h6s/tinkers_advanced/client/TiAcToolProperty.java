package com.c2h6s.tinkers_advanced.client;

import com.c2h6s.tinkers_advanced.content.item.toolItem.ElectronTunerItem;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import slimeknights.tconstruct.library.tools.capability.ToolEnergyCapability;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class TiAcToolProperty {
    public static final ItemPropertyFunction FUNCTION_ELECTRON_TUNER = ((pStack, pLevel, pEntity, pSeed) ->{
        ToolStack tool = ToolStack.from(pStack);
        if (tool.isBroken()) return -0.5f;
        return ToolEnergyCapability.getEnergy(tool)>0 ? tool.getPersistentData().getFloat(ElectronTunerItem.KEY_ATTACK_DAMAGE):-1;
    });
}
