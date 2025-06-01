package com.c2h6s.tinkers_advanced.content.item.toolItem;

import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.entity.MiningBeamProjectile;
import com.c2h6s.tinkers_advanced.content.item.tinkering.TiAcToolDefinitions;
import com.c2h6s.tinkers_advanced.registery.TiAcToolStats;
import com.c2h6s.tinkers_advanced.util.HarvestLogic;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.fluid.*;
import slimeknights.tconstruct.library.modifiers.fluid.block.BreakBlockFluidEffect;
import slimeknights.tconstruct.library.modifiers.fluid.general.ConditionalFluidEffect;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.definition.module.ToolHooks;
import slimeknights.tconstruct.library.tools.definition.module.aoe.AreaOfEffectIterator;
import slimeknights.tconstruct.library.tools.definition.module.mining.IsEffectiveToolHook;
import slimeknights.tconstruct.library.tools.definition.module.mining.MiningTierToolHook;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerToolParts;
import slimeknights.tconstruct.tools.modifiers.ability.interaction.BlockingModifier;

import java.util.List;
import java.util.Map;
import java.util.Random;

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

    public static final ResourceLocation LOCATION_SEC_MODE = TinkersAdvanced.getLocation("mining_mode_secondary");
    public static final ResourceLocation LOCATION_PRI_MODE = TinkersAdvanced.getLocation("mining_mode_primary");

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack slotStack, ItemStack held, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (slot.allowModification(player)&&action==ClickAction.SECONDARY){
            ToolStack tool = ToolStack.from(slotStack);
            if (held.isEmpty()) {
                tool.getPersistentData().putBoolean(LOCATION_SEC_MODE, !tool.getPersistentData().getBoolean(LOCATION_SEC_MODE));
            }
            if (held.is(TinkerToolParts.toolHandle.asItem())){
                tool.getPersistentData().putBoolean(LOCATION_PRI_MODE, !tool.getPersistentData().getBoolean(LOCATION_PRI_MODE));
            }
            super.overrideOtherStackedOnMe(slotStack, held, slot, action, player, access);
            return true;
        }
        return super.overrideOtherStackedOnMe(slotStack, held, slot, action, player, access);
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
            FluidStack fluidStack = TANK_HELPER.getFluid(tool);
            if (fluidStack.isEmpty()) {
                living.stopUsingItem();
                return;
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
                projectile = projectile1;
            }

            BlockHitResult result = level.clip(new ClipContext(living.getEyePosition(), living.getEyePosition().add(living.getLookAngle().normalize().scale(baseRange)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null));
            if (result.getType() != HitResult.Type.MISS && level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
                UseOnContext context = new UseOnContext(level, player, player.getUsedItemHand(), stack, result);
                BlockPos blockPos = result.getBlockPos();
                BlockState blockState = level.getBlockState(blockPos);
                boolean isEffective = IsEffectiveToolHook.isEffective(tool, blockState);
                Random random = new Random();
                float fluidEfficiency = tool.getStats().get(TiAcToolStats.FLUID_EFFICIENCY);
                fluidEfficiency = ConditionalStatModifierHook.getModifiedStat(tool, player, TiAcToolStats.FLUID_EFFICIENCY, fluidEfficiency);
                float fluidFactor = Math.max(0, 1 - fluidEfficiency);
                Tier tier = MiningTierToolHook.getTier(tool);
                if ((isEffective||(!blockState.requiresCorrectToolForDrops()&&blockState.getDestroySpeed(level,blockPos)>=0)) && !tool.getPersistentData().getBoolean(LOCATION_PRI_MODE)) {
                    if (random.nextFloat() < fluidFactor && !player.getAbilities().instabuild) {
                        fluidStack.shrink(1);
                        TANK_HELPER.setFluid(tool, new FluidStack(fluidStack.getFluid(), fluidStack.getAmount()));
                    }
                    BlockPos legacy = new BlockPos(player.getPersistentData().getInt(KEY_BLOCK_POSX), player.getPersistentData().getInt(KEY_BLOCK_POSY), player.getPersistentData().getInt(KEY_BLOCK_POSZ));
                    if (!result.getBlockPos().equals(legacy)) {
                        player.getPersistentData().putInt(KEY_DESTORY, 0);
                    }
                    player.getPersistentData().putInt(KEY_BLOCK_POSX, result.getBlockPos().getX());
                    player.getPersistentData().putInt(KEY_BLOCK_POSY, result.getBlockPos().getY());
                    player.getPersistentData().putInt(KEY_BLOCK_POSZ, result.getBlockPos().getZ());
                    float destroySpeed = level.getBlockState(blockPos).getDestroySpeed(level, blockPos)*10;
                    float destroyProgress = player.getPersistentData().getFloat(KEY_DESTORY);

                    float breakSpeed = (float) ((ConditionalStatModifierHook.getModifiedStat(tool,player,ToolStats.MINING_SPEED) * player.getAttributeValue(Attributes.ATTACK_SPEED)) * TiAcConfig.COMMON.MATTER_MANIPULATOR_BASE_BOOST.get());
                    breakSpeed += (float) ((fluidStack.getFluid().getFluidType().getTemperature() / 100f)*TiAcConfig.COMMON.MATTER_MANIPULATOR_FLUID_BOOST.get());
                    FluidEffects fluidEffects = FluidEffectManager.INSTANCE.find(fluidStack.getFluid());
                    ItemStack stack1 = stack.copy();
                    Map<Enchantment, Integer> map = stack1.getAllEnchantments();
                    if (fluidEffects.hasBlockEffects()) {
                        for (FluidEffect<? super FluidEffectContext.Block> effect : fluidEffects.blockEffects()) {
                            if (effect instanceof ConditionalFluidEffect<? super FluidEffectContext.Block> effect1) {
                                effect = effect1.effect();
                            }
                            if (effect instanceof BreakBlockFluidEffect effect1) {
                                breakSpeed += (float) ((effect1.hardness()/10)*TiAcConfig.COMMON.MATTER_MANIPULATOR_FLUID_BOOST.get());
                                if (TiAcConfig.COMMON.MATTER_MANIPULATOR_FLUID_ENCHANTING.get()&&!effect1.enchantments().isEmpty()) {
                                    effect1.enchantments().forEach((enchantment, integer) -> map.merge(enchantment, integer, Integer::sum));
                                    EnchantmentHelper.setEnchantments(map, stack1);
                                }
                            }
                        }
                    }

                    if (TierSortingRegistry.isCorrectTierForDrops(tier, blockState)) {

                        ToolStack copy = ToolStack.from(stack1);
                        breakSpeed = ForgeEventFactory.getBreakSpeed(player, blockState, breakSpeed, blockPos);
                        if (tool.getPersistentData().getBoolean(LOCATION_SEC_MODE)) {
                            breakSpeed *= TiAcConfig.COMMON.MATTER_MANIPULATOR_AOE_SPEED.get();
                        }
                        destroyProgress += Mth.clamp((breakSpeed / destroySpeed), 0, 10 - destroyProgress);
                        level.destroyBlockProgress(player.getId(), blockPos, (int) destroyProgress);
                        if (destroyProgress >= 10) {
                            HarvestLogic.breakBlockAndGiveItem(copy, stack1, new ToolHarvestContext(serverLevel, serverPlayer, blockState, blockPos, result.getDirection(), blockState.canHarvestBlock(level, blockPos, serverPlayer), this.isCorrectToolForDrops(blockState)));
                            for(ModifierEntry entry:tool.getModifierList()){
                                entry.getHook(ModifierHooks.TOOL_DAMAGE).onDamageTool(tool,entry,1,player);
                            }
                            serverLevel.playSound(null, blockPos, blockState.getSoundType(level, blockPos, null).getBreakSound(), SoundSource.BLOCKS, 1, 1);
                            if (tool.getPersistentData().getBoolean(LOCATION_SEC_MODE)) {
                                for (BlockPos blockPos1 : tool.getHook(ToolHooks.AOE_ITERATOR).getBlocks(tool, context, blockState, AreaOfEffectIterator.AOEMatchType.DISPLAY)) {
                                    BlockState blockState1 = level.getBlockState(blockPos1);
                                    float destroySpeed1 = blockState1.getDestroySpeed(level, blockPos1);
                                    if ((IsEffectiveToolHook.isEffective(tool, blockState1)||(!blockState.requiresCorrectToolForDrops()&&blockState.getDestroySpeed(level,blockPos)>=0)) && destroySpeed1 <= destroySpeed + 0.5 &&(TierSortingRegistry.isCorrectTierForDrops(tier, blockState1))) {
                                       if (HarvestLogic.breakBlockAndGiveItem(copy, stack1, new ToolHarvestContext(serverLevel, serverPlayer, blockState1, blockPos1, result.getDirection(), blockState1.canHarvestBlock(level, blockPos1, serverPlayer), this.isCorrectToolForDrops(blockState1)))){
                                           if (random.nextFloat() < fluidFactor*0.25f && !player.getAbilities().instabuild) {
                                               fluidStack.shrink(1);
                                               TANK_HELPER.setFluid(tool, new FluidStack(fluidStack.getFluid(), fluidStack.getAmount()));
                                           }
                                           for(ModifierEntry entry:tool.getModifierList()){
                                               entry.getHook(ModifierHooks.TOOL_DAMAGE).onDamageTool(tool,entry,1,player);
                                           }
                                       }
                                    }
                                }
                            }
                            player.getPersistentData().putInt(KEY_DESTORY, 0);
                            destroyProgress = 0;
                        } else player.getPersistentData().putFloat(KEY_DESTORY, destroyProgress);
                        projectile.setProgress((int) destroyProgress);
                    }
                } else if (tool.getPersistentData().getBoolean(LOCATION_PRI_MODE)) {
                    FluidEffects fluidEffects = FluidEffectManager.INSTANCE.find(fluidStack.getFluid());
                    if (fluidEffects.hasBlockEffects()) {
                        FluidEffectContext.Block context1 = FluidEffectContext.builder(level).user(player).block(result);
                         if (fluidEffects.applyToBlock(new FluidStack(fluidStack.getFluid(), 1000), 2, context1, IFluidHandler.FluidAction.SIMULATE)>0){
                             fluidStack.shrink(fluidEffects.applyToBlock(new FluidStack(fluidStack.getFluid(), 1000), 2, context1, IFluidHandler.FluidAction.EXECUTE));
                         }
                        if (tool.getPersistentData().getBoolean(LOCATION_SEC_MODE)) {
                            for (BlockPos blockPos1 : tool.getHook(ToolHooks.AOE_ITERATOR).getBlocks(tool, context, blockState, AreaOfEffectIterator.AOEMatchType.DISPLAY)) {
                                BlockHitResult result1 = new BlockHitResult(blockPos1.getCenter(), result.getDirection(), blockPos1, true);
                                FluidEffectContext.Block context2 = FluidEffectContext.builder(level).user(player).block(result1);
                                if (fluidEffects.applyToBlock(new FluidStack(fluidStack.getFluid(), 1000), 2, context2, IFluidHandler.FluidAction.SIMULATE)>0){
                                    fluidStack.shrink(fluidEffects.applyToBlock(new FluidStack(fluidStack.getFluid(), 1000), 2, context2, IFluidHandler.FluidAction.EXECUTE));
                                }
                            }
                        }
                    }
                    if (!player.getAbilities().instabuild) {
                        TANK_HELPER.setFluid(tool, fluidStack);
                    }
                }
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        super.releaseUsing(stack, worldIn, entityLiving, timeLeft);
        entityLiving.getPersistentData().putInt(KEY_DESTORY, 0);
    }

    @Override
    public List<Component> getStatInformation(IToolStackView tool, @org.jetbrains.annotations.Nullable Player player, List<Component> tooltips, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            float fluidEfficiency = tool.getStats().get(TiAcToolStats.FLUID_EFFICIENCY);
            fluidEfficiency = ConditionalStatModifierHook.getModifiedStat(tool, player, TiAcToolStats.FLUID_EFFICIENCY, fluidEfficiency);
            float baseRange = ConditionalStatModifierHook.getModifiedStat(tool,player, TiAcToolStats.RANGE);
            baseRange += (float) (player.getEntityReach()*2);
            float fluidFactor =Math.max(0,1-fluidEfficiency) ;
            tooltips.add(Component.translatable("tooltip.tinkers_advanced.fluid_consumption").append(" : §4" + String.format("%.1f",100*fluidFactor) + "% * 1mB"));
            tooltips.add(Component.translatable("tooltip.tinkers_advanced.range").append(" : §e" + baseRange));
        }
        super.getStatInformation(tool, player, tooltips, key, tooltipFlag);

        return tooltips;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        ToolStack tool = ToolStack.from(stack);
        tooltip.add(Component.translatable(tool.getPersistentData().getBoolean(LOCATION_PRI_MODE)?"tooltip.tinkers_advanced.splashing_mode":"tooltip.tinkers_advanced.mining_mode").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD));

        tooltip.add(Component.translatable("tooltip.tinkers_advanced.primary_mode_info").withStyle(ChatFormatting.DARK_AQUA));

        int expandw = 3+2*((tool.getModifierLevel(TinkerModifiers.expanded.getId())+1)/2);
        int expandh = 3+2*(tool.getModifierLevel(TinkerModifiers.expanded.getId())/2);
        tooltip.add(Component.translatable(tool.getPersistentData().getBoolean(LOCATION_SEC_MODE)?"tooltip.tinkers_advanced.expand_mining":"tooltip.tinkers_advanced.single_mining").append(tool.getPersistentData().getBoolean(LOCATION_SEC_MODE)? expandw + " * " + expandh :"").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD));

        tooltip.add(Component.translatable("tooltip.tinkers_advanced.secondary_mode_info").withStyle(ChatFormatting.DARK_PURPLE));

    }
}
