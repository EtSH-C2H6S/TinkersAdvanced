package com.c2h6s.tinkers_advanced.content.item;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.entity.PlasmaBeamProjectile;
import com.c2h6s.tinkers_advanced.content.item.tinkering.TiAcToolDefinitions;
import com.c2h6s.tinkers_advanced.registery.TiAcModifiers;
import com.c2h6s.tinkers_advanced.registery.TiAcToolStats;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffectManager;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffects;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.behavior.EnchantmentModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.EntityInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.tools.IndestructibleItemEntity;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerToolActions;
import slimeknights.tconstruct.tools.modifiers.ability.interaction.BlockingModifier;
import slimeknights.tconstruct.tools.modifiers.upgrades.ranged.ScopeModifier;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook.KEY_DRAWTIME;
import static slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper.*;

public class IonizedCannonItem extends ModifiableItem {
    private ItemStack toolForRendering;
    public static final ResourceLocation TAG_SOUND = new ResourceLocation(TinkersAdvanced.MODID,"cannon_sound");

    public IonizedCannonItem(Properties properties) {
        super(properties, TiAcToolDefinitions.IONIZE_CANNON);
    }

    @Override
    public ItemStack getRenderTool() {
        if (toolForRendering == null) {
            toolForRendering = ToolBuildHandler.buildToolForRendering(this, this.getToolDefinition());
        }
        return toolForRendering;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public boolean isNotReplaceableByPickAction(ItemStack stack, Player player, int inventorySlot) {
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.isCurse() && super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public int getEnchantmentLevel(ItemStack stack, Enchantment enchantment) {
        return EnchantmentModifierHook.getEnchantmentLevel(stack, enchantment);
    }

    @Override
    public Map<Enchantment,Integer> getAllEnchantments(ItemStack stack) {
        return EnchantmentModifierHook.getAllEnchantments(stack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ToolCapabilityProvider(stack);
    }

    @Override
    public void verifyTagAfterLoad(CompoundTag nbt) {
        ToolStack.verifyTag(this, nbt, getToolDefinition());
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        ToolStack.ensureInitialized(stack, getToolDefinition());
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return ModifierUtil.checkVolatileFlag(stack, SHINY);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        int rarity = ModifierUtil.getVolatileInt(stack, RARITY);
        return Rarity.values()[Mth.clamp(rarity, 0, 3)];
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return IndestructibleItemEntity.hasCustomEntity(stack);
    }

    @Override
    public Entity createEntity(Level world, Entity original, ItemStack stack) {
        return IndestructibleItemEntity.createFrom(world, original, stack);
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canBeDepleted() {
        return true;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return ToolDamageUtil.getFakeMaxDamage(stack);
    }

    @Override
    public int getDamage(ItemStack stack) {
        if (!canBeDepleted()) {
            return 0;
        }
        return ToolStack.from(stack).getDamage();
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        if (canBeDepleted()) {
            ToolStack.from(stack).setDamage(damage);
        }
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T damager, Consumer<T> onBroken) {
        ToolDamageUtil.handleDamageItem(stack, amount, damager, onBroken);
        return 0;
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return DurabilityDisplayModifierHook.showDurabilityBar(pStack);
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return DurabilityDisplayModifierHook.getDurabilityRGB(pStack);
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        return DurabilityDisplayModifierHook.getDurabilityWidth(pStack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        InventoryTickModifierHook.heldInventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity target) {
        return target.hurt(player.damageSources().playerAttack(player),1);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ModifierUtil.canPerformAction(ToolStack.from(stack), toolAction);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(IToolStackView tool, EquipmentSlot slot) {
        return AttributesModifierHook.getHeldAttributeModifiers(tool, slot);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt == null || slot.getType() != EquipmentSlot.Type.HAND) {
            return ImmutableMultimap.of();
        }
        return getAttributeModifiers(ToolStack.from(stack), slot);
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return canPerformAction(stack, TinkerToolActions.SHIELD_DISABLE);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return BlockingModifier.blockWhileCharging(ToolStack.from(stack), UseAnim.BOW);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

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
        float fluidEfficiency =tool.getStats().get(TiAcToolStats.FLUID_EFFICIENCY);
        fluidEfficiency = ConditionalStatModifierHook.getModifiedStat(tool,player,TiAcToolStats.FLUID_EFFICIENCY,fluidEfficiency);
        FluidEffects effect = FluidEffectManager.INSTANCE.find(fluidStack.getFluid());
        int consume =Math.round( Math.min(((effect.hasEntityEffects()?effect.getAmount(fluidStack.getFluid()):5)/fluidEfficiency),1));
        if (fluidStack.getAmount()<consume&&!creative){
            return InteractionResultHolder.fail(stack);
        }
        int drawTime;
        if (hand==InteractionHand.MAIN_HAND){
            drawTime = Math.round(player.getCurrentItemAttackStrengthDelay()*4);
        }
        else drawTime = (int) (80/ConditionalStatModifierHook.getModifiedStat(tool,player,ToolStats.ATTACK_SPEED));
        tool.getPersistentData().putInt(KEY_DRAWTIME,drawTime);
        if (tool.getModifierLevel(TiAcModifiers.AUTO_SHOT.get())<=0) {
            tool.getPersistentData().putBoolean(TAG_SOUND, true);
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        int chargeTime = this.getUseDuration(pStack)-pRemainingUseDuration;
        ToolStack toolStack = ToolStack.from(pStack);
        float charge = GeneralInteractionModifierHook.getToolCharge(toolStack,chargeTime);
        if (charge>=0.5f&&toolStack.getPersistentData().getBoolean(TAG_SOUND)){
            pLevel.playSound(null,pLivingEntity.getX(),pLivingEntity.getY(),pLivingEntity.getZ(), SoundEvents.WARDEN_SONIC_CHARGE,pLivingEntity.getSoundSource(),1,1);
            toolStack.getPersistentData().remove(TAG_SOUND);
        }
        if (charge==1.0f&&toolStack.getModifierLevel(TiAcModifiers.AUTO_SHOT.get())>0){
            pLivingEntity.releaseUsingItem();
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity living, int timeLeft) {
        ScopeModifier.stopScoping(living);
        if (!(living instanceof Player player)) {
            return;
        }
        ToolStack tool = ToolStack.from(stack);
        FluidStack fluidStack = TANK_HELPER.getFluid(tool);
        if (tool.isBroken()) {
            tool.getPersistentData().remove(KEY_DRAWTIME);
            return;
        }
        int chargeTime = this.getUseDuration(stack)-timeLeft;
        float charge = GeneralInteractionModifierHook.getToolCharge(tool, chargeTime);
        if (charge<0.45f){
            return;
        }
        boolean creative = player.getAbilities().instabuild;

        float fluidEfficiency =tool.getStats().get(TiAcToolStats.FLUID_EFFICIENCY);
        fluidEfficiency = ConditionalStatModifierHook.getModifiedStat(tool,player,TiAcToolStats.FLUID_EFFICIENCY,fluidEfficiency);
        FluidEffects effect = FluidEffectManager.INSTANCE.find(fluidStack.getFluid());
        int consume =Math.round( Math.max(((effect.hasEntityEffects()?effect.getAmount(fluidStack.getFluid())*0.5F:50)/fluidEfficiency),1));
        float baseRange;
        float baseScale;
        float baseDamage;

        baseRange = ConditionalStatModifierHook.getModifiedStat(tool,living,TiAcToolStats.RANGE);
        baseRange += (float) (player.getEntityReach()*2);

        baseScale = ConditionalStatModifierHook.getModifiedStat(tool,living,TiAcToolStats.SCALE);
        baseScale += (float) tool.getModifierLevel(TinkerModifiers.expanded.get())/2;
        baseScale *= charge;

        consume= (int) (consume*(1+baseScale*0.5));

        baseDamage = ToolAttackUtil.getAttributeAttackDamage(tool,living,player.getUsedItemHand()==InteractionHand.MAIN_HAND?EquipmentSlot.MAINHAND:EquipmentSlot.OFFHAND);
        baseDamage *= effect.hasEntityEffects()?3:1;
        baseDamage *= charge;


        PlasmaBeamProjectile projectile = new PlasmaBeamProjectile(level,baseScale);
        Vec3 vec3 = living.getLookAngle();
        projectile.tool = tool;
        projectile.fluidStack = fluidStack;
        projectile.shoot((float) vec3.x,(float)vec3.y,(float)vec3.z,1,0);
        projectile.setOwner(living);
        projectile.setPos(new Vec3(living.getX(),living.getEyeY(),living.getZ()));
        projectile.setDataLength(baseRange);
        projectile.baseDamage = baseDamage;
        projectile.OffHand = player.getUsedItemHand()==InteractionHand.OFF_HAND;
        level.addFreshEntity(projectile);
        if (!creative){
            fluidStack.shrink(consume);
            TANK_HELPER.setFluid(tool,fluidStack);
        }
        tool.getPersistentData().remove(KEY_DRAWTIME);
        ToolDamageUtil.damageAnimated(tool,1,player);
        player.awardStat(Stats.ITEM_USED.get(this));
    }

    @Override
    public List<Component> getStatInformation(IToolStackView tool, @org.jetbrains.annotations.Nullable Player player, List<Component> tooltips, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            FluidStack fluidStack = TANK_HELPER.getFluid(tool);
            float fluidEfficiency = tool.getStats().get(TiAcToolStats.FLUID_EFFICIENCY);
            fluidEfficiency = ConditionalStatModifierHook.getModifiedStat(tool, player, TiAcToolStats.FLUID_EFFICIENCY, fluidEfficiency);
            FluidEffects effect = FluidEffectManager.INSTANCE.find(fluidStack.getFluid());
            int consume = Math.round(Math.max(((effect.hasEntityEffects() ? effect.getAmount(fluidStack.getFluid()) * 0.5F : 10) / fluidEfficiency), 1));
            float baseRange;
            float baseScale;
            baseRange = ConditionalStatModifierHook.getModifiedStat(tool, player, TiAcToolStats.RANGE);
            baseRange += (float) (player.getEntityReach() * 2);
            baseRange += (float) tool.getModifierLevel(TinkerModifiers.expanded.get()) * 7.5f;

            baseScale = ConditionalStatModifierHook.getModifiedStat(tool, player, TiAcToolStats.SCALE);
            baseScale += (float) tool.getModifierLevel(TinkerModifiers.expanded.get()) * 0.75f;

            consume = (int) (consume * (1 + baseScale * 0.5));

            tooltips.add(Component.translatable("tooltip.tinkers_advanced.fluid_consumption").append(" : §4" + consume + " mB"));
            tooltips.add(Component.translatable("tooltip.tinkers_advanced.range").append(" : §e" + baseRange));
            tooltips.add(Component.translatable("tooltip.tinkers_advanced.scale").append(" : §a" + baseScale));
        }
        return super.getStatInformation(tool, player, tooltips, key, tooltipFlag);
    }
}
