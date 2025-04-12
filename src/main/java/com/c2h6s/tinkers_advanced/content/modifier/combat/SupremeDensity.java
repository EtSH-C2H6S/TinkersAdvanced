package com.c2h6s.tinkers_advanced.content.modifier.combat;

import com.c2h6s.etstlib.register.EtSTLibHooks;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

public class SupremeDensity extends EtSTBaseModifier implements ToolStatsModifierHook {
    public static boolean cachedCd = false;

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this,ModifierHooks.TOOL_STATS);
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
    public void addToolStats(IToolContext iToolContext, ModifierEntry modifierEntry, ModifierStatsBuilder modifierStatsBuilder) {
        for (int i =0;i<modifierEntry.getLevel();i++) {
            if (!cachedCd) {
                for (ModifierEntry modifier : iToolContext.getModifiers()) {
                    cachedCd = true;
                    modifier.getHook(ModifierHooks.TOOL_STATS).addToolStats(iToolContext, modifier, modifierStatsBuilder);
                }
                cachedCd = false;
            }
        }
    }

    @Override
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        for (int i =0;i<modifier.getLevel();i++) {
            if (!cachedCd) {
                for (ModifierEntry entry : tool.getModifierList()) {
                    cachedCd = true;
                    damage = entry.getHook(ModifierHooks.MELEE_DAMAGE).getMeleeDamage(tool, entry, context, baseDamage, damage);
                }
                cachedCd = false;
            }
        }
        return damage;
    }

    @Override
    public void postMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage) {
        for (int i =0;i<modifier.getLevel();i++) {
            if (!cachedCd) {
                for (ModifierEntry entry : tool.getModifiers()) {
                    cachedCd = true;
                    entry.getHook(ModifierHooks.MELEE_HIT).afterMeleeHit(tool,entry,context,damage);
                }
                cachedCd = false;
            }
        }
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        for (int i =0;i<modifier.getLevel();i++) {
            if (!cachedCd) {
                for (ModifierEntry entry : tool.getModifiers()) {
                    cachedCd = true;
                    entry.getHook(ModifierHooks.PROJECTILE_LAUNCH).onProjectileLaunch(tool,entry,shooter,projectile,arrow,persistentData,primary);
                }
                cachedCd = false;
            }
        }
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        for (int i =0;i<modifier.getLevel();i++) {
            if (!cachedCd) {
                for (ModifierEntry entry : modifiers) {
                    cachedCd = true;
                    entry.getHook(ModifierHooks.PROJECTILE_HIT).onProjectileHitEntity(modifiers,persistentData,entry,projectile,hit,attacker,target);
                }
                cachedCd = false;
            }
        }
        return false;
    }

    @Override
    public float onGetArrowDamage(ModDataNBT persistentData, ModifierEntry entry,ModifierNBT modifiers, AbstractArrow arrow, @Nullable LivingEntity attacker, @NotNull Entity target, float baseDamage, float damage) {
        for (int i =0;i<entry.getLevel();i++) {
            if (!cachedCd) {
                for (ModifierEntry modifierEntry : modifiers) {
                    cachedCd = true;
                    damage = modifierEntry.getHook(EtSTLibHooks.ARROW_DAMAGE).getArrowDamage(persistentData, modifierEntry, modifiers, arrow, attacker,target,baseDamage,damage);
                }
                cachedCd = false;
            }
        }
        return damage;
    }

    @Override
    public void afterArrowHit(ModDataNBT persistentData, ModifierEntry entry, ModifierNBT modifiers, AbstractArrow arrow, @Nullable LivingEntity attacker, @NotNull LivingEntity target, float damageDealt) {
        for (int i =0;i<entry.getLevel();i++) {
            if (!cachedCd) {
                for (ModifierEntry modifierEntry : modifiers) {
                    cachedCd = true;
                    modifierEntry.getHook(EtSTLibHooks.ARROW_HIT).afterArrowHit(persistentData,entry,modifiers,arrow,attacker,target,damageDealt);
                }
                cachedCd = false;
            }
        }
    }

    @Override
    public int modifierDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        return 0;
    }
}
