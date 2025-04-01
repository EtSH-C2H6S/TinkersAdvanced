package com.c2h6s.tinkers_advanced.content.modifier.defense;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

public class SecondaryArmor extends EtSTBaseModifier {
    public static final TinkerDataCapability.TinkerDataKey<Integer> KEY_SECONDARY_ARMOR = TinkerDataCapability.TinkerDataKey.of(TinkersAdvanced.getLocation("secondary_armor"));


    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addModule(new ArmorLevelModule(KEY_SECONDARY_ARMOR,false,null));
    }
    public SecondaryArmor(){
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST,this::onLivingHurt);
    }

    private void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)||!event.getSource().is(DamageTypeTags.BYPASSES_ARMOR)) return;
        LivingEntity living = event.getEntity();
        if (living instanceof Player player){
            living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((cap)->{
                int level = cap.get(KEY_SECONDARY_ARMOR,0);
                if (level>0){
                    player.getInventory().hurtArmor(event.getSource(),event.getAmount(),Inventory.ALL_ARMOR_SLOTS);
                    event.setAmount(CombatRules.getDamageAfterAbsorb(event.getAmount(), (float) player.getArmorValue(), (float) player.getAttributeValue(Attributes.ARMOR_TOUGHNESS)));
                }
            });
        }
    }

    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public int getPriority() {
        return 1000;
    }
}
