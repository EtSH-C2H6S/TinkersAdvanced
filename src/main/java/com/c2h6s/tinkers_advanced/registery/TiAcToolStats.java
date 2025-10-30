package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
    public static final FloatToolStat PIERCE;

//    static {
//        FLUID_EFFICIENCY = new FloatToolStat(name("fluid_efficiency"), -3135232, 0.0F, Integer.MIN_VALUE, 1.0F,null);
//        RANGE = new FloatToolStat(name("range"), -3135232, 12.0F, 0.0F, 64.0F,null);
//        SCALE = new FloatToolStat(name("scale"), -3135232, 1.0F, 0.0F, 8.0F,null);
//        POWER_MULTIPLIER = new FloatToolStat(name("power_multiplier"), -3135232, 1.0F, 0.0F, Integer.MAX_VALUE,null);
//    }

    static {
        FLUID_EFFICIENCY = ToolStats.register(new FloatToolStat(name("fluid_efficiency"), 0xC8FF5D, 0.0F, Integer.MIN_VALUE, 1.0F,null));
        RANGE =ToolStats.register( new FloatToolStat(name("range"), 0x4BFFAB, 12.0F, 0.0F, 64.0F,null));
        SCALE =ToolStats.register( new FloatToolStat(name("scale"), 0xA584FF, 1.0F, 0.0F, 8.0F,null));
        POWER_MULTIPLIER =ToolStats.register( new FloatToolStat(name("power_multiplier"), 0xFF888A, 1.0F, 0.0F, Integer.MAX_VALUE,null));
        PIERCE = ToolStats.register(new FloatToolStat(name("pierce"),0x7C6CFF,0,0,2048,null));
    }

//    @SubscribeEvent
//    public static void register(RegisterEvent event){
//        ToolStats.register(FLUID_EFFICIENCY);
//        ToolStats.register(RANGE);
//        ToolStats.register(SCALE);
//        ToolStats.register(POWER_MULTIPLIER);
//    }
}
