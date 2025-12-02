package com.c2h6s.tinkers_advanced.data.providers;

import appeng.datagen.providers.tags.ConventionTags;
import com.buuz135.industrial.utils.IndustrialTags;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.materials.data.TiAcTagkeys;
import com.c2h6s.tinkers_advanced.materials.init.TiAcMeItems;
import com.c2h6s.tinkers_advanced.materials.init.TiAcMeMaterials;
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
        this.tag(TinkerTags.Items.PATTERNS).add(TiAcMeItems.DISINTEGRATE_CRYSTAL.get(), TiAcMeItems.RESONANCE_CRYSTAL.get(), TiAcMeItems.VOLTAIC_CRYSTAL.get());
        this.tag(TinkerTags.Items.REUSABLE_PATTERNS).addOptionalTag(ConventionTags.INSCRIBER_PRESSES);
        this.tag(TiAcTagkeys.Items.PLASTIC).addOptionalTag(PneumaticCraftTags.Items.PLASTIC_SHEETS.location()).addOptionalTag(IndustrialTags.Items.PLASTIC.location()).addOptional(MekanismItems.HDPE_SHEET.getRegistryName());
        this.tag(TiAcTagkeys.Items.ANTIMONY_NUGGET).add(TiAcMeItems.ANTIMONY_NUGGET.get());
        this.tag(TiAcTagkeys.Items.BISMUTH_NUGGET).add(TiAcMeItems.BISMUTH_NUGGET.get());
        this.tag(TiAcTagkeys.Items.ANTIMONY_INGOT).add(TiAcMeItems.ANTIMONY_INGOT.get());
        this.tag(TiAcTagkeys.Items.BISMUTH_INGOT).add(TiAcMeItems.BISMUTH_INGOT.get());
        this.tag(Tags.Items.INGOTS)
                .add(TiAcMeItems.ANTIMONY_INGOT.get(), TiAcMeItems.BISMUTH_INGOT.get())
                .addOptional(TiAcMeMaterials.DENSIUM.getItemObject().getId())
                .addOptional(TiAcMeItems.OSGLOGLAS_INGOT.getId())
                .addOptional(TiAcMeItems.NEUTRONITE_INGOT.getId())
                .addOptional(TiAcMeItems.NUTRITION_SLIME_INGOT.getId())
                .addOptional(TiAcMeItems.BLAZE_NETHERITE.getId())
                .addOptional(TiAcMeItems.BASALZ_SIGNALUM.getId())
                .addOptional(TiAcMeItems.BLITZ_LUMIUM.getId())
                .addOptional(TiAcMeItems.BLIZZ_ENDERIUM.getId())
                .addOptional(TiAcMeItems.PNEUMATIC_STEEL.getId());
        this.tag(Tags.Items.NUGGETS)
                .add(TiAcMeItems.BISMUTH_NUGGET.get(), TiAcMeItems.ANTIMONY_NUGGET.get())
                .addOptional(TiAcMeItems.BLITZ_LUMIUM_NUGGET.getId());
    }
}
