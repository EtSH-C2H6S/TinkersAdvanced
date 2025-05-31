package com.c2h6s.tinkers_advanced.content.modifier.combat;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.etstlib.tool.modifiers.Combat.RealityBreaker;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.util.FakeExplosionUtil;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

public class Annihilate extends EtSTBaseModifier {
    public float cachedDamage;
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
    }

    @Override
    public boolean isNoLevels() {
        return true;
    }
    @Override
    public LegacyDamageSource modifyDamageSource(IToolStackView tool, ModifierEntry entry, LivingEntity attacker, InteractionHand hand, Entity target, EquipmentSlot sourceSlot, boolean isFullyCharged, boolean isExtraAttack, boolean isCritical, LegacyDamageSource source) {
        return source.setBypassArmor().setBypassInvul().setBypassInvulnerableTime().setBypassMagic().setBypassEnchantment().setBypassShield();
    }
    @Override
    public LegacyDamageSource modifyArrowDamageSource(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, AbstractArrow arrow, @Nullable LivingEntity attacker, @Nullable Entity target, LegacyDamageSource source) {
        return source.setBypassArmor().setBypassInvul().setBypassInvulnerableTime().setBypassMagic().setBypassEnchantment().setBypassShield();
    }

    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        this.cachedDamage = damage;
        return knockback;
    }

    @Override
    public void postMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getTarget() instanceof LivingEntity living&&!living.level().isClientSide&&cachedDamage>0){
            LivingEntity attacker = context.getAttacker();
            LegacyDamageSource source = LegacyDamageSource.any(living.damageSources().explosion(null)).setBypassInvulnerableTime();
            attacker.hurt(source,(float) (cachedDamage * TiAcConfig.COMMON.ANNIHILATE_EXPLOSION_SELF_MULTIPLIER.get()));
            IntOpenHashSet set = new IntOpenHashSet(attacker.getId());
            FakeExplosionUtil.fakeExplode((float) (cachedDamage * TiAcConfig.COMMON.ANNIHILATE_EXPLOSION_ATTACK_MULTIPLIER.get()),attacker,living.level(),living.position().add(new Vec3(0,living.getBbHeight()/2,0)),set,false);
        }
    }
}
