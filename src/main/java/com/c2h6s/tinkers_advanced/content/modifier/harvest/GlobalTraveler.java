package com.c2h6s.tinkers_advanced.content.modifier.harvest;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ProcessLootModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.BlockInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class GlobalTraveler extends EtSTBaseModifier implements ProcessLootModifierHook , BlockInteractionModifierHook , TooltipModifierHook {
    public static final ResourceLocation TRAVELER_X = TinkersAdvanced.getLocation("traveler_x");
    public static final ResourceLocation TRAVELER_Y = TinkersAdvanced.getLocation("traveler_y");
    public static final ResourceLocation TRAVELER_Z = TinkersAdvanced.getLocation("traveler_z");
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROCESS_LOOT,ModifierHooks.BLOCK_INTERACT,ModifierHooks.TOOLTIP);
    }

    @Override
    public int getPriority() {
        return Integer.MIN_VALUE;
    }

    @Override
    public InteractionResult beforeBlockUse(IToolStackView tool, ModifierEntry modifier, UseOnContext context, InteractionSource source) {
        Player player = context.getPlayer();
        BlockPos blockPos = context.getClickedPos();
        BlockEntity be = context.getLevel().getBlockEntity(blockPos);
        if (!context.getLevel().isClientSide&&be!=null&&be.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()){
            tool.getPersistentData().putInt(TRAVELER_X,blockPos.getX());
            tool.getPersistentData().putInt(TRAVELER_Y,blockPos.getY());
            tool.getPersistentData().putInt(TRAVELER_Z,blockPos.getZ());
            if (player!=null){
                player.sendSystemMessage(Component.translatable("msg.tinkers_advanced.global_traveler.container_bind").append(" : ").append(blockPos.toShortString()));
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void processLoot(IToolStackView iToolStackView, ModifierEntry modifierEntry, List<ItemStack> list, LootContext lootContext) {

    }

    @Override
    public void addTooltip(IToolStackView iToolStackView, ModifierEntry modifierEntry, @Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {

    }
}
