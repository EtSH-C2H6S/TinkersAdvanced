package com.c2h6s.tinkers_advanced.content.modifier.combat.toolBase;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.etstlib.entity.specialDamageSources.PercentageBypassArmorSource;
import com.c2h6s.etstlib.tool.modifiers.base.BasicFEModifier;
import com.c2h6s.etstlib.util.IToolUuidGetter;
import com.c2h6s.etstlib.util.MathUtil;
import com.c2h6s.etstlib.util.ToolEnergyUtil;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.content.entity.PlasmaSlashEntity;
import com.c2h6s.tinkers_advanced.content.item.toolItem.ElectronTunerItem;
import com.c2h6s.tinkers_advanced.content.objects.ToolEnergyProduction;
import com.c2h6s.tinkers_advanced.util.CommonUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierTraitHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.TinkerModifiers;

import java.util.List;
import java.util.function.BiConsumer;

import static com.c2h6s.tinkers_advanced.content.item.toolItem.ElectronTunerItem.*;

public class EnderTuning extends BasicFEModifier implements ModifierTraitHook, AttributesModifierHook, ToolDamageModifierHook , TooltipModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this,ModifierHooks.TOOL_STATS,ModifierHooks.MODIFIER_TRAITS,ModifierHooks.ATTRIBUTES,ModifierHooks.TOOL_DAMAGE,ModifierHooks.TOOLTIP);
    }

    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void onLeftClickEmpty(IToolStackView tool, ModifierEntry entry, Player player, Level level, EquipmentSlot equipmentSlot) {
        if (!level.isClientSide&&TiAcConfig.COMMON.ELECTRON_TUNER_SPECIAL_BONUS.get()&& getMode(tool)==1&&player.getAttackStrengthScale(0)>0.8){
            if (ToolEnergyUtil.extractEnergy(tool,TiAcConfig.COMMON.ELECTRON_TUNER_CONSUMPTION.get(),true)>=TiAcConfig.COMMON.ELECTRON_TUNER_CONSUMPTION.get()) {
                ToolEnergyUtil.extractEnergy(tool,TiAcConfig.COMMON.ELECTRON_TUNER_CONSUMPTION.get(),false);
                PlasmaSlashEntity entity = PlasmaSlashEntity.create(player, ElectronTunerItem.getSlashScale(tool), player.getLookAngle(), (ToolStack) tool);
                entity.baseDamage = tool.getStats().get(ToolStats.ATTACK_DAMAGE) * (0.25f + Math.min(0.75f, 0.25F * tool.getModifierLevel(TinkerModifiers.sweeping.get())));
                level.addFreshEntity(entity);
            }
        }
    }


    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        int energyCost = ToolEnergyUtil.extractEnergy(tool,amount*TiAcConfig.COMMON.ELECTRON_TUNER_CONSUMPTION.get(),true);
        if (energyCost>=TiAcConfig.COMMON.ELECTRON_TUNER_CONSUMPTION.get()){
            int cancel = energyCost/TiAcConfig.COMMON.ELECTRON_TUNER_CONSUMPTION.get();
            amount-= cancel;
            ToolEnergyUtil.extractEnergy(tool,cancel*TiAcConfig.COMMON.ELECTRON_TUNER_CONSUMPTION.get(),false);
        }
        return amount;
    }

    @Override
    public void addToolStats(IToolContext iToolContext, ModifierEntry modifierEntry, ModifierStatsBuilder modifierStatsBuilder) {
        super.addToolStats(iToolContext, modifierEntry, modifierStatsBuilder);
        float value = iToolContext.getPersistentData().getFloat(ElectronTunerItem.KEY_ATTACK_DAMAGE);
        if (value - 0.5 !=0) {
            float damageModifier = (float) Math.abs(TiAcConfig.COMMON.ELECTRON_TUNER_ATTACK_DAMAGE_ADJUSTABLE_RANGE.get() * (value - 0.5)) + 1;
            float speedModifier = (float) Math.abs(TiAcConfig.COMMON.ELECTRON_TUNER_ATTACK_SPEED_ADJUSTABLE_RANGE.get() * (value - 0.5)) + 1;
            if (value - 0.5 < 0) {
                ToolStats.ATTACK_SPEED.multiply(modifierStatsBuilder, speedModifier);
                ToolStats.ATTACK_DAMAGE.multiply(modifierStatsBuilder, 1 / damageModifier);
            } else {
                ToolStats.ATTACK_SPEED.multiply(modifierStatsBuilder, 1 / speedModifier);
                ToolStats.ATTACK_DAMAGE.multiply(modifierStatsBuilder, damageModifier);
            }
        }
    }

    @Override
    public LegacyDamageSource modifyDamageSource(IToolStackView tool, ModifierEntry entry, LivingEntity attacker, InteractionHand hand, Entity target, EquipmentSlot sourceSlot, boolean isFullyCharged, boolean isExtraAttack, boolean isCritical, LegacyDamageSource source) {
        if (TiAcConfig.COMMON.ELECTRON_TUNER_SPECIAL_BONUS.get()&&ElectronTunerItem.getMode(tool)==0) return source.setBypassArmor().setBypassInvulnerableTime();
        return source;
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getTarget() instanceof LivingEntity living&&TiAcConfig.COMMON.ELECTRON_TUNER_SPECIAL_BONUS.get()){
            living.forceAddEffect(new MobEffectInstance(TinkerModifiers.enderferenceEffect.get(),600,0), context.getAttacker());
        }
    }


    @Override
    public int getCapacity(ModifierEntry modifierEntry) {
        return 100000;
    }

    @Override
    public void addTraits(IToolContext iToolContext, ModifierEntry modifierEntry, TraitBuilder traitBuilder, boolean b) {
        if (iToolContext.getPersistentData().getFloat(ElectronTunerItem.KEY_ATTACK_DAMAGE)>0.75&&TiAcConfig.COMMON.ELECTRON_TUNER_SPECIAL_BONUS.get()){
            traitBuilder.add(TinkerModifiers.severing.getId(),3);
        }
    }

    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifierEntry, EquipmentSlot equipmentSlot, BiConsumer<Attribute, AttributeModifier> biConsumer) {
        if (getMode(tool) == 0&&TiAcConfig.COMMON.ELECTRON_TUNER_SPECIAL_BONUS.get()) {
            biConsumer.accept(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(((IToolUuidGetter) tool).etstlib$getUuid(), ForgeMod.ENTITY_REACH.get().getDescriptionId(), 3, AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifierEntry, @Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        list.add(Component.translatable(tool.getPersistentData().getBoolean(KEY_DISALLOW_INSERT)?"tooltip.tinkers_advanced.disallow_insert":"tooltip.tinkers_advanced.allow_insert").withStyle(ChatFormatting.RED));
        super.addTooltip(tool,modifierEntry,player,list,tooltipKey,tooltipFlag);
        ToolEnergyProduction production = ToolEnergyProduction.getOrCreate((ToolStack) tool);
        int value = production.lastGeneration;
        long remainedEnergyToGen = production.energyToProduce;
        long remainedEnergyToCost = production.energyToReduce;
        list.add(Component.literal((value>0?"+":"")+MathUtil.getEnergyString(value)+"/t").withStyle(ChatFormatting.RED));
        list.add(Component.translatable("tooltip.tinkers_advanced.energy_gen_remain").append(CommonUtil.getEnergyString(remainedEnergyToGen)).withStyle(ChatFormatting.RED));
        list.add(Component.translatable("tooltip.tinkers_advanced.energy_cost_remain").append(CommonUtil.getEnergyString(remainedEnergyToCost)).withStyle(ChatFormatting.RED));

    }
}
