package com.c2h6s.tinkers_advanced.data;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.data.providers.*;
import com.c2h6s.tinkers_advanced.materials.data.providers.tinker.*;
import com.c2h6s.tinkers_advanced.tools.data.providers.TiAcTPartSpriteProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.fluids.data.FluidBucketModelProvider;
import slimeknights.tconstruct.library.client.data.material.MaterialPartTextureGenerator;
import slimeknights.tconstruct.tools.data.sprite.TinkerMaterialSpriteProvider;
import slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider;


import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = TinkersAdvanced.MODID,bus=Mod.EventBusSubscriber.Bus.MOD)
public class TiAcDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator=event.getGenerator();
        PackOutput output=generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider=event.getLookupProvider();
        ExistingFileHelper helper=event.getExistingFileHelper();

        generator.addProvider(event.includeClient(),new TiAcBlockStateProvider(output,TinkersAdvanced.MODID,helper));
        generator.addProvider(event.includeClient(),new TiAcItemModelProvider(output,helper));
        TiAcBlockTagProvider blockTags = new TiAcBlockTagProvider(output, lookupProvider, helper);
        generator.addProvider(event.includeClient(),blockTags);

        generator.addProvider(event.includeClient(),new TiAcFluidTextureProvider(output));
        generator.addProvider(event.includeClient(),new TiAcFluidTagProvider(output,lookupProvider,helper));
        generator.addProvider(event.includeClient(),new TiAcMeMaterialRenderInfoProvider(output,new TiAcMeMaterialSpriteProvider(),helper));
        generator.addProvider(event.includeClient(),new MaterialPartTextureGenerator(output,helper,new TiAcTPartSpriteProvider(),new TinkerMaterialSpriteProvider(),new TiAcMeMaterialSpriteProvider()));
        generator.addProvider(event.includeClient(),new MaterialPartTextureGenerator(output,helper,new TinkerPartSpriteProvider(),new TiAcMeMaterialSpriteProvider()));
        generator.addProvider(event.includeServer(),new TiAcItemTagProvider(output,lookupProvider,blockTags.contentsGetter(),helper));

        //generator.addProvider(event.includeServer(),new TiAcLootTableProvider(output));
    }
}


