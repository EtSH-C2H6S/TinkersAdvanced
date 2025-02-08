package com.c2h6s.tinkers_advanced.content.modifier.combat;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

public class Anisotropy extends EtSTBaseModifier {
    public float getBonus(IToolStackView tool,int lvl){
        return 1+tool.getCurrentDurability()*lvl/((tool.getCurrentDurability()+tool.getDamage())*4f);
    }
    @Override
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        damage += baseDamage*getBonus(tool,modifier.getLevel())*(context.isFullyCharged()?1:-1);
        return damage;
    }
    @Override
    public void modifierProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        if (arrow!=null){
            arrow.setBaseDamage(arrow.getBaseDamage()+arrow.getBaseDamage()*getBonus(tool,modifier.getLevel())*(arrow.isCritArrow()?0.5:-0.5));
        }
    }
}
