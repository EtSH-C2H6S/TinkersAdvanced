package com.c2h6s.tinkers_advanced.core.data.providers;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static com.c2h6s.tinkers_advanced.core.init.TiAcCrItem.*;

public class TiAcCrItemModelProvider extends ItemModelProvider {
    public static final String PARENT_SIMPLE_ITEM ="item/generated";
    public static final String PARENT_BUCKET_FLUID ="forge:item/bucket_drip";
    public TiAcCrItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TinkersAdvanced.MODID, existingFileHelper);
    }

    public void generateItemModel(RegistryObject<Item> object,String typePath){
        withExistingParent( object.getId().getPath(), PARENT_SIMPLE_ITEM).texture("layer0",getItemLocation(object.getId().getPath(),typePath));
    }
    public ResourceLocation getItemLocation(String path,String typePath){
        return new ResourceLocation(TinkersAdvanced.MODID,"item/"+typePath+"/"+path);
    }

    @Override
    protected void registerModels() {
        generateItemModel(ULTRA_DENSE_BOOK,"misc");
    }
}
