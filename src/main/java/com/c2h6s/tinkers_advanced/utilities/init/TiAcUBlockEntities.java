package com.c2h6s.tinkers_advanced.utilities.init;

import com.c2h6s.tinkers_advanced.core.TiAcCrModule;
import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import com.c2h6s.tinkers_advanced.utilities.content.block.blockEntity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiAcUBlockEntities {
    @SubscribeEvent
    public static void init(TiAcLoadRegistryClassEvent event){}
    public static final RegistryObject<BlockEntityType<ExchangerBlockEntity>> EXCHANGER_BLOCK_ENTITY =
            TiAcCrModule.BLOCK_ENTITIES.register("exchanger",()->
                    BlockEntityType.Builder.of(ExchangerBlockEntity::new, TiAcUBlocks.EXCHANGER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<RoseGoldCastingBlockEntity.Table>> ROSE_GOLD_TABLE =
            TiAcCrModule.BLOCK_ENTITIES.register("rose_gold_casting_table_be",()->
                    BlockEntityType.Builder.of(RoseGoldCastingBlockEntity.Table::new, TiAcUBlocks.ROSE_GOLD_TABLE.get())
                            .build(null));
    public static final RegistryObject<BlockEntityType<RoseGoldCastingBlockEntity.Basin>> ROSE_GOLD_SLIME_BASIN =
            TiAcCrModule.BLOCK_ENTITIES.register("rose_gold_casting_basin_be",()->
                    BlockEntityType.Builder.of(RoseGoldCastingBlockEntity.Basin::new, TiAcUBlocks.ROSE_GOLD_BASIN.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<HepatizonCastingBlockEntity.Table>> HEPATIZON_TABLE =
            TiAcCrModule.BLOCK_ENTITIES.register("hepatizon_casting_table_be",()->
                    BlockEntityType.Builder.of(HepatizonCastingBlockEntity.Table::new, TiAcUBlocks.HEPATIZON_TABLE.get())
                            .build(null));
    public static final RegistryObject<BlockEntityType<HepatizonCastingBlockEntity.Basin>> HEPATIZON_BASIN =
            TiAcCrModule.BLOCK_ENTITIES.register("hepatizon_casting_basin_be",()->
                    BlockEntityType.Builder.of(HepatizonCastingBlockEntity.Basin::new, TiAcUBlocks.HEPATIZON_BASIN.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<RoseGoldFaucetBlockEntity>> ROSE_GOLD_FAUCET =
            TiAcCrModule.BLOCK_ENTITIES.register("rose_gold_faucet_be",()->
                    BlockEntityType.Builder.of(RoseGoldFaucetBlockEntity::new, TiAcUBlocks.ROSE_GOLD_FAUCET.get())
                            .build(null));
    public static final RegistryObject<BlockEntityType<HepatizonFaucetBlockEntity>> HEPATIZON_FAUCET =
            TiAcCrModule.BLOCK_ENTITIES.register("hepatizon_faucet_be",()->
                    BlockEntityType.Builder.of(HepatizonFaucetBlockEntity::new, TiAcUBlocks.HEPATIZON_FAUCET.get())
                            .build(null));
}
