package com.c2h6s.tinkers_advanced.mixin.TconMixin;

import com.c2h6s.tinkers_advanced.content.item.tinkering.TiAcToolDefinitions;
import com.c2h6s.tinkers_advanced.content.item.toolItem.ElectronTunerItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.tools.capability.ToolEnergyCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.function.Supplier;

@Mixin(value = ToolEnergyCapability.class,remap = false)
public class ToolEnergyCapabilityMixin {
    @Shadow @Final private Supplier<? extends IToolStackView> tool;

    @Inject(method = "receiveEnergy",at = @At("HEAD"), cancellable = true)
    public void cancelInsert(int maxReceive, boolean simulate, CallbackInfoReturnable<Integer> cir){
        IToolStackView tool = this.tool.get();
        if (tool.getPersistentData().getBoolean(ElectronTunerItem.KEY_DISALLOW_INSERT)) cir.setReturnValue(0);
    }
}
