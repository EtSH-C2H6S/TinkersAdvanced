package com.c2h6s.tinkers_advanced.data.enums;

public enum EnumMaterial {
    BISMUTH("bismuth",4,false,false,EnumMaterialStats.BISMUTH,EnumMaterialModifier.BISMUTH),

    ;
    public final String name;
    public final int tier;
    public final boolean craftable;
    public final boolean hidden;
    public final EnumMaterialStats stats;
    public final EnumMaterialModifier[] modifiers;
    EnumMaterial(String name,int tier,boolean craftable,boolean hidden,EnumMaterialStats stats,EnumMaterialModifier... modifiers){
        this.name = name;
        this.tier =tier;
        this.craftable = craftable;
        this.hidden = hidden;
        this.stats = stats;
        this.modifiers = modifiers;
    }

}
