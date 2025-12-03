package com.c2h6s.tinkers_advanced.materials.init;

import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.core.content.worldgen.ConfigPlacementFilter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TiAcMePlacementModifier {
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, TinkersAdvanced.MODID);

    public static final RegistryObject<PlacementModifierType<?>> CONFIG_BISMUTHINITE = PLACEMENT_MODIFIER.register("config_bismuthinite",()->()-> ConfigPlacementFilter.getCodec(TiAcConfig.COMMON.ALLOW_BISMUTHINITE, TiAcMePlacementModifier.CONFIG_BISMUTHINITE.get()));
    public static final RegistryObject<PlacementModifierType<?>> CONFIG_STIBNITE = PLACEMENT_MODIFIER.register("config_stibnite",()->()->ConfigPlacementFilter.getCodec(TiAcConfig.COMMON.ALLOW_STIBNITE, TiAcMePlacementModifier.CONFIG_STIBNITE.get()));
    public static final RegistryObject<PlacementModifierType<?>> CONFIG_IRIDIUM = PLACEMENT_MODIFIER.register("config_iridium",()->()->ConfigPlacementFilter.getCodec(TiAcConfig.COMMON.ALLOW_LEAN_IRIDIUM, TiAcMePlacementModifier.CONFIG_IRIDIUM.get()));
}
