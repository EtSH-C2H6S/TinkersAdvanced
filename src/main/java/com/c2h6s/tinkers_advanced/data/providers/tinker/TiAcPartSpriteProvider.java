package com.c2h6s.tinkers_advanced.data.providers.tinker;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.item.tinkering.materialStat.FluxCoreMaterialStat;
import slimeknights.tconstruct.library.client.data.material.AbstractPartSpriteProvider;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

public class TiAcPartSpriteProvider extends AbstractPartSpriteProvider {
    public TiAcPartSpriteProvider() {
        super(TinkersAdvanced.MODID);
    }

    @Override
    public String getName() {
        return "Tinkers' Advanced Part Sprite Provider";
    }

    @Override
    protected void addAllSpites() {
        addSprite("part/ionize_chamber/ionize_chamber", HandleMaterialStats.ID);
        buildTool("ionized_cannon").addBreakableHead("broad_blade_1").addBreakableHead("broad_blade_2").addBreakablePart("ionize_chamber",HandleMaterialStats.ID).addBinding("tough_collar");
        addSprite("part/particle_container/particle_container", HandleMaterialStats.ID);
        buildTool("matter_manipulator").addHead("pick_head").addBinding("tough_collar").addHandle("tough_handle").addHandle("particle_container");
        addSprite("part/flux_core/flux_core", StatlessMaterialStats.REPAIR_KIT.getIdentifier());
        buildTool("electron_tuner").addBreakableHead("small_blade")
                .addHead("mode_cleaver/small_blade")
                .addHead("mode_rapier/small_blade")
                .addHead("mode_sword/small_blade")
                .addPart("flux_core",StatlessMaterialStats.REPAIR_KIT.getIdentifier())
                .addPart("mode_cleaver/flux_core",StatlessMaterialStats.REPAIR_KIT.getIdentifier())
                .addPart("mode_rapier/flux_core",StatlessMaterialStats.REPAIR_KIT.getIdentifier())
                .addPart("mode_sword/flux_core",StatlessMaterialStats.REPAIR_KIT.getIdentifier())
                .addHandle("tool_handle")
                .addHandle("tough_handle");
    }
}
