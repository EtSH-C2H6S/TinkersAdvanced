package com.c2h6s.tinkers_advanced.core.library.data;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.tools.stats.PlatingMaterialStats;

public record MaterialStatsObject(IMaterialStats[] stats,@Nullable PlatingMaterialStats.Builder armorBuilder, boolean allowShield) {
}
