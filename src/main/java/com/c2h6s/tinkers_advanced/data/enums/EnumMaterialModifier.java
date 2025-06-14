package com.c2h6s.tinkers_advanced.data.enums;

import com.c2h6s.etstlib.data.EtSTLibModifierIds;
import com.c2h6s.etstlib.register.EtSTLibModifier;
import com.c2h6s.tinkers_advanced.content.item.tinkering.materialStat.FluxCoreMaterialStat;
import com.c2h6s.tinkers_advanced.registery.TiAcModifiers;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.stats.*;

public enum EnumMaterialModifier {
    ALLOY_ATOMIC(StatlessMaterialStats.BINDING.getIdentifier(),entry(EtSTLibModifierIds.ATOMIC_DECOMPOSE)),
    BISMUTH(null,entry(TiAcModifiers.TETANUS.getId()),entry(ModifierIds.heavy)),
    BISMUTHINITE(null,entry(TiAcModifiers.FRAGILE.getId()),entry(EtSTLibModifier.ANISOTROPY.getId())),
    BISMUTHINITE_FLUX_CORE(FluxCoreMaterialStat.ID,entry(TiAcModifiers.PIEZOELECTRIC_EFFECT.getId(),3)),

    CERTUS_DEFAULT(null,entry(EtSTLibModifier.ANISOTROPY.getId())),
    CERTUS_ARMOR(MaterialRegistry.ARMOR,entry(EtSTLibModifier.CRYSTAL_ARMOR.getId())),

    FLUIX_ARMOR(MaterialRegistry.ARMOR,entry(EtSTLibModifier.CRYSTAL_ARMOR.getId()),entry(EtSTLibModifier.clearing.getId())),
    FLUIX_GRIP(GripMaterialStats.ID,entry(EtSTLibModifier.ANISOTROPY.getId()),entry(EtSTLibModifier.EtSTLibModifierAE.applied_fixing.getId())),
    FLUIX_LIMB(LimbMaterialStats.ID,entry(EtSTLibModifier.ANISOTROPY.getId()),entry(EtSTLibModifier.EtSTLibModifierAE.energetic_attack.getId())),
    FLUIX_HEAD(HeadMaterialStats.ID,entry(EtSTLibModifier.ANISOTROPY.getId()),entry(EtSTLibModifier.EtSTLibModifierAE.energetic_attack.getId())),
    FLUIX_HANDLE(HandleMaterialStats.ID,entry(ModifierIds.looting),entry(EtSTLibModifier.EtSTLibModifierAE.applied_fixing.getId())),
    FLUIX_BINDING(StatlessMaterialStats.BINDING.getIdentifier(),entry(ModifierIds.fortune),entry(EtSTLibModifier.EtSTLibModifierAE.applied_fixing.getId())),
    FLUIX_FLUX_CORE(FluxCoreMaterialStat.ID,entry(ModifierIds.looting),entry(TiAcModifiers.PIEZOELECTRIC_EFFECT.getId())),

    ANTIMATTER_MELEE(MaterialRegistry.MELEE_HARVEST,entry(TiAcModifiers.ANNIHILATE.getId()),entry(EtSTLibModifierIds.ATOMIC_DECOMPOSE)),
    ANTIMATTER_ARMOR(MaterialRegistry.ARMOR,entry(TiAcModifiers.REACTIVE_EXPLOSIVE_ARMOR.getId())),

    REFINED_GLOWSTONE_DEFAULT(null,entry(EtSTLibModifier.glowing.getId())),
    REFINED_GLOWSTONE_ARMOR(MaterialRegistry.ARMOR,entry(EtSTLibModifier.glowing.getId()),entry(TinkerModifiers.golden.getId())),

    REFINED_OBSIDIAN_DEFAULT(null,entry(EtSTLibModifier.momentum_accelerate.getId()),entry(ModifierIds.dense)),
    REFINED_OBSIDIAN_ARMOR(MaterialRegistry.ARMOR,entry(ModifierIds.ductile,2),entry(ModifierIds.dense)),

    IRRADIUM_DEFAULT(null,entry(TiAcModifiers.RADIATION_BURNING.getId()),entry(EtSTLibModifier.glowing.getId())),
    IRRADIUM_ARMOR(MaterialRegistry.ARMOR,entry(TiAcModifiers.RADIOACTIVE_ARMOR.getId()),entry(EtSTLibModifier.glowing.getId())),

    PNEUMATIC_STEEL_DEFAULT(null,entry(EtSTLibModifier.EtSTLibModifierPnC.aerial_reinforced.getId()),entry(TiAcModifiers.AIR_SLASH.getId())),
    PNEUMATIC_STEEL_ARMOR(MaterialRegistry.ARMOR,entry(EtSTLibModifier.EtSTLibModifierPnC.aerial_reinforced.getId()),entry(TiAcModifiers.AERIAL_PROTECTION.getId())),

    BASALZ_SIGNALUM_DEFAULT(null,entry(EtSTLibModifier.SHORT_CIRCUIT.getId()),entry(TiAcModifiers.BASALZ_INFLICT.getId())),
    BASALZ_SIGNALUM_ARMOR(MaterialRegistry.ARMOR,entry(TiAcModifiers.SENSOR_INTERRUPT.getId()),entry(TiAcModifiers.BASALZ_DEFENSE.getId())),

    BLITZ_LUMIUM_DEFAULT(null,entry(EtSTLibModifier.glowing.getId()),entry(TiAcModifiers.Blitz_INFLICT.getId())),
    BLITZ_LUMIUM_ARMOR(MaterialRegistry.ARMOR,entry(EtSTLibModifier.glowing.getId()),entry(TiAcModifiers.Blitz_DEFENSE.getId())),

    BLIZZ_ENDERIUM_DEFAULT(null,entry(TiAcModifiers.METAMORPHIUM.getId()),entry(TiAcModifiers.BLIZZ_INFLICT.getId())),
    BLIZZ_ENDERIUM_ARMOR(MaterialRegistry.ARMOR,entry(TiAcModifiers.METAMORPHIUM.getId()),entry(TiAcModifiers.BLIZZ_DEFENSE.getId())),

    ACTIVATED_CHROMA_STEEL_MELEE(MaterialRegistry.MELEE_HARVEST,entry(TiAcModifiers.FLUX_INFUSED.getId()),entry(TiAcModifiers.THERMAL_ENHANCE.getId()),entry(TiAcModifiers.THERMAL_SLASH.getId())),
    ACTIVATED_CHROMA_STEEL_RANGED(MaterialRegistry.RANGED,entry(TiAcModifiers.FLUX_INFUSED.getId()),entry(TiAcModifiers.THERMAL_ENHANCE.getId()),entry(TiAcModifiers.FLUX_ARROW.getId())),
    ACTIVATED_CHROMA_STEEL_ARMOR(MaterialRegistry.ARMOR,entry(TiAcModifiers.FLUX_INFUSED.getId()),entry(TiAcModifiers.THERMAL_ENHANCE.getId()),entry(TiAcModifiers.FLUX_DEFENSE.getId())),

    BLAZE_NETHERITE(null,entry(TiAcModifiers.FLAME_ADAPTIVE.getId()),entry(ModifierIds.netherite)),

    IRIDIUM_DEFAULT(null,entry(EtSTLibModifier.INERT_METAL.getId()),entry(ModifierIds.dense)),
    IRIDIUM_FLUX_CORE(null,entry(EtSTLibModifier.INERT_METAL.getId()),entry(TiAcModifiers.PLATINOID_CATALYST.getId())),
    IRIDIUM_ARMOR(MaterialRegistry.ARMOR,entry(EtSTLibModifier.INERT_METAL.getId()),entry(EtSTLibModifier.SECONDARY_ARMOR.getId())),

    DENSIUM_DEFAULT(null,entry(EtSTLibModifier.EXTRA_DENSE.getId())),
    DENSIUM_HEAD(HeadMaterialStats.ID,entry(EtSTLibModifier.EXTRA_DENSE.getId()),entry(EtSTLibModifierIds.RUDE)),
    DENSIUM_HANDLE(HandleMaterialStats.ID,entry(EtSTLibModifier.EXTRA_DENSE.getId()),entry(EtSTLibModifier.momentum_accelerate.getId(),5),entry(ModifierIds.heavy)),
    DENSIUM_BINDING(StatlessMaterialStats.BINDING.getIdentifier(),entry(EtSTLibModifier.EXTRA_DENSE.getId()),entry(EtSTLibModifier.momentum_accelerate.getId())),
    DENSIUM_ARMOR(MaterialRegistry.ARMOR,entry(EtSTLibModifier.EXTRA_DENSE.getId()),entry(EtSTLibModifier.HYPER_DENSITY.getId())),
    DENSIUM_RANGED(MaterialRegistry.RANGED,entry(EtSTLibModifier.EXTRA_DENSE.getId()),entry(TinkerModifiers.momentum.getId(),5),entry(ModifierIds.heavy)),

    NEUTRONITE_DEFAULT(null,entry(TiAcModifiers.SUPREME_DENSITY.getId()),entry(TiAcModifiers.IONIZED.getId())),
    NEUTRONITE_ARMOR(MaterialRegistry.ARMOR,entry(TiAcModifiers.SUPREME_DENSITY_ARMOR.getId()),entry(TiAcModifiers.IONIZED.getId())),

    OSGLOGLAS_DEFAULT(null,entry(EtSTLibModifier.EXTRA_DENSE.getId()),entry(EtSTLibModifier.GLOBAL_TRAVELER.getId())),

    DISINTEGRATE_CRYSTAL_DEFAULT(null,entry(TiAcModifiers.DIS_INTEGRATE.getId()),entry(ModifierIds.fortune,3)),

    RESONATE_CRYSTAL_DEFAULT(null,entry(EtSTLibModifier.ANISOTROPY.getId()),entry(TiAcModifiers.ECHO_LOCATING.getId())),
    RESONATE_CRYSTAL_ARMOR(MaterialRegistry.ARMOR,entry(EtSTLibModifier.CRYSTAL_ARMOR.getId()),entry(EtSTLibModifier.RESONATING.getId())),

    VOLTAIC_CRYSTAL_DEFAULT(null,entry(EtSTLibModifier.energy_loaded.getId()),entry(TiAcModifiers.ELECTRIC.getId())),

    PLASTIC_DEFAULT(null,entry(TiAcModifiers.ELASTIC.getId()),entry(TiAcModifiers.SHAPING.getId())),

    PROTOCITE_DEFAULT(null,entry(TiAcModifiers.PROTO_REFINING.getId()),entry(EtSTLibModifier.glowing.getId())),
    PROTOCITE_ARMOR(MaterialRegistry.ARMOR,entry(TiAcModifiers.PROTO_DEFENSE.getId()),entry(EtSTLibModifier.glowing.getId())),

    COMPRESSED_IRON_DEFAULT(null,entry(ModifierIds.dense),entry(TinkerModifiers.magnetic.getId())),
    COMPRESSED_IRON_ARMOR(MaterialRegistry.ARMOR,entry(ModifierIds.blastProtection),entry(ModifierIds.projectileProtection)),

    PINK_SLIME_METAL(null,entry(TinkerModifiers.overslime.getId()),entry(TiAcModifiers.RETURN_TO_SLIME.getId())),

    NUTRITIVE_SLIMESTEEL(null,entry(TinkerModifiers.overslime.getId()),entry(TiAcModifiers.NUTRITIVE_SLIME.getId())),

    CINDER_SLIME_FLUX_CORE(FluxCoreMaterialStat.ID,entry(TinkerModifiers.overslime.getId()),entry(TiAcModifiers.OVERSLIME_GENERATOR.getId(),2)),

    PIG_IRON_FLUX_CORE(FluxCoreMaterialStat.ID,entry(TiAcModifiers.ELECTRIC_FOOD.getId())),

    COBALT_FLUX_CORE(FluxCoreMaterialStat.ID,entry(ModifierIds.lightweight),entry(TiAcModifiers.TRANSITION_CATALYST.getId())),

    STIBNITE_FLUX_CORE(FluxCoreMaterialStat.ID,entry(TiAcModifiers.UNSTABLE.getId()),entry(TiAcModifiers.PIEZOELECTRIC_EFFECT.getId(),4)),
    STIBNITE_DEFAULT(null,entry(TiAcModifiers.UNSTABLE.getId()),entry(EtSTLibModifier.ANISOTROPY.getId())),

    ANTIMONY_DEFAULT(null,entry(TiAcModifiers.PLAGUE.getId()),entry(ModifierIds.heavy)),
    ANTIMONY_ARMOR(MaterialRegistry.ARMOR,entry(TiAcModifiers.POISON_DEFENSE.getId())),

    VOID_STEEL_DEFAULT(null,entry(EtSTLibModifier.GLOBAL_TRAVELER.getId()),entry(TiAcModifiers.METAMORPHIUM.getId())),
    VOID_STEEL_ARMOR(MaterialRegistry.ARMOR,entry(TiAcModifiers.VOID_DODGING.getId()),entry(TiAcModifiers.METAMORPHIUM.getId())),
    ;

    public final ModifierEntry[] modifiers;
    public final MaterialStatsId statType;
    EnumMaterialModifier(MaterialStatsId statType, ModifierEntry... modifiers){
        this.modifiers = modifiers;
        this.statType = statType;
    }
    public static ModifierEntry entry(ModifierId id,int level){
        return new ModifierEntry(id,level);
    }
    public static ModifierEntry entry(ModifierId id){
        return new ModifierEntry(id,1);
    }
}
