package com.c2h6s.tinkers_advanced.content.item.toolItem;

import com.c2h6s.etstlib.util.ToolEnergyUtil;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.entity.PlasmaSlashEntity;
import com.c2h6s.tinkers_advanced.content.item.tinkering.TiAcToolDefinitions;
import com.c2h6s.tinkers_advanced.content.capability.ElectronTunerItemCapabilityProvider;
import com.c2h6s.tinkers_advanced.content.menu.ElectronTunerMenu;
import com.c2h6s.tinkers_advanced.content.modifierHooks.GeneratorModuleModifierHook;
import com.c2h6s.tinkers_advanced.content.modifierHooks.TiAcModifierHooks;
import com.c2h6s.tinkers_advanced.content.objects.ToolEnergyProduction;
import com.c2h6s.tinkers_advanced.network.TiAcPacketHandler;
import com.c2h6s.tinkers_advanced.network.packets.PElectronTunerMenuSyncS2C;
import com.c2h6s.tinkers_advanced.network.packets.PElectronTunerOpenMenuC2S;
import com.c2h6s.tinkers_advanced.registery.TiAcItems;
import com.c2h6s.tinkers_advanced.registery.TiAcToolStats;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffectManager;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffects;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;
import slimeknights.tconstruct.library.tools.capability.ToolEnergyCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.item.ModifiableSwordItem;

import java.util.List;

import static com.c2h6s.tinkers_advanced.util.CommonUtil.isModifiable;
import static slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper.TANK_HELPER;

@Mod.EventBusSubscriber(modid = TinkersAdvanced.MODID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ElectronTunerItem extends ModifiableSwordItem {
    public ElectronTunerItem(Properties properties) {
        super(properties, TiAcToolDefinitions.ELECTRON_TUNER);
    }
    public static final ResourceLocation KEY_ATTACK_DAMAGE = TinkersAdvanced.getLocation("attack_damage_factor");
    public static final ResourceLocation KEY_DISALLOW_INSERT = TinkersAdvanced.getLocation("disallow_insert");
    public static final MutableComponent itemName = Component.translatable("item."+ TiAcItems.ELECTRON_TUNER.getId().toLanguageKey());

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand hand) {
        if (playerIn.isShiftKeyDown()){
            playerIn.startUsingItem(hand);
            int slot = playerIn.getInventory().findSlotMatchingItem(playerIn.getItemInHand(hand));
            if (playerIn.level().isClientSide) TiAcPacketHandler.sendToServer(new PElectronTunerOpenMenuC2S(slot));
            return InteractionResultHolder.success(playerIn.getItemInHand(hand));
        }
        return super.use(worldIn,playerIn,hand);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 0;
    }

    public static void generatorTick(ItemStack stack, Level level, @Nullable LivingEntity holderEntity, @Nullable BlockEntity holderBlockEntity){
        if (!isModifiable(stack)) return;
        ToolStack tool = ToolStack.from(stack);
        ToolEnergyProduction production;
        if (tool.getPersistentData().contains(ToolEnergyProduction.LOCATION, CompoundTag.TAG_COMPOUND)){
            production = ToolEnergyProduction.readFromTool(tool);
        } else{
            production = ToolEnergyProduction.getOrCreate(tool);
        }
        if (production.needUpdate()) {
            long remainedEnergyToGen = production.energyToProduce;
            long remainedEnergyToCost = production.energyToReduce;
            long energyToGenTotal = 0;
            long energyToCostTotal = 0;
            int energyToGenerateThisTick = production.generatePerTick;
            int energyToConsumeThisTick = production.consumePerTick;
            if (production.requireGeneration()) energyToGenerateThisTick = 0;
            if (production.requireConsumption()) energyToConsumeThisTick = 0;
            IItemHandler handler = stack.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
            for (ModifierEntry modifier : tool.getModifierList()) {
                GeneratorModuleModifierHook hook = modifier.getHook(TiAcModifierHooks.GENERATOR_MODULE);
                int baseAmount = hook.getBasicGeneration(tool, modifier);
                int amplifiedAmount = (int) (baseAmount * tool.getStats().get(TiAcToolStats.POWER_MULTIPLIER));
                amplifiedAmount = hook.getConditionalGeneration(tool, modifier, holderEntity, holderBlockEntity, baseAmount, amplifiedAmount);
                if (amplifiedAmount > 0&&production.requireGeneration()) {
                    energyToGenTotal += hook.shrinkIngredientAndGetTotalEnergy(tool, modifier, holderEntity, holderBlockEntity, amplifiedAmount, handler);
                    if (energyToGenTotal>0){
                        energyToGenerateThisTick += amplifiedAmount;
                    }
                }
                if (amplifiedAmount < 0&&production.requireConsumption()) {
                    energyToCostTotal -= hook.shrinkIngredientAndGetTotalEnergy(tool, modifier, holderEntity, holderBlockEntity, amplifiedAmount, handler);
                    if (energyToGenTotal>0){
                        energyToConsumeThisTick -= amplifiedAmount;
                    }
                }
            }

            energyToGenTotal = Math.max(energyToGenTotal, 0);
            energyToCostTotal = Math.max(energyToCostTotal, 0);
            if (energyToGenTotal > 0) {
                long baseAmount = energyToGenTotal;
                for (ModifierEntry modifier : tool.getModifierList()) {
                    energyToGenTotal = modifier.getHook(TiAcModifierHooks.MODIFY_GENERATION).modifyTotalGeneration(tool, modifier, holderEntity, holderBlockEntity,stack,baseAmount, energyToGenTotal);
                }
            }
            if (energyToCostTotal > 0) {
                long baseAmount = energyToCostTotal;
                for (ModifierEntry modifier : tool.getModifierList()) {
                    energyToCostTotal = -modifier.getHook(TiAcModifierHooks.MODIFY_GENERATION).modifyTotalGeneration(tool, modifier, holderEntity, holderBlockEntity,stack,-baseAmount, -energyToCostTotal);
                }
            }
            energyToGenTotal = Math.max(energyToGenTotal, 0);
            energyToCostTotal = Math.max(energyToCostTotal, 0);
            remainedEnergyToCost += energyToCostTotal;
            remainedEnergyToGen += energyToGenTotal;
            energyToConsumeThisTick = Math.max(energyToConsumeThisTick, 0);
            energyToGenerateThisTick = Math.max(energyToGenerateThisTick, 0);
            production.energyToProduce = remainedEnergyToGen;
            production.energyToReduce = remainedEnergyToCost;
            if (energyToGenerateThisTick > 0 && remainedEnergyToGen > 0) {
                int baseAmount = energyToGenerateThisTick;
                for (ModifierEntry modifier : tool.getModifierList()) {
                    energyToGenerateThisTick = modifier.getHook(TiAcModifierHooks.MODIFY_GENERATION).modifyTotalPerTick(tool, modifier, holderEntity, holderBlockEntity,stack,baseAmount, energyToGenerateThisTick);
                }
                energyToGenerateThisTick = Math.max(energyToGenerateThisTick, 0);
            }
            if (energyToConsumeThisTick > 0 && remainedEnergyToCost > 0) {
                int baseAmount = energyToConsumeThisTick;
                for (ModifierEntry modifier : tool.getModifierList()) {
                    energyToConsumeThisTick = -modifier.getHook(TiAcModifierHooks.MODIFY_GENERATION).modifyTotalPerTick(tool, modifier, holderEntity, holderBlockEntity,stack,baseAmount, -energyToConsumeThisTick);
                }
                energyToConsumeThisTick = Math.max(energyToConsumeThisTick, 0);
            }
            production.consumePerTick = energyToConsumeThisTick;
            production.generatePerTick = energyToGenerateThisTick;
        }
        production.toolStack = tool;
        production.tick();
        if (level.getServer()!=null){
            production.gameTimeLastTick = level.getServer().getTickCount();
        }
        int changed = production.lastGeneration;
        ToolEnergyProduction.updateProduction(tool,production);
        for (ModifierEntry modifier : tool.getModifierList()) {
            modifier.getHook(TiAcModifierHooks.MODIFY_GENERATION).onGeneratorTick(tool, modifier, holderEntity, holderBlockEntity,stack, changed);
        }
        if (holderEntity instanceof Player player&&player.containerMenu instanceof ElectronTunerMenu menu&&menu.getToolItem()==stack){
            menu.updateToolSlot(stack);
            TiAcPacketHandler.sendToClient(new PElectronTunerMenuSyncS2C(stack,menu.toolSlot));
        }
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!worldIn.isClientSide) {
            LivingEntity holder = entityIn instanceof LivingEntity e ? e : null;
            generatorTick(stack,worldIn,holder,null);
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack slotStack, ItemStack held, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (slot.allowModification(player)&&action==ClickAction.SECONDARY&&held.isEmpty()&&isModifiable(slotStack)){
            ToolStack tool = ToolStack.from(slotStack);
            tool.getPersistentData().putBoolean(KEY_DISALLOW_INSERT,!tool.getPersistentData().getBoolean(KEY_DISALLOW_INSERT));
            return true;
        }
        return super.overrideOtherStackedOnMe(slotStack, held, slot, action, player, access);
    }

    public static float getSlashScale(IToolStackView tool){
        return (tool.getModifierLevel(TinkerModifiers.expanded.get()) + 1.5F) * (0.5F*getMode(tool)+0.5f);
    }

    public static float getSlashDamage(IToolStackView tool){
        float bonus = Math.min(0.6f,0.2F*tool.getModifierLevel(TinkerModifiers.sweeping.get()));
        return (tool.getStats().get(ToolStats.ATTACK_DAMAGE)+1)*(0.2f+bonus);
    }

    public static float getSlashDamageBonus(IToolStackView tool){
        float bonus = Math.min(0.6f,0.2F*tool.getModifierLevel(TinkerModifiers.sweeping.get()));
        bonus = Math.min(bonus,ToolEnergyUtil.extractEnergy(tool,(int) (TiAcConfig.COMMON.ELECTRON_TUNER_CONSUMPTION.get()*4*bonus),true)/(TiAcConfig.COMMON.ELECTRON_TUNER_CONSUMPTION.get()*4f));
        return bonus;
    }

    @Override
    public boolean onLeftClickEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity target) {
        if (isModifiable(stack)&&TiAcConfig.COMMON.ELECTRON_TUNER_SPECIAL_BONUS.get()&& player.getAttackStrengthScale(0)>0.8 && player.level() instanceof ServerLevel level){
            ToolStack tool = ToolStack.from(stack);
            if (getMode(tool)>=1){
                if (ToolEnergyUtil.extractEnergy(tool,TiAcConfig.COMMON.ELECTRON_TUNER_CONSUMPTION.get(),true)>=TiAcConfig.COMMON.ELECTRON_TUNER_CONSUMPTION.get()) {
                    float bonus = getSlashDamageBonus(tool);
                    ToolEnergyUtil.extractEnergy(tool, (int) (TiAcConfig.COMMON.ELECTRON_TUNER_CONSUMPTION.get()*4*bonus), false);
                    PlasmaSlashEntity entity = PlasmaSlashEntity.create(player, getSlashScale(tool), player.getLookAngle(), tool);
                    entity.baseDamage = getSlashDamage(tool);
                    level.addFreshEntity(entity);
                }
            }
        }
        return super.onLeftClickEntity(stack, player, target);
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event){
        Player player = event.getEntity();
        ItemStack stack = player.getItemInHand(player.getUsedItemHand());
        if (isModifiable(stack)&&TiAcConfig.COMMON.ELECTRON_TUNER_SPECIAL_BONUS.get()&& player.getAttackStrengthScale(0)>0.8 && player.level() instanceof ServerLevel level){
            ToolStack tool = ToolStack.from(stack);
            if (getMode(tool)==1){
                if (ToolEnergyUtil.extractEnergy(tool,TiAcConfig.COMMON.ELECTRON_TUNER_CONSUMPTION.get(),true)>=TiAcConfig.COMMON.ELECTRON_TUNER_CONSUMPTION.get()) {
                    float bonus = getSlashDamageBonus(tool);
                    ToolEnergyUtil.extractEnergy(tool, (int) (TiAcConfig.COMMON.ELECTRON_TUNER_CONSUMPTION.get()*4*bonus), false);
                    PlasmaSlashEntity entity = PlasmaSlashEntity.create(player, getSlashScale(tool), player.getLookAngle(), tool);
                    entity.baseDamage = getSlashDamage(tool);
                    level.addFreshEntity(entity);
                }
            }
        }
    }

    public static List<Component> getInfoList(IToolStackView tool){
        List<Component> list = new java.util.ArrayList<>(List.of(
                Component.translatable(ToolStats.ATTACK_DAMAGE.getTranslationKey()).append(String.format("%.2f", tool.getStats().get(ToolStats.ATTACK_DAMAGE) + 1)).withStyle(ChatFormatting.DARK_GRAY),
                Component.translatable(ToolStats.ATTACK_SPEED.getTranslationKey()).append(String.format("%.2f", tool.getStats().get(ToolStats.ATTACK_SPEED))).withStyle(ChatFormatting.DARK_GRAY),
                Component.translatable(getModeKey(tool)).withStyle(ChatFormatting.GOLD)
        ));
        if (TiAcConfig.COMMON.ELECTRON_TUNER_SPECIAL_BONUS.get()){
            list.add(Component.translatable(getModeKey(tool)+".bonus").withStyle(ChatFormatting.LIGHT_PURPLE));
        }
        return list;
    }

    @Override
    public List<Component> getStatInformation(IToolStackView tool, @org.jetbrains.annotations.Nullable Player player, List<Component> tooltips, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            tooltips.add(Component.translatable("tool_stat.tinkers_advanced.generate_factor").append("§4" + tool.getStats().get(TiAcToolStats.POWER_MULTIPLIER)));
        }
        return super.getStatInformation(tool, player, tooltips, key, tooltipFlag);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        if (stack.getItem() instanceof ElectronTunerItem) return new ToolCapabilityProvider(stack){
            @NotNull
            @Override
            public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
                if (cap== ForgeCapabilities.ITEM_HANDLER){
                    return LazyOptional.of(()->new ElectronTunerItemCapabilityProvider(()->ToolStack.from(stack),side==null)).cast();
                }
                return super.getCapability(cap, side);
            }
        };
        return super.initCapabilities(stack,nbt);
    }

    public static int getMode(IToolStackView tool){
        if (ToolEnergyCapability.getEnergy(tool)<=0) return -1;
        if (tool.getPersistentData().getFloat(KEY_ATTACK_DAMAGE)<0.25){
            return 0;
        } else if (tool.getPersistentData().getFloat(KEY_ATTACK_DAMAGE)<0.75){
            return 1;
        } else return 2;
    }

    public static String getModeKey(IToolStackView tool){
        if (tool.getPersistentData().getFloat(KEY_ATTACK_DAMAGE)<0.25){
            return "info.tinkers_advanced.electron_tuner.rapier";
        } else if (tool.getPersistentData().getFloat(KEY_ATTACK_DAMAGE)<0.75){
            return "info.tinkers_advanced.electron_tuner.sword";
        } else return "info.tinkers_advanced.electron_tuner.cleaver";
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        ToolStack tool = ToolStack.from(stack);
        tooltip.add(Component.translatable(tool.getPersistentData().getBoolean(KEY_DISALLOW_INSERT)?"tooltip.tinkers_advanced.disallow_insert":"tooltip.tinkers_advanced.allow_insert").withStyle(ChatFormatting.RED));
    }
}
