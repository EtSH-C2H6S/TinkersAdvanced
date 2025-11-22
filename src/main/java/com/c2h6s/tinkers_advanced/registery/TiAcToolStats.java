package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.etstlib.register.EtSTLibToolStat;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

@Deprecated(forRemoval = true)
public class TiAcToolStats {
    public static final FloatToolStat FLUID_EFFICIENCY = EtSTLibToolStat.FLUID_EFFICIENCY;
    public static final FloatToolStat SCALE = EtSTLibToolStat.SCALE;
    public static final FloatToolStat RANGE = EtSTLibToolStat.RANGE;
    public static final FloatToolStat POWER_MULTIPLIER = EtSTLibToolStat.POWER_MULTIPLIER;
    public static final FloatToolStat PIERCE = EtSTLibToolStat.PIERCE;
}
