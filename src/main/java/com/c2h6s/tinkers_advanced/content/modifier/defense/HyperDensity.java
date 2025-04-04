package com.c2h6s.tinkers_advanced.content.modifier.defense;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.mixin.LivingEntityAccessor;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

import static com.c2h6s.tinkers_advanced.content.modifier.defense.SecondaryArmor.KEY_SECONDARY_ARMOR;

public class HyperDensity extends EtSTBaseModifier {
    public static final TinkerDataCapability.TinkerDataKey<Integer> KEY_HYPER_DENSITY = TinkerDataCapability.TinkerDataKey.of(TinkersAdvanced.getLocation("hyper_density"));

    public HyperDensity(){
        MinecraftForge.EVENT_BUS.addListener(this::onLivingDamage);
    }

    private void onLivingDamage(LivingDamageEvent event) {
        LivingEntity living = event.getEntity();
        if (living!=null){
            living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((cap)->{
                int level = cap.get(KEY_HYPER_DENSITY,0);
                if (level>0){
                    event.setAmount(((LivingEntityAccessor)living).getDamageAfterArmorAbsorb(event.getSource(),event.getAmount()));
                }
                level = cap.get(KEY_SECONDARY_ARMOR,0);
                if (level>0){
                    event.setAmount(CombatRules.getDamageAfterAbsorb(event.getAmount(), (float) living.getArmorValue(), (float) living.getAttributeValue(Attributes.ARMOR_TOUGHNESS)));
                }
            });
        }
    }

    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addModule(new ArmorLevelModule(KEY_HYPER_DENSITY,false,null));
    }


}
