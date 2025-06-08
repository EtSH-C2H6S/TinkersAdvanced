package com.c2h6s.tinkers_advanced.data.providers;

import appeng.datagen.providers.tags.ConventionTags;
import com.buuz135.industrial.utils.IndustrialTags;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.data.TiAcTagkeys;
import com.c2h6s.tinkers_advanced.registery.TiAcItems;
import me.desht.pneumaticcraft.api.data.PneumaticCraftTags;
import mekanism.common.registries.MekanismItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.common.TinkerTags;

import java.util.concurrent.CompletableFuture;

public class TiAcItemTagProvider extends ItemTagsProvider {
    public TiAcItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, TinkersAdvanced.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(TinkerTags.Items.PATTERNS).add(TiAcItems.DISINTEGRATE_CRYSTAL.get(),TiAcItems.RESONANCE_CRYSTAL.get(),TiAcItems.VOLTAIC_CRYSTAL.get());
        this.tag(TinkerTags.Items.REUSABLE_PATTERNS).addOptionalTag(ConventionTags.INSCRIBER_PRESSES);
        this.tag(TiAcTagkeys.Items.PLASTIC).addOptionalTag(PneumaticCraftTags.Items.PLASTIC_SHEETS.location()).addOptionalTag(IndustrialTags.Items.PLASTIC.location()).addOptional(MekanismItems.HDPE_SHEET.getRegistryName());
        this.tag(TiAcTagkeys.Items.ANTIMONY_NUGGET).add(TiAcItems.ANTIMONY_NUGGET.get());
        this.tag(TiAcTagkeys.Items.BISMUTH_NUGGET).add(TiAcItems.BISMUTH_NUGGET.get());
        this.tag(TiAcTagkeys.Items.ANTIMONY_INGOT).add(TiAcItems.ANTIMONY_INGOT.get());
        this.tag(TiAcTagkeys.Items.BISMUTH_INGOT).add(TiAcItems.BISMUTH_INGOT.get());
        this.tag(Tags.Items.INGOTS)
                .add(TiAcItems.ANTIMONY_INGOT.get(),TiAcItems.BISMUTH_INGOT.get())
                .addOptional(TiAcItems.DENSIUM_INGOT.getId())
                .addOptional(TiAcItems.OSGLOGLAS_INGOT.getId())
                .addOptional(TiAcItems.NEUTRONITE_INGOT.getId())
                .addOptional(TiAcItems.NUTRITION_SLIME_INGOT.getId())
                .addOptional(TiAcItems.BLAZE_NETHERITE.getId())
                .addOptional(TiAcItems.BASALZ_SIGNALUM.getId())
                .addOptional(TiAcItems.BLITZ_LUMIUM.getId())
                .addOptional(TiAcItems.BLIZZ_ENDERIUM.getId())
                .addOptional(TiAcItems.PNEUMATIC_STEEL.getId());
        this.tag(Tags.Items.NUGGETS)
                .add(TiAcItems.BISMUTH_NUGGET.get(),TiAcItems.ANTIMONY_NUGGET.get())
                .addOptional(TiAcItems.BLITZ_LUMIUM_NUGGET.getId());
    }
}
