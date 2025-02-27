package com.c2h6s.tinkers_advanced.data.enums;

import com.c2h6s.etstlib.register.EtSTLibModifier;
import com.c2h6s.tinkers_advanced.registery.TiAcModifiers;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.tools.data.ModifierIds;

import static com.c2h6s.tinkers_advanced.util.MaterialStatIdConstants.*;

public enum EnumMaterialModifier {
    ALLOY_ATOMIC(BINDING,entry(EtSTLibModifier.atomic_decompose.getId())),
    BISMUTH(DEFAULT,entry(TiAcModifiers.TETANUS.getId()),entry(ModifierIds.heavy))
    ;
    private final ModifierEntry[] modifiers;
    private final String statType;
    EnumMaterialModifier(String statType, ModifierEntry... modifiers){
        this.modifiers = modifiers;
        this.statType = statType;
    }
    EnumMaterialModifier(MaterialStatsId id,ModifierEntry... modifiers){
        this.modifiers = modifiers;
        this.statType = id.getNamespace()+":"+id.getPath();
    }
    public static ModifierEntry entry(ModifierId id,int level){
        return new ModifierEntry(id,level);
    }
    public static ModifierEntry entry(ModifierId id){
        return new ModifierEntry(id,1);
    }
}
