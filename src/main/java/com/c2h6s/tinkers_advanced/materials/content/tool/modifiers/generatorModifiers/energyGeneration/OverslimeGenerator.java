package com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.generatorModifiers.energyGeneration;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.core.library.modifier.hooks.GeneratorModuleModifierHook;
import com.c2h6s.tinkers_advanced.core.library.modifier.hooks.TiAcModifierHooks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.modules.capacity.OverslimeModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class OverslimeGenerator extends EtSTBaseModifier implements GeneratorModuleModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, TiAcModifierHooks.GENERATOR_MODULE);
    }

    @Override
    public int getBasicGeneration(IToolStackView tool, ModifierEntry entry) {
        return TiAcConfig.COMMON.COMBUSTION_GENERATOR_BASIC_GENERATION.get()*entry.getLevel();
    }

    @Override
    public long shrinkIngredientAndGetTotalEnergy(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, int generateAmount, @NotNull IItemHandler handler) {
        int perSlime = TiAcConfig.COMMON.OVERSLIME_GENERATOR_GENERATION_EACH_OVERSLIME.get();
        long amount =generateAmount * 20L;
        int consumption = (int) Math.max(1,amount/perSlime);
        var overslime = OverslimeModule.INSTANCE;
        consumption = Math.min(consumption,overslime.getAmount(tool));
        if (consumption>0){
            overslime.removeAmount(tool,entry,consumption);
            return (long) consumption * perSlime;
        }
        return 0;
    }
}
