package com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.common;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.materials.content.block.StibniteOreBlock;
import com.c2h6s.tinkers_advanced.registery.TiAcBlocks;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

public class Unstable extends EtSTBaseModifier implements BreakSpeedModifierHook, BlockBreakModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BREAK_SPEED,ModifierHooks.BLOCK_BREAK);
    }

    @Override
    public Component onModifierRemoved(IToolStackView tool, Modifier modifier) {
        tool.getPersistentData().remove(KEY_RANDOMIZE);
        return super.onModifierRemoved(tool, modifier);
    }

    public static final ResourceLocation KEY_RANDOMIZE = TinkersAdvanced.getLocation("randomize");

    public static void randomize(IToolStackView tool){
        tool.getPersistentData().putFloat(KEY_RANDOMIZE,RANDOM.nextFloat());
    }

    public static float randomizeAndGetNext(IToolStackView tool){
        float f =RANDOM.nextFloat();
        tool.getPersistentData().putFloat(KEY_RANDOMIZE,f);
        return f;
    }

    public float getDamageMultiplier(IToolStackView tool,ModifierEntry entry){
        float multiplier = randomizeAndGetNext(tool);
        if (multiplier<0.75){
            multiplier*=2;
            return -(1-(1/(1+multiplier*entry.getLevel())));
        } else {
            multiplier-=0.5f;
            multiplier*=2;
            return multiplier*entry.getLevel();
        }
    }

    @Override
    public void modifierProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        persistentData.putFloat(TinkersAdvanced.getLocation("unstable_damage"),getDamageMultiplier(tool,modifier));
    }

    @Override
    public float onGetArrowDamage(ModDataNBT persistentData, ModifierEntry entry, ModifierNBT modifiers, AbstractArrow arrow, @Nullable LivingEntity attacker, @NotNull Entity target, float baseDamage, float damage) {
        return damage+baseDamage*persistentData.getFloat(TinkersAdvanced.getLocation("unstable_damage"));
    }

    @Override
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        return damage+baseDamage*getDamageMultiplier(tool,modifier);
    }

    @Override
    public int modifierDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        return amount+(int) (randomizeAndGetNext(tool)*10)%modifier.getLevel();
    }



    @Override
    public void onBreakSpeed(IToolStackView iToolStackView, ModifierEntry modifierEntry, PlayerEvent.BreakSpeed breakSpeed, Direction direction, boolean b, float v) {
        if (breakSpeed.getEntity()!=null&&breakSpeed.getPosition().isPresent()) {
            Level level = breakSpeed.getEntity().level();
            BlockState blockState = breakSpeed.getState();
            if (blockState.is(TiAcBlocks.STIBNITE_ORE.get()) && blockState.getValue(StibniteOreBlock.STIBNITE_STATE)) {
                blockState.setValue(StibniteOreBlock.STIBNITE_STATE, false);
                level.setBlockAndUpdate(breakSpeed.getPosition().get(), blockState);
            }
        }
        float multiplier = iToolStackView.getPersistentData().getFloat(KEY_RANDOMIZE);
        if (multiplier<0.5){
            multiplier*=2;
            breakSpeed.setNewSpeed(breakSpeed.getNewSpeed()/(1+multiplier*modifierEntry.getLevel()));
        }else {
            multiplier-=0.5f;
            multiplier*=2;
            breakSpeed.setNewSpeed(breakSpeed.getNewSpeed()*(1+multiplier*modifierEntry.getLevel()));
        }
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        randomize(tool);
    }
}
