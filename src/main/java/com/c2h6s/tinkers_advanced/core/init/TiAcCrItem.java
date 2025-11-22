package com.c2h6s.tinkers_advanced.core.init;

import com.c2h6s.tinkers_advanced.core.content.item.UltraDenseBookItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.c2h6s.tinkers_advanced.TinkersAdvanced.MODID;

public class TiAcCrItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    protected static List<RegistryObject<Item>> LIST_MISC =new ArrayList<>( List.of());
    protected static List<RegistryObject<Item>> LIST_MATERIAL=new ArrayList<>( List.of());
    protected static List<RegistryObject<Item>> LIST_TOOL=new ArrayList<>( List.of());
    protected static List<RegistryObject<BlockItem>> LIST_SIMPLE_BLOCK =new ArrayList<>( List.of());
    protected static List<RegistryObject<BlockItem>> LIST_MISC_BLOCK =new ArrayList<>( List.of());
    protected static List<RegistryObject<BlockItem>> LIST_UTILITIES_BLOCK =new ArrayList<>( List.of());
    protected static List<RegistryObject<Item>> LIST_MATERIAL_ITEM_MODEL =new ArrayList<>( List.of());
    protected static List<RegistryObject<Item>> LIST_MISC_ITEM_MODEL =new ArrayList<>( List.of());
    public static List<RegistryObject<Item>> getListMisc(){
        return List.copyOf(LIST_MISC);
    }
    public static List<RegistryObject<Item>> getListMaterial(){
        return List.copyOf(LIST_MATERIAL);
    }
    public static List<RegistryObject<Item>> getListTool(){
        return List.copyOf(LIST_TOOL);
    }
    public static List<RegistryObject<Item>> getListMaterialItemModel(){
        return List.copyOf(LIST_MATERIAL_ITEM_MODEL);
    }
    public static List<RegistryObject<Item>> getListMiscItemModel(){
        return List.copyOf(LIST_MISC_ITEM_MODEL);
    }
    public static List<RegistryObject<BlockItem>> getListMiscBlock(){
        return List.copyOf(LIST_MISC_BLOCK);
    }
    public static List<RegistryObject<BlockItem>> getListUtilitiesBlock(){
        return List.copyOf(LIST_UTILITIES_BLOCK);
    }

    public static List<RegistryObject<Item>> getListSimpleMaterialModel(){
        return List.copyOf(LIST_MATERIAL_ITEM_MODEL);
    }
    public static List<RegistryObject<Item>> getListSimpleMiscModel(){
        return List.copyOf(LIST_MISC_ITEM_MODEL);
    }

    public static List<RegistryObject<BlockItem>> getListSimpleBlock(){
        return List.copyOf(LIST_SIMPLE_BLOCK);
    }

    public static RegistryObject<Item> registerMixc(DeferredRegister<Item> register, String name, Supplier<? extends Item> sup, boolean simpleModel){
        RegistryObject<Item> object = register.register(name,sup);
        LIST_MISC.add(object);
        if (simpleModel){
            LIST_MISC_ITEM_MODEL.add(object);
        }
        return object;
    }
    public static RegistryObject<Item> registerMaterial(DeferredRegister<Item> register,String name, Supplier<? extends Item> sup,boolean simpleModel){
        RegistryObject<Item> object = register.register(name,sup);
        LIST_MATERIAL.add(object);
        if (simpleModel){
            LIST_MATERIAL_ITEM_MODEL.add(object);
        }
        return object;
    }
    public static RegistryObject<Item> registerToolOrPart(DeferredRegister<Item> register,String name, Supplier<? extends Item> sup){
        RegistryObject<Item> object = register.register(name,sup);
        LIST_TOOL.add(object);
        return object;
    }
    public static RegistryObject<BlockItem> registerSimpleBlockItem(DeferredRegister<Item> register,RegistryObject<? extends Block> block){
        RegistryObject<BlockItem> object = register.register(block.getId().getPath(),() -> new BlockItem(block.get(), new Item.Properties()));
        LIST_SIMPLE_BLOCK.add(object);
        return object;
    }
    public static RegistryObject<BlockItem> registerBlockItem(DeferredRegister<Item> register,RegistryObject<? extends Block> block){
        RegistryObject<BlockItem> object = register.register(block.getId().getPath(),() -> new BlockItem(block.get(), new Item.Properties()));
        LIST_MISC_BLOCK.add(object);
        return object;
    }
    public static RegistryObject<BlockItem> registerUtilitiesBlockItem(DeferredRegister<Item> register,RegistryObject<? extends Block> block){
        RegistryObject<BlockItem> object = register.register(block.getId().getPath(),() -> new BlockItem(block.get(), new Item.Properties()));
        LIST_UTILITIES_BLOCK.add(object);
        return object;
    }

    public static final RegistryObject<Item> ULTRA_DENSE_BOOK = registerMixc(ITEMS,"ultra_dense_book",()->new UltraDenseBookItem(new Item.Properties()),true);
}
