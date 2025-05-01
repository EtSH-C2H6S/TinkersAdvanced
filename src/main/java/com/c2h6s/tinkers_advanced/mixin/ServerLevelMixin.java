package com.c2h6s.tinkers_advanced.mixin;

import com.c2h6s.tinkers_advanced.content.objects.EntityTicker;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    @Inject(method = "tickNonPassenger",at = @At(value = "HEAD"),cancellable = true)
    public void tickEntityTicker(Entity p_8648_, CallbackInfo ci){
        var map = EntityTicker.tickerMap.get(p_8648_);
        if(map!=null){
            for (var uuid:map.keySet()){
                if(uuid!=null&&map.get(uuid)!=null){
                    if(!map.get(uuid).tick()) {
                        ci.cancel();
                        break;
                    }
                }
            }
        }
    }
}
