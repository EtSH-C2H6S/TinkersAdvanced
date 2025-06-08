package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.content.item.HiddenMaterial;
import com.c2h6s.tinkers_advanced.content.item.UltraDenseBookItem;
import com.c2h6s.tinkers_advanced.content.item.tinkering.materialStat.FluxCoreMaterialStat;
import com.c2h6s.tinkers_advanced.content.item.toolItem.ElectronTunerItem;
import com.c2h6s.tinkers_advanced.content.item.toolItem.IonizedCannonItem;
import com.c2h6s.tinkers_advanced.content.item.toolItem.MatterManipulator;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
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
    public static final DeferredRegister<Item> IF_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final ItemDeferredRegisterExtension TINKER_ITEMS = new ItemDeferredRegisterExtension(MODID);

    protected static List<RegistryObject<Item>> LIST_MIXC=new ArrayList<>( List.of());
    protected static List<RegistryObject<Item>> LIST_MATERIAL=new ArrayList<>( List.of());
    protected static List<RegistryObject<Item>> LIST_TOOL=new ArrayList<>( List.of());

    protected static List<RegistryObject<BlockItem>> LIST_SIMPLE_BLOCK =new ArrayList<>( List.of());
    protected static List<RegistryObject<BlockItem>> LIST_MIXC_BLOCK =new ArrayList<>( List.of());
    protected static List<RegistryObject<Item>> LIST_MATERIAL_ITEM_MODEL =new ArrayList<>( List.of());
    protected static List<RegistryObject<Item>> LIST_MIXC_ITEM_MODEL =new ArrayList<>( List.of());

    public static List<RegistryObject<Item>> getListSimpleMaterialModel(){
        return List.copyOf(LIST_MATERIAL_ITEM_MODEL);
    }
    public static List<RegistryObject<Item>> getListSimpleMiscModel(){
        return List.copyOf(LIST_MIXC_ITEM_MODEL);
    }

    public static List<RegistryObject<BlockItem>> getListSimpleBlock(){
        return List.copyOf(LIST_SIMPLE_BLOCK);
    }

    public static RegistryObject<Item> registerMixc(DeferredRegister<Item> register,String name, Supplier<? extends Item> sup,boolean simpleModel){
        RegistryObject<Item> object = register.register(name,sup);
        LIST_MIXC.add(object);
        if (simpleModel){
            LIST_MIXC_ITEM_MODEL.add(object);
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
        LIST_MIXC_BLOCK.add(object);
        return object;
    }

    public static final RegistryObject<BlockItem> BISMUTHINITE_ORE = registerSimpleBlockItem(ITEMS,TiAcBlocks.BISMUTHINITE);
    public static final RegistryObject<BlockItem> IRIDIUM_LEAN_ORE = registerSimpleBlockItem(ITEMS,TiAcBlocks.IRIDIUM_LEAN_ORE);
    public static final RegistryObject<BlockItem> STIBNITE_ORE = registerBlockItem(ITEMS,TiAcBlocks.STIBNITE_ORE);
    public static final RegistryObject<BlockItem> EXCHANGER = registerBlockItem(ITEMS,TiAcBlocks.EXCHANGER);

    public static final RegistryObject<Item> BISMUTH_INGOT = registerMaterial(ITEMS,"bismuth_ingot",()->new Item(new Item.Properties()),true);
    public static final RegistryObject<Item> BISMUTH_NUGGET = registerMaterial(ITEMS,"bismuth_nugget",()->new Item(new Item.Properties()),true);
    public static final RegistryObject<Item> BISMUTHINITE = registerMaterial(ITEMS,"bismuthinite",()->new Item(new Item.Properties()),true);
    public static final RegistryObject<Item> BLAZE_NETHERITE = registerMaterial(ITEMS,"blaze_netherite",()->new Item(new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()),true);
    public static final RegistryObject<Item> IRIDIUM_CHUNK = registerMaterial(ITEMS,"iridium_chunk",()->new Item(new Item.Properties().rarity(Rarity.UNCOMMON)),true);
    public static final RegistryObject<Item> RESONANCE_CRYSTAL = registerMaterial(ITEMS,"resonance_crystal",()->new Item(new Item.Properties().rarity(Rarity.UNCOMMON)),true);
    public static final RegistryObject<Item> DISINTEGRATE_CRYSTAL = registerMaterial(ITEMS,"disintegrate_crystal",()->new Item(new Item.Properties().rarity(Rarity.UNCOMMON)),true);
    public static final RegistryObject<Item> VOLTAIC_CRYSTAL = registerMaterial(ITEMS,"voltaic_crystal",()->new Item(new Item.Properties().rarity(Rarity.RARE)),true);
    public static final RegistryObject<Item> ANTIMONY_INGOT = registerMaterial(ITEMS,"antimony_ingot",()->new Item(new Item.Properties().rarity(Rarity.UNCOMMON)),true);
    public static final RegistryObject<Item> ANTIMONY_NUGGET = registerMaterial(ITEMS,"antimony_nugget",()->new Item(new Item.Properties().rarity(Rarity.UNCOMMON)),true);
    public static final RegistryObject<Item> STIBNITE = registerMaterial(ITEMS,"stibnite",()->new Item(new Item.Properties().rarity(Rarity.UNCOMMON)),true);
    public static final RegistryObject<Item> ULTRA_DENSE_BOOK = registerMixc(ITEMS,"ultra_dense_book",()->new UltraDenseBookItem(new Item.Properties()),true);

    public static final ItemObject<ToolPartItem> IONIZE_CHAMBER = TINKER_ITEMS.register("ionize_chamber",()->new ToolPartItem(new Item.Properties(), HandleMaterialStats.ID));
    public static final ItemObject<ToolPartItem> PARTICLE_CONTAINER = TINKER_ITEMS.register("particle_container",()->new ToolPartItem(new Item.Properties(), HandleMaterialStats.ID));
    public static final ItemObject<ToolPartItem> FLUX_CORE = TINKER_ITEMS.register("flux_core",()->new ToolPartItem(new Item.Properties(), FluxCoreMaterialStat.ID));


    public static final ItemObject<ModifiableItem> IONIZED_CANNON = TINKER_ITEMS.register("ionized_cannon",()->new IonizedCannonItem(new Item.Properties().stacksTo(1)));
    public static final ItemObject<ModifiableItem> MATTER_MANIPULATOR = TINKER_ITEMS.register("matter_manipulator",()->new MatterManipulator(new Item.Properties().stacksTo(1)));
    public static final ItemObject<ModifiableItem> ELECTRON_TUNER = TINKER_ITEMS.register("electron_tuner",()->new ElectronTunerItem(new Item.Properties().stacksTo(1)));



    public static final RegistryObject<Item> IRRADIUM_INGOT = registerMaterial(MEK_ITEMS,"irradium_ingot",()->new Item(new Item.Properties().rarity(Rarity.RARE)),true);
    public static final RegistryObject<Item> PROTOCITE_PELLET = registerMaterial(MEK_ITEMS,"protocite_pellet",()->new Item(new Item.Properties().rarity(Rarity.RARE)),true);
    public static final RegistryObject<Item> DENSIUM_INGOT = registerMaterial(MEK_ITEMS,"densium_ingot",()->new Item(new Item.Properties().rarity(Rarity.UNCOMMON)),true);
    public static final RegistryObject<Item> OSGLOGLAS_INGOT = registerMaterial(MEK_ITEMS,"osgloglas_ingot",()->new Item(new Item.Properties()),true);
    public static final RegistryObject<Item> NEUTRONITE_INGOT = registerMaterial(MEK_ITEMS,"neutronite_ingot",()->new HiddenMaterial(new Item.Properties().rarity(Rarity.EPIC).fireResistant(),List.of(
            Component.translatable("tooltip.tinkers_advanced.hidden_material_mek").withStyle(ChatFormatting.LIGHT_PURPLE),
            Component.translatable("tooltip.tinkers_advanced.neutronite_1").withStyle(ChatFormatting.GRAY),
            Component.translatable("tooltip.tinkers_advanced.neutronite_2"),
            Component.translatable("tooltip.tinkers_advanced.neutronite_3").withStyle(ChatFormatting.DARK_AQUA)
    ), TiAcConfig.COMMON.EXPLODING_FUSION_REACTOR),true);
    public static final RegistryObject<Item> NUTRITION_SLIME_INGOT = registerMaterial(MEK_ITEMS,"nutrition_slime_ingot",()->new Item(new Item.Properties()),true);



    public static final RegistryObject<Item> PNEUMATIC_STEEL = registerMaterial(PNC_ITEMS,"penumatic_reinforced_steel",()->new Item(new Item.Properties()),true);
    public static final RegistryObject<Item> PNEUMATIC_STEEL_HOT = registerMixc(PNC_ITEMS,"hot_reinforced_steel",()->new Item(new Item.Properties()),true);



    public static final RegistryObject<Item> BASALZ_SIGNALUM = registerMaterial(THERMAL_ITEMS,"basalz_signalum",()->new Item(new Item.Properties()),true);
    public static final RegistryObject<Item> BLITZ_LUMIUM = registerMaterial(THERMAL_ITEMS,"blitz_lumium",()->new Item(new Item.Properties()),true);
    public static final RegistryObject<Item> BLITZ_LUMIUM_NUGGET = registerMaterial(THERMAL_ITEMS,"blitz_lumium_nugget",()->new Item(new Item.Properties()),true);
    public static final RegistryObject<Item> BLIZZ_ENDERIUM = registerMaterial(THERMAL_ITEMS,"blizz_enderium",()->new Item(new Item.Properties()),true);
    public static final RegistryObject<Item> ACTIVATED_CHROMATIC_STEEL = registerMaterial(THERMAL_ITEMS,"activated_chromatic_steel",()->new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()),true);
}
