package com.c2h6s.tinkers_advanced.tools.data.providers;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.tools.data.enums.TiAcTEnumModifier;
import com.c2h6s.tinkers_advanced.materials.init.TiAcMeItems;
import com.c2h6s.tinkers_advanced.tools.init.TiAcTItems;
import me.desht.pneumaticcraft.common.core.ModBlocks;
import me.desht.pneumaticcraft.common.core.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
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
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.casting.material.MaterialFluidRecipeBuilder;
import slimeknights.tconstruct.library.recipe.fuel.MeltingFuelBuilder;
import slimeknights.tconstruct.library.recipe.material.MaterialRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe;
import slimeknights.tconstruct.library.recipe.melting.MaterialMeltingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.tinkerstation.building.ToolBuildingRecipeBuilder;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.world.TinkerWorld;

import java.util.Arrays;
import java.util.function.Consumer;

import static com.c2h6s.tinkers_advanced.registery.TiAcModifiers.*;

public class TiAcTRecipeProvider extends RecipeProvider implements ISmelteryRecipeHelper {
    public TiAcTRecipeProvider(PackOutput generator) {
        super(generator);
    }
    public static final TagKey<Item> GEM_MULTICAST = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), TConstruct.getResource("casts/multi_use/gem"));
    public static final TagKey<Item> GEM_SINGLECAST = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), TConstruct.getResource("casts/single_use/gem"));
    public static final TagKey<Item> INGOT_MULTICAST = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), TConstruct.getResource("casts/multi_use/ingot"));
    public static final TagKey<Item> INGOT_SINGLECAST = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), TConstruct.getResource("casts/single_use/ingot"));
    public static final TagKey<Item> PLATE_MULTICAST = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), TConstruct.getResource("casts/multi_use/plate"));
    public static final TagKey<Item> PLATE_SINGLECAST = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), TConstruct.getResource("casts/single_use/plate"));
    public static final TagKey<Item> NUGGET_MULTICAST = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), TConstruct.getResource("casts/multi_use/nugget"));
    public static final TagKey<Item> NUGGET_SINGLECAST = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), TConstruct.getResource("casts/single_use/nugget"));

    public static final ResourceLocation baseFolder = new ResourceLocation(TinkersAdvanced.MODID,"materials/");
    public static ResourceLocation namedFolder(String name){
        return ResourceLocation.tryParse(baseFolder+name+"/"+name);
    }
    public static ResourceLocation modifierFolder(String name){
        return ResourceLocation.tryParse(TinkersAdvanced.getLocation("modifiers/")+name);
    }
    public static ResourceLocation salvageFolder(String name){
        return ResourceLocation.tryParse(TinkersAdvanced.getLocation("modifiers/salvage/")+name);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ResourceLocation folder;
        Consumer<FinishedRecipe> conditional;
        folder = namedFolder("mixc");
        ToolBuildingRecipeBuilder.toolBuildingRecipe(TiAcTItems.MATTER_MANIPULATOR.get()).save(consumer, new ResourceLocation(folder + "/matter_manipulator"));

        Arrays.stream(TiAcTEnumModifier.values()).toList().forEach(enumModifier -> {
            Consumer<FinishedRecipe> conditionalConsumer = enumModifier.condition != null ? withCondition(consumer, enumModifier.condition) : consumer;
            enumModifier.builder.save(conditionalConsumer, modifierFolder(enumModifier.id.getPath()));
            enumModifier.builder.saveSalvage(conditionalConsumer, salvageFolder(enumModifier.id.getPath()));
        });
        ModifierRecipeBuilder builder = ModifierRecipeBuilder.modifier(EXTRA_CAPACITY.getId())
                .addInput(Items.REDSTONE)
                .addInput(Items.HONEYCOMB)
                .addInput(Items.REDSTONE)
                .addInput(Items.COPPER_INGOT)
                .addInput(Items.COPPER_INGOT)
                .allowCrystal()
                .setTools(Ingredient.of(TiAcTItems.ELECTRON_TUNER.asItem()))
                .setSlots(SlotType.UPGRADE, 1)
                .setMaxLevel(255);
        builder.save(consumer,modifierFolder(EXTRA_CAPACITY.getId().getPath()));
        builder.saveSalvage(consumer,salvageFolder(EXTRA_CAPACITY.getId().getPath()));

        builder = ModifierRecipeBuilder.modifier(COMPRESSED_AIR_GENERATOR.getId())
                .addInput(TiAcMeItems.DISINTEGRATE_CRYSTAL.get())
                .addInput(ModItems.REINFORCED_AIR_CANISTER.get())
                .addInput(ModBlocks.PNEUMATIC_DYNAMO.get())
                .allowCrystal()
                .setTools(Ingredient.of(TiAcTItems.ELECTRON_TUNER.asItem()))
                .setSlots(SlotType.ABILITY, 1)
                .setMaxLevel(1);
        builder.save(consumer,modifierFolder(COMPRESSED_AIR_GENERATOR.getId().getPath()));
        builder.saveSalvage(consumer,salvageFolder(COMPRESSED_AIR_GENERATOR.getId().getPath()));

        builder = ModifierRecipeBuilder.modifier(PLAYER_LOCATING.getId())
                .addInput(TiAcMeItems.RESONANCE_CRYSTAL.get())
                .addInput(TiAcMeItems.DISINTEGRATE_CRYSTAL.get())
                .addInput(TiAcMeItems.VOLTAIC_CRYSTAL.get())
                .addInput(TinkerFluids.moltenEnder.asItem())
                .addInput(TinkerFluids.enderSlime.asItem())
                .allowCrystal()
                .setTools(Ingredient.of(TiAcTItems.ELECTRON_TUNER.asItem()))
                .setSlots(SlotType.ABILITY, 1)
                .setMaxLevel(1);
        builder.save(consumer,modifierFolder(PLAYER_LOCATING.getId().getPath()));
        builder.saveSalvage(consumer,salvageFolder(PLAYER_LOCATING.getId().getPath()));

        builder = ModifierRecipeBuilder.modifier(ModifierIds.arrowPierce)
                .addInput(Items.POINTED_DRIPSTONE).addInput(Items.POINTED_DRIPSTONE).addInput(Items.POINTED_DRIPSTONE)
                .addInput(TinkerWorld.earthGeode.asItem()).addInput(Items.ECHO_SHARD)
                .setSlots(SlotType.UPGRADE,1)
                .setTools(Ingredient.of(TiAcTItems.IONIZED_CANNON.asItem()));
        builder.save(consumer,modifierFolder(ModifierIds.arrowPierce.getPath()));
        builder.saveSalvage(consumer,salvageFolder(ModifierIds.arrowPierce.getPath()));


        conditional = withCondition(consumer, modLoaded("createutilities"));
        builder = ModifierRecipeBuilder.modifier(PLAYER_LOCATING.getId())
                .addInput(ForgeRegistries.ITEMS.getValue(new ResourceLocation("createutilities", "graviton_tube")))
                .addInput(TiAcMeItems.VOLTAIC_CRYSTAL.get())
                .addInput(ForgeRegistries.ITEMS.getValue(new ResourceLocation("createutilities", "graviton_tube")))
                .allowCrystal()
                .setTools(Ingredient.of(TiAcTItems.ELECTRON_TUNER.asItem()))
                .setSlots(SlotType.ABILITY, 1)
                .setMaxLevel(1);
        builder.save(conditional,modifierFolder(PLAYER_LOCATING.getId().getPath()+"alter"));
        builder.saveSalvage(conditional,salvageFolder(PLAYER_LOCATING.getId().getPath()+"alter"));

        builder = ModifierRecipeBuilder.modifier(ENERGY_DISTRIBUTOR.getId())
                .addInput(Items.REDSTONE)
                .addInput(Items.REPEATER)
                .addInput(Items.REDSTONE)
                .addInput(Items.REPEATER)
                .addInput(Items.COMPARATOR)
                .allowCrystal()
                .setTools(Ingredient.of(TiAcTItems.ELECTRON_TUNER.asItem()))
                .setSlots(SlotType.ABILITY, 1)
                .setMaxLevel(1);
        builder.save(consumer,modifierFolder(ENERGY_DISTRIBUTOR.getId().getPath()));
        builder.saveSalvage(consumer,salvageFolder(ENERGY_DISTRIBUTOR.getId().getPath()));

        builder = ModifierRecipeBuilder.modifier(ENERGY_BIN.getId())
                .addInput(Items.REDSTONE)
                .addInput(Items.LAVA_BUCKET)
                .allowCrystal()
                .setTools(Ingredient.of(TiAcTItems.ELECTRON_TUNER.asItem()))
                .setSlots(SlotType.ABILITY, 1)
                .setMaxLevel(1);
        builder.save(consumer,modifierFolder(ENERGY_BIN.getId().getPath()));
        builder.saveSalvage(consumer,salvageFolder(ENERGY_BIN.getId().getPath()));
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
    public void melt9Ingot(TagKey<Fluid> fluid,ItemLike ingredient,int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),FluidOutput.fromTag(fluid,810),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(810))).save(consumer,new  ResourceLocation(location+"_melting_metal_block"));
        ItemCastingRecipeBuilder.basinRecipe(ingredient).setFluid(FluidIngredient.of(fluid,810)).setCoolingTime(temperature,810).save(consumer,new  ResourceLocation(location+"_casting_metal_block"));
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
    public void melt1Plate(TagKey<Fluid> fluid, Item ingredient, int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),FluidOutput.fromTag(fluid,90),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(90))).save(consumer,new  ResourceLocation(location+"_melting_plate"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,90).setFluid(FluidIngredient.of(FluidIngredient.of(fluid,90))).setCast(PLATE_MULTICAST,false).save(consumer,new  ResourceLocation(location+"_casting_plate_single"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,90).setFluid(FluidIngredient.of(FluidIngredient.of(fluid,90))).setCast(PLATE_SINGLECAST,true).save(consumer,new  ResourceLocation(location+"_casting_plate_multi"));
    }
    public void melt1Ingot(TagKey<Fluid> fluid, Item ingredient, int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),FluidOutput.fromTag(fluid,90),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(90))).save(consumer,new  ResourceLocation(location+"_melting_ingot"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,90).setFluid(FluidIngredient.of(FluidIngredient.of(fluid,90))).setCast(INGOT_MULTICAST,false).save(consumer,new  ResourceLocation(location+"_casting_ingot_single"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,90).setFluid(FluidIngredient.of(FluidIngredient.of(fluid,90))).setCast(INGOT_SINGLECAST,true).save(consumer,new  ResourceLocation(location+"_casting_ingot_multi"));
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

    public void melt1Nugget(Fluid fluid, TagKey<Item> ingredient, int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),new FluidStack(fluid,10),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(10))).save(consumer,new  ResourceLocation(location+"_melting_nugget"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,10).setFluid(FluidIngredient.of(new FluidStack(fluid,10))).setCast(NUGGET_MULTICAST,false).save(consumer,new  ResourceLocation(location+"_casting_nugget_single"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,10).setFluid(FluidIngredient.of(new FluidStack(fluid,10))).setCast(NUGGET_SINGLECAST,true).save(consumer,new  ResourceLocation(location+"_casting_nugget_multi"));
    }
    public void melt1Nugget(TagKey<Fluid> fluid, TagKey<Item> ingredient, int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        MeltingRecipeBuilder.melting(Ingredient.of(ingredient),FluidOutput.fromTag(fluid,10),temperature, IMeltingRecipe.calcTime(temperature, IMeltingRecipe.calcTimeFactor(10))).save(consumer,new  ResourceLocation(location+"_melting_nugget"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,10).setFluid(FluidIngredient.of(fluid,10)).setCast(NUGGET_MULTICAST,false).save(consumer,new  ResourceLocation(location+"_casting_nugget_single"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,10).setFluid(FluidIngredient.of(fluid,10)).setCast(NUGGET_SINGLECAST,true).save(consumer,new  ResourceLocation(location+"_casting_nugget_multi"));
    }
    public void cast1Nugget(Fluid fluid, ItemLike ingredient, int temperature, Consumer<FinishedRecipe> consumer, ResourceLocation location){
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,10).setFluid(FluidIngredient.of(new FluidStack(fluid,10))).setCast(NUGGET_MULTICAST,false).save(consumer,new  ResourceLocation(location+"_casting_nugget_single"));
        ItemCastingRecipeBuilder.tableRecipe(ingredient).setCoolingTime(temperature,10).setFluid(FluidIngredient.of(new FluidStack(fluid,10))).setCast(NUGGET_SINGLECAST,true).save(consumer,new  ResourceLocation(location+"_casting_nugget_multi"));
    }

    public void tableNugget(ItemLike ingot,ItemLike nugget,Ingredient ingotIng,Ingredient nuggetIng,Consumer<FinishedRecipe> consumer,ResourceLocation location){
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ingot,1).requires(nuggetIng,9).unlockedBy(getHasName(nugget),has(nugget)).save(consumer,new ResourceLocation(location+"to_ingot"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,nugget,9).requires(ingotIng).unlockedBy(getHasName(ingot),has(ingot)).save(consumer,new ResourceLocation(location+"to_nugget"));
    }
    public void tableNugget(ItemLike ingot,ItemLike nugget,Consumer<FinishedRecipe> consumer,ResourceLocation location){
        this.tableNugget(ingot,nugget,Ingredient.of(ingot),Ingredient.of(nugget),consumer,location);
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
