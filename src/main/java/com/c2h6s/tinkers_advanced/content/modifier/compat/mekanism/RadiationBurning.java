package com.c2h6s.tinkers_advanced.content.modifier.compat.mekanism;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.etstlib.util.CommonConstants;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import mekanism.api.radiation.IRadiationManager;
import mekanism.api.radiation.capability.IRadiationEntity;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

public class RadiationBurning extends EtSTBaseModifier {
    public static final IRadiationManager RADIATION_MANAGER = IRadiationManager.INSTANCE;
    @Override
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        if (context.getTarget() instanceof LivingEntity living){
            LazyOptional<IRadiationEntity> optional = living.getCapability(Capabilities.RADIATION_ENTITY);
            if (optional.isPresent()){
                IRadiationEntity cap = optional.orElse(null);
                float bonus = (float) Math.min(TiAcConfig.COMMON.IRRADIUM_MAX_BONUS.get(),cap.getRadiation() * TiAcConfig.COMMON.IRRADIUM_BONUS_PER_Sv.get());
                if (bonus>0.001){
                    return damage+baseDamage*bonus;
                }
            }
        }
        return damage;
    }
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getTarget() instanceof LivingEntity living&&context.isFullyCharged()){
            RADIATION_MANAGER.radiate(living,modifier.getLevel()*TiAcConfig.COMMON.IRRADIUM_RADIATION_INFLICT.get());
        }
    }

    @Override
    public float onGetArrowDamage(ModDataNBT persistentData, ModifierEntry entry, ModifierNBT modifiers, AbstractArrow arrow, @Nullable LivingEntity attacker, @NotNull Entity target, float baseDamage, float damage) {
        if (target instanceof LivingEntity living){
            LazyOptional<IRadiationEntity> optional = living.getCapability(Capabilities.RADIATION_ENTITY);
            if (optional.isPresent()){
                IRadiationEntity cap = optional.orElse(null);
                float bonus = (float) Math.min(TiAcConfig.COMMON.IRRADIUM_MAX_BONUS.get(),cap.getRadiation() * TiAcConfig.COMMON.IRRADIUM_BONUS_PER_Sv.get());
                if (bonus>0.001){
                    return damage+baseDamage*bonus;
                }
            }
        }
        return damage;
    }
    @Override
    public void afterArrowHit(ModDataNBT persistentData, ModifierEntry entry, ModifierNBT modifiers, AbstractArrow arrow, @Nullable LivingEntity attacker, @NotNull LivingEntity target, float damageDealt) {
        if (arrow.getTags().contains(CommonConstants.KEY_CRITARROW)) RADIATION_MANAGER.radiate(target,entry.getLevel()*TiAcConfig.COMMON.IRRADIUM_RADIATION_INFLICT.get());
    }
}
