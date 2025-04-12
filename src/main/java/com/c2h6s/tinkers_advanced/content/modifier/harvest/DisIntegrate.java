package com.c2h6s.tinkers_advanced.content.modifier.harvest;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
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


public class DisIntegrate extends EtSTBaseModifier implements BlockBreakModifierHook , BreakSpeedModifierHook {
    public static final ResourceLocation KEY_DISINTEGRATE = TinkersAdvanced.getLocation("dis_integrate");

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BLOCK_BREAK,ModifierHooks.BREAK_SPEED);
    }

    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (tool.getPersistentData().getInt(KEY_DISINTEGRATE)>0){
            tool.getPersistentData().putInt(KEY_DISINTEGRATE,tool.getPersistentData().getInt(KEY_DISINTEGRATE)- modifier.getLevel());
        }
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifierEntry, ToolHarvestContext toolHarvestContext) {
        tool.getPersistentData().putInt(KEY_DISINTEGRATE,tool.getPersistentData().getInt(KEY_DISINTEGRATE)+50*modifierEntry.getLevel());
    }

    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifierEntry, PlayerEvent.BreakSpeed breakSpeed, Direction direction, boolean b, float v) {
        if (tool.getPersistentData().getInt(KEY_DISINTEGRATE)>0){
            breakSpeed.setNewSpeed(breakSpeed.getNewSpeed()*(1+tool.getPersistentData().getInt(KEY_DISINTEGRATE)/250f));
        }
    }

    @Override
    public int modifierDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        if (tool.getPersistentData().getInt(KEY_DISINTEGRATE)>0){
            amount = (int) (amount*(1+tool.getPersistentData().getInt(KEY_DISINTEGRATE)/500f));
        }
        return amount;
    }
}
