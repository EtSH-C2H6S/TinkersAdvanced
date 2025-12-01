package com.c2h6s.tinkers_advanced.core;

import com.c2h6s.etstlib.content.misc.entityTicker.EntityTicker;
import com.c2h6s.etstlib.content.register.EtSTLibRegistries;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import com.c2h6s.tinkers_advanced.core.data.condition.ConfigCondition;
import com.c2h6s.tinkers_advanced.core.init.TiAcCrConditions;
import com.c2h6s.tinkers_advanced.core.init.TiAcCrItem;
import com.c2h6s.tinkers_advanced.core.init.TiAcCrParticleTypes;
import com.c2h6s.tinkers_advanced.core.init.TiAcCrTabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import slimeknights.mantle.registration.deferred.EntityTypeDeferredRegister;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;

import static com.c2h6s.tinkers_advanced.TinkersAdvanced.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,modid = MODID)
public class TiAcCrModule {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);
    public static final DeferredRegister<EntityTicker> TICKERS = DeferredRegister.create(EtSTLibRegistries.ENTITY_TICKER, MODID);
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
    public static final EntityTypeDeferredRegister ENTITIES = new EntityTypeDeferredRegister(MODID);
    public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(MODID);

    public static void register(IEventBus bus){
        bus.post(new TiAcLoadRegistryClassEvent());
        TiAcCrItem.ITEMS.register(bus);
        TiAcCrTabs.CREATIVE_MODE_TABS.register(bus);
        BLOCKS.register(bus);
        BLOCK_ENTITIES.register(bus);
        EFFECTS.register(bus);
        TiAcCrParticleTypes.PARTICLES.register(bus);
        TICKERS.register(bus);
        FLUIDS.register(bus);
        MENUS.register(bus);
        MODIFIERS.register(bus);
        ENTITIES.register(bus);
    }
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event){

    }
    @SubscribeEvent
    public static void onRegister(RegisterEvent event){
        if (event.getRegistryKey()== Registries.RECIPE_SERIALIZER){
            TinkersAdvanced.LOGGER.info("TiAc on RegisterEvent");
            CraftingHelper.register(ConfigCondition.SERIALIZER);
            TiAcCrConditions.init();
        }
    }
}
