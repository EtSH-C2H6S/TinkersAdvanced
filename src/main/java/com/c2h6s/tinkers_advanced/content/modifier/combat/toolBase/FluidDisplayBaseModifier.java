package com.c2h6s.tinkers_advanced.content.modifier.combat.toolBase;

import com.c2h6s.etstlib.register.EtSTLibHooks;
import com.c2h6s.etstlib.tool.hooks.CustomBarDisplayModifierHook;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import net.minecraft.world.phys.Vec2;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import static slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper.TANK_HELPER;


public abstract class FluidDisplayBaseModifier extends EtSTBaseModifier implements CustomBarDisplayModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, EtSTLibHooks.CUSTOM_BAR);
    }

    @Override
    public String barId(IToolStackView iToolStackView, ModifierEntry modifierEntry, int i) {
        return "fluid_amount_display";
    }

    @Override
    public boolean showBar(IToolStackView iToolStackView, ModifierEntry modifierEntry, int i) {
        return !TANK_HELPER.getFluid(iToolStackView).isEmpty();
    }

    @Override
    public Vec2 getBarXYSize(IToolStackView iToolStackView, ModifierEntry modifierEntry, int i) {
        return new Vec2(Math.min(13,13*(TANK_HELPER.getFluid(iToolStackView).getAmount()/(float)TANK_HELPER.getCapacity(iToolStackView))),1);
    }

    @Override
    public int getBarRGB(IToolStackView iToolStackView, ModifierEntry modifierEntry, int i) {
        return 0xffff987a;
    }
}
