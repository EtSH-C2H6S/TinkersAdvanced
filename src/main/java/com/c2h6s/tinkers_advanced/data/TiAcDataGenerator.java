package com.c2h6s.tinkers_advanced.data;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.data.providers.*;
import com.c2h6s.tinkers_advanced.data.providers.tinker.*;
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

        generator.addProvider(event.includeClient(),new TiAcBlockStateProvider(output,helper));
        generator.addProvider(event.includeClient(),new TiAcItemModelProvider(output,helper));
        generator.addProvider(event.includeClient(),new TiAcFluidTextureProvider(output));
        generator.addProvider(event.includeClient(),new TiAcFluidTagProvider(output,lookupProvider,helper));
        generator.addProvider(event.includeClient(),new TiAcRecipeProvider(output));
        generator.addProvider(event.includeClient(),new TiAcMaterialProvider(output));
        generator.addProvider(event.includeClient(),new TiAcMaterialStatProvider(output));
        generator.addProvider(event.includeClient(),new TiAcMaterialModifierProvider(output));
        generator.addProvider(event.includeClient(),new TiAcFluidEffectProvider(output));
        generator.addProvider(event.includeClient(),new TiAcMaterialTagProvider(output,helper));
        generator.addProvider(event.includeClient(),new TiAcModifierTagProvider(output,helper));
        generator.addProvider(event.includeClient(),new FluidBucketModelProvider(output,TinkersAdvanced.MODID));
        generator.addProvider(event.includeClient(),new TiAcMaterialRenderInfoProvider(output,new TiAcMaterialSpriteProvider(),helper));
        generator.addProvider(event.includeClient(),new MaterialPartTextureGenerator(output,helper,new TiAcPartSpriteProvider(),new TinkerMaterialSpriteProvider(),new TiAcMaterialSpriteProvider()));
        generator.addProvider(event.includeClient(),new MaterialPartTextureGenerator(output,helper,new TinkerPartSpriteProvider(),new TiAcMaterialSpriteProvider()));
        TiAcBlockTagProvider blockTags = new TiAcBlockTagProvider(output, lookupProvider, helper);
        generator.addProvider(event.includeClient(),blockTags);
        generator.addProvider(event.includeServer(),new TiAcItemTagProvider(output,lookupProvider,blockTags.contentsGetter(),helper));
        generator.addProvider(event.includeServer(),new TiAcModifierProvider(output));

        //generator.addProvider(event.includeServer(),new TiAcLootTableProvider(output));
    }
}


