package com.c2h6s.tinkers_advanced.core.library.data;

import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;

import java.util.List;

public record MaterialModifiersObject(List<Pair<@Nullable MaterialStatsId, ModifierEntry[]>> modifierPairs) {
}
