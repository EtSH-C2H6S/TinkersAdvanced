package com.c2h6s.tinkers_advanced.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.List;
import java.util.Objects;

import static slimeknights.tconstruct.library.tools.helper.ToolHarvestLogic.getDamage;

public class HarvestLogic {
    public static boolean breakBlockAndTeleport(ToolStack tool, ItemStack stack, ToolHarvestContext context, BlockPos lootPos) {
        ServerPlayer player = Objects.requireNonNull(context.getPlayer());
        ServerLevel world = context.getWorld();
        BlockPos pos = context.getPos();
        GameType type = player.gameMode.getGameModeForPlayer();
        int exp = ForgeHooks.onBlockBreakEvent(world, type, player, pos);
        if (exp == -1) {
            return false;
        }
        if (player.blockActionRestricted(world, pos, type)) {
            return false;
        }

        if (player.isCreative()) {
            removeBlock(tool, context);
            return true;
        }

        BlockState state = context.getState();
        int damage = getDamage(tool, world, pos, state);

        boolean canHarvest = context.canHarvest();
        BlockEntity te = canHarvest ? world.getBlockEntity(pos) : null;
        boolean removed = removeBlock(tool, context);

        Block block = state.getBlock();
        if (removed && canHarvest) {
            List<ItemStack> list = Block.getDrops(state,world,pos,te,player,stack);
            list.forEach(itemStack->{
                ItemEntity entity = new ItemEntity(world,player.getX(),player.getY()+0.5*player.getBbHeight(),player.getZ(),itemStack,0,0,0);
                entity.setNoPickUpDelay();
                world.addFreshEntity(entity);
            });
        }

        if (removed && exp > 0) {
            block.popExperience(world, lootPos, exp);
        }

        if (!tool.isBroken() && removed) {
            for (ModifierEntry entry : tool.getModifierList()) {
                entry.getHook(ModifierHooks.BLOCK_BREAK).afterBlockBreak(tool, entry, context);
            }
            ToolDamageUtil.damageAnimated(tool, damage, player);
        }

        return true;
    }
    public static boolean breakBlockAndGiveItem(ToolStack tool, ItemStack stack, ToolHarvestContext context) {
        ServerPlayer player = Objects.requireNonNull(context.getPlayer());
        ServerLevel world = context.getWorld();
        BlockPos pos = context.getPos();
        GameType type = player.gameMode.getGameModeForPlayer();
        int exp = ForgeHooks.onBlockBreakEvent(world, type, player, pos);
        if (exp == -1) {
            return false;
        }
        if (player.blockActionRestricted(world, pos, type)) {
            return false;
        }

        if (player.isCreative()) {
            removeBlock(tool, context);
            return true;
        }

        BlockState state = context.getState();
        int damage = getDamage(tool, world, pos, state);

        boolean canHarvest = context.canHarvest();
        BlockEntity te = canHarvest ? world.getBlockEntity(pos) : null;
        boolean removed = removeBlock(tool, context);

        Block block = state.getBlock();
        List<ItemStack> list;
        if (removed && canHarvest) {
            list = Block.getDrops(state,world,pos,te,player,stack);
            list.forEach(item->{
                ItemStack itemStack = item.copy();
                if (!player.addItem(itemStack)&&itemStack.getCount()>0){
                    ItemEntity entity = new ItemEntity(world,player.position().x,player.position().y+player.getBbHeight()/2,player.position().z,item,0,0,0);
                    entity.setNoPickUpDelay();
                    world.addFreshEntity(entity);
                }
            });
        }

        if (removed && exp > 0) {
            ForgeHooks.dropXpForBlock(state,world,player.blockPosition(),stack);
        }

        if (!tool.isBroken() && removed) {
            for (ModifierEntry entry : tool.getModifierList()) {
                entry.getHook(ModifierHooks.BLOCK_BREAK).afterBlockBreak(tool, entry, context);
            }
            ToolDamageUtil.damageAnimated(tool, damage, player);
        }

        return true;
    }
    private static boolean removeBlock(IToolStackView tool, ToolHarvestContext context) {
        Boolean removed = null;
        if (!tool.isBroken()) {
            for (ModifierEntry entry : tool.getModifierList()) {
                removed = entry.getHook(ModifierHooks.REMOVE_BLOCK).removeBlock(tool, entry, context);
                if (removed != null) {
                    break;
                }
            }
        }
        BlockState state = context.getState();
        ServerLevel world = context.getWorld();
        BlockPos pos = context.getPos();
        if (removed == null) {
            removed = state.onDestroyedByPlayer(world, pos, context.getPlayer(), context.canHarvest(), world.getFluidState(pos));
        }
        if (removed) {
            state.getBlock().destroy(world, pos, state);
        }
        return removed;
    }
}
