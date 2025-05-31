package com.c2h6s.tinkers_advanced.content.modifierHooks;


import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class TiAcModifierHooks {
    public static final ModuleHook<GeneratorModuleModifierHook> GENERATOR_MODULE = ModifierHooks.register(TinkersAdvanced.getLocation("genetator_module"), GeneratorModuleModifierHook.class, GeneratorModuleModifierHook.AllMerger::new, new GeneratorModuleModifierHook() {
        @Override
        public int getBasicGeneration(IToolStackView tool, ModifierEntry entry) {
            return 0;
        }

        @Override
        public int getConditionalGeneration(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, int baseAmount, int amplifiedAmount) {
            return 0;
        }

        @Override
        public long shrinkIngredientAndGetTotalEnergy(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, int tickEnergyToGenerate, @NotNull IItemHandler handler) {
            return 0;
        }
    });
    public static final ModuleHook<ModifyGenerationModifierHook> MODIFY_GENERATION = ModifierHooks.register(TinkersAdvanced.getLocation("modify_generation"), ModifyGenerationModifierHook.class, ModifyGenerationModifierHook.AllMerger::new, new ModifyGenerationModifierHook() {
        @Override
        public long modifyTotalGeneration(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, ItemStack stack,long baseAmount, long amount) {
            return amount;
        }

        @Override
        public int modifyTotalPerTick(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, ItemStack stack,int baseAmount, int amount) {
            return amount;
        }

        @Override
        public void onGeneratorTick(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, ItemStack stack, int generatedAmount) {
        }
    });
}
