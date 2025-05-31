package com.c2h6s.tinkers_advanced.content.worldgen;

import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TiAcPlacementModifier {
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, TinkersAdvanced.MODID);

    public static final RegistryObject<PlacementModifierType<?>> CONFIG_BISMUTHINITE = PLACEMENT_MODIFIER.register("config_bismuthinite",()->()->ConfigPlacementFilter.getCodec(TiAcConfig.COMMON.ALLOW_BISMUTHINITE, TiAcPlacementModifier.CONFIG_BISMUTHINITE.get()));
    public static final RegistryObject<PlacementModifierType<?>> CONFIG_STIBNITE = PLACEMENT_MODIFIER.register("config_stibnite",()->()->ConfigPlacementFilter.getCodec(TiAcConfig.COMMON.ALLOW_STIBNITE, TiAcPlacementModifier.CONFIG_STIBNITE.get()));
    public static final RegistryObject<PlacementModifierType<?>> CONFIG_IRIDIUM = PLACEMENT_MODIFIER.register("config_iridium",()->()->ConfigPlacementFilter.getCodec(TiAcConfig.COMMON.ALLOW_LEAN_IRIDIUM, TiAcPlacementModifier.CONFIG_IRIDIUM.get()));
}
