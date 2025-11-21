package com.c2h6s.tinkers_advanced.client.event;

import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.registery.TiAcItems;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TinkersAdvanced.MODID,value = Dist.CLIENT)
public class TiAcForgeEventHandler {
    @SubscribeEvent
    public static void onMovementInputUpdate(MovementInputUpdateEvent event){
        Player player = event.getEntity();
        if (player.isUsingItem()&&player.getUseItem().is(TiAcItems.MATTER_MANIPULATOR.asItem())&& TiAcConfig.COMMON.MATTER_MANIPULATOR_CANCEL_SLOWDOWN.get()){
            var input = event.getInput();
            float leftImp = input.leftImpulse;
            float forwardImp = input.forwardImpulse;
            if (-5<leftImp&&leftImp<0){
                input.leftImpulse = -5;
            } else if (0<leftImp&&leftImp<5){
                input.leftImpulse = 5;
            }
            if (-5<forwardImp&&forwardImp<0){
                input.forwardImpulse = -5;
            } else if (0<forwardImp&&forwardImp<5){
                input.forwardImpulse = 5;
            }
        }
    }
}
