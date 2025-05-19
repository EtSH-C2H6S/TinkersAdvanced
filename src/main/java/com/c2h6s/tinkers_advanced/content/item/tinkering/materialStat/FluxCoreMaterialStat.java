package com.c2h6s.tinkers_advanced.content.item.tinkering.materialStat;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.registery.TiAcToolStats;
import net.minecraft.network.chat.Component;
import slimeknights.mantle.data.loadable.primitive.FloatLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.capability.ToolEnergyCapability;
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

import java.util.List;

public record FluxCoreMaterialStat(float capacityFactor, float generateFactor) implements IMaterialStats {
    private static final String CAPACITY_PREFIX = IMaterialStats.makeTooltipKey(TinkersAdvanced.getLocation("capacity_factor"));
    private static final String GENERATOR_PREFIX = IMaterialStats.makeTooltipKey(TinkersAdvanced.getLocation("generate_factor"));
    public static final MaterialStatsId ID = new MaterialStatsId(TinkersAdvanced.getLocation("flux_core"));
    public static final MaterialStatType<FluxCoreMaterialStat> TYPE = new MaterialStatType<>(ID,new FluxCoreMaterialStat(0,0), RecordLoadable.create(
            FloatLoadable.ANY.defaultField("capacity",0f,true,FluxCoreMaterialStat::capacityFactor),
            FloatLoadable.ANY.defaultField("generate",0f,true,FluxCoreMaterialStat::generateFactor),
            FluxCoreMaterialStat::new
    ));
    private static final List<Component> DESCRIPTION = List.of(
            IMaterialStats.makeTooltip(TinkersAdvanced.getLocation("flux_core.capacity_factor.description")),
            IMaterialStats.makeTooltip(TinkersAdvanced.getLocation("flux_core.generate_factor.description"))
    );

    @Override
    public MaterialStatType<?> getType() {
        return TYPE;
    }

    @Override
    public List<Component> getLocalizedInfo() {
        return List.of(
                IToolStat.formatColoredMultiplier(CAPACITY_PREFIX, this.capacityFactor),
                IToolStat.formatColoredMultiplier(GENERATOR_PREFIX, this.generateFactor)
        );
    }

    @Override
    public List<Component> getLocalizedDescriptions() {
        return DESCRIPTION;
    }

    @Override
    public void apply(ModifierStatsBuilder modifierStatsBuilder, float v) {
        ToolEnergyCapability.MAX_STAT.percent(modifierStatsBuilder,v*this.capacityFactor);
        TiAcToolStats.POWER_MULTIPLIER.percent(modifierStatsBuilder,v*this.generateFactor);
    }
}
