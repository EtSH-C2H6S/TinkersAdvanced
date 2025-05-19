package com.c2h6s.tinkers_advanced.content.modifier.combat;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.registery.TiAcParticleTypes;
import com.c2h6s.tinkers_advanced.util.CommonUtil;
import com.c2h6s.tinkers_advanced.util.ParticleChainUtil;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import java.util.List;

public class Electric extends EtSTBaseModifier {
    @Override
    public void postMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.isFullyCharged()){
            int i =0;
            int j = 0;
            Entity entityStart = context.getAttacker();
            Entity entityEnd = context.getTarget();
            IntOpenHashSet blacklist = new IntOpenHashSet();
            blacklist.add(entityStart.getId());
            blacklist.add(entityEnd.getId());
            LegacyDamageSource source =LegacyDamageSource.mobAttack(context.getAttacker()).setBypassShield().setBypassEnchantment().setBypassMagic().setBypassArmor().setBypassInvulnerableTime().setMsgId("plasma");
            entityEnd.hurt(source,damageDealt/2);
            while (i<=modifier.getLevel()*2+6&&j<16){
                entityStart = entityEnd;
                if (entityStart != null) {
                    entityEnd = CommonUtil.getNearestEntity(entityStart,modifier.getLevel()*2+2,blacklist,(entity -> !(entity instanceof ItemEntity)&&!(entity instanceof ExperienceOrb)&&!(entity instanceof Player)&&!(entity instanceof ThrowableItemProjectile)&&!entity.isInvulnerableTo(source)));
                    if (entityEnd!=null){
                        blacklist.add(entityEnd.getId());
                        if (entityEnd.hurt(source,damageDealt/2)){
                            ParticleChainUtil.drawLine(TiAcParticleTypes.ELECTRIC.get(), entityStart.position().add(0,entityStart.getBbHeight()/2,0),entityEnd.position().add(0,entityEnd.getBbHeight()/2,0) ,0.2,ParticleChainUtil.EnumParticleFunctions.RANDOM.name,0.005, ParticleChainUtil.EnumParticleFunctions.RANDOM.name,0.2,32,320);
                        }
                    }
                }
                if (entityEnd instanceof LivingEntity) i++;
                j++;
            }
        }
    }

    @Override
    public void afterArrowHit(ModDataNBT persistentData, ModifierEntry entry, ModifierNBT modifiers, AbstractArrow arrow, @Nullable LivingEntity attacker, @NotNull LivingEntity target, float damageDealt) {
        if (arrow.getTags().contains("is_critical")){
            int i =0;
            int j = 0;
            Entity entityStart = attacker;
            Entity entityEnd = target;
            if (attacker == null) return;
            IntOpenHashSet blacklist = new IntOpenHashSet();
            blacklist.add(entityStart.getId());
            blacklist.add(entityEnd.getId());
            LegacyDamageSource source =LegacyDamageSource.mobAttack(attacker).setBypassShield().setBypassEnchantment().setBypassMagic().setBypassArmor().setBypassInvulnerableTime().setMsgId("plasma");
            entityEnd.hurt(source,damageDealt/2);
            while (i<=entry.getLevel()*2+6&&j<16){
                entityStart = entityEnd;
                if (entityStart != null) {
                    entityEnd = CommonUtil.getNearestEntity(entityStart,entry.getLevel()*2+2,blacklist,(entity -> !(entity instanceof ItemEntity)&&!(entity instanceof ExperienceOrb)&&!(entity instanceof Player)&&!(entity instanceof ThrowableItemProjectile)&&!entity.isInvulnerableTo(source)));
                    if (entityEnd!=null){
                        blacklist.add(entityEnd.getId());
                        if (entityEnd.hurt(source,damageDealt/2)){
                            ParticleChainUtil.drawLine(TiAcParticleTypes.ELECTRIC.get(), entityStart.position().add(0,entityStart.getBbHeight()/2,0),entityEnd.position().add(0,entityEnd.getBbHeight()/2,0) ,0.2,ParticleChainUtil.EnumParticleFunctions.RANDOM.name,0.005, ParticleChainUtil.EnumParticleFunctions.RANDOM.name,0.2,32,320);
                        }
                    }
                }
                if (entityEnd instanceof LivingEntity) i++;
                j++;
            }
        }
    }
}
