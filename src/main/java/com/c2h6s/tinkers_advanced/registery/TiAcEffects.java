package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.effect.Ionized;
import com.c2h6s.tinkers_advanced.content.effect.Plague;
import com.c2h6s.tinkers_advanced.content.effect.ProtoPoison;
import com.c2h6s.tinkers_advanced.content.effect.Tetanus;
import com.c2h6s.tinkers_advanced.content.effect.base.EtSTBaseEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TiAcEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TinkersAdvanced.MODID);


    public static final RegistryObject<MobEffect> TETANUS = EFFECTS.register("tetanus", Tetanus::new);
    public static final RegistryObject<MobEffect> IONIZED = EFFECTS.register("ionized", Ionized::new);
    public static final RegistryObject<MobEffect> PROTO_POISON = EFFECTS.register("proto_poison", ProtoPoison::new);
    public static final RegistryObject<MobEffect> PLAGUE = EFFECTS.register("plague", Plague::new);
}
