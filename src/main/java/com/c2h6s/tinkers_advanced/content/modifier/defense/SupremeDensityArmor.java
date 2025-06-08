package com.c2h6s.tinkers_advanced.content.modifier.defense;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SupremeDensityArmor extends EtSTBaseModifier implements ToolStatsModifierHook {
    public static boolean cachedCd = false;
    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("supreme_density");
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addModule(new ArmorLevelModule(key, false, (TagKey)null));
        builder.addHook(this, ModifierHooks.TOOL_STATS);
    }
    @Override
    public int getPriority() {
        return Integer.MIN_VALUE;
    }

    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        for (int i =0;i<modifier.getLevel();i++) {
            if (!cachedCd) {
                for (ModifierEntry entry : tool.getModifiers()) {
                    cachedCd = true;
                    entry.getHook(ModifierHooks.INVENTORY_TICK).onInventoryTick(tool, entry, world,holder,itemSlot,isSelected,isCorrectSlot,stack);
                }
                cachedCd = false;
            }
        }
    }

    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        ToolStats.ARMOR.multiply(builder,2);
        ToolStats.ARMOR_TOUGHNESS.multiply(builder,2);
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity living = event.getEntity();
        living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
            int level = holder.get(key, 0);
            if (level > 0&&living instanceof Player &&event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                float percent = Math.min(1,0.4f*level);
                float am = event.getAmount()*percent;
                am = ForgeHooks.onLivingHurt(living,event.getSource(), am);
                am = CombatRules.getDamageAfterAbsorb(am, (float) living.getArmorValue(), (float) living.getAttributeValue(Attributes.ARMOR_TOUGHNESS));
                am = getMagicDR(am+event.getAmount()*(1-percent),event.getSource(),living);
                event.setAmount(am);
            }
        });
    }
    public static float getMagicDR(float amount,DamageSource damageSource,LivingEntity living){
        int a;
        if (living.hasEffect(MobEffects.DAMAGE_RESISTANCE) && damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            a = (living.getEffect(MobEffects.DAMAGE_RESISTANCE).getAmplifier() + 1) * 5;
            int b = 25 - a;
            float f = amount * (float) b;
            float f1 = amount;
            amount = Math.max(f / 25.0F, 0.0F);
            float f2 = f1 - amount;
            if (f2 > 0.0F && f2 < 3.4028235E37F) {
                if (living instanceof ServerPlayer) {
                    ((ServerPlayer)living).awardStat(Stats.CUSTOM.get(Stats.DAMAGE_RESISTED), Math.round(f2 * 10.0F));
                } else if (damageSource.getEntity() instanceof ServerPlayer) {
                    ((ServerPlayer)damageSource.getEntity()).awardStat(Stats.CUSTOM.get(Stats.DAMAGE_DEALT_RESISTED), Math.round(f2 * 10.0F));
                }
            }
        }
        if (amount <= 0.0F) {
            return 0.0F;
        } else if (damageSource.is(DamageTypeTags.BYPASSES_ENCHANTMENTS)) {
            return amount;
        } else {
            a = EnchantmentHelper.getDamageProtection(living.getArmorSlots(), damageSource);
            if (a > 0) {
                amount = CombatRules.getDamageAfterMagicAbsorb(amount, (float) a);
            }
            return amount;
        }
    }

    @Override
    public int modifierDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        return 0;
    }
}
