package com.c2h6s.tinkers_advanced.data.providers;

import com.c2h6s.etstlib.content.block.SelfDroppingTieredBlock;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.core.TiAcCrModule;
import com.c2h6s.tinkers_advanced.materials.init.TiAcMeBlocks;
import com.c2h6s.tinkers_advanced.utilities.init.TiAcUBlocks;
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
        tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(TiAcMeBlocks.IRIDIUM_LEAN_ORE.get());
        tag(Tiers.DIAMOND.getTag()).add(TiAcMeBlocks.BISMUTHINITE.get(),
                TiAcMeBlocks.STIBNITE_ORE.get()
        );
        tag(Tiers.WOOD.getTag()).add(TiAcUBlocks.EXCHANGER.get());
        tag(Tiers.IRON.getTag()).add(
                TiAcUBlocks.ROSE_GOLD_FAUCET.get(),
                TiAcUBlocks.ROSE_GOLD_TABLE.get(),
                TiAcUBlocks.ROSE_GOLD_BASIN.get(),
                TiAcUBlocks.HEPATIZON_BASIN.get(),
                TiAcUBlocks.HEPATIZON_TABLE.get(),
                TiAcUBlocks.HEPATIZON_FAUCET.get()
        );
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(TiAcMeBlocks.IRIDIUM_LEAN_ORE.get())
                .add(TiAcMeBlocks.BISMUTHINITE.get())
                .add(TiAcMeBlocks.STIBNITE_ORE.get())
                .add(TiAcUBlocks.EXCHANGER.get())
                .add(TiAcUBlocks.ROSE_GOLD_FAUCET.get())
                .add(TiAcUBlocks.ROSE_GOLD_TABLE.get())
                .add(TiAcUBlocks.ROSE_GOLD_BASIN.get())
                .add(TiAcUBlocks.HEPATIZON_BASIN.get())
                .add(TiAcUBlocks.HEPATIZON_TABLE.get())
                .add(TiAcUBlocks.HEPATIZON_FAUCET.get())
        ;
        TiAcCrModule.BLOCKS.getEntries().forEach(reg->{
            if (reg.isPresent()&&reg.get() instanceof SelfDroppingTieredBlock block){
                block.getMiningTierOptional().ifPresent(blockTagKey -> tag(blockTagKey).add(block));
            }
        });
    }
}
