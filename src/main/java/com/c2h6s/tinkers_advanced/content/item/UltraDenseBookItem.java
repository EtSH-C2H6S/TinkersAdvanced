package com.c2h6s.tinkers_advanced.content.item;

import com.c2h6s.tinkers_advanced.client.book.TiAcBookData;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.mantle.item.LecternBookItem;

import static slimeknights.tconstruct.library.tools.capability.inventory.InventorySlotMenuModule.isValidContainer;

public class UltraDenseBookItem extends LecternBookItem {
    private final Multimap<Attribute, AttributeModifier> attributes;
    public UltraDenseBookItem(Properties properties) {
        super(properties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", Integer.MIN_VALUE-3f, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier",Integer.MIN_VALUE-3f, AttributeModifier.Operation.ADDITION));
        this.attributes = builder.build();
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
