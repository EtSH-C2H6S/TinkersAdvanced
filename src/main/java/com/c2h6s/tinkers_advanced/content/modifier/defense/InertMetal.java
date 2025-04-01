package com.c2h6s.tinkers_advanced.content.modifier.defense;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InertMetal extends EtSTBaseModifier {
    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide && isCorrectSlot && world.getGameTime()%(20/modifier.getLevel())==0){
            Collection<MobEffectInstance> collection = holder.getActiveEffects();
            ArrayList<MobEffectInstance> harmful =new ArrayList<>();
            collection.forEach((instance)-> {
                        if (instance.getEffect().getCategory() == MobEffectCategory.HARMFUL) {
                            harmful.add(instance);
                        }});
            if (!harmful.isEmpty()){
                MobEffect effect = harmful.get(RANDOM.nextInt(harmful.size())).getEffect();
                if (holder.getEffect(effect)!=null) holder.removeEffect(effect);
            }
        }
    }
}
