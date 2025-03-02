package com.c2h6s.tinkers_advanced.data.providers;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.registery.TiAcItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class TiAcBlockStateProvider extends BlockStateProvider {
    public TiAcBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TinkersAdvanced.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (RegistryObject<BlockItem> object: TiAcItems.getListSimpleBlock()){
            Block block =object.get().getBlock();
            ModelFile file = cubeAll(block);
            simpleBlock(block,file);
        }
    }
}
