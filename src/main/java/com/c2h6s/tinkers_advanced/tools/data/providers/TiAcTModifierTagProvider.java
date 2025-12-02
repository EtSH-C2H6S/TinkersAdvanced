package com.c2h6s.tinkers_advanced.tools.data.providers;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.tools.data.TiAcTModifierIds;
import com.c2h6s.tinkers_advanced.materials.data.TiAcTagkeys;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierTagProvider;
import slimeknights.tconstruct.tools.TinkerModifiers;

import static com.c2h6s.tinkers_advanced.registery.TiAcModifiers.*;

public class TiAcTModifierTagProvider extends AbstractModifierTagProvider {
    public TiAcTModifierTagProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, TinkersAdvanced.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(TiAcTagkeys.Modifiers.GENERATOR_MODIFIERS)
                .add(
                        COMBUSTION_GENERATOR.getId(),
                        OVERSLIME_GENERATOR.getId(),
                        SMELTERY_GENERATOR.getId(),
                        PLATINOID_CATALYST.getId(),
                        TRANSITION_CATALYST.getId(),
                        ENERGY_BIN.getId(),
                        ENERGY_DISTRIBUTOR.getId()
                ).addOptional(COMPRESSED_AIR_GENERATOR.getId());
        this.tag(TiAcTagkeys.Modifiers.SPECIAL_TOOL)
                .add(RESONANCE_AMPLIFIER.getId(),
                        DEEP_CATALYST.getId(),
                        EXTRA_CAPACITY.getId(),
                        FOCUSING_ARRAY.getId(),
                        AUTO_SHOT.getId(),
                        PLAYER_LOCATING.getId(),
                        TinkerModifiers.expanded.getId(),
                        TinkerModifiers.sweeping.getId(),
                        TiAcTModifierIds.SWIFT_STRIKE_EX);
    }

    @Override
    public String getName() {
        return "Tinker's Advanced-Tools Modifier Tag Provider.";
    }
}
