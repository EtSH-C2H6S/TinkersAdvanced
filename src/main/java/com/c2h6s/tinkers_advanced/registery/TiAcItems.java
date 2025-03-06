package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.content.item.IonizedCannonItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.c2h6s.tinkers_advanced.TinkersAdvanced.MODID;

public class TiAcItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Item> MEK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Item> PNC_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Item> THERMAL_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final ItemDeferredRegisterExtension TINKER_ITEMS = new ItemDeferredRegisterExtension(MODID);

    protected static List<RegistryObject<Item>> LIST_MIXC=new ArrayList<>( List.of());
    protected static List<RegistryObject<Item>> LIST_MATERIAL=new ArrayList<>( List.of());
    protected static List<RegistryObject<Item>> LIST_TOOL=new ArrayList<>( List.of());

    protected static List<RegistryObject<BlockItem>> LIST_SIMPLE_BLOCK =new ArrayList<>( List.of());
    protected static List<RegistryObject<Item>> LIST_MATERIAL_ITEM_MODEL =new ArrayList<>( List.of());

    public static List<RegistryObject<Item>> getListSimpleModel(){
        return List.copyOf(LIST_MATERIAL_ITEM_MODEL);
    }

    public static List<RegistryObject<BlockItem>> getListSimpleBlock(){
        return List.copyOf(LIST_SIMPLE_BLOCK);
    }

    public static RegistryObject<Item> registerMixc(DeferredRegister<Item> register,String name, Supplier<? extends Item> sup,boolean simpleModel){
        RegistryObject<Item> object = register.register(name,sup);
        LIST_MIXC.add(object);
        if (simpleModel){
            LIST_MATERIAL_ITEM_MODEL.add(object);
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


    public static final RegistryObject<BlockItem> BISMUTHINITE_ORE = registerSimpleBlockItem(ITEMS,TiAcBlocks.BISMUTHINITE);
    public static final RegistryObject<BlockItem> BISMUTHINITE_ORE_DEEPSLATE = registerSimpleBlockItem(ITEMS,TiAcBlocks.BISMUTHINITE_DEEPSLATE);

    public static final RegistryObject<Item> BISMUTH_INGOT = registerMaterial(ITEMS,"bismuth_ingot",()->new Item(new Item.Properties()),true);
    public static final RegistryObject<Item> BISMUTHINITE = registerMaterial(ITEMS,"bismuthinite",()->new Item(new Item.Properties()),true);

    public static final ItemObject<ToolPartItem> IONIZE_CHAMBER = TINKER_ITEMS.register("ionize_chamber",()->new ToolPartItem(new Item.Properties(), HandleMaterialStats.ID));

    public static final ItemObject<ModifiableItem> IONIZED_CANNON = TINKER_ITEMS.register("ionized_cannon",()->new IonizedCannonItem(new Item.Properties().stacksTo(1)));



    public static final RegistryObject<Item> IRRADIUM_INGOT = registerMaterial(MEK_ITEMS,"irradium_ingot",()->new Item(new Item.Properties()),true);



    public static final RegistryObject<Item> PNEUMATIC_STEEL = registerMaterial(PNC_ITEMS,"penumatic_reinforced_steel",()->new Item(new Item.Properties()),true);
    public static final RegistryObject<Item> PNEUMATIC_STEEL_HOT = registerMaterial(PNC_ITEMS,"hot_reinforced_steel",()->new Item(new Item.Properties()),true);



    public static final RegistryObject<Item> BASALZ_SIGNALUM = registerMaterial(THERMAL_ITEMS,"basalz_signalum",()->new Item(new Item.Properties()),true);
    public static final RegistryObject<Item> BLITZ_LUMIUM = registerMaterial(THERMAL_ITEMS,"blitz_lumium",()->new Item(new Item.Properties()),true);
    public static final RegistryObject<Item> BLIZZ_ENDERIUM = registerMaterial(THERMAL_ITEMS,"blizz_enderium",()->new Item(new Item.Properties()),true);
}
