package com.c2h6s.tinkers_advanced.data.providers.tinker;

import com.c2h6s.tinkers_advanced.data.enums.EnumModifier;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;

import java.util.Arrays;

public class TiAcModifierProvider extends AbstractModifierProvider {
    public TiAcModifierProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void addModifiers() {
        Arrays.stream(EnumModifier.values()).toList().forEach((enumModifier -> {
            buildModifier(enumModifier.id,enumModifier.condition).addModules(enumModifier.modules).tooltipDisplay(enumModifier.tooltipDisplay);
        }));
    }

    @Override
    public String getName() {
        return "TiAc Modifier Provider";
    }
}
