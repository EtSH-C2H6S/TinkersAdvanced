package com.c2h6s.tinkers_advanced.content.modifier.compat.thermal;

import cofh.core.common.network.packet.client.OverlayMessagePacket;
import com.c2h6s.etstlib.util.ToolEnergyUtil;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.data.TiAcMaterialIds;
import com.c2h6s.tinkers_advanced.util.FakeExplosionUtil;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariant;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.BowAmmoModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.*;

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
                    default -> toolStack.replaceMaterial(i, TiAcMaterialIds.Thermal.ACTIVATED_CHROMATIC_STEEL);
                    case 1->toolStack.replaceMaterial(i,TiAcMaterialIds.Thermal.Variant.ACTIVATED_CHROMATIC_STEEL_ACTIVATED);
                    case 2->toolStack.replaceMaterial(i,TiAcMaterialIds.Thermal.Variant.ACTIVATED_CHROMATIC_STEEL_EMPOWERED);
                }
            }
        }
    }

    @Override
    public ItemStack findAmmo(IToolStackView tool, ModifierEntry modifier, LivingEntity livingEntity, ItemStack stack, Predicate<ItemStack> predicate) {
        if (getMode(tool)>0&& ToolEnergyUtil.extractEnergy(tool,500,true)>=500){
            ToolEnergyUtil.extractEnergy(tool,500,false);
            return new ItemStack(Items.ARROW,64);
        }
        return stack;
    }

    @Override
    public void modifierProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        if (arrow!=null) {
            switch (getMode(tool)) {
                default -> {
                }
                case 1 -> {
                    arrow.setBaseDamage(arrow.getBaseDamage() + 1);
                    arrow.getPersistentData().putInt(KEY_ARROW_CHARGE, getMode(tool));
                }
                case 2->{
                    if (ToolEnergyUtil.extractEnergy(tool,500,true)>=500) {
                        arrow.setBaseDamage(arrow.getBaseDamage() + 1);
                        ToolEnergyUtil.extractEnergy(tool,500,false);
                        arrow.getPersistentData().putInt(KEY_ARROW_CHARGE, getMode(tool));
                    }
                }
            }
        }
    }


    @Override
    public void onProjectileHitBlock(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, BlockHitResult hit, @Nullable LivingEntity attacker) {
        if (attacker!=null&&projectile.getPersistentData().getInt(KEY_ARROW_CHARGE)==2) {
            FakeExplosionUtil.fakeExplode(5,attacker,attacker.level(),hit.getLocation(),new IntOpenHashSet(attacker.getId()),false );
        }
        if (projectile.getPersistentData().getInt(KEY_ARROW_CHARGE)>0){
            projectile.discard();
        }
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (attacker!=null&&projectile.getPersistentData().getInt(KEY_ARROW_CHARGE)==2) {
            FakeExplosionUtil.fakeExplode(5,attacker,attacker.level(),hit.getLocation(),new IntOpenHashSet(attacker.getId()),false );
            if (target!=null) target.invulnerableTime =0;
        }
        return false;
    }
}
