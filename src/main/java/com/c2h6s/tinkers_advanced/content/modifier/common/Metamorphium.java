package com.c2h6s.tinkers_advanced.content.modifier.common;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class Metamorphium extends EtSTBaseModifier implements ToolDamageModifierHook, ToolStatsModifierHook {
    public static final ResourceLocation KEY_MORPH_BONUS = TinkersAdvanced.getLocation("meta_morph");
    public static final ResourceLocation CD_LOCATION = TinkersAdvanced.getLocation("meta_morph_cd");
    public static final ResourceLocation CHANGED_LOCATION = TinkersAdvanced.getLocation("meta_morph_changed");

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.TOOL_DAMAGE,ModifierHooks.TOOL_STATS);
    }

    @Override
    public int getPriority() {
        return 500;
    }

    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (tool.getPersistentData().getBoolean(CD_LOCATION)&&!world.isClientSide){
            ToolStack toolStack = (ToolStack) tool;
            tool.getPersistentData().putBoolean(CD_LOCATION,false);
            if (tool.getPersistentData().getBoolean(CHANGED_LOCATION)) {
                tool.getPersistentData().putBoolean(CHANGED_LOCATION,false);
                toolStack.rebuildStats();
            }
        }
    }

    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        if (holder!=null&&!holder.level().isClientSide) {
            if (RANDOM.nextInt(10) == 0 && tool.getPersistentData().getFloat(KEY_MORPH_BONUS) < 0.25 && !tool.getPersistentData().getBoolean(CD_LOCATION)) {
                tool.getPersistentData().putFloat(KEY_MORPH_BONUS, tool.getPersistentData().getFloat(KEY_MORPH_BONUS) + 0.005f * modifier.getLevel() * RANDOM.nextFloat());
                tool.getPersistentData().putBoolean(CHANGED_LOCATION, true);
                ((ToolStack) tool).rebuildStats();
            }
            tool.getPersistentData().putBoolean(CD_LOCATION, true);
        }
        return amount;
    }

    @Override
    public void addToolStats(IToolContext iToolContext, ModifierEntry modifierEntry, ModifierStatsBuilder modifierStatsBuilder) {
        if (iToolContext.getPersistentData().getFloat(KEY_MORPH_BONUS)>0){
            float bonus = iToolContext.getPersistentData().getFloat(KEY_MORPH_BONUS);
            ToolStats.ARMOR_TOUGHNESS.percent(modifierStatsBuilder,bonus);
            ToolStats.ARMOR.percent(modifierStatsBuilder,bonus);
            ToolStats.ATTACK_DAMAGE.percent(modifierStatsBuilder,bonus);
            ToolStats.ATTACK_SPEED.percent(modifierStatsBuilder,bonus);
            ToolStats.MINING_SPEED.percent(modifierStatsBuilder,bonus);
            ToolStats.DURABILITY.percent(modifierStatsBuilder,bonus);
            ToolStats.DRAW_SPEED.percent(modifierStatsBuilder,bonus);
            ToolStats.PROJECTILE_DAMAGE.percent(modifierStatsBuilder,bonus);
        }
    }


    @Override
    public @NotNull Component getDisplayName(IToolStackView tool, ModifierEntry entry, @Nullable RegistryAccess access) {
        return super.getDisplayName().copy().append(" +"+String.format("%.1f",tool.getPersistentData().getFloat(KEY_MORPH_BONUS)*100)+"%");
    }
}
