package com.c2h6s.tinkers_advanced.content.modifier.compat.ae2;
/*
import appeng.items.tools.powered.AbstractPortableCell;
import appeng.menu.MenuOpener;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.misc.PocketCellLocator;
import com.c2h6s.tinkers_advanced.mixin.AEMixin.AbstractPortableCellAccessor;
import com.c2h6s.tinkers_advanced.network.packets.POpenLegCellC2S;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandlerModifiable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.mantle.data.predicate.item.ItemPredicate;
import slimeknights.tconstruct.library.json.IntRange;
import slimeknights.tconstruct.library.json.LevelingInt;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.KeybindInteractModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.util.ModifierCondition;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.recipe.partbuilder.Pattern;
import slimeknights.tconstruct.library.tools.capability.inventory.InventoryModule;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.concurrent.atomic.AtomicBoolean;

public class PocketCell extends EtSTBaseModifier implements KeybindInteractModifierHook {
    public static final ResourceLocation KEY = TinkersAdvanced.getLocation("pocket_cell");
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this,ModifierHooks.ARMOR_INTERACT);
        hookBuilder.addModule(new InventoryModule(KEY, LevelingInt.flat(1),LevelingInt.flat(1), ItemPredicate.simple((item)->item instanceof AbstractPortableCell), new Pattern(TinkersAdvanced.getLocation("portable_cell")), ModifierCondition.ANY_CONTEXT,new IntRange(0,Integer.MAX_VALUE)));
    }

    @Override
    public boolean startInteract(IToolStackView tool, ModifierEntry modifier, Player player, EquipmentSlot slot, TooltipKey keyModifier) {
        if (slot!=EquipmentSlot.LEGS) return false;
        ItemStack stack = player.getItemBySlot(slot);
        AtomicBoolean shouldInteract = new AtomicBoolean(false);
        stack.getCapability(ForgeCapabilities.ITEM_HANDLER).filter(cap->cap instanceof IItemHandlerModifiable).ifPresent(handler->{
            for (int i =0;i<handler.getSlots();i++){
                ItemStack itemStack = handler.getStackInSlot(i);
                if (itemStack.getItem() instanceof AbstractPortableCell){
                    shouldInteract.set(true);
                }
            }
        });
        return shouldInteract.get();
    }

    @Override
    public void stopInteract(IToolStackView tool, ModifierEntry modifier, Player player, EquipmentSlot slot) {
        if (slot != EquipmentSlot.LEGS) return;
        ItemStack stack = player.getItemBySlot(slot);
        stack.getCapability(ForgeCapabilities.ITEM_HANDLER).filter(cap -> cap instanceof IItemHandlerModifiable).ifPresent(handler -> {
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack itemStack = handler.getStackInSlot(i);
                if (itemStack.getItem() instanceof AbstractPortableCell cell) {
                    boolean client = player.level().isClientSide;
                    player.sendSystemMessage(Component.literal(String.valueOf(client)));
                    if (!client) {
                        player.sendSystemMessage(Component.literal("1"));
                        boolean b = MenuOpener.open(((AbstractPortableCellAccessor) cell).getMenuType(), player, new PocketCellLocator(i, null));
                        player.sendSystemMessage(Component.literal("Opened:"+b));
                    }
                }
            }
        });
    }
}

 */
