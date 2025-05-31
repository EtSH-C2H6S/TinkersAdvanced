package com.c2h6s.tinkers_advanced.content.modifierHooks;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;

public interface GeneratorModuleModifierHook {
    //基础的发电速率（每tick产出）。
    int getBasicGeneration (IToolStackView tool, ModifierEntry entry);
    //被工具的产能倍率等条件增幅后的发电速率（每tick）。返回负值时该能量模块消耗能量。
    default int getConditionalGeneration(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity,int baseAmount,int amplifiedAmount){
        return amplifiedAmount;
    };
    //消耗燃料并获得总产出。返回负值时则表示总耗能。出于性能考虑，最好返回能够供20tick运行的能量来降低卡顿。
    long shrinkIngredientAndGetTotalEnergy(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, int generateAmount,@NotNull IItemHandler handler);



    record AllMerger(Collection<GeneratorModuleModifierHook> modules) implements GeneratorModuleModifierHook{
        @Override
        public int getBasicGeneration(IToolStackView tool, ModifierEntry entry) {
            int amount = 0;
            for (GeneratorModuleModifierHook module:modules){
                amount+= module.getBasicGeneration(tool,entry);
            }
            return amount;
        }

        @Override
        public int getConditionalGeneration(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, int baseAmount, int amplifiedAmount) {
            int amount = 0;
            for (GeneratorModuleModifierHook module:modules){
                amount+= module.getConditionalGeneration(tool,entry,holderEntity,holderBlockEntity,baseAmount,amplifiedAmount);
            }
            return amount;
        }

        @Override
        public long shrinkIngredientAndGetTotalEnergy(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, int generateAmount,@NotNull IItemHandler handler) {
            long amount = 0;
            for (GeneratorModuleModifierHook module:modules){
                amount+= module.shrinkIngredientAndGetTotalEnergy(tool,entry,holderEntity,holderBlockEntity,generateAmount,handler);
            }
            return amount;
        }
    }

}
