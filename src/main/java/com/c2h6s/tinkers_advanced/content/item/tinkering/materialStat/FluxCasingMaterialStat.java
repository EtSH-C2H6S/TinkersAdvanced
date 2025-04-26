package com.c2h6s.tinkers_advanced.content.item.tinkering.materialStat;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.registery.TiAcToolStats;
import net.minecraft.network.chat.Component;
import slimeknights.mantle.data.loadable.primitive.IntLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.capability.ToolEnergyCapability;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

import java.util.List;

public record FluxCasingMaterialStat(int baseCapacity, int baseGenerate) implements IMaterialStats {
    public static final MaterialStatsId ID = new MaterialStatsId(TinkersAdvanced.getLocation("flux_casing"));
    private static final MaterialStatType<FluxCasingMaterialStat> TYPE = new MaterialStatType<>(ID,new FluxCasingMaterialStat(0,0), RecordLoadable.create(
            IntLoadable.FROM_ZERO.defaultField("capacity",0,true,FluxCasingMaterialStat::baseCapacity),
            IntLoadable.FROM_ZERO.defaultField("generate",0,true,FluxCasingMaterialStat::baseGenerate),
            FluxCasingMaterialStat::new
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
                ToolEnergyCapability.MAX_STAT.formatValue(this.baseCapacity),
                TiAcToolStats.POWER_MULTIPLIER.formatValue(this.baseGenerate)
        );
    }

    @Override
    public List<Component> getLocalizedDescriptions() {
        return DESCRIPTION;
    }

    @Override
    public void apply(ModifierStatsBuilder modifierStatsBuilder, float v) {
        ToolEnergyCapability.MAX_STAT.update(modifierStatsBuilder,v*this.baseCapacity);
        TiAcToolStats.POWER_MULTIPLIER.percent(modifierStatsBuilder,v*this.baseGenerate);
    }
}
