package com.c2h6s.tinkers_advanced.data.providers;

import appeng.api.ids.AETags;
import appeng.core.definitions.AEItems;
import appeng.datagen.providers.tags.ConventionTags;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.registery.TiAcItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
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
    }
}
