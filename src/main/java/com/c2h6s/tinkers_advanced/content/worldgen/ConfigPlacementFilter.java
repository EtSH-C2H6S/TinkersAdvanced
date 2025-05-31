package com.c2h6s.tinkers_advanced.content.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ConfigPlacementFilter extends PlacementFilter {
    private final Supplier<Boolean> supplier;
    private final PlacementModifierType<?> type;
    public static Codec<PlacementModifier> getCodec(@NotNull Supplier<Boolean> supplier, @NotNull PlacementModifierType<?> type){
        return Codec.unit(() -> new ConfigPlacementFilter(supplier,type));
    }
    public ConfigPlacementFilter(@NotNull Supplier<Boolean> supplier,@NotNull PlacementModifierType<?> type){
        this.supplier = supplier;
        this.type = type;
    }

    @Override
    protected boolean shouldPlace(PlacementContext placementContext, RandomSource randomSource, BlockPos blockPos) {
        return this.supplier.get();
    }

    @Override
    public @NotNull PlacementModifierType<?> type() {
        return this.type;
    }
}
