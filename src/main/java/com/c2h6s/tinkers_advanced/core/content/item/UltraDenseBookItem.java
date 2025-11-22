package com.c2h6s.tinkers_advanced.core.content.item;

import com.c2h6s.tinkers_advanced.core.client.book.TiAcBookData;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import slimeknights.mantle.client.SafeClientAccess;
import slimeknights.mantle.item.LecternBookItem;
import slimeknights.tconstruct.TConstruct;

import javax.annotation.Nullable;
import java.util.List;

import static slimeknights.tconstruct.library.tools.capability.inventory.InventorySlotMenuModule.isValidContainer;

public class UltraDenseBookItem extends LecternBookItem {
    private static final Component CLICK_TO_OPEN = TConstruct.makeTranslation("item", "book.click_to_open").withStyle(ChatFormatting.YELLOW, ChatFormatting.ITALIC);

    private final Multimap<Attribute, AttributeModifier> attributes;
    public UltraDenseBookItem(Properties properties) {
        super(properties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", Integer.MIN_VALUE-3f, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier",Integer.MIN_VALUE-3f, AttributeModifier.Operation.ADDITION));
        this.attributes = builder.build();
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        if (world != null && world.isClientSide) {
            Player player = SafeClientAccess.getPlayer();
            if (player != null && isValidContainer(player.containerMenu)) {
                Inventory inventory = player.getInventory();
                if (inventory.items.contains(stack) || inventory.offhand.contains(stack)) {
                    tooltip.add(CLICK_TO_OPEN);
                }
            }
        }
        super.appendHoverText(stack, world, tooltip, flagIn);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (world.isClientSide) {
            TiAcBookData.ULTRA_DENSE_BOOK.openGui(hand,stack);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public void openLecternScreenClient(BlockPos pos, ItemStack stack) {
        TiAcBookData.ULTRA_DENSE_BOOK.openGui(pos,stack);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack held, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (action == ClickAction.SECONDARY && held.isEmpty() && slot.container == player.getInventory() && slot.allowModification(player) && isValidContainer(player.containerMenu)) {
            if (player.level().isClientSide) {
                player.containerMenu.resumeRemoteUpdates();
                player.closeContainer();
                TiAcBookData.ULTRA_DENSE_BOOK.openGui(slot.getSlotIndex(), stack);
            }
            return true;
        }
        return false;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pSlot) {
        return pSlot==EquipmentSlot.MAINHAND? this.attributes:super.getDefaultAttributeModifiers(pSlot);
    }
}
