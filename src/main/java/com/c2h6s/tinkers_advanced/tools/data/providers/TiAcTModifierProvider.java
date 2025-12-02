package com.c2h6s.tinkers_advanced.tools.data.providers;

import com.c2h6s.tinkers_advanced.tools.data.enums.TiAcTEnumModifier;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;

import java.util.Arrays;

public class TiAcTModifierProvider extends AbstractModifierProvider {
    public TiAcTModifierProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void addModifiers() {
        Arrays.stream(TiAcTEnumModifier.values()).toList().forEach((enumModifier -> {
            buildModifier(enumModifier.id,enumModifier.condition).addModules(enumModifier.modules).tooltipDisplay(enumModifier.tooltipDisplay);
        }));
    }

    @Override
    public String getName() {
        return "TiAcT Modifier Provider";
    }
}
