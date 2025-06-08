package com.c2h6s.tinkers_advanced.content.modifier.combat;

import com.c2h6s.etstlib.content.misc.entityTicker.EntityTickerInstance;
import com.c2h6s.etstlib.content.misc.entityTicker.EntityTickerManager;
import com.c2h6s.etstlib.content.misc.vibration.VibrationContext;
import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.etstlib.register.EtSTLibHooks;
import com.c2h6s.etstlib.tool.hooks.VibrationListeningModifierHook;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.registery.TiAcEntityTicker;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import java.util.UUID;

public class EchoLocating extends EtSTBaseModifier implements VibrationListeningModifierHook {
    public static final UUID ECHO_UUID = UUID.fromString("f9e0ced7-997a-cc95-3947-9106ecea3b3a");
    public static final ResourceLocation KEY_COOLDOWN = TinkersAdvanced.getLocation("echo_locating_cooldown");
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.EQUIPMENT_CHANGE, EtSTLibHooks.VIBRATION_LISTENING);
    }

    @Override
    public UUID getAcceptorUUID(IToolStackView iToolStackView, ModifierEntry modifierEntry, Player player, Level level, EquipmentSlot equipmentSlot) {
        return ECHO_UUID;
    }

    @Override
    public int listenRange(IToolStackView iToolStackView, ModifierEntry modifierEntry, Player player, Level level, EquipmentSlot equipmentSlot, int i) {
        return 16;
    }

    @Override
    public boolean canReceiveVibration(IToolStackView iToolStackView, ModifierEntry modifierEntry, Player player, ServerLevel serverLevel, EquipmentSlot equipmentSlot, VibrationContext vibrationContext) {
        if (iToolStackView.getPersistentData().getInt(KEY_COOLDOWN)>0) return false;
        if (vibrationContext.directEntity instanceof LivingEntity living){
            EntityTickerManager.EntityTickerManagerInstance instance = EntityTickerManager.getInstance(living);
            return !instance.hasTicker(TiAcEntityTicker.SCULK_MARKED.get())&&living!=player;
        }
        if (vibrationContext.projectileOwner instanceof LivingEntity living){
            EntityTickerManager.EntityTickerManagerInstance instance = EntityTickerManager.getInstance(living);
            return !instance.hasTicker(TiAcEntityTicker.SCULK_MARKED.get())&&living!=player;
        }
        return false;
    }

    @Override
    public void onReceivingVibration(IToolStackView iToolStackView, ModifierEntry modifierEntry, Player player, ServerLevel serverLevel, EquipmentSlot equipmentSlot, VibrationContext vibrationContext) {
        LivingEntity living = null;
        if (vibrationContext.projectileOwner instanceof LivingEntity entity) living = entity;
        if (vibrationContext.directEntity instanceof LivingEntity entity) living = entity;
        if (living!=null) {
            EntityTickerManager.EntityTickerManagerInstance managerInstance = EntityTickerManager.getInstance(living);
            managerInstance.addTicker(new EntityTickerInstance(TiAcEntityTicker.SCULK_MARKED.get(), 1,10+10*modifierEntry.getLevel()),Integer::max,Integer::sum);
            iToolStackView.getPersistentData().putInt(KEY_COOLDOWN,4);
        }
    }

    @Override
    public void postMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getTarget() instanceof LivingEntity living&&context.getLevel() instanceof ServerLevel serverLevel){
            EntityTickerManager.EntityTickerManagerInstance instance = EntityTickerManager.getInstance(living);
            if (instance.hasTicker(TiAcEntityTicker.SCULK_MARKED.get())){
                living.hurt(LegacyDamageSource.any(living.damageSources().sonicBoom(context.getAttacker())).setBypassInvulnerableTime(),damageDealt*0.5f);
                serverLevel.sendParticles(ParticleTypes.SONIC_BOOM,living.getX(),living.getY()+0.5*living.getBbHeight(),living.getZ(),1,0,0,0,0);
            }
        }
    }

    @Override
    public void afterArrowHit(ModDataNBT persistentData, ModifierEntry entry, ModifierNBT modifiers, AbstractArrow arrow, @Nullable LivingEntity attacker, @NotNull LivingEntity target, float damageDealt) {
        if (attacker!=null&&attacker.level() instanceof ServerLevel serverLevel){
            EntityTickerManager.EntityTickerManagerInstance instance = EntityTickerManager.getInstance(target);
            if (instance.hasTicker(TiAcEntityTicker.SCULK_MARKED.get())){
                target.hurt(LegacyDamageSource.any(attacker.damageSources().sonicBoom(attacker)).setBypassInvulnerableTime(),damageDealt*0.5f);
                serverLevel.sendParticles(ParticleTypes.SONIC_BOOM,target.getX(),target.getY()+0.5*target.getBbHeight(),target.getZ(),1,0,0,0,0);
            }
        }
    }

    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide&&world.getGameTime()%5==0&&tool.getPersistentData().getInt(KEY_COOLDOWN)>0){
            tool.getPersistentData().putInt(KEY_COOLDOWN,tool.getPersistentData().getInt(KEY_COOLDOWN)-1);
        }
    }

}
