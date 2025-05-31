package com.c2h6s.tinkers_advanced.content.modifier.harvest;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.EnchantmentModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.DisplayNameModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import java.util.Map;

public class ProtoRefining extends EtSTBaseModifier implements EnchantmentModifierHook, BlockBreakModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.ENCHANTMENTS,ModifierHooks.BLOCK_BREAK);
    }

    public static final ResourceLocation KEY_REFINE = TinkersAdvanced.getLocation("key_refining");

    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        ModDataNBT nbt = tool.getPersistentData();
        if (nbt.getInt(KEY_REFINE)>0&&!world.isClientSide&&world.getGameTime()%20==1){
            nbt.putInt(KEY_REFINE,nbt.getInt(KEY_REFINE)-1);
        }
    }

    public static int getBonus(IToolStackView tool,ModifierEntry entry){
        ModDataNBT nbt = tool.getPersistentData();
        return Mth.clamp(nbt.getInt(KEY_REFINE) / TiAcConfig.COMMON.PROTO_REFINING_TIMES_REQUIRED.get(),0,entry.getLevel()*TiAcConfig.COMMON.PROTO_REFINING_BONUS_LEVEL.get());
    }
    public static void addBonus(IToolStackView tool,ModifierEntry entry){
        ModDataNBT nbt = tool.getPersistentData();
        nbt.putInt(KEY_REFINE,Math.min(nbt.getInt(KEY_REFINE)+entry.getLevel(),entry.getLevel()*TiAcConfig.COMMON.PROTO_REFINING_TIMES_REQUIRED.get()*2*TiAcConfig.COMMON.PROTO_REFINING_BONUS_LEVEL.get()));
    }

    @Override
    public int updateEnchantmentLevel(IToolStackView tool, ModifierEntry modifierEntry, Enchantment enchantment, int level) {
        int bonus = getBonus(tool,modifierEntry);
        if (bonus>0&&(enchantment==Enchantments.MOB_LOOTING||enchantment==Enchantments.BLOCK_FORTUNE)){
            level+=bonus;
        }
        return level;
    }

    @Override
    public void updateEnchantments(IToolStackView tool, ModifierEntry modifierEntry, Map<Enchantment, Integer> map) {
        int bonus = getBonus(tool,modifierEntry);
        if (bonus>0){
            EnchantmentModifierHook.addEnchantment(map, Enchantments.BLOCK_FORTUNE,bonus);
            EnchantmentModifierHook.addEnchantment(map, Enchantments.MOB_LOOTING,bonus);
        }
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifierEntry, ToolHarvestContext toolHarvestContext) {
        addBonus(tool,modifierEntry);
    }
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        addBonus(tool,modifier);
    }

    @Override
    public Component getDisplayName(IToolStackView tool, ModifierEntry entry, @Nullable RegistryAccess access) {
        return super.getDisplayName(tool, entry, access).copy().append(" "+"+"+getBonus(tool,entry));
    }
}
