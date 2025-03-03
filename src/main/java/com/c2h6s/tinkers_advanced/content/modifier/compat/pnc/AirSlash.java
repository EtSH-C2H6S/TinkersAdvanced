package com.c2h6s.tinkers_advanced.content.modifier.compat.pnc;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.content.entity.AirSlashProjectile;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import static com.c2h6s.etstlib.tool.modifiers.capabilityProvider.PnCIntegration.AirStorageProvider.*;

public class AirSlash extends EtSTBaseModifier {
    @Override
    public boolean isNoLevels() {
        return true;
    }
    @Override
    public void onLeftClickEmpty(IToolStackView tool, ModifierEntry entry, Player player, Level level, EquipmentSlot equipmentSlot) {
        if (getAir(tool)>100&&player.getAttackStrengthScale(0)>0.7){
            float damage = (getPressure(tool)*0.25f)+0.2f*tool.getStats().get(ToolStats.ATTACK_DAMAGE);
            AirSlashProjectile projectile = new AirSlashProjectile(player.level());
            Vec3 vec3 = player.getLookAngle();
            projectile.baseDamage = damage;
            projectile.setOwner(player);
            projectile.shoot(vec3.x,vec3.y,vec3.z,2,0);
            projectile.setPos(player.position().add(0,player.getBbHeight()/2,0));
            player.level().addFreshEntity(projectile);
            player.level().playSound(null,player.blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP,player.getSoundSource(),1,1);
            if (!player.level().isClientSide){
                addAir(tool,-100);
            }
        }
    }

    @Override
    public void onLeftClickBlock(IToolStackView tool, ModifierEntry entry, Player player, Level level, EquipmentSlot equipmentSlot, BlockState state, BlockPos pos) {
        if (getAir(tool)>100&&player.getAttackStrengthScale(0)>0.7){
            float damage = (getPressure(tool)*0.25f)+0.2f*tool.getStats().get(ToolStats.ATTACK_DAMAGE);
            AirSlashProjectile projectile = new AirSlashProjectile(player.level());
            Vec3 vec3 = player.getLookAngle();
            projectile.baseDamage = damage;
            projectile.setOwner(player);
            projectile.shoot(vec3.x,vec3.y,vec3.z,2,0);
            projectile.setPos(player.position().add(0,player.getBbHeight()/2,0));
            player.level().addFreshEntity(projectile);
            player.level().playSound(null,player.blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP,player.getSoundSource(),1,1);
            if (!player.level().isClientSide){
                addAir(tool,-100);
            }
        }
    }

    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        if (getAir(tool)>100&&!context.isExtraAttack()&&context.isFullyCharged()){
            float slashDamage = (getPressure(tool)*0.25f)+0.2f*damage;
            AirSlashProjectile projectile = new AirSlashProjectile(context.getAttacker().level());
            Vec3 vec3 = context.getAttacker().getLookAngle();
            projectile.baseDamage = slashDamage;
            projectile.setOwner(context.getAttacker());
            projectile.shoot(vec3.x,vec3.y,vec3.z,2,0);
            projectile.setPos(context.getAttacker().position().add(0,context.getAttacker().getBbHeight()/2,0));
            context.getAttacker().level().addFreshEntity(projectile);
            context.getAttacker().level().playSound(null,context.getAttacker().blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP,context.getAttacker().getSoundSource(),1,1);
            if (!context.getAttacker().level().isClientSide){
                addAir(tool,-100);
            }
        }
        return knockback;
    }
}
