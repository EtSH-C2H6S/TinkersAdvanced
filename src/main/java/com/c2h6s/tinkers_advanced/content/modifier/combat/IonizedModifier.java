package com.c2h6s.tinkers_advanced.content.modifier.combat;

import com.c2h6s.etstlib.content.misc.entityTicker.EntityTickerInstance;
import com.c2h6s.etstlib.content.misc.entityTicker.EntityTickerManager;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.etstlib.util.CommonConstants;
import com.c2h6s.tinkers_advanced.registery.TiAcEntityTicker;
import com.c2h6s.tinkers_advanced.util.CommonUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;


public class IonizedModifier extends EtSTBaseModifier implements OnAttackedModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.ON_ATTACKED);
    }

    @Override
    public void postMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage) {
        if (context.getTarget() instanceof LivingEntity living&&context.isFullyCharged()){
            living.getPersistentData().putInt(CommonUtil.KEY_ATTACKER,context.getAttacker().getId());
            EntityTickerManager.EntityTickerManagerInstance managerInstance = EntityTickerManager.getInstance(living);
            EntityTickerInstance instance = new EntityTickerInstance(TiAcEntityTicker.IONIZED.get(), modifier.getLevel(),200);
            managerInstance.addTicker(instance,(a,b)->Math.min(a+b,10),Integer::sum);
        }
    }
    @Override
    public void afterArrowHit(ModDataNBT persistentData, ModifierEntry entry, ModifierNBT modifiers, AbstractArrow arrow, @Nullable LivingEntity attacker, @NotNull LivingEntity target, float damageDealt) {
        if (arrow.getTags().contains(CommonConstants.KEY_CRITARROW)) {
            if (attacker != null) {
                target.getPersistentData().putInt(CommonUtil.KEY_ATTACKER, attacker.getId());
            }
            EntityTickerManager.EntityTickerManagerInstance managerInstance = EntityTickerManager.getInstance(target);
            EntityTickerInstance instance = new EntityTickerInstance(TiAcEntityTicker.IONIZED.get(), entry.getLevel(), 200);
            managerInstance.addTicker(instance, (a, b) -> Math.min(a + b, 10), Integer::sum);
        }
    }

    @Override
    public void onAttacked(IToolStackView iToolStackView, ModifierEntry modifier, EquipmentContext context, EquipmentSlot equipmentSlot, DamageSource damageSource, float v, boolean b) {
        if (damageSource.getEntity() instanceof LivingEntity living){
            living.getPersistentData().putInt(CommonUtil.KEY_ATTACKER,context.getEntity().getId());
            EntityTickerManager.EntityTickerManagerInstance managerInstance = EntityTickerManager.getInstance(living);
            EntityTickerInstance instance = new EntityTickerInstance(TiAcEntityTicker.IONIZED.get(), modifier.getLevel(), 200);
            managerInstance.addTicker(instance, (a, c) -> Math.min(a + c, 10), Integer::sum);
        }
    }
}
