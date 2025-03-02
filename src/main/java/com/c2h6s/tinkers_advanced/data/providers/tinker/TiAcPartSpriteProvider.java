package com.c2h6s.tinkers_advanced.data.providers.tinker;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import slimeknights.tconstruct.library.client.data.material.AbstractPartSpriteProvider;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;

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
        buildTool("ionized_cannon").addBreakableHead("broad_blade_1").addBreakablePart("broad_blade_2").addBreakablePart("ionize_chamber",HandleMaterialStats.ID).addBinding("tough_collar");
    }
}
