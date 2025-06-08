package com.c2h6s.tinkers_advanced.content.modifier.generatorModifiers.energyGeneration;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.content.modifierHooks.GeneratorModuleModifierHook;
import com.c2h6s.tinkers_advanced.content.modifierHooks.TiAcModifierHooks;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.build.ModifierTraitModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.recipe.fuel.MeltingFuel;
import slimeknights.tconstruct.library.recipe.fuel.MeltingFuelLookup;
import slimeknights.tconstruct.library.tools.capability.fluid.TankModule;
import slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.data.ModifierIds;

import static slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper.TANK_HELPER;

public class SmelteryGenerator extends EtSTBaseModifier implements GeneratorModuleModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, TiAcModifierHooks.GENERATOR_MODULE);
        hookBuilder.addModule(new ModifierTraitModule(ModifierIds.tank,1,true));
    }

    @Override
    public int getBasicGeneration(IToolStackView tool, ModifierEntry entry) {
        return TiAcConfig.COMMON.SMELTERY_GENERATOR_BASIC_GENERATION.get()*entry.getLevel();
    }

    @Override
    public int getConditionalGeneration(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, int baseAmount, int amplifiedAmount) {
        FluidStack stack = TANK_HELPER.getFluid(tool);
        if (stack.isEmpty()) return amplifiedAmount;
        Fluid fluid = stack.getFluid();
        MeltingFuel fuel = MeltingFuelLookup.findFuel(fluid);
        if (fuel==null) return amplifiedAmount;
        return (int) (fuel.getTemperature()*TiAcConfig.COMMON.SMELTERY_GENERATOR_TEMPERATURE_MULTIPLIER.get()*amplifiedAmount);
    }

    @Override
    public long shrinkIngredientAndGetTotalEnergy(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, int generateAmount, @NotNull IItemHandler handler) {
        generateAmount*=20;
        FluidStack stack = TANK_HELPER.getFluid(tool);
        if (stack.isEmpty()) return 0;
        Fluid fluid = stack.getFluid();
        MeltingFuel fuel = MeltingFuelLookup.findFuel(fluid);
        if (fuel==null) return 0;
        int baseAmount = fuel.getAmount(fluid);
        long baseToProduce = (long) fuel.getDuration() * TiAcConfig.COMMON.SMELTERY_GENERATOR_EACH_BURNING_TIME.get()/baseAmount;
        int consumption = (int) Math.max(1,generateAmount/baseToProduce);
        consumption = Math.min(consumption,stack.getAmount());
        stack.shrink(consumption);
        TANK_HELPER.setFluid(tool,stack);
        return consumption*baseToProduce;
    }
}
