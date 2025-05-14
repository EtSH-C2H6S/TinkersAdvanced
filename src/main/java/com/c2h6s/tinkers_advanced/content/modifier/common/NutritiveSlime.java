package com.c2h6s.tinkers_advanced.content.modifier.common;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.modifiers.slotless.OverslimeModifier;

public class NutritiveSlime extends EtSTBaseModifier {
    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (isCorrectSlot&&holder instanceof ServerPlayer player&&world.getGameTime()%(20/modifier.getLevel())==0){
            FoodData foodData = player.getFoodData();
            OverslimeModifier overslime = TinkerModifiers.overslime.get();
            int slimeAmount = overslime.getShield(tool);
            if (slimeAmount<=0) return;
            int recover;
            if (foodData.needsFood()){
                recover = Math.min(20-foodData.getFoodLevel(),slimeAmount/ TiAcConfig.COMMON.NUTRITIVE_SLIME_COST.get());
                if (recover>0){
                    foodData.setFoodLevel(foodData.getFoodLevel()+recover);
                    overslime.setShield(tool,modifier,overslime.getShield(tool)-recover*10);
                    slimeAmount-=recover*10;
                    if (slimeAmount<=0) return;
                }
            }
            if (foodData.getSaturationLevel()<foodData.getFoodLevel()){
                recover = (int) Math.min(foodData.getFoodLevel()-foodData.getSaturationLevel(), slimeAmount / TiAcConfig.COMMON.NUTRITIVE_SLIME_COST.get());
                if (recover>0){
                    foodData.setSaturation(foodData.getSaturationLevel()+recover);
                    overslime.setShield(tool,modifier,overslime.getShield(tool)-recover*10);
                }
            }
        }
    }
}
