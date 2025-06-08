package com.c2h6s.tinkers_advanced.data.enums;

import com.c2h6s.tinkers_advanced.data.TiAcModifierIds;
import com.c2h6s.tinkers_advanced.registery.TiAcItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.ICondition;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.json.IntRange;
import slimeknights.tconstruct.library.json.LevelingValue;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.impl.BasicModifier;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.modifiers.modules.util.ModifierCondition;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.capability.ToolEnergyCapability;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import static com.c2h6s.tinkers_advanced.data.TiAcModifierIds.*;

public enum EnumModifier {
    SWIFT_STRIKE_EX(TiAcModifierIds.SWIFT_STRIKE_EX,ModifierRecipeBuilder
            .modifier(TiAcModifierIds.SWIFT_STRIKE_EX)
            .setMaxLevel(5)
            .addInput(Items.AMETHYST_BLOCK,10)
            .addInput(Items.ECHO_SHARD)
            .setSlots(SlotType.UPGRADE,1)
            .setTools(Ingredient.of(TiAcItems.IONIZED_CANNON))
            .allowCrystal(),
            null,
            BasicModifier.TooltipDisplay.ALWAYS,
            ModifierLevelDisplay.DEFAULT,new StatBoostModule(ToolStats.ATTACK_SPEED, StatBoostModule.StatOperation.PERCENT,LevelingValue.eachLevel(0.25f),ModifierCondition.ANY_CONTEXT)
    )
    ;
    public final ModifierId id;
    public final ModifierRecipeBuilder builder;
    public final BasicModifier.TooltipDisplay tooltipDisplay;
    public final ModifierModule[] modules;
    public final ICondition condition;
    public final ModifierLevelDisplay display;

    EnumModifier(ModifierId id, ModifierRecipeBuilder builder, @Nullable ICondition condition, BasicModifier.TooltipDisplay tooltipDisplay, ModifierLevelDisplay display, ModifierModule... modules) {
        this.id = id;
        this.builder = builder;
        this.tooltipDisplay = tooltipDisplay;
        this.modules = modules;
        this.condition = condition;
        this.display = display;
    }
}
