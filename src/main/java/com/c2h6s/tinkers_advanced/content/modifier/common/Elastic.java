package com.c2h6s.tinkers_advanced.content.modifier.common;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Random;

public class Elastic extends EtSTBaseModifier {
    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public int modifierDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        Random random = new Random();
        if (random.nextInt(10)<modifier.getLevel()){
            tool.setDamage(Math.max(0,tool.getDamage()-amount*2));
            return 0;
        }
        return amount;
    }
}
