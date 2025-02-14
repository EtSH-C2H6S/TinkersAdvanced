package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;

import static slimeknights.tconstruct.library.tools.stat.ToolStats.register;

public class TiAcToolStats {
    private static ToolStatId name(String name) {
        return new ToolStatId(TinkersAdvanced.MODID, name);
    }

    public TiAcToolStats(){
    }

    public static final FloatToolStat FLUID_EFFICIENCY;
    public static final FloatToolStat SCALE;
    public static final FloatToolStat RANGE;

    static {
        FLUID_EFFICIENCY = register(new FloatToolStat(name("fluid_efficiency"), -3135232, 1.0F, 0.0F, 64.0F));
        RANGE = register(new FloatToolStat(name("range"), -3135232, 8.0F, 0.0F, 64.0F));
        SCALE = register(new FloatToolStat(name("scale"), -3135232, 1.0F, 0.0F, 64.0F));
    }
}
