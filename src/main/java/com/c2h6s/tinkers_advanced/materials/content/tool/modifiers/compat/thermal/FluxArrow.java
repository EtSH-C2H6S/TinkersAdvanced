package com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.compat.thermal;

import cofh.core.common.network.packet.client.OverlayMessagePacket;
import com.c2h6s.etstlib.util.ToolEnergyUtil;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.data.TiAcMaterialIds;
import com.c2h6s.tinkers_advanced.core.util.FakeExplosionUtil;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariant;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.BowAmmoModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.*;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;
import java.util.function.Predicate;

public class FluxArrow extends FluxInfused implements BowAmmoModifierHook {
    public static final String KEY_ARROW_CHARGE = "tiac_key_arrow_charge";

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BOW_AMMO);
    }

    @Override
    public void addToolStats(IToolContext iToolContext, ModifierEntry modifierEntry, ModifierStatsBuilder modifierStatsBuilder) {
        super.addToolStats(iToolContext, modifierEntry, modifierStatsBuilder);
        ToolStats.DRAW_SPEED.percent(modifierStatsBuilder,getMode(iToolContext.getPersistentData())>=2?0.5:0);
    }

    @Override
    public void addTooltip(IToolStackView iToolStackView, ModifierEntry modifierEntry, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
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
                    default -> toolStack.replaceMaterial(i, TiAcMaterialIds.Thermal.ACTIVATED_CHROMATIC_STEEL);
                }
            }
        }
    }

    @Override
    public ItemStack findAmmo(IToolStackView tool, ModifierEntry modifier, LivingEntity livingEntity, ItemStack stack, Predicate<ItemStack> predicate) {
        int basicConsumption = TiAcConfig.COMMON.FLUX_ARROW_CONSUMPTION.get();
        if (getMode(tool)>0&& ToolEnergyUtil.extractEnergy(tool,basicConsumption*2,true)>=basicConsumption*2&&stack.isEmpty()){
            ToolEnergyUtil.extractEnergy(tool,basicConsumption*2,false);
            return new ItemStack(Items.ARROW,64);
        }
        return stack;
    }

    @Override
    public void shrinkAmmo(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, ItemStack ammo, int needed) {
        int basicConsumption = TiAcConfig.COMMON.FLUX_ARROW_CONSUMPTION.get();
        if (!ammo.is(Items.ARROW)&&ToolEnergyUtil.extractEnergy(tool,basicConsumption*4,true)>=basicConsumption*4&&getMode(tool)>1){
            ToolEnergyUtil.extractEnergy(tool,basicConsumption*4,false);
            return;
        }
        BowAmmoModifierHook.super.shrinkAmmo(tool,modifier,shooter,ammo,needed);
    }

    @Override
    public void modifierProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        if (arrow!=null) {
            int basicConsumption = TiAcConfig.COMMON.FLUX_ARROW_CONSUMPTION.get();
            switch (getMode(tool)) {
                case 1 -> {
                    if (ToolEnergyUtil.extractEnergy(tool,basicConsumption,true)>=basicConsumption) {
                        arrow.setBaseDamage(arrow.getBaseDamage() + 1);
                        arrow.getPersistentData().putInt(KEY_ARROW_CHARGE, getMode(tool));
                    }
                }
                case 2->{
                    if (ToolEnergyUtil.extractEnergy(tool,basicConsumption*3,true)>=basicConsumption*3) {
                        arrow.setBaseDamage(arrow.getBaseDamage() + 1);
                        ToolEnergyUtil.extractEnergy(tool,basicConsumption*3,false);
                        arrow.getPersistentData().putInt(KEY_ARROW_CHARGE, getMode(tool));
                    }
                }
                default -> {
                }
            }
        }
    }


    @Override
    public void onProjectileHitBlock(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, BlockHitResult hit, @Nullable LivingEntity attacker) {
        if (attacker!=null&&projectile instanceof AbstractArrow arrow&&arrow.isCritArrow()&&arrow.getPersistentData().getInt(KEY_ARROW_CHARGE)==2) {
            FakeExplosionUtil.fakeExplode(getExplosionDamage(arrow),attacker,attacker.level(),hit.getLocation(),new IntOpenHashSet(attacker.getId()),false );
        }
        if (projectile.getPersistentData().getInt(KEY_ARROW_CHARGE)>0){
            projectile.discard();
        }
    }

    @Override
    public void afterArrowHit(ModDataNBT persistentData, ModifierEntry entry, ModifierNBT modifiers, AbstractArrow arrow, @Nullable LivingEntity attacker, @NotNull LivingEntity target, float damageDealt) {
        if (attacker!=null&&arrow.isCritArrow()&&arrow.getPersistentData().getInt(KEY_ARROW_CHARGE)==2) {
            FakeExplosionUtil.fakeExplode(getExplosionDamage(arrow),attacker,attacker.level(),target.position().add(0,target.getBbHeight()/2,0),new IntOpenHashSet(attacker.getId()),false );
            target.invulnerableTime =0;
        }
    }

    public static float getExplosionDamage(AbstractArrow arrow){
        return (float) (TiAcConfig.COMMON.FLUX_ARROW_BASE_EXPLOSION_DAMAGE.get().floatValue()+TiAcConfig.COMMON.FLUX_ARROW_EXPLOSION_DAMAGE_FROM_DAMAGE.get().floatValue()*arrow.getBaseDamage()*arrow.getDeltaMovement().length());
    }
}
