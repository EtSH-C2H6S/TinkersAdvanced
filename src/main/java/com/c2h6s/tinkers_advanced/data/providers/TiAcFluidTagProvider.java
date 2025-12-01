package com.c2h6s.tinkers_advanced.data.providers;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.data.TiAcTagkeys;
import com.c2h6s.tinkers_advanced.materials.init.TiAcMeFluids;
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
        tag(TiAcTagkeys.Fluids.MOLTEN_BISMUTH).add(TiAcMeFluids.MOLTEN_BISMUTH.get());
        tag(TiAcTagkeys.Fluids.MOLTEN_ANTIMONY).add(TiAcMeFluids.MOLTEN_ANTIMONY.get());
        tag(TiAcTagkeys.Fluids.MOLTEN_IRIDIUM).add(TiAcMeFluids.MOLTEN_IRIDIUM.get());
        tag(TinkerTags.Fluids.METAL_TOOLTIPS).add(
                        TiAcMeFluids.MOLTEN_IRIDIUM.get(),
                        TiAcMeFluids.MOLTEN_BLAZE_NETHERITE.get(),
                        TiAcMeFluids.MOLTEN_ANTIMONY.get(),
                        TiAcMeFluids.MOLTEN_BISMUTH.get()
                )
                .addOptional(TiAcMeFluids.MOLTEN_IRRADIUM.getId())
                .addOptional(TiAcMeFluids.MOLTEN_BASALZ_SIGNALUM.getId())
                .addOptional(TiAcMeFluids.MOLTEN_BLIZZ_ENDERIUM.getId())
                .addOptional(TiAcMeFluids.MOLTEN_VOID_STEEL.getId())
                .addOptional(TiAcMeFluids.MOLTEN_BILTZ_LUMIUM.getId());
        tag(TiAcTagkeys.Fluids.MOLTEN_ANTIMATTER).addOptional(TiAcMeFluids.MOLTEN_ANTIMATTER.getId());
        tag(TiAcTagkeys.Fluids.MOLTEN_VOID_STEEL).addOptional(TiAcMeFluids.MOLTEN_VOID_STEEL.getId());
        tag(TinkerTags.Fluids.SLIME_TOOLTIPS).addOptional(TiAcMeFluids.MOLTEN_ANTIMATTER.getId());
    }
}
