package com.c2h6s.tinkers_advanced.data.providers.tinker;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.modifier.common.ReturnToSlime;
import com.c2h6s.tinkers_advanced.data.TiAcModifierIds;
import com.c2h6s.tinkers_advanced.data.TiAcTagkeys;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierTagProvider;
import slimeknights.tconstruct.tools.TinkerModifiers;

import static com.c2h6s.tinkers_advanced.registery.TiAcModifiers.*;

public class TiAcModifierTagProvider extends AbstractModifierTagProvider {
    public TiAcModifierTagProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
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
                        TiAcModifierIds.SWIFT_STRIKE_EX);
        this.tag(TinkerTags.Modifiers.OVERSLIME_FRIEND)
                .add(
                        RETURN_TO_SLIME.getId(),
                        NUTRITIVE_SLIME.getId(),
                        OVERSLIME_GENERATOR.getId()
                );
    }

    @Override
    public String getName() {
        return "Tinker's Advanced Modifier Tag Provider.";
    }
}
