package com.c2h6s.tinkers_advanced.materials.data;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.materials.data.providers.tinker.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.fluids.data.FluidBucketModelProvider;

@Mod.EventBusSubscriber(modid = TinkersAdvanced.MODID,bus=Mod.EventBusSubscriber.Bus.MOD)
public class TiAcMeDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator=event.getGenerator();
        PackOutput output=generator.getPackOutput();
        ExistingFileHelper helper=event.getExistingFileHelper();

        generator.addProvider(event.includeClient(),new TiAcMeMaterialProvider(output));
        generator.addProvider(event.includeClient(),new TiAcMeMaterialStatProvider(output));
        generator.addProvider(event.includeClient(),new TiAcMeMaterialModifierProvider(output));
        generator.addProvider(event.includeClient(),new TiAcMeFluidEffectProvider(output));
        generator.addProvider(event.includeClient(),new TiAcMeMaterialTagProvider(output,helper));
        generator.addProvider(event.includeClient(),new TiAcMeModifierTagProvider(output,helper));
        generator.addProvider(event.includeClient(),new TiAcMeRecipeProvider(output));
        generator.addProvider(event.includeClient(),new FluidBucketModelProvider(output,TinkersAdvanced.MODID));
    }
}
