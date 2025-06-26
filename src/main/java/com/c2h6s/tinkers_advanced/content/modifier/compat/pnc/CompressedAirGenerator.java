package com.c2h6s.tinkers_advanced.content.modifier.compat.pnc;

import com.c2h6s.etstlib.tool.modifiers.base.BasicPressurizableModifier;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.etstlib.tool.modifiers.capabilityProvider.PnCIntegration.AirStorageProvider;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.content.modifierHooks.GeneratorModuleModifierHook;
import com.c2h6s.tinkers_advanced.content.modifierHooks.TiAcModifierHooks;
import me.desht.pneumaticcraft.common.config.ConfigHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class CompressedAirGenerator extends BasicPressurizableModifier implements GeneratorModuleModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, TiAcModifierHooks.GENERATOR_MODULE);
    }

    @Override
    public int getBasicGeneration(IToolStackView tool, ModifierEntry entry) {
        return (int) (TiAcConfig.COMMON.COMPRESSED_AIR_GENERATON_BASIC_GENERATION.get()*AirStorageProvider.getPressure(tool));
    }

    @Override
    public long shrinkIngredientAndGetTotalEnergy(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, int generateAmount, @NotNull IItemHandler handler) {
        generateAmount*=20;
        int perGeneration = ConfigHelper.common().machines.pneumaticDynamoEfficiency.get();
        if (AirStorageProvider.getAir(tool)>0){
            int consumption = (int) (generateAmount*100f/perGeneration);
            consumption = Math.min(consumption,AirStorageProvider.getAir(tool));
            if (consumption>0){
                AirStorageProvider.addAir(tool,-consumption);
                return (long) consumption *perGeneration/100;
            }
        }
        return 0;
    }

    @Override
    public int getBaseVolume(ModifierEntry modifierEntry) {
        return 1000;
    }

    @Override
    public float getMaxPressure(ModifierEntry modifierEntry) {
        return 10;
    }
}
