package com.c2h6s.tinkers_advanced.core.library.registry;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.core.init.TiAcCrConditions;
import com.c2h6s.tinkers_advanced.core.library.data.MaterialModifiersObject;
import com.c2h6s.tinkers_advanced.core.library.data.MaterialStatsObject;
import lombok.Getter;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.common.crafting.conditions.AndCondition;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;
import slimeknights.mantle.recipe.data.IRecipeHelper;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.mantle.recipe.ingredient.FluidIngredient;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipeBuilder;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.casting.material.MaterialFluidRecipeBuilder;
import slimeknights.tconstruct.library.recipe.material.MaterialRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.MaterialMeltingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.stats.PlatingMaterialStats;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static slimeknights.tconstruct.fluids.block.BurningLiquidBlock.createBurning;

public class SimpleMaterialObject {
    public SimpleMaterialObject(String name, String nameSpace, @Nullable FluidObject<? extends ForgeFlowingFluid> fluidObject, boolean isUnit,
                                @Nullable ItemObject<? extends Item> itemObject, MaterialInfo materialInfo, ICondition condition,
                                boolean isSimpleModel) {
        this.name = name;
        this.nameSpace = nameSpace;
        this.fluidObject = fluidObject;
        this.itemObject = itemObject;
        this.materialInfo = materialInfo;
        this.condition = condition;
        this.isUnit = isUnit;
        this.isSimpleModel = isSimpleModel;
    }

    @Getter
    private final String name;
    @Getter
    private final String nameSpace;
    @Getter
    private boolean isSimpleModel;

    //常量
    public final ResourceLocation baseFolder(){
        return new ResourceLocation(this.nameSpace,"materials/");
    }
    public ResourceLocation getFolder(){
        return ResourceLocation.tryParse(baseFolder()+name+"/"+name);
    }
    public MaterialId getMaterialId(){
        return new MaterialId(TinkersAdvanced.getLocation(name));
    }

    //注册物
    @Getter
    private final @Nullable FluidObject<? extends ForgeFlowingFluid> fluidObject;
    @Getter
    private final @Nullable ItemObject<? extends Item> itemObject;

    //配方相关
    @Getter
    private @Nullable ICondition condition;
    @Getter
    private boolean isUnit;
    @Getter
    private MaterialRecipeInfo recipeInfo;

    //材料相关
    @Getter
    private final MaterialInfo materialInfo;


    public void assembleRecipes(Consumer<FinishedRecipe> consumer, IRecipeHelper helper){
        if (this.recipeInfo==null) return;
        Consumer<FinishedRecipe> withCondition = helper.withCondition(consumer,condition);
        if (recipeInfo.hasMolten()&&recipeInfo.hasItem()){
            MeltingRecipeBuilder.melting(recipeInfo.getItemIng(), recipeInfo.getFluidOutput(),recipeInfo.getMeltTemp(),1)
                    .save(withCondition,new ResourceLocation(getFolder()+"_melting_"+recipeInfo.getFluidAmount()+"mb"));
            recipeInfo.getCastItemOptional().ifPresentOrElse(castItemObject -> {

                var recipeBuilder = recipeInfo.isBasin ? ItemCastingRecipeBuilder.basinRecipe(recipeInfo.getItemOutput()) :
                        ItemCastingRecipeBuilder.tableRecipe(recipeInfo.getItemOutput());
                recipeBuilder.setCast(castItemObject.getMultiUseTag(), false)
                        .setFluid(recipeInfo.getFluidIng()).setCoolingTime(recipeInfo.meltTemp, recipeInfo.fluidAmount)
                        .save(withCondition, new ResourceLocation(getFolder() + "_casting_multi_" + recipeInfo.getFluidAmount() + "mb"));

                recipeBuilder = recipeInfo.isBasin ? ItemCastingRecipeBuilder.basinRecipe(recipeInfo.getItemOutput()) :
                        ItemCastingRecipeBuilder.tableRecipe(recipeInfo.getItemOutput());
                recipeBuilder.setCast(castItemObject.getSingleUseTag(), true)
                        .setFluid(recipeInfo.getFluidIng()).setCoolingTime(recipeInfo.meltTemp, recipeInfo.fluidAmount)
                        .save(withCondition, new ResourceLocation(getFolder() + "_casting_single_" + recipeInfo.getFluidAmount() + "mb"));
            },()->{
                var recipeBuilder = recipeInfo.isBasin?ItemCastingRecipeBuilder.basinRecipe(recipeInfo.getItemOutput()):
                        ItemCastingRecipeBuilder.tableRecipe(recipeInfo.getItemOutput());
                recipeBuilder.setFluid(recipeInfo.getFluidIng()).setCoolingTime(recipeInfo.meltTemp, recipeInfo.fluidAmount)
                        .save(withCondition, new ResourceLocation(getFolder()+"_casting_none_"+recipeInfo.getFluidAmount()+"mb"));
            });
            if (isUnit) {
                MaterialMeltingRecipeBuilder.material(getMaterialId(), recipeInfo.getMeltTemp(), recipeInfo.getFluidOutput())
                        .save(withCondition, new ResourceLocation(getFolder() + "_material_melting"));
                MaterialFluidRecipeBuilder.material(getMaterialId()).setFluid(recipeInfo.getFluidIng()).setTemperature(recipeInfo.getMeltTemp())
                        .save(withCondition, new ResourceLocation(getFolder() + "_material_fluid"));
            }
        }
        if (recipeInfo.hasItem()) {
            var builder = MaterialRecipeBuilder.materialRecipe(getMaterialId()).setIngredient(recipeInfo.getItemIng())
                    .setNeeded(recipeInfo.getRequiredValue()).setValue(recipeInfo.getMaterialValue());
            if (recipeInfo.hasLeftOver()) {
                builder.setLeftover(recipeInfo.getLeftoverOutput());
            }
            builder.save(withCondition,
                    new ResourceLocation(getFolder() + "_material_" + recipeInfo.getMaterialValue() + recipeInfo.getRequiredValue()));
        }
        for (int i =0;i<recipeInfo.alloyRecipes.size();i++){
            var alloyRecipeBuilder = recipeInfo.alloyRecipes.get(i);
            alloyRecipeBuilder.save(withCondition, new ResourceLocation(getFolder() + "_material_alloy_"+i));
        }
    }

    protected void mergeCondition(ICondition condition){
        if (this.condition==null) this.condition = condition;
        else this.condition = new AndCondition(this.condition,condition);
    }
    protected void setConditions(ICondition... conditions){
        this.condition = new AndCondition(conditions);
    }

    public MaterialRecipeInfo.MaterialRecipeInfoBuilder addRecipeInfo(){
        return new MaterialRecipeInfo.MaterialRecipeInfoBuilder(this);
    }


    //材料相关
    public static class MaterialInfo{
        @Getter
        private final int tier;
        @Getter
        private final boolean isHidden;
        @Getter
        private final int sortOrder;
        @Getter
        private final boolean craftable;
        @Getter
        private final MaterialStatsObject stats;
        @Getter
        private final MaterialModifiersObject modifiers;
        @Getter
        private final boolean isOriginal;
        @Getter
        private final String compatModId;

        public MaterialInfo(int tier, boolean isHidden, int sortOrder, boolean craftable, MaterialStatsObject stats, MaterialModifiersObject modifiers, boolean isOriginal, String compatModId) {
            this.tier = tier;
            this.isHidden = isHidden;
            this.sortOrder = sortOrder;
            this.craftable = craftable;
            this.stats = stats;
            this.modifiers = modifiers;
            this.isOriginal = isOriginal;
            this.compatModId = compatModId;
        }
        public static class MaterialInfoBuilder{
            Builder parent;
            int tier;
            boolean isHidden;
            int sortOrder;
            boolean craftable;
            MaterialStatsObject stats;
            MaterialModifiersObject modifiers;
            boolean isOriginal;
            String compatModId;
            public MaterialInfoBuilder(int tier,boolean isHidden,int sortOrder,boolean craftable,Builder parent){
                this.tier = tier;
                this.isHidden = isHidden;
                this.sortOrder = sortOrder;
                this.craftable = craftable;
                this.parent = parent;
            }
            public MaterialInfoBuilder setOriginal(){
                this.isOriginal = true;
                return this;
            }
            public MaterialInfoBuilder setCompatModId(String id){
                this.compatModId = id;
                return this;
            }
            public MaterialStatsObjectBuilder buildStats(){
                return new MaterialStatsObjectBuilder(this);
            }
            public MaterialModifiersObjectBuilder buildModifiers(){
                return new MaterialModifiersObjectBuilder(this);
            }
            public Builder build(){
                 parent.materialInfo = new MaterialInfo(this.tier,this.isHidden,this.sortOrder,this.craftable,
                        this.stats,this.modifiers,this.isOriginal,this.compatModId);
                 return parent;
            }
            public static class MaterialStatsObjectBuilder {
                IMaterialStats[] stats;
                @Nullable PlatingMaterialStats.Builder armorBuilder;
                final MaterialInfoBuilder parent;
                boolean allowShield = false;
                protected MaterialStatsObjectBuilder(MaterialInfoBuilder parent) {
                    this.parent = parent;
                }
                public MaterialStatsObjectBuilder setStats(IMaterialStats... stats) {
                    this.stats = stats;
                    return this;
                }
                public MaterialStatsObjectBuilder setAllowShield() {
                    this.allowShield = true;
                    return this;
                }
                public MaterialStatsObjectBuilder setArmorBuilder(PlatingMaterialStats.Builder builder) {
                    this.armorBuilder = builder;
                    return this;
                }
                public MaterialInfoBuilder build() {
                    this.parent.stats = new MaterialStatsObject(this.stats, this.armorBuilder, this.allowShield);
                    return this.parent;
                }
            }
        }
        public static class MaterialModifiersObjectBuilder{
            final MaterialInfoBuilder parent;
            List<Pair<@Nullable MaterialStatsId, ModifierEntry[]>> list = new ArrayList<>();
            public MaterialModifiersObjectBuilder(MaterialInfoBuilder parent){
                this.parent = parent;
            }
            public MaterialModifiersObjectBuilder buildDefault(ModifierEntry... entries){
                list.add(new Pair<>(null,entries));
                return this;
            }
            public MaterialModifiersObjectBuilder buildPerStat(MaterialStatsId id,ModifierEntry... entries){
                list.add(new Pair<>(id,entries));
                return this;
            }
            public MaterialInfoBuilder build(){
                this.parent.modifiers = new MaterialModifiersObject(List.copyOf(list));
                return this.parent;
            }
        }
    }

    //材料配方相关
    public static class MaterialRecipeInfo{
        @Getter
        private final Pair<Optional<TagKey<Item>>, ItemLike> itemPair;
        @Getter
        private final Pair<Optional<TagKey<Fluid>>,Supplier<Fluid>> fluidPair;
        @Getter
        private final Optional<CastItemObject> castItemOptional;
        @Getter
        private final Pair<Optional<TagKey<Item>>, ItemLike> leftOverPair;
        @Getter
        private final int meltTemp;
        @Getter
        private final int fluidAmount;
        @Getter
        private final int materialValue;
        @Getter
        private final int requiredValue;
        @Getter
        private final List<AlloyRecipeBuilder> alloyRecipes = new ArrayList<>();
        @Getter
        private final boolean isBasin;

        public MaterialRecipeInfo(Pair<Optional<TagKey<Item>>, ItemLike> itemPair, Pair<Optional<TagKey<Fluid>>, Supplier<Fluid>> fluidPair, Optional<CastItemObject> castItemOptional, Pair<Optional<TagKey<Item>>, ItemLike> leftOverPair, int temp, int amount, int value, int requiredValue, boolean isBasin) {
            this.itemPair = itemPair;
            this.fluidPair = fluidPair;
            this.castItemOptional = castItemOptional;
            this.leftOverPair = leftOverPair;
            meltTemp = temp;
            fluidAmount = amount;
            materialValue = value;
            this.requiredValue = requiredValue;
            this.isBasin = isBasin;
        }

        public Ingredient getItemIng(){
            return itemPair.getA().map(Ingredient::of).orElseGet(() -> Ingredient.of(itemPair.getB()));
        }
        public ItemOutput getItemOutput(){
            return itemPair.getA().map(ItemOutput::fromTag).orElseGet(() -> ItemOutput.fromItem(itemPair.getB()));
        }
        public Ingredient getLeftoverIng(){
            return leftOverPair.getA().map(Ingredient::of).orElseGet(() -> Ingredient.of(leftOverPair.getB()));
        }
        public ItemOutput getLeftoverOutput(){
            return leftOverPair.getA().map(ItemOutput::fromTag).orElseGet(() -> ItemOutput.fromItem(leftOverPair.getB()));
        }
        public FluidIngredient getFluidIng(){
            if (!hasMolten()) throw new NullPointerException("Tried Fetching fluid when a material don't have molten form");
            return fluidPair.getA().map(fluidTagKey -> (FluidIngredient) FluidIngredient.of(fluidTagKey,fluidAmount))
                    .orElseGet(()->FluidIngredient.of(new FluidStack(fluidPair.getB().get(),fluidAmount)));
        }
        public FluidOutput getFluidOutput(){
            if (!hasMolten()) throw new NullPointerException("Tried Fetching fluid when a material don't have molten form");
            return fluidPair.getA().map(fluidTagKey -> FluidOutput.fromTag(fluidTagKey,fluidAmount))
                    .orElseGet(() -> FluidOutput.fromFluid(fluidPair.getB().get(),fluidAmount));
        }
        public boolean hasMolten(){
            return fluidPair.getA().isPresent()||fluidPair.getB().get()!=null;
        }
        public boolean hasItem(){
            return itemPair.getA().isPresent()||itemPair.getB()!=null;
        }
        public boolean hasLeftOver(){
            return leftOverPair.getA().isPresent()||leftOverPair.getB()!=null;
        }
        public MaterialRecipeInfo addAlloyRecipes(AlloyRecipeBuilder... alloyRecipeBuilders){
            this.alloyRecipes.addAll(Arrays.stream(alloyRecipeBuilders).toList());
            return this;
        }
        public static class MaterialRecipeInfoBuilder{
            public MaterialRecipeInfoBuilder(SimpleMaterialObject materialObject){
                this.simpleMaterialObject = materialObject;
                this.itemPair = new Pair<>(Optional.empty(),simpleMaterialObject.itemObject);
                this.fluidPair = new Pair<>(Optional.empty(),()->{
                    if (simpleMaterialObject.fluidObject != null) return simpleMaterialObject.fluidObject.get();
                    return null;
                });
                this.leftOverPair = new Pair<>(Optional.empty(),simpleMaterialObject.itemObject);
                this.castItemOptional = Optional.empty();
                this.materialValue = 1;
                this.requiredValue = 1;
            }
            SimpleMaterialObject simpleMaterialObject;
            Pair<Optional<TagKey<Item>>, ItemLike> itemPair;
            Pair<Optional<TagKey<Fluid>>,Supplier<Fluid>> fluidPair;
            Optional<CastItemObject> castItemOptional;
            Pair<Optional<TagKey<Item>>, ItemLike> leftOverPair;
            int meltTemp;
            int fluidAmount;
            int materialValue;
            int requiredValue;
            boolean isBasin;
            private FluidOutput getFluidOutput(){
                return fluidPair.getA().map(fluidTagKey -> FluidOutput.fromTag(fluidTagKey,fluidAmount))
                        .orElseGet(() -> FluidOutput.fromFluid(fluidPair.getB().get(),fluidAmount));
            }
            public MaterialRecipeInfoBuilder setItemIn(@Nullable TagKey<Item> tag,@Nullable ItemLike item){
                this.itemPair = new Pair<>(Optional.ofNullable(tag),item);
                return this;
            }
            public MaterialRecipeInfoBuilder setFluidIn(@Nullable TagKey<Fluid> tag,@Nullable Supplier<Fluid> fluid){
                this.fluidPair = new Pair<>(Optional.ofNullable(tag),fluid);
                return this;
            }
            public MaterialRecipeInfoBuilder setFluidIn(@Nullable TagKey<Fluid> tag,
                                                      @NotNull RegistryObject<? extends Fluid> fluid){
                this.fluidPair = new Pair<>(Optional.ofNullable(tag),()->fluid.orElse(null));
                return this;
            }
            public MaterialRecipeInfoBuilder setCast(Optional<CastItemObject> castItemOptional){
                this.castItemOptional = castItemOptional;
                return this;
            }
            public MaterialRecipeInfoBuilder setIngot(){
                this.castItemOptional =Optional.of(TinkerSmeltery.ingotCast);
                return this;
            }
            public MaterialRecipeInfoBuilder setGem(){
                this.castItemOptional =Optional.of(TinkerSmeltery.gemCast);
                return this;
            }
            public MaterialRecipeInfoBuilder setLeftover(@Nullable TagKey<Item> tag,@Nullable ItemLike item){
                this.leftOverPair = new Pair<>(Optional.ofNullable(tag),item);
                return this;
            }
            public MaterialRecipeInfoBuilder setTemp(int temp){
                this.meltTemp = temp;
                return this;
            }
            public MaterialRecipeInfoBuilder setFluidAmount(int amount){
                this.fluidAmount = amount;
                return this;
            }
            public MaterialRecipeInfoBuilder setMaterialValue(int value,int required){
                this.materialValue = value;
                this.requiredValue = required;
                return this;
            }
            public MaterialRecipeInfoBuilder setBasin(){
                this.isBasin = true;
                return this;
            }
            public SimpleMaterialObject build(){
                simpleMaterialObject.recipeInfo = new MaterialRecipeInfo(this.itemPair,this.fluidPair,
                        this.castItemOptional,this.leftOverPair,this.meltTemp,this.fluidAmount,this.materialValue,
                        this.requiredValue,isBasin);
                return simpleMaterialObject;
            }
        }
    }

    public static class Builder{
        SimpleMaterialWrappedRegister register;
        String name;
        String nameSpace;
        Supplier<? extends Item> itemSupplier;
        MaterialInfo materialInfo;
        boolean isUnit;
        @Getter
        boolean isOriginal;
        @Getter
        List<String> compatModIds = new ArrayList<>();
        List<ICondition> conditions = new ArrayList<>();
        String itemName;
        String fluidName;
        boolean isSimpleModel = true;
        Function<FluidDeferredRegister.Builder, FluidObject<? extends ForgeFlowingFluid>> fluidRegFunction;
        public Builder(String nameSpace,String name,SimpleMaterialWrappedRegister register){
            this.name = name;
            this.nameSpace = nameSpace;
            this.register = register;
            this.fluidName = "molten_"+name;
        }
        public Builder registerItem(Supplier<? extends Item> supplier){
            this.itemSupplier = supplier;
            return this;
        }
        public Builder setUnit(){
            this.isUnit = true;
            return this;
        }
        public Builder setItemIngot(){
            this.itemName = name+"_ingot";
            return this;
        }
        public Builder addCompatModId(String modId){
            this.compatModIds.add(modId);
            this.conditions.add(new ModLoadedCondition(modId));
            if (isOriginal&&TiAcCrConditions.MODID_ORIGINAL_CONDITIONAL_MAP.get(modId)!=null){
                conditions.add(TiAcCrConditions.MODID_ORIGINAL_CONDITIONAL_MAP.get(modId));
            }
            else if (TiAcCrConditions.MODID_CONDITIONAL_MAP.get(modId)!=null)
                conditions.add(TiAcCrConditions.MODID_CONDITIONAL_MAP.get(modId));
            return this;
        }
        public Builder setOriginal(){
            this.isOriginal = true;
            conditions.add(TiAcCrConditions.ALLOW_ORIGINAL_MATERIALS);
            return this;
        }
        public Builder setItemName(String itemName){
            this.itemName = itemName;
            return this;
        }
        public Builder registerBurningFluid(int temp,boolean gas,int lightLevel,int burnTime,int damage){
            this.fluidRegFunction = builder -> {
                builder.type(hot(name,temp,gas)).bucket().block(createBurning(MapColor.COLOR_GRAY,lightLevel,burnTime,damage)).commonTag();
                return gas?builder.invertedFlowing():builder.flowing();
            };
            return this;
        }
        public Builder registerFluid(int temp,boolean gas,Function<Supplier<? extends FlowingFluid>, LiquidBlock> blockFunction){
            this.fluidRegFunction = builder -> {
                builder.type(hot(name,temp,gas)).bucket().block(blockFunction).commonTag();
                return gas?builder.invertedFlowing():builder.flowing();
            };
            return this;
        }
        public Builder setFluidName(String fluidName){
            this.fluidName = fluidName;
            return this;
        }
        public Builder addConditions(ICondition... conditions){
            this.conditions.addAll(Arrays.stream(conditions).toList());
            return this;
        }
        public Builder setCustomModel(){
            this.isSimpleModel = false;
            return this;
        }
        public MaterialInfo.MaterialInfoBuilder buildMaterial(int tier,boolean isHidden,int sortOrder,boolean craftable){
            return new MaterialInfo.MaterialInfoBuilder(tier,isHidden,sortOrder,craftable,this);
        }
        public SimpleMaterialObject build(){
            SimpleMaterialObject object;
            if (this.itemName==null) this.itemName = name;
            if (this.fluidName==null) this.fluidName = name;
            AtomicBoolean isLoaded = new AtomicBoolean(true);
            compatModIds.forEach(s -> {
                if (isLoaded.get()&&!ModList.get().isLoaded(s)) isLoaded.set(false);
            });
            if (isLoaded.get()){
                ItemObject<Item> itemObj = null;
                    if (itemSupplier!=null) itemObj = register.itemRegister.register(itemName,itemSupplier);
                FluidObject<? extends ForgeFlowingFluid> fluidObj = null;
                    if (fluidRegFunction!=null) fluidObj = fluidRegFunction.apply(register.fluidRegister.register(fluidName));
                object = new SimpleMaterialObject(this.name, this.nameSpace, fluidObj,this.isUnit, itemObj, materialInfo,
                        new AndCondition(conditions.toArray(new ICondition[0])),this.isSimpleModel);
                register.entryMap.put(name,object);
                return object;
            }
            object = new SimpleMaterialObject(this.name,this.nameSpace,null,this.isUnit,null,this.materialInfo,
                    new AndCondition(conditions.toArray(new ICondition[0])),this.isSimpleModel);
            register.entryMap.put(name,object);
            return object;
        }
        protected static FluidType.Properties hot(String name, int Temp, boolean gas) {
            return FluidType.Properties.create().density(gas?-2000:2000).viscosity(10000).temperature(Temp)
                    .descriptionId("fluid."+TinkersAdvanced.MODID+"."+name)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                    .motionScale(0.0023333333333333335D)
                    .canSwim(false).canDrown(false)
                    .pathType(BlockPathTypes.LAVA).adjacentPathType(null);
        }
    }
//    public static class RestrictedBuilder<I extends Item,F extends Fluid> extends Builder{
//        public RestrictedBuilder(String nameSpace, String name, SimpleMaterialWrappedRegister register) {
//            super(nameSpace, name, register);
//        }
//        Supplier<I> itemSupplier;
//        Supplier<F> fluidSupplier;
//        Function<FluidDeferredRegister.Builder, FluidObject<F>> fluidRegFunction;
//        public RestrictedBuilder<I,F> registerRestrictedItem(Supplier<I> supplier){
//            this.itemSupplier = supplier;
//            return this;
//        }
//        public RestrictedBuilder<I,F> registerRestrictedBurningFluid(int temp,boolean gas,int lightLevel,int burnTime,int damage){
//            this.fluidRegFunction = builder -> {
//                builder.type(hot(name,temp,gas)).bucket().block(createBurning(MapColor.COLOR_GRAY,lightLevel,burnTime,damage)).commonTag();
//                return gas?builder.invertedFlowing():builder.flowing();
//            };
//            return this;
//        }
//        public RestrictedBuilder<I,F> registerRestrictedFluid(int temp,boolean gas,int lightLevel,Function<Supplier<? extends FlowingFluid>, LiquidBlock> blockFunction){
//            this.fluidRegFunction = builder -> {
//                builder.type(hot(name,temp,gas)).bucket().block(blockFunction).commonTag();
//                return gas?builder.invertedFlowing():builder.flowing();
//            };
//            return this;
//        }
//        @Override
//        public SimpleMaterialObject<I,F> build(){
//            conditions.add(new ModLoadedCondition(compatModIds));
//            if (TiAcCrConditions.MODID_CONFIG_MAP.get(compatModIds)!=null)
//                conditions.add(TiAcCrConditions.MODID_CONDITIONAL_MAP.get(compatModIds));
//            if (isOriginal) conditions.add(TiAcCrConditions.ALLOW_ORIGINAL_MATERIALS);
//            if (compatModIds==null|| ModList.get().isLoaded(compatModIds)){
//                ItemObject<I> itemObj = null;
//                if (itemSupplier!=null) itemObj = register.itemRegister.register(name,itemSupplier);
//                FluidObject<F> fluidObj = null;
//                if (fluidSupplier!=null) fluidObj = fluidRegFunction.apply(register.fluidRegister.register(name));
//                return new SimpleMaterialObject<>(this.name, this.nameSpace, fluidObj,this.isUnit, itemObj, materialInfo,
//                        new AndCondition(conditions.toArray(new ICondition[0])),this.isSimpleModel);
//            }
//            return new SimpleMaterialObject<>(this.name,this.nameSpace,null,this.isUnit,null,this.materialInfo,
//                    new AndCondition(conditions.toArray(new ICondition[0])),this.isSimpleModel);
//        }
//    }
}
