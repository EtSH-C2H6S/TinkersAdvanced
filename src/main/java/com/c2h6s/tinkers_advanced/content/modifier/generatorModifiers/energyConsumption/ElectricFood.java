package com.c2h6s.tinkers_advanced.content.modifier.generatorModifiers.energyConsumption;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.content.modifierHooks.GeneratorModuleModifierHook;
import com.c2h6s.tinkers_advanced.content.modifierHooks.TiAcModifierHooks;
import com.c2h6s.tinkers_advanced.content.objects.ToolEnergyProduction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class ElectricFood extends EtSTBaseModifier implements GeneratorModuleModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, TiAcModifierHooks.GENERATOR_MODULE);
    }

    @Override
    public int getBasicGeneration(IToolStackView tool, ModifierEntry entry) {
        return -TiAcConfig.COMMON.ELECTRIC_FOOD_BASIC_CONSUMPTION.get()*entry.getLevel();
    }

    @Override
    public long shrinkIngredientAndGetTotalEnergy(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, int generateAmount, @NotNull IItemHandler handler) {
        generateAmount*=20;
        if (holderEntity instanceof Player player){
            FoodData data = player.getFoodData();
            int foodLevelToAdd =20 - data.getFoodLevel();
            int saturationToAdd = (int) (20 - data.getSaturationLevel());
            int eachConsumption = TiAcConfig.COMMON.ELECTRIC_FOOD_CONSUMPTION_EACH_FOODLEVEL.get();
            if (foodLevelToAdd+saturationToAdd>0) {
                int toAdd = Mth.clamp(-generateAmount / eachConsumption, 1, foodLevelToAdd + saturationToAdd);
                if (toAdd>foodLevelToAdd){
                    data.setFoodLevel(20);
                    data.setSaturation(data.getSaturationLevel()+toAdd-foodLevelToAdd);
                } else data.setFoodLevel(data.getFoodLevel()+toAdd);
                return (long) -toAdd * eachConsumption;
            }
        }
        return 0;
    }
}
