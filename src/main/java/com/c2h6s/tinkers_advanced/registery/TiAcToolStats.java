package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

@Mod.EventBusSubscriber(modid = TinkersAdvanced.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiAcToolStats {
    private static ToolStatId name(String name) {
        return new ToolStatId(TinkersAdvanced.MODID, name);
    }

    public static final FloatToolStat FLUID_EFFICIENCY;
    public static final FloatToolStat SCALE;
    public static final FloatToolStat RANGE;
    public static final FloatToolStat POWER_MULTIPLIER;

    static {
        FLUID_EFFICIENCY = new FloatToolStat(name("fluid_efficiency"), -3135232, 0.0F, Integer.MIN_VALUE, 1.0F);
        RANGE = new FloatToolStat(name("range"), -3135232, 12.0F, 0.0F, 64.0F);
        SCALE = new FloatToolStat(name("scale"), -3135232, 1.0F, 0.0F, 8.0F);
        POWER_MULTIPLIER = new FloatToolStat(name("power_multiplier"), -3135232, 1.0F, 0.0F, Integer.MAX_VALUE);
    }

    public static void register(RegisterEvent event){
        ToolStats.register(FLUID_EFFICIENCY);
        ToolStats.register(RANGE);
        ToolStats.register(SCALE);
        ToolStats.register(POWER_MULTIPLIER);
    }
}
