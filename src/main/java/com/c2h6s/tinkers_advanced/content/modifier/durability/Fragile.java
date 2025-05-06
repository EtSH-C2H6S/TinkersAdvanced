package com.c2h6s.tinkers_advanced.content.modifier.durability;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class Fragile extends EtSTBaseModifier {
    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        double chance = TiAcConfig.COMMON.FRAGILE_CHANCE.get();
        int loss = TiAcConfig.COMMON.FRAGILE_EXTRA_COST.get();
        if (chance<=0||loss<=0) return amount;
        if (RANDOM.nextInt((int) (1/chance))<modifier.getLevel()){
            amount+=loss;
        }
        return amount;
    }
}

