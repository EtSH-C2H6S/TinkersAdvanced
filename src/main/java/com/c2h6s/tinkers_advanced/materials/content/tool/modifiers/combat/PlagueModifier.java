package com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.combat;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.etstlib.util.CommonConstants;
import com.c2h6s.tinkers_advanced.registery.TiAcEffects;
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

public class PlagueModifier extends EtSTBaseModifier {
    @Override
    public void postMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt,float damage) {
        if (context.isFullyCharged()&&context.getTarget() instanceof LivingEntity living){
            living.addEffect(new MobEffectInstance(TiAcEffects.PLAGUE.get(),400,modifier.getLevel()-1));
        }
    }

    @Override
    public void afterArrowHit(ModDataNBT persistentData, ModifierEntry entry, ModifierNBT modifiers, AbstractArrow arrow, @Nullable LivingEntity attacker, @NotNull LivingEntity target, float damageDealt) {
        if (arrow.getTags().contains(CommonConstants.KEY_CRITARROW)){
            target.addEffect(new MobEffectInstance(TiAcEffects.PLAGUE.get(),400,entry.getLevel()-1));
        }
    }
}
