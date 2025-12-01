package com.c2h6s.tinkers_advanced.materials.init;

import com.c2h6s.tinkers_advanced.core.TiAcCrModule;
import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import com.c2h6s.tinkers_advanced.materials.content.effect.Ionized;
import com.c2h6s.tinkers_advanced.materials.content.effect.Plague;
import com.c2h6s.tinkers_advanced.materials.content.effect.ProtoPoison;
import com.c2h6s.tinkers_advanced.materials.content.effect.Tetanus;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiAcMeEffects {
    @SubscribeEvent
    public static void init(TiAcLoadRegistryClassEvent event){}

    public static final RegistryObject<MobEffect> TETANUS = TiAcCrModule.EFFECTS.register("tetanus", Tetanus::new);
    public static final RegistryObject<MobEffect> IONIZED = TiAcCrModule.EFFECTS.register("ionized", Ionized::new);
    public static final RegistryObject<MobEffect> PROTO_POISON = TiAcCrModule.EFFECTS.register("proto_poison", ProtoPoison::new);
    public static final RegistryObject<MobEffect> PLAGUE = TiAcCrModule.EFFECTS.register("plague", Plague::new);
}
