package com.c2h6s.tinkers_advanced.content.modifier.durability;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class Fragile extends EtSTBaseModifier {
    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        if (RANDOM.nextInt(10)<modifier.getLevel()){
            amount+=1;
        }
        return amount;
    }
}

