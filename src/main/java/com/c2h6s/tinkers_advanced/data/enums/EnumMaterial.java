package com.c2h6s.tinkers_advanced.data.enums;

import appeng.datagen.providers.tags.ConventionTags;
import com.c2h6s.tinkers_advanced.data.TiAcMaterialIds;
import com.c2h6s.tinkers_advanced.data.TiAcTagkeys;
import me.desht.pneumaticcraft.api.data.PneumaticCraftTags;
import mekanism.common.tags.MekanismTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import slimeknights.mantle.recipe.condition.TagFilledCondition;
import slimeknights.tconstruct.common.json.ConfigEnabledCondition;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

import static com.c2h6s.tinkers_advanced.data.enums.EnumMaterialModifier.*;

public enum EnumMaterial {
    BISMUTH(TiAcMaterialIds.BISMUTH,4,false,false,EnumMaterialStats.BISMUTH,null,EnumMaterialModifier.BISMUTH),
    BISMUTHINITE(TiAcMaterialIds.BISMUTHINITE,2,true,false,EnumMaterialStats.BISMUTHINITE,null,EnumMaterialModifier.BISMUTHINITE,BISMUTHINITE_FLUX_CORE),
    ANTIMONY(TiAcMaterialIds.ANTIMONY,4,false,false,EnumMaterialStats.ANTIMONY,null, ANTIMONY_ARMOR,ANTIMONY_DEFAULT),
    STIBNITE(TiAcMaterialIds.STIBNITE,4,true,false,EnumMaterialStats.STIBNITE,null, STIBNITE_DEFAULT,STIBNITE_FLUX_CORE),
    ALLOY_ATOMIC(TiAcMaterialIds.Mekanism.ALLOY_ATOMIC,3,true,false,EnumMaterialStats.ALLOY_ATOMIC, tagFilled(MekanismTags.Items.ALLOYS_ATOMIC),EnumMaterialModifier.ALLOY_ATOMIC),
    FLUIX(TiAcMaterialIds.AE2.FLUIX,2,true,false,EnumMaterialStats.FLUIX, tagFilled(ConventionTags.FLUIX_CRYSTAL),FLUIX_ARMOR,FLUIX_BINDING,FLUIX_GRIP,FLUIX_HEAD,FLUIX_HANDLE,FLUIX_LIMB, FLUIX_FLUX_CORE),
    CERTUS(TiAcMaterialIds.AE2.CERTUS,1,true,false,EnumMaterialStats.CERTUS, tagFilled(ConventionTags.CERTUS_QUARTZ),CERTUS_ARMOR,CERTUS_DEFAULT),
    ANTIMATTER(TiAcMaterialIds.Mekanism.ANTIMATTER,4,false,false,EnumMaterialStats.ANTIMATTER, tagFilled(MekanismTags.Items.PELLETS_ANTIMATTER),ANTIMATTER_ARMOR,ANTIMATTER_MELEE),
    REFINED_GLOWSTONE(TiAcMaterialIds.Mekanism.REFINED_GLOWSTONE,3,true,false,EnumMaterialStats.REFINED_GLOWSTONE, tagFilled(MekanismTags.Items.INGOTS_REFINED_GLOWSTONE),REFINED_GLOWSTONE_DEFAULT,REFINED_GLOWSTONE_ARMOR),
    REFINED_OBSIDIAN(TiAcMaterialIds.Mekanism.REFINED_OBSIDIAN,4,true,false,EnumMaterialStats.REFINED_OBSIDIAN, tagFilled(MekanismTags.Items.INGOTS_REFINED_OBSIDIAN),REFINED_OBSIDIAN_ARMOR,REFINED_OBSIDIAN_DEFAULT),
    IRRADIUM(TiAcMaterialIds.Mekanism.IRRADIUM,4,false,false,EnumMaterialStats.IRRADIUM,modLoaded("mekanism"),IRRADIUM_DEFAULT,IRRADIUM_ARMOR),
    PNEUMATIC_STEEL(TiAcMaterialIds.PnC.PNEUMATIC_STEEL,4,true,false,EnumMaterialStats.PNEUMATIC_STEEL,modLoaded("pneumaticcraft"),PNEUMATIC_STEEL_ARMOR,PNEUMATIC_STEEL_DEFAULT),
    BASALZ_SIGNALUM(TiAcMaterialIds.Thermal.BASALZ_SIGNALUM,3,false,false,EnumMaterialStats.BASALZ_SIGNALUM,modLoaded("thermal"),BASALZ_SIGNALUM_ARMOR,BASALZ_SIGNALUM_DEFAULT),
    BLITZ_LUMIUM(TiAcMaterialIds.Thermal.BLITZ_LUMIUM,4,false,false,EnumMaterialStats.BLITZ_LUMIUM,modLoaded("thermal"),BLITZ_LUMIUM_ARMOR,BLITZ_LUMIUM_DEFAULT),
    BLIZZ_ENDERIUM(TiAcMaterialIds.Thermal.BLIZZ_ENDERIUM,4,false,false,EnumMaterialStats.BLIZZ_ENDERIUM,modLoaded("thermal"),BLIZZ_ENDERIUM_ARMOR,BLIZZ_ENDERIUM_DEFAULT),
    ACTIVATED_CHROMATIC_STEEL(TiAcMaterialIds.Thermal.ACTIVATED_CHROMATIC_STEEL,4,false,false,EnumMaterialStats.ACTIVATED_CHROMA_STEEL,modLoaded("thermal"),ACTIVATED_CHROMA_STEEL_MELEE,ACTIVATED_CHROMA_STEEL_ARMOR,ACTIVATED_CHROMA_STEEL_RANGED),
    BLAZE_NETHERITE(TiAcMaterialIds.BLAZE_NETHERITE,4,false,false,EnumMaterialStats.BLAZE_NETHERITE,null,EnumMaterialModifier.BLAZE_NETHERITE),
    IRIDIUM(TiAcMaterialIds.IRIDIUM,4,false,false,EnumMaterialStats.IRIDIUM,null, IRIDIUM_DEFAULT,IRIDIUM_ARMOR,IRIDIUM_FLUX_CORE),
    DENSIUM(TiAcMaterialIds.Mekanism.DENSIUM,4,false,false,EnumMaterialStats.DENSIUM,modLoaded("mekanism"), DENSIUM_ARMOR,DENSIUM_BINDING,DENSIUM_DEFAULT,DENSIUM_HANDLE,DENSIUM_HEAD),
    NEUTRONITE(TiAcMaterialIds.Mekanism.NEUTRONITE,128,false,true,EnumMaterialStats.NEUTRONITE,modLoaded("mekanism"), NEUTRONITE_DEFAULT,NEUTRONITE_ARMOR),
    OSGLOGLAS(TiAcMaterialIds.Mekanism.OSGLOGLAS,4,false,false,EnumMaterialStats.OSGLOGLAS,modLoaded("mekanism"), OSGLOGLAS_DEFAULT),
    DISINTEGRATE_CRYSTAL(TiAcMaterialIds.DISINTEGRATE_CRYSTAL,4,true,false,EnumMaterialStats.DISINTEGRATE_CRYSTAL,null,DISINTEGRATE_CRYSTAL_DEFAULT),
    RESONANCE_CRYSTAL(TiAcMaterialIds.RESONANCE_CRYSTAL,4,true,false,EnumMaterialStats.RESONANCE_CRYSTAL,null,RESONATE_CRYSTAL_ARMOR,RESONATE_CRYSTAL_DEFAULT),
    VOLTAIC_CRYSTAL(TiAcMaterialIds.VOLTAIC_CRYSTAL,4,true,false,EnumMaterialStats.VOLTAIC_CRYSTAL,null,VOLTAIC_CRYSTAL_DEFAULT),
    PLASTIC(TiAcMaterialIds.CommonIntegration.PLASTIC,2,true,false,EnumMaterialStats.PLASTIC,tagFilled(TiAcTagkeys.Items.PLASTIC),PLASTIC_DEFAULT),
    PROTOCITE(TiAcMaterialIds.Mekanism.PROTOCITE,4,false,false,EnumMaterialStats.PROTOCITE,modLoaded("mekanism"),PROTOCITE_ARMOR,PROTOCITE_DEFAULT),
    COMPRESSED_IRON(TiAcMaterialIds.PnC.COMPRESSED_IRON,2,true,false,EnumMaterialStats.COMPRESSED_IRON,tagFilled(PneumaticCraftTags.Items.INGOTS_COMPRESSED_IRON),COMPRESSED_IRON_ARMOR,COMPRESSED_IRON_DEFAULT),
    PINK_SLIME_STEEL(TiAcMaterialIds.IndustrialForgoing.PINK_SLIME_METAL,3,false,false,EnumMaterialStats.PINK_SLIME_METAL,modLoaded("industrialforegoing"),PINK_SLIME_METAL),
    NUTRITIVE_SLIMESTEEL(TiAcMaterialIds.Mekanism.NUTRITIVE_SLIMESTEEL,3,false,false,EnumMaterialStats.NUTRITIVE_SLIMESTEEL,modLoaded("mekanism"),EnumMaterialModifier.NUTRITIVE_SLIMESTEEL),
    VOID_STEEL(TiAcMaterialIds.CreateUtilities.VOID_STEEL,4,false,false,EnumMaterialStats.VOID_STEEL,modLoaded("createutilities"),VOID_STEEL_ARMOR,VOID_STEEL_DEFAULT),
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
    public static ICondition tagFilled(TagKey<Item> tagKey){
        return new OrCondition(ConfigEnabledCondition.FORCE_INTEGRATION_MATERIALS, new TagFilledCondition<>(tagKey));
    }
}
