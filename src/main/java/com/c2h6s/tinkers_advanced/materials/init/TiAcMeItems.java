package com.c2h6s.tinkers_advanced.materials.init;

import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import com.c2h6s.tinkers_advanced.core.content.item.HiddenMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

import static com.c2h6s.tinkers_advanced.TinkersAdvanced.MODID;
import static com.c2h6s.tinkers_advanced.core.init.TiAcCrItem.*;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiAcMeItems {
    @SubscribeEvent
    public static void init(TiAcLoadRegistryClassEvent event){}
    public static final DeferredRegister<Item> MEK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Item> PNC_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Item> THERMAL_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Item> IF_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);


    public static final RegistryObject<BlockItem> BISMUTHINITE_ORE = registerSimpleBlockItem(ITEMS, TiAcMeBlocks.BISMUTHINITE);
    public static final RegistryObject<BlockItem> IRIDIUM_LEAN_ORE = registerSimpleBlockItem(ITEMS, TiAcMeBlocks.IRIDIUM_LEAN_ORE);
    public static final RegistryObject<BlockItem> STIBNITE_ORE = registerBlockItem(ITEMS, TiAcMeBlocks.STIBNITE_ORE);


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


    public static final RegistryObject<Item> IRRADIUM_INGOT = registerMaterial(MEK_ITEMS,"irradium_ingot",()->new Item(new Item.Properties().rarity(Rarity.RARE)),true);
    public static final RegistryObject<Item> PROTOCITE_PELLET = registerMaterial(MEK_ITEMS,"protocite_pellet",()->new Item(new Item.Properties().rarity(Rarity.RARE)),true);
//    public static final RegistryObject<Item> DENSIUM_INGOT = registerMaterial(MEK_ITEMS,"densium_ingot",()->new Item(new Item.Properties().rarity(Rarity.UNCOMMON)),true);
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
