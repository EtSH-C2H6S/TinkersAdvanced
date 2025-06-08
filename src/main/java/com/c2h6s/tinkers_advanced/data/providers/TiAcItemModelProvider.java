package com.c2h6s.tinkers_advanced.data.providers;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.registery.TiAcItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.loaders.DynamicFluidContainerModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.object.FluidObject;

public class TiAcItemModelProvider extends ItemModelProvider {
    public static final String PARENT_SIMPLE_ITEM ="item/generated";
    public static final String PARENT_BUCKET_FLUID ="forge:item/bucket_drip";

    public TiAcItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TinkersAdvanced.MODID, existingFileHelper);
    }

    public void generateItemModel(RegistryObject<Item> object,String typePath){
        withExistingParent( object.getId().getPath(), PARENT_SIMPLE_ITEM).texture("layer0",getItemLocation(object.getId().getPath(),typePath));
    }
    public void generateBlockItemModel(RegistryObject<BlockItem> object){
        withExistingParent(object.getId().getPath(), getBlockItemLocation(object.getId().getPath()));
    }
    public void generateBucketItemModel(FluidObject<ForgeFlowingFluid> object,boolean flip){
        withExistingParent(object.getId().getPath()+"_bucket",PARENT_BUCKET_FLUID).customLoader((itemModelBuilder,existingFileHelper)->DynamicFluidContainerModelBuilder
                .begin(itemModelBuilder,existingFileHelper)
                .fluid(object.get())
                .flipGas(flip));
    }

    public ResourceLocation getItemLocation(String path,String typePath){
        return new ResourceLocation(TinkersAdvanced.MODID,"item/"+typePath+"/"+path);
    }
    public ResourceLocation getBlockItemLocation(String path){
        return new ResourceLocation(TinkersAdvanced.MODID,"block/"+path);
    }

    @Override
    protected void registerModels() {
        for (RegistryObject<Item> object: TiAcItems.getListSimpleMaterialModel()){
            generateItemModel(object,"material");
        }
        for (RegistryObject<Item> object: TiAcItems.getListSimpleMiscModel()){
            generateItemModel(object,"misc");
        }
        for (RegistryObject<BlockItem> object:TiAcItems.getListSimpleBlock()){
            generateBlockItemModel(object);
        }
        generateBlockItemModel(TiAcItems.STIBNITE_ORE);
//        Map<FluidObject<ForgeFlowingFluid>,Boolean> map = TiAcFluids.getFluidMap();
//        for (FluidObject<ForgeFlowingFluid> object : map.keySet()){
//            generateBucketItemModel(object,map.get(object));
//        }
    }
}
