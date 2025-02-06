package com.c2h6s.tinkers_advanced.data.providers;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.registery.TiAcItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class TiAcItemModelProvider extends ItemModelProvider {
    public static final String PARENT_SIMPLE_ITEM ="item/generated";

    public TiAcItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TinkersAdvanced.MODID, existingFileHelper);
    }

    public void generateItemModel(RegistryObject<Item> object){
        withExistingParent(object.getId().getPath(), PARENT_SIMPLE_ITEM).texture("layer0",getItemLocation(object.getId().getPath()));
    }
    public void generateBlockItemModel(RegistryObject<Item> object){
        withExistingParent(object.getId().getPath(), getBlockItemLocation(object.getId().getPath())).texture("layer0",getBlockItemLocation(object.getId().getPath()));
    }

    public ResourceLocation getItemLocation(String path){
        return new ResourceLocation(TinkersAdvanced.MODID,"item/"+path);
    }
    public ResourceLocation getBlockItemLocation(String path){
        return new ResourceLocation(TinkersAdvanced.MODID,"block/"+path);
    }

    @Override
    protected void registerModels() {
        for (RegistryObject<Item> object: TiAcItems.getListSimpleModel()){
            generateItemModel(object);
        }
        for (RegistryObject<Item> object:TiAcItems.getListSimpleBlock()){
            generateBlockItemModel(object);
        }
    }
}
