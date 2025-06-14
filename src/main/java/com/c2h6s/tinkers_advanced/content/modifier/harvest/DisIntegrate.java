package com.c2h6s.tinkers_advanced.content.modifier.harvest;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Random;


public class DisIntegrate extends EtSTBaseModifier implements BlockBreakModifierHook , BreakSpeedModifierHook {
    public static final ResourceLocation KEY_DISINTEGRATE = TinkersAdvanced.getLocation("dis_integrate");

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BLOCK_BREAK,ModifierHooks.BREAK_SPEED);
    }

    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (tool.getPersistentData().getInt(KEY_DISINTEGRATE)>0&&!world.isClientSide&&world.getGameTime()%20==0){
            tool.getPersistentData().putInt(KEY_DISINTEGRATE,tool.getPersistentData().getInt(KEY_DISINTEGRATE)- modifier.getLevel()* TiAcConfig.COMMON.DISINTEGRATE_EACH_DECREASE.get());
        }
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifierEntry, ToolHarvestContext toolHarvestContext) {
        if (tool.getPersistentData().getInt(KEY_DISINTEGRATE)<TiAcConfig.COMMON.DISINTEGRATE_MAX_BONUS.get()) tool.getPersistentData().putInt(KEY_DISINTEGRATE,tool.getPersistentData().getInt(KEY_DISINTEGRATE)+TiAcConfig.COMMON.DISINTEGRATE_EACH_DECREASE.get()*modifierEntry.getLevel());
    }

    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifierEntry, PlayerEvent.BreakSpeed breakSpeed, Direction direction, boolean b, float v) {
        if (tool.getPersistentData().getInt(KEY_DISINTEGRATE)>0){
            breakSpeed.setNewSpeed(breakSpeed.getNewSpeed()*(1+tool.getPersistentData().getInt(KEY_DISINTEGRATE)/100f));
        }
    }

    @Override
    public int modifierDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        if (tool.getPersistentData().getInt(KEY_DISINTEGRATE)>0){
            float value = (amount*(1+tool.getPersistentData().getInt(KEY_DISINTEGRATE)/200f));
            amount = (int) value;
            Random random = new Random();
            if (random.nextFloat()<value-amount){
                amount+=1;
            }
        }
        return amount;
    }
}
