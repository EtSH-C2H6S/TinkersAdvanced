package com.c2h6s.tinkers_advanced.content.modifier.compat.thermal;

import cofh.core.common.network.packet.client.OverlayMessagePacket;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.data.TiAcMaterialIds;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariant;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.DamageBlockModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.armor.ModifyDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.build.EnchantmentModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.MaterialNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.List;

import static com.c2h6s.etstlib.util.ToolEnergyUtil.extractEnergy;

public class FluxDefense extends FluxInfused implements DamageBlockModifierHook , ModifyDamageModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this,ModifierHooks.DAMAGE_BLOCK,ModifierHooks.MODIFY_HURT);
    }

    @Override
    public void addTooltip(IToolStackView iToolStackView, ModifierEntry modifierEntry, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
    }

    @Override
    public void onModeSwitch(IToolStackView tool, ServerPlayer player, ModifierEntry entry) {
        OverlayMessagePacket.sendToClient(Component.translatable("msg.tinkers_advanced.flux_mode.thermal_slash."+getMode(tool)),player);
        MaterialNBT nbt = tool.getMaterials();
        ToolStack toolStack = (ToolStack) tool;
        for (int i=0;i<nbt.size();i++){
            MaterialVariant variant = nbt.get(i);
            MaterialId materialId = variant.getId();
            if (materialId.getNamespace().equals(TinkersAdvanced.MODID)&&materialId.getPath().equals("activated_chromatic_steel")){
                switch (getMode(tool)){
                    default -> toolStack.replaceMaterial(i, TiAcMaterialIds.Thermal.ACTIVATED_CHROMATIC_STEEL);
                    case 1->toolStack.replaceMaterial(i,TiAcMaterialIds.Thermal.Variant.ACTIVATED_CHROMATIC_STEEL_ACTIVATED);
                    case 2->toolStack.replaceMaterial(i,TiAcMaterialIds.Thermal.Variant.ACTIVATED_CHROMATIC_STEEL_EMPOWERED);
                }
            }
        }
    }

    @Override
    public boolean isDamageBlocked(IToolStackView tool, ModifierEntry modifierEntry, EquipmentContext context, EquipmentSlot equipmentSlot, DamageSource damageSource, float amount) {
        LivingEntity living = context.getEntity();
        if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return false;
        if (living.invulnerableTime>0){
            return true;
        }
        if (living.isInvulnerableTo(damageSource)) return true;
        if (RANDOM.nextInt(20)==0&&getMode(tool)>=1&&extractEnergy(tool,20000,true)>=10000){
            extractEnergy(tool,20000,false);
            living.invulnerableTime+=10;
            living.level().playSound(null,living.blockPosition(), SoundEvents.FIREWORK_ROCKET_LAUNCH,living.getSoundSource(),1,2);
            return true;
        }
        return false;
    }


    @Override
    public float modifyDamageTaken(IToolStackView tool, ModifierEntry modifierEntry, EquipmentContext context, EquipmentSlot equipmentSlot, DamageSource damageSource, float amount, boolean direct) {
        if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return amount;
        if (getMode(tool)>=2&&amount>0){
            int toReduce = (int) (amount*0.2);
            toReduce = Math.min(toReduce,extractEnergy(tool,2000*toReduce,true));
            if (toReduce>0){
                extractEnergy(tool,2000*toReduce,false);
                return amount-toReduce;
            }
        }
        return amount;
    }

}
