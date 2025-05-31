package com.c2h6s.tinkers_advanced.content.capability;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.capability.inventory.ToolInventoryCapability;
import slimeknights.tconstruct.library.tools.nbt.IModDataView;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import java.util.function.Supplier;

import static slimeknights.tconstruct.library.tools.capability.inventory.InventoryModule.GET_COMPOUND_LIST;
import static slimeknights.tconstruct.library.tools.capability.inventory.InventoryModule.writeStack;

public class ElectronTunerItemCapabilityProvider extends ToolInventoryCapability {
    public final boolean canExtractFromInput;
    public ElectronTunerItemCapabilityProvider(Supplier<? extends IToolStackView> tool, boolean canExtractFromInput) {
        super(tool);
        this.canExtractFromInput = canExtractFromInput;
    }
    public static final ResourceLocation LOCATION = TinkersAdvanced.getLocation("electron_tuner_inventory");

    public final InventoryModifierHook inventory = new InventoryModifierHook() {
        @Override
        public int getSlots(IToolStackView iToolStackView, ModifierEntry modifierEntry) {
            return 16;
        }

        @Override
        public ItemStack getStack(IToolStackView tool, ModifierEntry modifier, int slot) {
            IModDataView modData = tool.getPersistentData();
            ResourceLocation key = LOCATION;
            if (slot < this.getSlots(tool, modifier) && modData.contains(key, 9)) {
                ListTag list = tool.getPersistentData().get(key, GET_COMPOUND_LIST);

                for(int i = 0; i < list.size(); ++i) {
                    CompoundTag compound = list.getCompound(i);
                    if (compound.getInt("Slot") == slot) {
                        return ItemStack.of(compound);
                    }
                }
            }

            return ItemStack.EMPTY;
        }

        @Override
        public void setStack(IToolStackView tool, ModifierEntry modifier, int slot, ItemStack stack) {
            if (slot < this.getSlots(tool, modifier)) {
                ModDataNBT modData = tool.getPersistentData();
                ResourceLocation key = LOCATION;
                ListTag list;
                if (modData.contains(key, 9)) {
                    list = modData.get(key, GET_COMPOUND_LIST);

                    for(int i = 0; i < list.size(); ++i) {
                        CompoundTag compound = list.getCompound(i);
                        if (compound.getInt("Slot") == slot) {
                            if (stack.isEmpty()) {
                                list.remove(i);
                            } else {
                                compound.getAllKeys().clear();
                                writeStack(stack, slot, compound);
                            }

                            return;
                        }
                    }
                } else {
                    if (stack.isEmpty()) {
                        return;
                    }

                    list = new ListTag();
                    modData.put(key, list);
                }

                if (!stack.isEmpty()) {
                    list.add(writeStack(stack, slot, new CompoundTag()));
                }
            }

        }
    };

    @Override
    public int getSlots() {
        return 16;
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (slot<8&&!canExtractFromInput) return ItemStack.EMPTY;
        return super.extractItem(slot, amount, simulate);
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (slot>=8&&!canExtractFromInput) return stack;
        return super.insertItem(slot, stack, simulate);
    }

    @Nullable
    @Override
    protected InventoryModifierHook findHook(IToolStackView tool, int index) {
        return this.inventory ;
    }

    @Override
    protected InventoryModifierHook getHook(ModifierEntry entry) {
        return this.inventory;
    }
}
