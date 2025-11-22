package com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.combat;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.etstlib.tool.modifiers.Combat.RealityBreaker;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.core.util.FakeExplosionUtil;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class Annihilate extends RealityBreaker {

    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public void postMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt,float damage) {
        damage = Math.max(damageDealt,damage);
        if (context.getTarget() instanceof LivingEntity living&&!living.level().isClientSide&&damage>0){
            LivingEntity attacker = context.getAttacker();
            LegacyDamageSource source = LegacyDamageSource.any(living.damageSources().explosion(null)).setBypassInvulnerableTime();
            attacker.hurt(source,(float) (damage * TiAcConfig.COMMON.ANNIHILATE_EXPLOSION_SELF_MULTIPLIER.get()));
            IntOpenHashSet set = new IntOpenHashSet(attacker.getId());
            FakeExplosionUtil.fakeExplode((float) (damage * TiAcConfig.COMMON.ANNIHILATE_EXPLOSION_ATTACK_MULTIPLIER.get()),attacker,living.level(),living.position().add(new Vec3(0,living.getBbHeight()/2,0)),set,false);
        }
        super.postMeleeHit(tool,modifier,context,damageDealt,damage);
    }
}
