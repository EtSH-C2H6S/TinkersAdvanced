package com.c2h6s.tinkers_advanced.data;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.data.providers.TiAcFluidTagProvider;
import com.c2h6s.tinkers_advanced.data.providers.TiAcFluidTextureProvider;
import com.c2h6s.tinkers_advanced.data.providers.TiAcItemModelProvider;
import com.c2h6s.tinkers_advanced.data.providers.TiAcMaterialRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = TinkersAdvanced.MODID,bus=Mod.EventBusSubscriber.Bus.MOD)
@OnlyIn(Dist.CLIENT)
public class TiAcDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator=event.getGenerator();
        PackOutput output=generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider=event.getLookupProvider();
        ExistingFileHelper helper=event.getExistingFileHelper();

        generator.addProvider(event.includeClient(),new TiAcItemModelProvider(output,helper));
        generator.addProvider(event.includeClient(),new TiAcFluidTextureProvider(output));
        generator.addProvider(event.includeClient(),new TiAcFluidTagProvider(output,lookupProvider,helper));
        generator.addProvider(event.includeClient(),new TiAcMaterialRecipeProvider(output));

    }
}


