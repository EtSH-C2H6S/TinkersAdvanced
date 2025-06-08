package com.c2h6s.tinkers_advanced.data.providers;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.registery.TiAcBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TiAcBlockTagProvider extends BlockTagsProvider {
    public TiAcBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TinkersAdvanced.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(TiAcBlocks.IRIDIUM_LEAN_ORE.get());
        tag(Tiers.DIAMOND.getTag()).add(TiAcBlocks.BISMUTHINITE.get(),
                TiAcBlocks.STIBNITE_ORE.get()
        );
        tag(Tiers.WOOD.getTag()).add(TiAcBlocks.EXCHANGER.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(TiAcBlocks.IRIDIUM_LEAN_ORE.get())
                .add(TiAcBlocks.BISMUTHINITE.get())
                .add(TiAcBlocks.STIBNITE_ORE.get())
                .add(TiAcBlocks.EXCHANGER.get())
        ;
    }
}
