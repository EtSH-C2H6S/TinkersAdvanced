package com.c2h6s.tinkers_advanced.content.modifier.compat.mekanism;

import com.c2h6s.etstlib.register.EtSTLibToolStat;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.lib.radiation.RadiationManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

@Mod.EventBusSubscriber(modid = TinkersAdvanced.MODID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RadioactiveArmor extends EtSTBaseModifier implements OnAttackedModifierHook , ToolStatsModifierHook {
    public static final TinkerDataCapability.TinkerDataKey<Integer> KEY = TinkerDataCapability.TinkerDataKey.of(new ResourceLocation(TinkersAdvanced.MODID,"radioactive_armor_level"));
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this,ModifierHooks.ON_ATTACKED,ModifierHooks.TOOL_STATS);
        hookBuilder.addModule(new ArmorLevelModule(KEY,false,null));
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event){
        LivingEntity living = event.getEntity();
        living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent(cap->{
            int level = cap.get(KEY,0);
            if (level>0){
                LivingEntity attacker = event.getSource().getEntity() instanceof LivingEntity living1?living1:null;
                if (attacker!=null){
                    attacker.getCapability(Capabilities.RADIATION_ENTITY).ifPresent(iRadiationEntity -> {
                        float bonus = (float) Math.min(TiAcConfig.COMMON.IRRADIUM_MAX_BONUS.get(),iRadiationEntity.getRadiation() * TiAcConfig.COMMON.IRRADIUM_BONUS_PER_Sv.get());
                        if (bonus>0.001){
                            event.setAmount(event.getAmount()*(1-bonus));
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onAttacked(IToolStackView iToolStackView, ModifierEntry modifierEntry, EquipmentContext equipmentContext, EquipmentSlot equipmentSlot, DamageSource damageSource, float v, boolean b) {
        if (damageSource.getEntity() instanceof LivingEntity living){
            RadiationManager.get().radiate(living,modifierEntry.getLevel()* TiAcConfig.COMMON.IRRADIUM_RADIATION_INFLICT.get());
        }
    }

    @Override
    public void addToolStats(IToolContext iToolContext, ModifierEntry modifierEntry, ModifierStatsBuilder modifierStatsBuilder) {
        EtSTLibToolStat.RADIATION_PROTECT.add(modifierStatsBuilder,modifierEntry.getLevel()*0.25);
    }
}
