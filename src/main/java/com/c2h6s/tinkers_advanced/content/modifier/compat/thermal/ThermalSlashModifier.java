package com.c2h6s.tinkers_advanced.content.modifier.compat.thermal;

import cofh.core.common.network.packet.client.OverlayMessagePacket;
import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.etstlib.util.ToolEnergyUtil;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.entity.ThermalSlashProjectile;
import com.c2h6s.tinkers_advanced.data.TiAcMaterialIds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariant;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.build.EnchantmentModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.MaterialNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.data.ModifierIds;

import java.util.List;


public class ThermalSlashModifier extends FluxInfused implements BreakSpeedModifierHook, BlockBreakModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BREAK_SPEED,ModifierHooks.BLOCK_BREAK);
        hookBuilder.addModule(new EnchantmentModule.Constant(Enchantments.BLOCK_FORTUNE,1));
        hookBuilder.addModule(new EnchantmentModule.Constant(Enchantments.MOB_LOOTING,1));
    }

    @Override
    public void addToolStats(IToolContext iToolContext, ModifierEntry modifierEntry, ModifierStatsBuilder modifierStatsBuilder) {
        super.addToolStats(iToolContext, modifierEntry, modifierStatsBuilder);
        ToolStats.ATTACK_SPEED.percent(modifierStatsBuilder,getMode(iToolContext.getPersistentData())>=2?0.5:0);
    }

    public static void onOffHandSwing(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand hand, InteractionSource source){

    }

    @Override
    public void onModeSwitch(IToolStackView tool, ServerPlayer player, ModifierEntry entry) {
        OverlayMessagePacket.sendToClient(Component.translatable("msg.tinkers_advanced.flux_mode.thermal_slash."+getMode(tool)),player);
        MaterialNBT nbt = tool.getMaterials();
        ToolStack toolStack = (ToolStack) tool;
        for (int i=0;i<nbt.size();i++){
            MaterialVariant variant = nbt.get(i);
            MaterialId materialId = variant.getId();
            if (materialId.getNamespace().equals(TinkersAdvanced.MODID)&&materialId.getPath().equals("activated_chromatic_steel")){
                switch (getMode(tool)){
                    case 1->toolStack.replaceMaterial(i,TiAcMaterialIds.Thermal.Variant.ACTIVATED_CHROMATIC_STEEL_ACTIVATED);
                    case 2->toolStack.replaceMaterial(i,TiAcMaterialIds.Thermal.Variant.ACTIVATED_CHROMATIC_STEEL_EMPOWERED);
                    default -> toolStack.replaceMaterial(i,TiAcMaterialIds.Thermal.ACTIVATED_CHROMATIC_STEEL);
                }
            }
        }
    }

    @Override
    public void onLeftClickBlock(IToolStackView tool, ModifierEntry entry, Player player, Level level, EquipmentSlot equipmentSlot, BlockState state, BlockPos pos) {
        int basicConsumption = TiAcConfig.COMMON.FLUX_SLASH_CONSUMPTION.get();
        float basicDamage = TiAcConfig.COMMON.FLUX_SLASH_BASIC_SLASH_DAMAGE.get().floatValue();
        float sharpnessBonus = TiAcConfig.COMMON.FLUX_SLASH_SLASH_DAMAGE_PER_SHARPNESS.get().floatValue();
        float attackDamageBonus = TiAcConfig.COMMON.FLUX_SLASH_SLASH_DAMAGE_FROM_ATTACK_DAMAGE.get().floatValue();
        if (getMode(tool)==2&&!level.isClientSide&&ToolEnergyUtil.extractEnergy(tool,basicConsumption*2,true)>=basicConsumption*2&&player.getAttackStrengthScale(0)>0.8&&!tool.getItem().isCorrectToolForDrops(state)){
            ThermalSlashProjectile projectile = new ThermalSlashProjectile(level);
            Vec3 to = player.getLookAngle();
            projectile.setPos(player.getEyePosition());
            projectile.shoot(to.x, to.y, to.z,2f,0);
            projectile.setOwner(player);
            projectile.modifierLevel=entry.getLevel();
            projectile.baseDamage = basicDamage;
            projectile.baseDamage += sharpnessBonus*tool.getModifierLevel(ModifierIds.sharpness);
            projectile.baseDamage += attackDamageBonus*tool.getStats().get(ToolStats.ATTACK_DAMAGE)*attackDamageBonus;
            level.addFreshEntity(projectile);
            ToolEnergyUtil.extractEnergy(tool,basicConsumption*2,false);
        }
    }

    @Override
    public void onLeftClickEmpty(IToolStackView tool, ModifierEntry entry, Player player, Level level, EquipmentSlot equipmentSlot) {
        int basicConsumption = TiAcConfig.COMMON.FLUX_SLASH_CONSUMPTION.get();
        float basicDamage = TiAcConfig.COMMON.FLUX_SLASH_BASIC_SLASH_DAMAGE.get().floatValue();
        float sharpnessBonus = TiAcConfig.COMMON.FLUX_SLASH_SLASH_DAMAGE_PER_SHARPNESS.get().floatValue();
        float attackDamageBonus = TiAcConfig.COMMON.FLUX_SLASH_SLASH_DAMAGE_FROM_ATTACK_DAMAGE.get().floatValue();
        if (getMode(tool)==2&&!level.isClientSide&&ToolEnergyUtil.extractEnergy(tool,basicConsumption*2,true)>=basicConsumption*2&&player.getAttackStrengthScale(0)>0.8){
            ThermalSlashProjectile projectile = new ThermalSlashProjectile(level);
            Vec3 to = player.getLookAngle();
            projectile.setPos(player.getEyePosition());
            projectile.shoot(to.x, to.y, to.z,2f,0);
            projectile.setOwner(player);
            projectile.modifierLevel=entry.getLevel();
            projectile.baseDamage = basicDamage;
            projectile.baseDamage += sharpnessBonus*tool.getModifierLevel(ModifierIds.sharpness);
            projectile.baseDamage += attackDamageBonus*tool.getStats().get(ToolStats.ATTACK_DAMAGE)*attackDamageBonus;
            level.addFreshEntity(projectile);
            ToolEnergyUtil.extractEnergy(tool,basicConsumption*2,false);
        }
    }

    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        int basicConsumption = TiAcConfig.COMMON.FLUX_SLASH_CONSUMPTION.get();
        float basicDamage = TiAcConfig.COMMON.FLUX_SLASH_BASIC_SLASH_DAMAGE.get().floatValue();
        float sharpnessBonus = TiAcConfig.COMMON.FLUX_SLASH_SLASH_DAMAGE_PER_SHARPNESS.get().floatValue();
        float attackDamageBonus = TiAcConfig.COMMON.FLUX_SLASH_SLASH_DAMAGE_FROM_ATTACK_DAMAGE.get().floatValue();
        if (getMode(tool)==2&&!context.getLevel().isClientSide&&ToolEnergyUtil.extractEnergy(tool,basicConsumption*2,true)>=basicConsumption*2&&context.isFullyCharged()&&context.getAttacker() instanceof ServerPlayer player){
            ThermalSlashProjectile projectile = new ThermalSlashProjectile(context.getLevel());
            Vec3 to = player.getLookAngle();
            projectile.setPos(player.getEyePosition());
            projectile.shoot(to.x, to.y, to.z,2f,0);
            projectile.setOwner(player);
            projectile.baseDamage = basicDamage;
            projectile.baseDamage += sharpnessBonus*tool.getModifierLevel(ModifierIds.sharpness);
            projectile.baseDamage += damage*attackDamageBonus;
            context.getLevel().addFreshEntity(projectile);
            ToolEnergyUtil.extractEnergy(tool,basicConsumption*2,false);
        }
        return knockback;
    }

    @Override
    public void failedMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageAttempted) {
        this.afterMeleeHit(tool,modifier,context,damageAttempted);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        Player player = context.getPlayerAttacker();
        int basicConsumption = TiAcConfig.COMMON.FLUX_SLASH_CONSUMPTION.get();
        float basicDamage = TiAcConfig.COMMON.FLUX_SLASH_FLUX_DAMAGE.get().floatValue();
        if (player!=null&&getMode(tool)>=1&&ToolEnergyUtil.extractEnergy(tool,basicConsumption,true)>=basicConsumption&&context.isFullyCharged()) {
            context.getTarget().hurt(LegacyDamageSource.playerAttack(player).setBypassMagic().setBypassInvulnerableTime().setBypassArmor().setMsgId("flux"), basicDamage);
            ToolEnergyUtil.extractEnergy(tool,basicConsumption,false);
        }
    }

    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        if (isEffective){
            int basicConsumption = TiAcConfig.COMMON.FLUX_SLASH_CONSUMPTION.get();
            float digSpeed = TiAcConfig.COMMON.FLUX_SLASH_DIG_SPEED_BONUS.get().floatValue();
            switch (getMode(tool)){
                case 1 ->{
                    if (ToolEnergyUtil.extractEnergy(tool,basicConsumption/2,true)>=basicConsumption/4) {
                        event.setNewSpeed(event.getNewSpeed() +event.getOriginalSpeed()*digSpeed);
                    }
                }
                case 2 ->{
                    if (ToolEnergyUtil.extractEnergy(tool,basicConsumption,true)>=basicConsumption/2) {
                        event.setNewSpeed(event.getNewSpeed() +event.getOriginalSpeed()*digSpeed*2);
                    }
                }
                default -> {}
            }
        }
    }




    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifierEntry, ToolHarvestContext toolHarvestContext) {
        if (toolHarvestContext.isEffective()){
            int basicConsumption = TiAcConfig.COMMON.FLUX_SLASH_CONSUMPTION.get();
            switch (getMode(tool)){
                case 1-> ToolEnergyUtil.extractEnergy(tool,basicConsumption/4,false);
                case 2-> ToolEnergyUtil.extractEnergy(tool,basicConsumption/2,false);
                default -> {}
            }
        }
    }

    @Override
    public void addTooltip(IToolStackView iToolStackView, ModifierEntry modifierEntry, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
    }
}
