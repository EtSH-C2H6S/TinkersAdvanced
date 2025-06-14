package com.c2h6s.tinkers_advanced.content.modifier.generatorModifiers.energyModification;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.etstlib.util.ToolEnergyUtil;
import com.c2h6s.tinkers_advanced.content.item.toolItem.ElectronTunerItem;
import com.c2h6s.tinkers_advanced.content.modifierHooks.ModifyGenerationModifierHook;
import com.c2h6s.tinkers_advanced.content.modifierHooks.TiAcModifierHooks;
import com.c2h6s.tinkers_advanced.registery.TiAcModifiers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.ToolEnergyCapability;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.List;

public class EnergyDistribute extends EtSTBaseModifier implements ModifyGenerationModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, TiAcModifierHooks.MODIFY_GENERATION);
    }

    @Override
    public void onGeneratorTick(IToolStackView tool, ModifierEntry entry, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity, ItemStack stack, int generatedAmount) {
        if (ToolEnergyCapability.getEnergy(tool)<=0) return;
        if (holderEntity instanceof Player player){
            Inventory inventory = player.getInventory();
            List<ItemStack> itemStacks = new java.util.ArrayList<>(List.copyOf(player.getInventory().armor));
            itemStacks.addAll(inventory.offhand);
            for (ItemStack stack1:itemStacks){
                if (stack1.isEmpty()) continue;
                if (stack1.getItem() instanceof IModifiable){
                    IToolStackView toolStack = ToolStack.from(stack1);
                    if (toolStack.getModifierLevel(TiAcModifiers.ENERGY_DISTRIBUTOR.get())>0) continue;
                }
                int energy = ToolEnergyCapability.getEnergy(tool);
                if (energy<=0) return;
                stack1.getCapability(ForgeCapabilities.ENERGY).ifPresent(energyStorage->{
                    if (energyStorage.canReceive()) {
                        int insert = energyStorage.receiveEnergy(energy, true);
                        insert = Math.min(ToolEnergyUtil.extractEnergy(tool, insert, true), insert);
                        if (insert > 0) {
                            energyStorage.receiveEnergy(insert, false);
                            ToolEnergyUtil.extractEnergy(tool, insert, false);
                        }
                    }
                });
            }
            for (int i =0;i<9;i++){
                ItemStack stack1 = inventory.items.get(i);
                if (stack1.isEmpty()) continue;
                if (stack1.getItem() instanceof IModifiable){
                    IToolStackView toolStack = ToolStack.from(stack1);
                    if (toolStack.getModifierLevel(TiAcModifiers.ENERGY_DISTRIBUTOR.get())>0) continue;
                }
                int energy = ToolEnergyCapability.getEnergy(tool);
                if (energy<=0) return;
                stack1.getCapability(ForgeCapabilities.ENERGY).ifPresent(energyStorage->{
                    if (energyStorage.canReceive()) {
                        int insert = energyStorage.receiveEnergy(energy, true);
                        insert = Math.min(ToolEnergyUtil.extractEnergy(tool, insert, true), insert);
                        if (insert > 0) {
                            energyStorage.receiveEnergy(insert, false);
                            ToolEnergyUtil.extractEnergy(tool, insert, false);
                        }
                    }
                });
            }
        }
    }
}
