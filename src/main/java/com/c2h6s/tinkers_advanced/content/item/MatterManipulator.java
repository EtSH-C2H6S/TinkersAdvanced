package com.c2h6s.tinkers_advanced.content.item;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.entity.MiningBeamProjectile;
import com.c2h6s.tinkers_advanced.content.item.tinkering.TiAcToolDefinitions;
import com.c2h6s.tinkers_advanced.registery.TiAcToolStats;
import com.c2h6s.tinkers_advanced.util.HarvestLogic;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffect;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffectContext;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffectManager;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffects;
import slimeknights.tconstruct.library.modifiers.fluid.block.BreakBlockFluidEffect;
import slimeknights.tconstruct.library.modifiers.hook.behavior.EnchantmentModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.modifiers.ability.interaction.BlockingModifier;

import java.util.Map;

import static slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper.TANK_HELPER;

public class MatterManipulator extends ModifiableItem {
    public MatterManipulator(Properties properties) {
        super(properties, TiAcToolDefinitions.MATTER_MANIPULATOR);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack held, Slot slot, ClickAction action, Player player) {
        return super.overrideStackedOnOther(held, slot, action, player);
    }
    public static final ResourceLocation LOCATION_MINING = TinkersAdvanced.getLocation("mining_boolean");
    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        ToolStack tool = ToolStack.from(stack);
        return tool.getPersistentData().getBoolean(LOCATION_MINING);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState pBlock) {
        return true;
    }

    @Override
    public int getEnchantmentLevel(ItemStack stack, Enchantment enchantment) {
        ToolStack tool = ToolStack.from(stack);
        FluidStack fluidStack = TANK_HELPER.getFluid(tool);
        int i = EnchantmentModifierHook.getEnchantmentLevel(stack, enchantment);
        if (!fluidStack.isEmpty()&&fluidStack.getFluid()!=null){
            FluidEffects fluidEffects = FluidEffectManager.INSTANCE.find(fluidStack.getFluid());
            if (fluidEffects.hasBlockEffects()){
                for (FluidEffect<? super FluidEffectContext.Block> effect:fluidEffects.blockEffects()){
                    if (effect instanceof BreakBlockFluidEffect effect1&& effect1.enchantments().containsKey(enchantment)){
                        i+=effect1.enchantments().get(enchantment);
                    }
                }
            }
        }
        return i;
    }
    @Override
    public Map<Enchantment, Integer> getAllEnchantments(ItemStack stack) {
        Map<Enchantment, Integer> map = EnchantmentModifierHook.getAllEnchantments(stack);
        ToolStack tool = ToolStack.from(stack);
        FluidStack fluidStack = TANK_HELPER.getFluid(tool);
        int i = 0;
        if (!fluidStack.isEmpty()&&fluidStack.getFluid()!=null) {
            FluidEffects fluidEffects = FluidEffectManager.INSTANCE.find(fluidStack.getFluid());
            if (fluidEffects.hasBlockEffects()) {
                for (FluidEffect<? super FluidEffectContext.Block> effect : fluidEffects.blockEffects()) {
                    if (effect instanceof BreakBlockFluidEffect effect1){
                        for (Enchantment enchantment: effect1.enchantments().keySet()){
                            map.merge(enchantment,effect1.enchantments().get(enchantment),Integer::sum);
                        }
                    }
                }
            }
        }
        return map;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return BlockingModifier.blockWhileCharging(ToolStack.from(stack), UseAnim.BOW);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    public static final ResourceLocation LOCATION_ENTITY_ID = TinkersAdvanced.getLocation("mining_laser_id");

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        boolean creative = player.getAbilities().instabuild;
        ItemStack stack = player.getItemInHand(hand);
        ToolStack tool = ToolStack.from(stack);
        if (tool.isBroken()) {
            return InteractionResultHolder.fail(stack);
        }
        FluidStack fluidStack = TANK_HELPER.getFluid(tool);
        if (fluidStack.isEmpty()){
            return InteractionResultHolder.fail(stack);
        }
        tool.getPersistentData().putBoolean(LOCATION_MINING,true);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
        ToolStack tool = ToolStack.from(stack);
        tool.getPersistentData().putBoolean(LOCATION_MINING,false);
    }

    public static final String KEY_BLOCK_POSX ="tiac_bp_legacyx";
    public static final String KEY_BLOCK_POSY ="tiac_bp_legacyy";
    public static final String KEY_BLOCK_POSZ ="tiac_bp_legacyz";
    public static final String KEY_DESTORY ="tiac_bp_legacy";

    @Override
    public void onUseTick(Level level, LivingEntity living, ItemStack stack, int timeLeft) {
        if (living instanceof Player player) {
            ToolStack tool = ToolStack.from(stack);
            if (tool.isBroken()) {
                living.stopUsingItem();
            }
            FluidStack fluidStack = TANK_HELPER.getFluid(tool);
            if (fluidStack.isEmpty()) {
                living.stopUsingItem();
            }
            float baseRange;
            baseRange = ConditionalStatModifierHook.getModifiedStat(tool,player, TiAcToolStats.RANGE);
            baseRange += (float) (player.getEntityReach()*2);
            MiningBeamProjectile projectile = level.getEntity(tool.getPersistentData().getInt(LOCATION_ENTITY_ID)) instanceof MiningBeamProjectile e?e:null;
            if (projectile==null){
                MiningBeamProjectile projectile1 = new MiningBeamProjectile(level,baseRange);
                projectile1.setPos(player.getEyePosition());
                projectile1.stack = stack;
                Vec3 offset = player.getLookAngle().cross(new Vec3(0,1,0)).normalize().scale(0.3f);
                boolean OffHand = player.getUsedItemHand()==InteractionHand.OFF_HAND;
                if (OffHand){
                    offset = offset.reverse();
                }
                projectile1.setPos(player.getEyePosition().add(offset));
                projectile1.setOwner(player);
                level.addFreshEntity(projectile1);
                tool.getPersistentData().putInt(LOCATION_ENTITY_ID, projectile1.getId());
            }

            BlockHitResult result = level.clip(new ClipContext(living.getEyePosition(),living.getEyePosition().add(living.getLookAngle().normalize().scale(baseRange)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,null));
            if (result.getType()!= HitResult.Type.MISS&&level instanceof ServerLevel serverLevel&&player instanceof ServerPlayer serverPlayer){
                UseOnContext context = new UseOnContext(player,player.getUsedItemHand(),result);
                Direction direction = result.getDirection();
                BlockPos blockPos = result.getBlockPos();
                BlockState blockState = level.getBlockState(blockPos);
                BlockPos legacy =new BlockPos(player.getPersistentData().getInt(KEY_BLOCK_POSX),player.getPersistentData().getInt(KEY_BLOCK_POSY),player.getPersistentData().getInt(KEY_BLOCK_POSZ));
                if (!result.getBlockPos().equals(legacy)){
                    player.getPersistentData().putInt(KEY_DESTORY,0);
                }
                player.getPersistentData().putInt(KEY_BLOCK_POSX, result.getBlockPos().getX());
                player.getPersistentData().putInt(KEY_BLOCK_POSY, result.getBlockPos().getY());
                player.getPersistentData().putInt(KEY_BLOCK_POSZ, result.getBlockPos().getZ());
                float destroySpeed = level.getBlockState(blockPos).getDestroySpeed(level,blockPos);

                float destroyProgress = player.getPersistentData().getFloat(KEY_DESTORY);
                float breakSpeed = tool.getStats().get(ToolStats.MINING_SPEED);
                destroyProgress += Mth.clamp((breakSpeed / destroySpeed), 1, 10 - destroyProgress);
                serverLevel.destroyBlockProgress(player.getId(),blockPos,(int) destroyProgress);

                if (destroyProgress>=10) {
                    serverLevel.destroyBlockProgress(player.getId(),blockPos,-1);
                    HarvestLogic.breakBlockAndTeleport(tool,stack,new ToolHarvestContext(serverLevel,serverPlayer,blockState,blockPos,result.getDirection(),blockState.canHarvestBlock(level,blockPos,serverPlayer),this.isCorrectToolForDrops(blockState)),player.blockPosition());
                    player.getPersistentData().putInt(KEY_DESTORY,0);
                }else player.getPersistentData().putFloat(KEY_DESTORY,destroyProgress);
            }
        }
    }


    public static Direction.Axis getSuitableAxis(Direction direction){
        Direction.Axis axis;
        switch (direction){
            case SOUTH, NORTH -> axis = Direction.Axis.Y;
            case WEST, EAST -> axis = Direction.Axis.Z;
            default -> axis = Direction.Axis.X;
        }
        return axis;
    }

}
