package com.c2h6s.tinkers_advanced.data.providers;

import com.c2h6s.etstlib.util.ModListConstants;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.data.TiAcTagkeys;
import com.c2h6s.tinkers_advanced.registery.TiAcFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.common.TinkerTags;

import java.util.concurrent.CompletableFuture;

public class TiAcFluidTagProvider extends FluidTagsProvider {
    public TiAcFluidTagProvider(PackOutput p_255941_, CompletableFuture<HolderLookup.Provider> p_256600_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_255941_, p_256600_, TinkersAdvanced.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(TiAcTagkeys.Fluids.MOLTEN_BISMUTH).add(TiAcFluids.MOLTEN_BISMUTH.get());
        tag(TinkerTags.Fluids.METAL_TOOLTIPS).add(TiAcFluids.MOLTEN_BISMUTH.get());
        tag(TinkerTags.Fluids.METAL_TOOLTIPS).add(TiAcFluids.MOLTEN_BLAZE_NETHERITE.get());
        tag(TinkerTags.Fluids.METAL_TOOLTIPS).addOptional(TiAcFluids.MOLTEN_IRRADIUM.getId());
        tag(TinkerTags.Fluids.METAL_TOOLTIPS).addOptional(TiAcFluids.MOLTEN_BASALZ_SIGNALUM.getId());
        tag(TinkerTags.Fluids.METAL_TOOLTIPS).addOptional(TiAcFluids.MOLTEN_BILTZ_LUMIUM.getId());
        tag(TinkerTags.Fluids.METAL_TOOLTIPS).addOptional(TiAcFluids.MOLTEN_BLIZZ_ENDERIUM.getId());
        tag(TiAcTagkeys.Fluids.MOLTEN_ANTIMATTER).addOptional(TiAcFluids.MOLTEN_ANTIMATTER.getId());
        tag(TinkerTags.Fluids.SLIME_TOOLTIPS).addOptional(TiAcFluids.MOLTEN_ANTIMATTER.getId());
    }
}
