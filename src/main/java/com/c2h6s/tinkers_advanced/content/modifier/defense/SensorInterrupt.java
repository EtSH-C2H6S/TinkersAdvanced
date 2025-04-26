package com.c2h6s.tinkers_advanced.content.modifier.defense;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class SensorInterrupt extends EtSTBaseModifier {
    public SensorInterrupt(){
        MinecraftForge.EVENT_BUS.addListener(this::onGetCaught);
    }

    private void onGetCaught(LivingChangeTargetEvent event) {
        if (event.getNewTarget()!=null&&event.getEntity()!=null) {
            EquipmentContext context = new EquipmentContext(event.getNewTarget());
            for (EquipmentSlot slot : List.of(EquipmentSlot.CHEST, EquipmentSlot.FEET, EquipmentSlot.HEAD, EquipmentSlot.LEGS)) {
                IToolStackView tool = context.getValidTool(slot);
                if (tool != null) {
                    int i = tool.getModifierLevel(this);
                    Vec3 vec3 = event.getEntity().position().subtract(event.getNewTarget().position());
                    if (i > 0 && vec3.length() > 5f / i && event.getEntity().getLastHurtByMob() != event.getNewTarget()){
                        event.setCanceled(true);
                        return;
                    }
                }
            }
        }
    }
}
