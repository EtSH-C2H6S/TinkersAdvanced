package com.c2h6s.tinkers_advanced.data.enums;

import appeng.datagen.providers.tags.ConventionTags;
import com.c2h6s.tinkers_advanced.data.TiAcMaterialIds;
import mekanism.common.tags.MekanismTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import slimeknights.mantle.recipe.helper.TagEmptyCondition;
import slimeknights.tconstruct.common.json.ConfigEnabledCondition;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

import static com.c2h6s.tinkers_advanced.data.enums.EnumMaterialModifier.*;

public enum EnumMaterial {
    BISMUTH(TiAcMaterialIds.BISMUTH,4,false,false,EnumMaterialStats.BISMUTH,null,EnumMaterialModifier.BISMUTH),
    BISMUTHINITE(TiAcMaterialIds.BISMUTHINITE,2,true,false,EnumMaterialStats.BISMUTHINITE,null,EnumMaterialModifier.BISMUTHINITE),
    ALLOY_ATOMIC(TiAcMaterialIds.Mekanism.ALLOY_ATOMIC,3,true,false,EnumMaterialStats.ALLOY_ATOMIC,tagEmpty(MekanismTags.Items.ALLOYS_ATOMIC),EnumMaterialModifier.ALLOY_ATOMIC),
    FLUIX(TiAcMaterialIds.AE2.FLUIX,2,true,false,EnumMaterialStats.FLUIX,tagEmpty(ConventionTags.FLUIX_CRYSTAL),FLUIX_ARMOR,FLUIX_BINDING,FLUIX_GRIP,FLUIX_HEAD,FLUIX_HANDLE,FLUIX_LIMB),
    CERTUS(TiAcMaterialIds.AE2.CERTUS,1,true,false,EnumMaterialStats.CERTUS,tagEmpty(ConventionTags.CERTUS_QUARTZ),CERTUS_ARMOR,CERTUS_DEFAULT),
    ANTIMATTER(TiAcMaterialIds.Mekanism.ANTIMATTER,4,false,false,EnumMaterialStats.ANTIMATTER,tagEmpty(MekanismTags.Items.PELLETS_ANTIMATTER),ANTIMATTER_ARMOR,ANTIMATTER_MELEE),
    REFINED_GLOWSTONE(TiAcMaterialIds.Mekanism.REFINED_GLOWSTONE,3,true,false,EnumMaterialStats.REFINED_GLOWSTONE,tagEmpty(MekanismTags.Items.INGOTS_REFINED_GLOWSTONE),REFINED_GLOWSTONE_DEFAULT,REFINED_GLOWSTONE_ARMOR),
    REFINED_OBSIDIAN(TiAcMaterialIds.Mekanism.REFINED_OBSIDIAN,4,true,false,EnumMaterialStats.REFINED_OBSIDIAN,tagEmpty(MekanismTags.Items.INGOTS_REFINED_OBSIDIAN),REFINED_OBSIDIAN_ARMOR,REFINED_OBSIDIAN_DEFAULT),
    IRRADIUM(TiAcMaterialIds.Mekanism.IRRADIUM,4,false,false,EnumMaterialStats.IRRADIUM,modLoaded("mekanism"),IRRADIUM_DEFAULT,IRRADIUM_ARMOR),
    PNEUMATIC_STEEL(TiAcMaterialIds.PnC.PNEUMATIC_STEEL,4,true,false,EnumMaterialStats.PNEUMATIC_STEEL,modLoaded("pneumaticcraft"),PNEUMATIC_STEEL_ARMOR,PNEUMATIC_STEEL_DEFAULT),
    ;
    public final MaterialId id;
    public final int tier;
    public final boolean craftable;
    public final boolean hidden;
    public final EnumMaterialStats stats;
    public final EnumMaterialModifier[] modifiers;
    public final ICondition condition;
    EnumMaterial(MaterialId id, int tier, boolean craftable, boolean hidden, EnumMaterialStats stats, ICondition condition, EnumMaterialModifier... modifiers){
        this.id = id;
        this.tier =tier;
        this.craftable = craftable;
        this.hidden = hidden;
        this.stats = stats;
        this.modifiers = modifiers;
        this.condition = condition;
    }
    public static ICondition modLoaded(String modId){
        return new OrCondition(ConfigEnabledCondition.FORCE_INTEGRATION_MATERIALS,new ModLoadedCondition(modId));
    }
    public static ICondition tagEmpty(TagKey<Item> tagKey){
        return new OrCondition(ConfigEnabledCondition.FORCE_INTEGRATION_MATERIALS,new NotCondition(new TagEmptyCondition<Item>(tagKey)));
    }
}
