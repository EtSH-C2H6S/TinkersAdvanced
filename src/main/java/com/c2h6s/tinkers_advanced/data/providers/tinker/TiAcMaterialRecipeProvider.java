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
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.mantle.recipe.helper.TagEmptyCondition;
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

    public static final ResourceLocation baseFolder = new ResourceLocation(TinkersAdvanced.MODID,"materials/");
    public static ResourceLocation namedFolder(String name){
        return ResourceLocation.tryParse(baseFolder+name+"/"+name);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ResourceLocation folder;
        Consumer<FinishedRecipe> Conditional;

        folder = namedFolder("fuel");
        Conditional = withCondition(consumer,modLoaded("mekanism"));
        fuel("fusion_plasma",FluidIngredient.of(TiAcFluids.FUSION_PLASMA.get(),50),100,3840,Conditional);
        AlloyRecipeBuilder.alloy(FluidOutput.fromStack( new FluidStack(TiAcFluids.FUSION_PLASMA.get(),500)),1440).addCatalyst(FluidIngredient.of(TinkerFluids.blazingBlood.get(),1000)).addInput(GeneratorsFluids.FUSION_FUEL.getFluidStack(1000)).save(Conditional,new ResourceLocation(folder+"_fusion_alloy"));
        fuel("antimatter",FluidIngredient.of(TiAcFluids.MOLTEN_ANTIMATTER.get(),10),1000,9999,Conditional);

        folder = namedFolder("bismuth");
        meltMaterial(TiAcTagkeys.Fluids.MOLTEN_BISMUTH,90,TiAcMaterialIds.BISMUTH,770,consumer,folder);
        melt1Ingot(TiAcFluids.MOLTEN_BISMUTH.get(),TiAcTagkeys.Items.BISMUTH_INGOT,770,consumer,folder);
        materialRecipe(TiAcMaterialIds.BISMUTH,Ingredient.of(TiAcTagkeys.Items.BISMUTH_INGOT),1,1,consumer,folder);
        folder = namedFolder("bismuthinite");
        materialRecipe(TiAcMaterialIds.BISMUTHINITE,Ingredient.of(TiAcItems.BISMUTHINITE.get()),1,1,consumer,folder);
        //AE2
        Conditional = withCondition(consumer,new NotCondition(new TagEmptyCondition<>(ConventionTags.FLUIX_CRYSTAL)));
        folder = namedFolder("fluix");
        materialRecipe(TiAcMaterialIds.AE2.FLUIX,Ingredient.of(ConventionTags.FLUIX_CRYSTAL),1,1, Conditional,folder);
        Conditional = withCondition(consumer,new NotCondition(new TagEmptyCondition<>(ConventionTags.CERTUS_QUARTZ)));
        folder = namedFolder("certus_quartz");
        materialRecipe(TiAcMaterialIds.AE2.CERTUS,Ingredient.of(ConventionTags.CERTUS_QUARTZ),1,1, Conditional,folder);
        //Mekanism
        Conditional = withCondition(consumer,new NotCondition(new TagEmptyCondition<>(MekanismTags.Items.ALLOYS_ATOMIC)));
        folder = namedFolder("alloy_atomic");
        materialRecipe(TiAcMaterialIds.Mekanism.ALLOY_ATOMIC,Ingredient.of(MekanismTags.Items.ALLOYS_ATOMIC),1,1, Conditional,folder);
        Conditional = withCondition(consumer,new NotCondition(new TagEmptyCondition<>(MekanismTags.Items.INGOTS_REFINED_GLOWSTONE)));
        folder = namedFolder("refined_glowstone");
        meltMaterial(TinkerFluids.moltenRefinedGlowstone.get(), 90,TiAcMaterialIds.Mekanism.REFINED_GLOWSTONE,825,Conditional,folder);
        materialRecipe(TiAcMaterialIds.Mekanism.REFINED_GLOWSTONE,Ingredient.of(MekanismTags.Items.INGOTS_REFINED_GLOWSTONE),1,1, Conditional,folder);
        Conditional = withCondition(consumer,new NotCondition(new TagEmptyCondition<>(MekanismTags.Items.NUGGETS_REFINED_GLOWSTONE)));
        materialRecipe(TiAcMaterialIds.Mekanism.REFINED_GLOWSTONE,Ingredient.of(MekanismTags.Items.NUGGETS_REFINED_GLOWSTONE),9,1, Conditional,folder);
        Conditional = withCondition(consumer,new NotCondition(new TagEmptyCondition<>(MekanismTags.Items.STORAGE_BLOCKS_REFINED_GLOWSTONE)),new NotCondition(new TagEmptyCondition<>(MekanismTags.Items.INGOTS_REFINED_OBSIDIAN)));
        materialRecipe(TiAcMaterialIds.Mekanism.REFINED_GLOWSTONE,Ingredient.of(MekanismTags.Items.STORAGE_BLOCKS_REFINED_GLOWSTONE),MekanismTags.Items.INGOTS_REFINED_GLOWSTONE,1,9, Conditional,folder);
        Conditional = withCondition(consumer,new NotCondition(new TagEmptyCondition<>(MekanismTags.Items.INGOTS_REFINED_GLOWSTONE)));
        folder = namedFolder("refined_obsidian");
        meltMaterial(TinkerFluids.moltenRefinedObsidian.get(), 90,TiAcMaterialIds.Mekanism.REFINED_OBSIDIAN,1475,Conditional,folder);
        materialRecipe(TiAcMaterialIds.Mekanism.REFINED_OBSIDIAN,Ingredient.of(MekanismTags.Items.INGOTS_REFINED_OBSIDIAN),1,1, Conditional,folder);
        Conditional = withCondition(consumer,new NotCondition(new TagEmptyCondition<>(MekanismTags.Items.NUGGETS_REFINED_OBSIDIAN)));
        materialRecipe(TiAcMaterialIds.Mekanism.REFINED_OBSIDIAN,Ingredient.of(MekanismTags.Items.NUGGETS_REFINED_OBSIDIAN),9,1, Conditional,folder);
        Conditional = withCondition(consumer,new NotCondition(new TagEmptyCondition<>(MekanismTags.Items.STORAGE_BLOCKS_REFINED_OBSIDIAN)),new NotCondition(new TagEmptyCondition<>(MekanismTags.Items.INGOTS_REFINED_OBSIDIAN)));
        materialRecipe(TiAcMaterialIds.Mekanism.REFINED_OBSIDIAN,Ingredient.of(MekanismTags.Items.STORAGE_BLOCKS_REFINED_OBSIDIAN),MekanismTags.Items.INGOTS_REFINED_OBSIDIAN,1,9, Conditional,folder);
        Conditional = withCondition(consumer,new NotCondition(new TagEmptyCondition<>(MekanismTags.Items.PELLETS_ANTIMATTER)));
        folder = namedFolder("antimatter");
        meltMaterial(TiAcTagkeys.Fluids.MOLTEN_ANTIMATTER,250,TiAcMaterialIds.Mekanism.ANTIMATTER,1490,Conditional,folder);
        melt1Slimeball(TiAcFluids.MOLTEN_ANTIMATTER.get(),MekanismTags.Items.PELLETS_ANTIMATTER,1490,Conditional,folder);
        materialRecipe(TiAcMaterialIds.Mekanism.ANTIMATTER,Ingredient.of(MekanismTags.Items.PELLETS_ANTIMATTER),1,1,Conditional,folder);
        folder = namedFolder("irradium");
        Conditional = withCondition(consumer,modLoaded("mekanism"));
        melt1Ingot(TiAcFluids.MOLTEN_IRRADIUM.get(),TiAcItems.IRRADIUM_INGOT.get(),2250,Conditional,folder);
        meltMaterial(TiAcFluids.MOLTEN_IRRADIUM.get(),90,TiAcMaterialIds.Mekanism.IRRADIUM,2250,Conditional,folder);
        materialRecipe(TiAcMaterialIds.Mekanism.IRRADIUM,Ingredient.of(TiAcItems.IRRADIUM_INGOT.get()),1,1, Conditional,folder);
        CombinerRecipeBuilder.combining(ItemStackIngredientCreator.INSTANCE.from(TinkerMaterials.manyullyn.getIngot()), ItemStackIngredientCreator.INSTANCE.from(MekanismItems.POLONIUM_PELLET),new ItemStack(TiAcItems.IRRADIUM_INGOT.get())).build(consumer,new ResourceLocation(folder+"_ingot_create"));
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
    public void melt1Ingot(Fluid fluid, TagKey<Item> ingredient, int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),new FluidStack(fluid,90),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(90))).save(consumer,new  ResourceLocation(location+"_melting_ingot"));
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
        MeltingFuelBuilder.fuel(ingredient,duration,temp).save(consumer,new ResourceLocation(namedFolder("fuel")+"-"+name));
    }

    public static ICondition modLoaded(String modId){
        return new OrCondition(ConfigEnabledCondition.FORCE_INTEGRATION_MATERIALS,new ModLoadedCondition(modId));
    }
    public static ICondition tagEmpty(TagKey<Item> tagKey){
        return new OrCondition(ConfigEnabledCondition.FORCE_INTEGRATION_MATERIALS,new NotCondition(new TagEmptyCondition<Item>(tagKey)));
    }


    @Override
    public String getModId() {
        return TinkersAdvanced.MODID;
    }
}
