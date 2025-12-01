package com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.common;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.materials.content.block.StibniteOreBlock;
import com.c2h6s.tinkers_advanced.materials.init.TiAcMeBlocks;
import com.c2h6s.tinkers_advanced.core.util.CommonUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.state.BlockState;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.UUID;
import java.util.function.BiConsumer;

public class Stabilize extends EtSTBaseModifier implements BlockBreakModifierHook, ToolStatsModifierHook, AttributesModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BLOCK_BREAK,ModifierHooks.TOOL_STATS,ModifierHooks.ATTRIBUTES);
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        for (Direction direction:Direction.values()){
            BlockPos blockPos = context.getTargetedPos().relative(direction);
            BlockState state = context.getWorld().getBlockState(blockPos);
            if (state.is(TiAcMeBlocks.STIBNITE_ORE.get())){
                context.getWorld().setBlockAndUpdate(blockPos, TiAcMeBlocks.STIBNITE_ORE.get().defaultBlockState().setValue(StibniteOreBlock.STIBNITE_STATE,false));
            }
        }
    }

    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        ToolStats.ATTACK_DAMAGE.add(builder,modifier.getLevel()*1.5);
        ToolStats.ARMOR.add(builder,modifier.getLevel()*1.5);
    }

    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        UUID uuid = CommonUtil.getUUIDFromTool(tool,modifier.getId(),slot);
        consumer.accept(Attributes.MOVEMENT_SPEED,new AttributeModifier(uuid,Attributes.MOVEMENT_SPEED.getDescriptionId(),-0.1*modifier.getLevel(), AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
}
