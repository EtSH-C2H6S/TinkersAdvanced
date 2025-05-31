package com.c2h6s.tinkers_advanced.content.modifier.generatorModifiers.energyGeneration;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.content.modifierHooks.GeneratorModuleModifierHook;
import com.c2h6s.tinkers_advanced.content.modifierHooks.TiAcModifierHooks;
import com.c2h6s.tinkers_advanced.content.objects.ToolEnergyProduction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class CombustionGenerator extends EtSTBaseModifier implements GeneratorModuleModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, TiAcModifierHooks.GENERATOR_MODULE);
    }

    @Override
    public int getBasicGeneration(IToolStackView tool, ModifierEntry entry) {
        return TiAcConfig.COMMON.COMBUSTION_GENERATOR_BASIC_GENERATION.get() *entry.getLevel();
    }


    @Override
    public long shrinkIngredientAndGetTotalEnergy(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, int generateAmount, @NotNull IItemHandler handler) {
        long amount =0;
        long required = generateAmount* 20L;
        for (int i = 0; i < 8; i++) {
            IItemHandlerModifiable itemHandler = (IItemHandlerModifiable) handler;
            ItemStack stack = itemHandler.getStackInSlot(i);
            int burnTime = ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);
            if (burnTime > 0) {
                long production = (long) burnTime *TiAcConfig.COMMON.COMBUSTION_GENERATOR_GENERATION_EACH_BURNING_TIME.get();
                int count = Mth.clamp(Math.round((float) required / production), 1, stack.getCount());
                ItemStack remaining = ForgeHooks.getCraftingRemainingItem(stack);
                if (!remaining.isEmpty()) {
                    int output = 0;
                    for (int j = 8; j < 16; j++) {
                        ItemStack leftover = itemHandler.insertItem(j, remaining, true);
                        if (leftover.getCount() < remaining.getCount()) {
                            output += remaining.getCount() - leftover.getCount();
                            remaining = itemHandler.insertItem(j, remaining, false);
                        }
                        if (remaining.isEmpty()) break;
                    }
                    count = Math.min(count, output);
                }
                required -= (int) (count * production);
                amount += (long) count * production;
                stack.shrink(count);
                itemHandler.setStackInSlot(i, stack);
            }
            if (required <= 0) break;
        }
        return amount;
    }
}
