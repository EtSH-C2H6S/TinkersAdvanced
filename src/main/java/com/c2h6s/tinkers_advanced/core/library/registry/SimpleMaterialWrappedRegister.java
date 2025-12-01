package com.c2h6s.tinkers_advanced.core.library.registry;

import lombok.Getter;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.deferred.ItemDeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class SimpleMaterialWrappedRegister {
    @Getter
    protected final ItemDeferredRegister itemRegister;
    @Getter
    protected final FluidDeferredRegister fluidRegister;
    @Getter
    protected final Map<String,SimpleMaterialObject> entryMap = new ConcurrentHashMap<>();
    public final String modId;
    public SimpleMaterialWrappedRegister(String modID){
        this.itemRegister = new ItemDeferredRegister(modID);
        this.fluidRegister = new FluidDeferredRegister(modID);
        this.modId = modID;
    }
    public void register(IEventBus modEventBus){
        this.itemRegister.register(modEventBus);
        this.fluidRegister.register(modEventBus);
    }
    public SimpleMaterialObject.Builder buildMaterial(String name){
        return new SimpleMaterialObject.Builder(this.modId,name,this);
    }
}
