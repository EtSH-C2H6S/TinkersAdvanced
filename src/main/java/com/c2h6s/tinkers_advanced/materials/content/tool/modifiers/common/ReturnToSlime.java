package com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.common;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ProcessLootModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.capacity.OverslimeModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class ReturnToSlime extends EtSTBaseModifier implements ProcessLootModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROCESS_LOOT);
    }

    @Override
    public int getPriority() {
        return -100;
    }

    @Override
    public void processLoot(IToolStackView iToolStackView, ModifierEntry modifierEntry, List<ItemStack> list, LootContext lootContext) {
        if (RANDOM.nextFloat()<modifierEntry.getLevel()* TiAcConfig.COMMON.RETURN_TO_SLIME_CHANCE.get()) {
            int i = 0;
            for (ItemStack stack : list) {
                i += stack.getCount();
            }
            if (i > 0) {
                var overslime = OverslimeModule.INSTANCE;
                overslime.addAmount(iToolStackView, modifierEntry, i * modifierEntry.getLevel());
            }
        }
    }
}
