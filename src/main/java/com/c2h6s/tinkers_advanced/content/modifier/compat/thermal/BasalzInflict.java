package com.c2h6s.tinkers_advanced.content.modifier.compat.thermal;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

public class BasalzInflict extends EtSTBaseModifier {
    @Override
    public void afterMeleeHit(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.isFullyCharged()&&context.getTarget() instanceof LivingEntity living){
            living.forceAddEffect(new MobEffectInstance(CoreMobEffects.SUNDERED.get(),80+40*modifier.getLevel(),modifier.getLevel()-1),context.getAttacker());
            living.invulnerableTime=0;
        }
    }
    @Override
    public void afterArrowHit(ModDataNBT persistentData, ModifierEntry modifier, ModifierNBT modifiers, AbstractArrow arrow, @Nullable LivingEntity attacker, @NotNull LivingEntity target, float damageDealt) {
        if (arrow.isCritArrow()){
            target.forceAddEffect(new MobEffectInstance(CoreMobEffects.SUNDERED.get(),80+40*modifier.getLevel(),modifier.getLevel()-1),attacker);
            target.invulnerableTime=0;
        }
    }
}
