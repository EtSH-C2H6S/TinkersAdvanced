package com.c2h6s.tinkers_advanced.content.modifierHooks;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;

public interface ModifyGenerationModifierHook {
    //修改全部发电机模块的总产出，产能为正值耗能为负值
    default long modifyTotalGeneration(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, ItemStack stack,long baseAmount, long amount){
        return amount;
    }
    //修改全部发电机模块的总每刻产出速率，产能为正值耗能为负值
    default int modifyTotalPerTick(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, ItemStack stack,int baseAmount, int amount){
        return amount;
    }
    //在全部能量模块运行后被调用，根据当前刻内的发电/耗电量来施加一些效果。
    default void onGeneratorTick(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, ItemStack stack, int generatedAmount){}

    record AllMerger(Collection<ModifyGenerationModifierHook> modules) implements ModifyGenerationModifierHook{

        @Override
        public long modifyTotalGeneration(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, ItemStack stack,long baseAmount, long amount) {
            for (ModifyGenerationModifierHook module:this.modules){
                amount = module.modifyTotalGeneration(tool,entry,holderEntity,holderBlockEntity,stack,baseAmount,amount);
            }
            return amount;
        }

        @Override
        public int modifyTotalPerTick(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, ItemStack stack,int baseAmount, int amount) {
            for (ModifyGenerationModifierHook module:this.modules){
                amount = module.modifyTotalPerTick(tool,entry,holderEntity,holderBlockEntity,stack,baseAmount,amount);
            }
            return amount;
        }

        @Override
        public void onGeneratorTick(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity,ItemStack stack, int generatedAmount) {
            for (ModifyGenerationModifierHook module:this.modules){
                module.onGeneratorTick(tool,entry,holderEntity,holderBlockEntity,stack,generatedAmount);
            }
        }
    }
}
