package com.c2h6s.tinkers_advanced.core.data;

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
public class TiAcCrDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator=event.getGenerator();
        PackOutput output=generator.getPackOutput();
        ExistingFileHelper helper=event.getExistingFileHelper();

        generator.addProvider(event.includeClient(),new TiAcItemModelProvider(output,helper));
    }
}


