package com.c2h6s.tinkers_advanced.data.providers.tinker;

import appeng.datagen.providers.tags.ConventionTags;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.data.TiAcMaterialIds;
import com.c2h6s.tinkers_advanced.data.TiAcTagkeys;
import com.c2h6s.tinkers_advanced.registery.TiAcFluids;
import com.c2h6s.tinkers_advanced.registery.TiAcItems;
import mekanism.api.datagen.recipe.builder.CombinerRecipeBuilder;
import mekanism.common.recipe.ingredient.creator.ItemStackIngredientCreator;
import mekanism.common.registries.MekanismFluids;
import mekanism.common.registries.MekanismItems;
import mekanism.common.tags.MekanismTags;
import mekanism.generators.common.registries.GeneratorsFluids;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.recipe.condition.TagFilledCondition;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.mantle.recipe.ingredient.FluidIngredient;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.json.ConfigEnabledCondition;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipeBuilder;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.casting.material.MaterialFluidRecipeBuilder;
import slimeknights.tconstruct.library.recipe.fuel.MeltingFuelBuilder;
import slimeknights.tconstruct.library.recipe.material.MaterialRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe;
import slimeknights.tconstruct.library.recipe.melting.MaterialMeltingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.tinkerstation.building.ToolBuildingRecipeBuilder;
import slimeknights.tconstruct.shared.TinkerMaterials;

import java.util.function.Consumer;

public class TiAcMaterialRecipeProvider extends RecipeProvider implements ISmelteryRecipeHelper {
    public TiAcMaterialRecipeProvider(PackOutput generator) {
        super(generator);
    }
    public static final TagKey<Item> GEM_MULTICAST = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), TConstruct.getResource("casts/multi_use/gem"));
    public static final TagKey<Item> GEM_SINGLECAST = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), TConstruct.getResource("casts/single_use/gem"));
    public static final TagKey<Item> INGOT_MULTICAST = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), TConstruct.getResource("casts/multi_use/ingot"));
    public static final TagKey<Item> INGOT_SINGLECAST = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), TConstruct.getResource("casts/single_use/ingot"));
    public static final TagKey<Item> PLATE_MULTICAST = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), TConstruct.getResource("casts/multi_use/plate"));
    public static final TagKey<Item> PLATE_SINGLECAST = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), TConstruct.getResource("casts/single_use/plate"));

    public static final ResourceLocation baseFolder = new ResourceLocation(TinkersAdvanced.MODID,"materials/");
    public static ResourceLocation namedFolder(String name){
        return ResourceLocation.tryParse(baseFolder+name+"/"+name);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ResourceLocation folder;
        Consumer<FinishedRecipe> conditional;
        folder = namedFolder("mixc");
        ToolBuildingRecipeBuilder.toolBuildingRecipe(TiAcItems.MATTER_MANIPULATOR.get()).save(consumer,new ResourceLocation(folder+"/matter_manipulator"));

        folder = namedFolder("fuel");
        fuel("over_heated_lava",FluidIngredient.of(TiAcFluids.OVER_HEATED_LAVA.get(),100),200,2000,consumer);
        AlloyRecipeBuilder.alloy(FluidOutput.fromStack( new FluidStack(TiAcFluids.OVER_HEATED_LAVA.get(),500)),1500)
                .addCatalyst(FluidIngredient.of(TinkerFluids.moltenCinderslime.get(),270))
                .addInput(FluidIngredient.of(TinkerFluids.blazingBlood.get(),500))
                .addInput(Fluids.LAVA,1000)
                .save(consumer,new ResourceLocation(folder+"_over_heated_lava"));
        fuel("gaseous_lava",FluidIngredient.of(TiAcFluids.GASEOUS_LAVA.get(),100),200,3000,consumer);
        AlloyRecipeBuilder.alloy(FluidOutput.fromStack( new FluidStack(TiAcFluids.GASEOUS_LAVA.get(),500)),2000)
                .addCatalyst(FluidIngredient.of(TinkerFluids.moltenManyullyn.get(),360))
                .addInput(FluidIngredient.of(TiAcFluids.MOLTEN_BLAZE_NETHERITE.get(),10))
                .addInput(FluidIngredient.of(TinkerFluids.honey.get(), 500))
                .addInput(TiAcFluids.OVER_HEATED_LAVA.get(),1000)
                .save(consumer,new ResourceLocation(folder+"_gaseous_lava"));
        fuel("plasmatic_lava",FluidIngredient.of(TiAcFluids.PLASMATIC_LAVA.get(),100),200,4000,consumer);
        AlloyRecipeBuilder.alloy(FluidOutput.fromStack( new FluidStack(TiAcFluids.PLASMATIC_LAVA.get(),500)),3000)
                .addCatalyst(FluidIngredient.of(TiAcFluids.MOLTEN_IRIDIUM.get(),450))
                .addInput(FluidIngredient.of(TinkerFluids.enderSlime.get(),250))
                .addInput(FluidIngredient.of(TinkerFluids.moltenEnder.get(), 750))
                .addInput(TiAcFluids.GASEOUS_LAVA.get(),1000)
                .save(consumer,new ResourceLocation(folder+"_plasmatic_lava"));
        conditional = withCondition(consumer,modLoaded("mekanism"));
        fuel("antimatter",FluidIngredient.of(TiAcFluids.MOLTEN_ANTIMATTER.get(),10),1000,9999, conditional);
        AlloyRecipeBuilder.alloy(FluidOutput.fromStack( new FluidStack(TiAcFluids.OVER_HEATED_LAVA.get(),500)),1500)
                .addCatalyst(FluidIngredient.of(TinkerFluids.moltenRefinedGlowstone.get(), 90))
                .addInput(MekanismFluids.ETHENE.getFluid(), 250)
                .addInput(MekanismFluids.OXYGEN.getFluid(), 750)
                .addInput(Fluids.LAVA,1000)
                .save(conditional,new ResourceLocation(folder+"_over_heated_lava_mek"));
        AlloyRecipeBuilder.alloy(FluidOutput.fromStack( new FluidStack(TiAcFluids.GASEOUS_LAVA.get(),500)),2000)
                .addCatalyst(FluidIngredient.of(TinkerFluids.moltenRefinedObsidian.get(), 270))
                .addInput(MekanismFluids.URANIUM_HEXAFLUORIDE.getFluid(), 250)
                .addInput(MekanismFluids.HEAVY_WATER.getFluid(), 500)
                .addInput(TiAcFluids.OVER_HEATED_LAVA.get(),1000)
                .save(conditional,new ResourceLocation(folder+"_gaseous_lava_mek"));
        conditional = withCondition(consumer,modLoaded("mekanismgenerators"));
        AlloyRecipeBuilder.alloy(FluidOutput.fromStack( new FluidStack(TiAcFluids.PLASMATIC_LAVA.get(),500)),3000)
                .addCatalyst(FluidIngredient.of(TiAcFluids.MOLTEN_DENSIUM.get(), 360))
                .addCatalyst(FluidIngredient.of(TiAcFluids.MOLTEN_IRRADIUM.get(), 270))
                .addInput(GeneratorsFluids.DEUTERIUM.getFluid(), 500)
                .addInput(GeneratorsFluids.TRITIUM.getFluid(), 500)
                .addInput(TiAcFluids.GASEOUS_LAVA.get(),1000)
                .save(conditional,new ResourceLocation(folder+"_plasmatic_lava_mek"));
        conditional = withCondition(consumer,modLoaded("thermal"));
        fuel("pyrotheum",FluidIngredient.of(TiAcFluids.PYROTHEUM.get(),10),200,3000, conditional);
        AlloyRecipeBuilder.alloy(FluidOutput.fromStack( new FluidStack(TiAcFluids.PYROTHEUM.get(),500)),1320)
                .addInput(FluidIngredient.of(TinkerFluids.blazingBlood.get(),1250))
                .addInput(ForgeRegistries.FLUIDS.getValue(new ResourceLocation("thermal","refined_fuel")),1000)
                .addInput(ForgeRegistries.FLUIDS.getValue(new ResourceLocation("thermal","creosote")),750)
                .save(conditional,new ResourceLocation(folder+"_pyrotheum"));


        folder = namedFolder("bismuth");
        meltMaterial(TiAcTagkeys.Fluids.MOLTEN_BISMUTH,90,TiAcMaterialIds.BISMUTH,770,consumer,folder);
        melt1Ingot(TiAcFluids.MOLTEN_BISMUTH.get(),TiAcTagkeys.Items.BISMUTH_INGOT,770,consumer,folder);
        materialRecipe(TiAcMaterialIds.BISMUTH,Ingredient.of(TiAcTagkeys.Items.BISMUTH_INGOT),1,1,consumer,folder);
        folder = namedFolder("bismuthinite");
        materialRecipe(TiAcMaterialIds.BISMUTHINITE,Ingredient.of(TiAcItems.BISMUTHINITE.get()),1,1,consumer,folder);
        folder = namedFolder("disintegrate_crystal");
        materialRecipe(TiAcMaterialIds.DISINTEGRATE_CRYSTAL,Ingredient.of(TiAcItems.DISINTEGRATE_CRYSTAL.get()),1,1,consumer,folder);
        folder = namedFolder("resonance_crystal");
        materialRecipe(TiAcMaterialIds.RESONANCE_CRYSTAL,Ingredient.of(TiAcItems.RESONANCE_CRYSTAL.get()),1,1,consumer,folder);
        folder = namedFolder("voltaic_crystal");
        materialRecipe(TiAcMaterialIds.VOLTAIC_CRYSTAL,Ingredient.of(TiAcItems.VOLTAIC_CRYSTAL.get()),1,1,consumer,folder);
        folder = namedFolder("blaze_netherite");
        meltMaterial(TiAcFluids.MOLTEN_BLAZE_NETHERITE.get(),90,TiAcMaterialIds.BLAZE_NETHERITE,1480,consumer,folder);
        melt1Ingot(TiAcFluids.MOLTEN_BLAZE_NETHERITE.get(),TiAcItems.BLAZE_NETHERITE.get(),1480,consumer,folder);
        materialRecipe(TiAcMaterialIds.BLAZE_NETHERITE,Ingredient.of(TiAcItems.BLAZE_NETHERITE.get()),1,1,consumer,folder);
        ItemCastingRecipeBuilder.tableRecipe(TiAcItems.BLAZE_NETHERITE.get()).setFluid(TinkerFluids.blazingBlood.get(), 200).setCast(Tags.Items.INGOTS_NETHERITE,true).setCoolingTime(1500,200).save(consumer,new ResourceLocation(folder+"_made"));
        folder = namedFolder("iridium");
        materialRecipe(TiAcMaterialIds.IRIDIUM,Ingredient.of(TiAcItems.IRIDIUM_CHUNK.get()),3,1,consumer,folder);
        MeltingRecipeBuilder.melting(Ingredient.of(TiAcItems.IRIDIUM_CHUNK.get()),FluidOutput.fromTag(TiAcTagkeys.Fluids.MOLTEN_IRIDIUM,30),1375,30).save(consumer,new ResourceLocation(folder+"_melting_chunk"));
        ItemCastingRecipeBuilder.tableRecipe(TiAcItems.IRIDIUM_CHUNK.get()).setCoolingTime(10).setFluid(TiAcTagkeys.Fluids.MOLTEN_IRIDIUM,30).save(consumer,new ResourceLocation(folder+"_casting_chunk"));
        MeltingRecipeBuilder.melting(Ingredient.of( TiAcItems.IRIDIUM_LEAN_ORE.get()),FluidOutput.fromTag(TiAcTagkeys.Fluids.MOLTEN_IRIDIUM,90),1575,30).save(consumer,new ResourceLocation(folder+"_melting_ore"));
        meltMaterial(TiAcTagkeys.Fluids.MOLTEN_IRIDIUM,90,TiAcMaterialIds.IRIDIUM,1375,consumer,folder);
        conditional = withCondition(consumer,tagFilled(TiAcTagkeys.Items.IRIDIUM_INGOT));
        melt1Ingot(TiAcTagkeys.Fluids.MOLTEN_IRIDIUM,TiAcTagkeys.Items.IRIDIUM_INGOT,1375, conditional,folder);
        //AE2
        conditional = withCondition(consumer,tagFilled(ConventionTags.FLUIX_CRYSTAL));
        folder = namedFolder("fluix");
        materialRecipe(TiAcMaterialIds.AE2.FLUIX,Ingredient.of(ConventionTags.FLUIX_CRYSTAL),1,1, conditional,folder);
        conditional = withCondition(consumer,tagFilled(ConventionTags.CERTUS_QUARTZ));
        folder = namedFolder("certus_quartz");
        materialRecipe(TiAcMaterialIds.AE2.CERTUS,Ingredient.of(ConventionTags.CERTUS_QUARTZ),1,1, conditional,folder);
        //Mekanism
        conditional = withCondition(consumer,tagFilled(MekanismTags.Items.ALLOYS_ATOMIC));
        folder = namedFolder("alloy_atomic");
        materialRecipe(TiAcMaterialIds.Mekanism.ALLOY_ATOMIC,Ingredient.of(MekanismTags.Items.ALLOYS_ATOMIC),1,1, conditional,folder);
        conditional = withCondition(consumer,tagFilled(MekanismTags.Items.INGOTS_REFINED_GLOWSTONE));
        folder = namedFolder("refined_glowstone");
        meltMaterial(TinkerFluids.moltenRefinedGlowstone.get(), 90,TiAcMaterialIds.Mekanism.REFINED_GLOWSTONE,825, conditional,folder);
        materialRecipe(TiAcMaterialIds.Mekanism.REFINED_GLOWSTONE,Ingredient.of(MekanismTags.Items.INGOTS_REFINED_GLOWSTONE),1,1, conditional,folder);
        conditional = withCondition(consumer,tagFilled(MekanismTags.Items.NUGGETS_REFINED_GLOWSTONE));
        materialRecipe(TiAcMaterialIds.Mekanism.REFINED_GLOWSTONE,Ingredient.of(MekanismTags.Items.NUGGETS_REFINED_GLOWSTONE),9,1, conditional,folder);
        conditional = withCondition(consumer,tagFilled(MekanismTags.Items.STORAGE_BLOCKS_REFINED_GLOWSTONE),tagFilled(MekanismTags.Items.INGOTS_REFINED_OBSIDIAN));
        materialRecipe(TiAcMaterialIds.Mekanism.REFINED_GLOWSTONE,Ingredient.of(MekanismTags.Items.STORAGE_BLOCKS_REFINED_GLOWSTONE),MekanismTags.Items.INGOTS_REFINED_GLOWSTONE,1,9, conditional,folder);
        conditional = withCondition(consumer,tagFilled(MekanismTags.Items.INGOTS_REFINED_GLOWSTONE));
        folder = namedFolder("refined_obsidian");
        meltMaterial(TinkerFluids.moltenRefinedObsidian.get(), 90,TiAcMaterialIds.Mekanism.REFINED_OBSIDIAN,1475, conditional,folder);
        materialRecipe(TiAcMaterialIds.Mekanism.REFINED_OBSIDIAN,Ingredient.of(MekanismTags.Items.INGOTS_REFINED_OBSIDIAN),1,1, conditional,folder);
        conditional = withCondition(consumer,tagFilled(MekanismTags.Items.NUGGETS_REFINED_OBSIDIAN));
        materialRecipe(TiAcMaterialIds.Mekanism.REFINED_OBSIDIAN,Ingredient.of(MekanismTags.Items.NUGGETS_REFINED_OBSIDIAN),9,1, conditional,folder);
        conditional = withCondition(consumer,tagFilled(MekanismTags.Items.STORAGE_BLOCKS_REFINED_OBSIDIAN),tagFilled(MekanismTags.Items.INGOTS_REFINED_OBSIDIAN));
        materialRecipe(TiAcMaterialIds.Mekanism.REFINED_OBSIDIAN,Ingredient.of(MekanismTags.Items.STORAGE_BLOCKS_REFINED_OBSIDIAN),MekanismTags.Items.INGOTS_REFINED_OBSIDIAN,1,9, conditional,folder);
        conditional = withCondition(consumer,tagFilled(MekanismTags.Items.PELLETS_ANTIMATTER));
        folder = namedFolder("antimatter");
        meltMaterial(TiAcTagkeys.Fluids.MOLTEN_ANTIMATTER,250,TiAcMaterialIds.Mekanism.ANTIMATTER,3996, conditional,folder);
        melt1Slimeball(TiAcFluids.MOLTEN_ANTIMATTER.get(),MekanismTags.Items.PELLETS_ANTIMATTER,3996, conditional,folder);
        materialRecipe(TiAcMaterialIds.Mekanism.ANTIMATTER,Ingredient.of(MekanismTags.Items.PELLETS_ANTIMATTER),1,1, conditional,folder);
        folder = namedFolder("irradium");
        conditional = withCondition(consumer,modLoaded("mekanism"));
        melt1Ingot(TiAcFluids.MOLTEN_IRRADIUM.get(),TiAcItems.IRRADIUM_INGOT.get(),2750, conditional,folder);
        meltMaterial(TiAcFluids.MOLTEN_IRRADIUM.get(),90,TiAcMaterialIds.Mekanism.IRRADIUM,2750, conditional,folder);
        materialRecipe(TiAcMaterialIds.Mekanism.IRRADIUM,Ingredient.of(TiAcItems.IRRADIUM_INGOT.get()),1,1, conditional,folder);
        CombinerRecipeBuilder.combining(ItemStackIngredientCreator.INSTANCE.from(TinkerMaterials.manyullyn.getIngot()), ItemStackIngredientCreator.INSTANCE.from(MekanismItems.POLONIUM_PELLET),new ItemStack(TiAcItems.IRRADIUM_INGOT.get())).build(consumer,new ResourceLocation(folder+"_ingot_create"));
        folder = namedFolder("protocite");
        melt1Ingot(TiAcFluids.MOLTEN_PROTOCITE.get(),TiAcItems.PROTOCITE_PELLET.get(),2750, conditional,folder);
        meltMaterial(TiAcFluids.MOLTEN_PROTOCITE.get(),90,TiAcMaterialIds.Mekanism.PROTOCITE,2750, conditional,folder);
        materialRecipe(TiAcMaterialIds.Mekanism.PROTOCITE,Ingredient.of(TiAcItems.PROTOCITE_PELLET.get()),1,1, conditional,folder);
        CombinerRecipeBuilder.combining(ItemStackIngredientCreator.INSTANCE.from(TinkerMaterials.hepatizon.getIngot()), ItemStackIngredientCreator.INSTANCE.from(MekanismItems.PLUTONIUM_PELLET),new ItemStack(TiAcItems.PROTOCITE_PELLET.get())).build(consumer,new ResourceLocation(folder+"_ingot_create"));
        folder = namedFolder("densium");
        melt1Ingot(TiAcFluids.MOLTEN_DENSIUM.get(),TiAcItems.DENSIUM_INGOT.get(),1755, conditional,folder);
        meltMaterial(TiAcFluids.MOLTEN_DENSIUM.get(),90,TiAcMaterialIds.Mekanism.DENSIUM,1755, conditional,folder);
        materialRecipe(TiAcMaterialIds.Mekanism.DENSIUM,Ingredient.of(TiAcItems.DENSIUM_INGOT.get()),1,1, conditional,folder);
        AlloyRecipeBuilder.alloy(FluidOutput.fromStack(new FluidStack(TiAcFluids.MOLTEN_DENSIUM.get(),90)),1755)
                .addInput(TiAcFluids.MOLTEN_IRIDIUM.get(),90)
                .addInput(TinkerFluids.moltenOsmium.get(),180)
                .addInput(TinkerFluids.moltenRefinedObsidian.get(),450)
                .save(conditional,new ResourceLocation(folder+"_alloy"));
        folder = namedFolder("osgloglas");
        melt1Ingot(TiAcFluids.MOLTEN_OSGLOGLAS.get(),TiAcItems.OSGLOGLAS_INGOT.get(),1755, conditional,folder);
        meltMaterial(TiAcFluids.MOLTEN_OSGLOGLAS.get(),90,TiAcMaterialIds.Mekanism.OSGLOGLAS,1755, conditional,folder);
        materialRecipe(TiAcMaterialIds.Mekanism.OSGLOGLAS,Ingredient.of(TiAcItems.OSGLOGLAS_INGOT.get()),1,1, conditional,folder);
        AlloyRecipeBuilder.alloy(FluidOutput.fromStack(new FluidStack(TiAcFluids.MOLTEN_OSGLOGLAS.get(),90)),1755)
                .addInput(TinkerFluids.moltenOsmium.get(),90)
                .addInput(TinkerFluids.moltenRefinedObsidian.get(),180)
                .addInput(TinkerFluids.moltenRefinedGlowstone.get(),360)
                .save(conditional,new ResourceLocation(folder+"_alloy"));
        folder = namedFolder("neutronite");
        meltMaterial(TiAcFluids.MOLTEN_NEUTRONITE.get(),90,TiAcMaterialIds.Mekanism.NEUTRONITE,9273, conditional,folder);
        MeltingRecipeBuilder.melting(Ingredient.of(TiAcItems.NEUTRONITE_INGOT.get()),FluidOutput.fromFluid(TiAcFluids.MOLTEN_NEUTRONITE.get(),1),9273,1).save(conditional,new ResourceLocation(folder+"_melting_1mb"));
        ItemCastingRecipeBuilder.tableRecipe(TiAcItems.NEUTRONITE_INGOT.get()).setCoolingTime(1).setFluid(TiAcFluids.MOLTEN_NEUTRONITE.get(),1).save(conditional,new ResourceLocation(folder+"_casting_1mb"));
        materialRecipe(TiAcMaterialIds.Mekanism.NEUTRONITE,Ingredient.of(TiAcItems.NEUTRONITE_INGOT.get()),90,1, conditional,folder);
        //PnC
        conditional = withCondition(consumer,modLoaded("pneumaticcraft"));
        folder = namedFolder("pneumatic_steel");
        materialRecipe(TiAcMaterialIds.PnC.PNEUMATIC_STEEL,Ingredient.of(TiAcItems.PNEUMATIC_STEEL.get()),1,1, conditional,folder);
        //Thermal
        conditional = withCondition(consumer,modLoaded("thermal"));
        folder = namedFolder("basalz_signalum");
        materialRecipe(TiAcMaterialIds.Thermal.BASALZ_SIGNALUM,Ingredient.of(TiAcItems.BASALZ_SIGNALUM.get()),1,1, conditional,folder);
        melt1Ingot(TiAcFluids.MOLTEN_BASALZ_SIGNALUM.get(),TiAcItems.BASALZ_SIGNALUM.get(),995, conditional,folder);
        meltMaterial(TiAcFluids.MOLTEN_BASALZ_SIGNALUM.get(),90,TiAcMaterialIds.Thermal.BASALZ_SIGNALUM,995, conditional,folder);
        folder = namedFolder("blitz_lumium");
        materialRecipe(TiAcMaterialIds.Thermal.BLITZ_LUMIUM,Ingredient.of(TiAcItems.BLITZ_LUMIUM.get()),1,1, conditional,folder);
        cast1Ingot(TiAcFluids.MOLTEN_BILTZ_LUMIUM.get(),TiAcItems.BLITZ_LUMIUM.get(),995, conditional,folder);
        meltMaterial(TiAcFluids.MOLTEN_BILTZ_LUMIUM.get(),90,TiAcMaterialIds.Thermal.BLITZ_LUMIUM,995, conditional,folder);
        MeltingRecipeBuilder.melting(Ingredient.of(TiAcItems.BLITZ_LUMIUM.get()),FluidOutput.fromFluid(TinkerFluids.moltenLumium.get(), 30),1000,30).addByproduct(new FluidStack(TiAcFluids.MOLTEN_BILTZ_LUMIUM.get(),60)).save(conditional,new ResourceLocation(folder+"_melting_foundry"));
        folder = namedFolder("blizz_enderium");
        materialRecipe(TiAcMaterialIds.Thermal.BLIZZ_ENDERIUM,Ingredient.of(TiAcItems.BLIZZ_ENDERIUM.get()),1,1, conditional,folder);
        melt1Ingot(TiAcFluids.MOLTEN_BLIZZ_ENDERIUM.get(),TiAcItems.BLIZZ_ENDERIUM.get(),1640, conditional,folder);
        meltMaterial(TiAcFluids.MOLTEN_BLIZZ_ENDERIUM.get(),90,TiAcMaterialIds.Thermal.BLIZZ_ENDERIUM,1640, conditional,folder);
        folder = namedFolder("activated_chromatic_steel");
        materialRecipe(TiAcMaterialIds.Thermal.ACTIVATED_CHROMATIC_STEEL,Ingredient.of(TiAcItems.ACTIVATED_CHROMATIC_STEEL.get()),1,1, conditional,folder);
        melt1Plate(TiAcFluids.MOLTEN_ACTIVATED_CHROMATIC_STEEL.get(),TiAcItems.ACTIVATED_CHROMATIC_STEEL.get(),2720, conditional,folder);
        meltMaterial(TiAcFluids.MOLTEN_ACTIVATED_CHROMATIC_STEEL.get(),90,TiAcMaterialIds.Thermal.ACTIVATED_CHROMATIC_STEEL,2720, conditional,folder);
        AlloyRecipeBuilder.alloy(FluidOutput.fromStack(new FluidStack(TiAcFluids.MOLTEN_ACTIVATED_CHROMATIC_STEEL.get(),90)),2720)
                .addInput(TiAcFluids.MOLTEN_BASALZ_SIGNALUM.get(),270)
                .addInput(TiAcFluids.MOLTEN_BILTZ_LUMIUM.get(),270)
                .addInput(TiAcFluids.MOLTEN_BLIZZ_ENDERIUM.get(),270)
                .addInput(TiAcFluids.MOLTEN_BLAZE_NETHERITE.get(),270)
                .save(conditional,new ResourceLocation(folder+"_alloy"));
        //Common Integration
        folder = namedFolder("plastic");
        conditional =withCondition(consumer, tagFilled(TiAcTagkeys.Items.PLASTIC));
        materialRecipe(TiAcMaterialIds.CommonIntegration.PLASTIC,Ingredient.of(TiAcTagkeys.Items.PLASTIC),1,1,conditional,folder);
    }

    public void melt1B(Fluid fluid, ItemLike ingredient, int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),new FluidStack(fluid,1000),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(1000))).save(consumer, new  ResourceLocation(location+"_melting_1b"));
        ItemCastingRecipeBuilder.basinRecipe(ingredient).setFluid(FluidIngredient.of(new FluidStack(fluid,1000))).setCoolingTime(temperature,1000).save(consumer,new  ResourceLocation(location+"_casting_1b"));
    }
    public void melt1B(Fluid fluid, TagKey<Item> ingredient, int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),new FluidStack(fluid,1000),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(1000))).save(consumer, new  ResourceLocation(location+"_melting_1b"));
        ItemCastingRecipeBuilder.basinRecipe(ingredient).setFluid(FluidIngredient.of(new FluidStack(fluid,1000))).setCoolingTime(temperature,1000).save(consumer,new  ResourceLocation(location+"_casting_1b"));
    }
    public void melt9Gem(Fluid fluid,ItemLike ingredient,int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),new FluidStack(fluid,900),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(900))).save(consumer, new  ResourceLocation(location+"_melting_gem_block"));
        ItemCastingRecipeBuilder.basinRecipe(ingredient).setFluid(FluidIngredient.of(new FluidStack(fluid,900))).setCoolingTime(temperature,900).save(consumer,new  ResourceLocation(location+"_casting_gem_block"));
    }
    public void melt9Gem(Fluid fluid,TagKey<Item> ingredient,int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),new FluidStack(fluid,900),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(900))).save(consumer, new  ResourceLocation(location+"_melting_gem_block"));
        ItemCastingRecipeBuilder.basinRecipe(ingredient).setFluid(FluidIngredient.of(new FluidStack(fluid,900))).setCoolingTime(temperature,900).save(consumer,new  ResourceLocation(location+"_casting_gem_block"));
    }
    public void melt9Ingot(Fluid fluid,ItemLike ingredient,int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),new FluidStack(fluid,810),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(810))).save(consumer,new  ResourceLocation(location+"_melting_metal_block"));
        ItemCastingRecipeBuilder.basinRecipe(ingredient).setFluid(FluidIngredient.of(new FluidStack(fluid,810))).setCoolingTime(temperature,810).save(consumer,new  ResourceLocation(location+"_casting_metal_block"));
    }
    public void melt9Ingot(Fluid fluid,TagKey<Item> ingredient,int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),new FluidStack(fluid,810),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(810))).save(consumer,new  ResourceLocation(location+"_melting_metal_block"));
        ItemCastingRecipeBuilder.basinRecipe(ingredient).setFluid(FluidIngredient.of(new FluidStack(fluid,810))).setCoolingTime(temperature,810).save(consumer,new  ResourceLocation(location+"_casting_metal_block"));
    }
    public void melt1Slimeball(Fluid fluid,ItemLike ingredient,int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),new FluidStack(fluid,250),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(250))).save(consumer,new  ResourceLocation(location+"_melting_250mb"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setFluid(FluidIngredient.of(new FluidStack(fluid,250))).setCoolingTime(temperature,250).save(consumer,new  ResourceLocation(location+"_casting_250mb"));
    }
    public void melt1Slimeball(Fluid fluid,TagKey<Item> ingredient,int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),new FluidStack(fluid,250),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(250))).save(consumer,new  ResourceLocation(location+"_melting_250mb"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setFluid(FluidIngredient.of(new FluidStack(fluid,250))).setCoolingTime(temperature,250).save(consumer,new  ResourceLocation(location+"_casting_250mb"));
    }
    public void melt1Gem(Fluid fluid,ItemLike ingredient,int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),new FluidStack(fluid,100),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(100))).save(consumer,new  ResourceLocation(location+"_melting_gem"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,100).setFluid(FluidIngredient.of(new FluidStack(fluid,100))).setCast(GEM_MULTICAST,false).save(consumer,new  ResourceLocation(location+"_casting_gem_multi"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,100).setFluid(FluidIngredient.of(new FluidStack(fluid,100))).setCast(GEM_SINGLECAST,true).save(consumer,new  ResourceLocation(location+"_casting_gem_single"));
    }
    public void melt1Gem(Fluid fluid,TagKey<Item> ingredient,int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),new FluidStack(fluid,100),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(100))).save(consumer,new  ResourceLocation(location+"_melting_gem"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,100).setFluid(FluidIngredient.of(new FluidStack(fluid,100))).setCast(GEM_MULTICAST,false).save(consumer,new  ResourceLocation(location+"_casting_gem_multi"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,100).setFluid(FluidIngredient.of(new FluidStack(fluid,100))).setCast(GEM_SINGLECAST,true).save(consumer,new  ResourceLocation(location+"_casting_gem_single"));
    }
    public void melt1Ingot(Fluid fluid, ItemLike ingredient, int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),new FluidStack(fluid,90),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(90))).save(consumer,new  ResourceLocation(location+"_melting_ingot"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,90).setFluid(FluidIngredient.of(new FluidStack(fluid,90))).setCast(INGOT_MULTICAST,false).save(consumer,new  ResourceLocation(location+"_casting_ingot_single"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,90).setFluid(FluidIngredient.of(new FluidStack(fluid,90))).setCast(INGOT_SINGLECAST,true).save(consumer,new  ResourceLocation(location+"_casting_ingot_multi"));
    }
    public void melt1Plate(Fluid fluid, ItemLike ingredient, int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),new FluidStack(fluid,90),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(90))).save(consumer,new  ResourceLocation(location+"_melting_plate"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,90).setFluid(FluidIngredient.of(new FluidStack(fluid,90))).setCast(PLATE_MULTICAST,false).save(consumer,new  ResourceLocation(location+"_casting_plate_single"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,90).setFluid(FluidIngredient.of(new FluidStack(fluid,90))).setCast(PLATE_SINGLECAST,true).save(consumer,new  ResourceLocation(location+"_casting_plate_multi"));
    }
    public void melt1Ingot(Fluid fluid, TagKey<Item> ingredient, int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),new FluidStack(fluid,90),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(90))).save(consumer,new  ResourceLocation(location+"_melting_ingot"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,90).setFluid(FluidIngredient.of(new FluidStack(fluid,90))).setCast(INGOT_MULTICAST,false).save(consumer,new  ResourceLocation(location+"_casting_ingot_single"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,90).setFluid(FluidIngredient.of(new FluidStack(fluid,90))).setCast(INGOT_SINGLECAST,true).save(consumer,new  ResourceLocation(location+"_casting_ingot_multi"));
    }
    public void melt1Ingot(TagKey<Fluid> fluid, TagKey<Item> ingredient, int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),FluidOutput.fromTag(fluid,90),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(90))).save(consumer,new  ResourceLocation(location+"_melting_ingot"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,90).setFluid(FluidIngredient.of(fluid,90)).setCast(INGOT_MULTICAST,false).save(consumer,new  ResourceLocation(location+"_casting_ingot_single"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,90).setFluid(FluidIngredient.of(fluid,90)).setCast(INGOT_SINGLECAST,true).save(consumer,new  ResourceLocation(location+"_casting_ingot_multi"));
    }
    public void cast1Ingot(Fluid fluid, ItemLike ingredient, int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,90).setFluid(FluidIngredient.of(new FluidStack(fluid,90))).setCast(INGOT_MULTICAST,false).save(consumer,new  ResourceLocation(location+"_casting_ingot_single"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,90).setFluid(FluidIngredient.of(new FluidStack(fluid,90))).setCast(INGOT_SINGLECAST,true).save(consumer,new  ResourceLocation(location+"_casting_ingot_multi"));
    }


    public void meltMaterial(TagKey<Fluid> fluid,int amount, MaterialVariantId id, int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MaterialMeltingRecipeBuilder.material(id,temperature, FluidOutput.fromTag(fluid,amount)).save(consumer, new  ResourceLocation(location+"_material_melt"));
        MaterialFluidRecipeBuilder.material(id).setFluid(fluid,amount).setTemperature(temperature).save(consumer, new  ResourceLocation(location+"_material_cast"));
    }
    public void meltMaterial(Fluid fluid,int amount, MaterialVariantId id, int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MaterialMeltingRecipeBuilder.material(id,temperature, FluidOutput.fromFluid(fluid,amount)).save(consumer, new  ResourceLocation(location+"_material_melt"));
        MaterialFluidRecipeBuilder.material(id).setTemperature(temperature).setFluid(FluidIngredient.of(new FluidStack(fluid,amount))).save(consumer, new  ResourceLocation(location+"_material_cast"));
    }
    public void materialRecipe(MaterialVariantId id, Ingredient ingredient, int needed, int value, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MaterialRecipeBuilder.materialRecipe(id).setIngredient(ingredient).setNeeded(needed).setValue(value).save(consumer,new  ResourceLocation(location+"_material"+needed+value));
    }
    public void materialRecipe(MaterialVariantId id, Ingredient ingredient,TagKey<Item> leftOver, int needed, int value, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MaterialRecipeBuilder.materialRecipe(id).setIngredient(ingredient).setNeeded(needed).setValue(value).setLeftover(ItemOutput.fromTag(leftOver)).save(consumer,new  ResourceLocation(location+"_material"+needed+value));
    }

    public void fuel(String name,FluidIngredient ingredient,int duration,int temp,Consumer<FinishedRecipe> consumer){
        MeltingFuelBuilder.fuel(ingredient,duration,temp).save(consumer,new ResourceLocation(namedFolder("fuel")+"_"+name+"fuel"));
    }

    public static ICondition modLoaded(String modId){
        return new OrCondition(ConfigEnabledCondition.FORCE_INTEGRATION_MATERIALS,new ModLoadedCondition(modId));
    }
    public static ICondition tagFilled(TagKey<Item> tagKey){
        return new OrCondition(ConfigEnabledCondition.FORCE_INTEGRATION_MATERIALS,new TagFilledCondition<>(tagKey));
    }


    @Override
    public String getModId() {
        return TinkersAdvanced.MODID;
    }
}
