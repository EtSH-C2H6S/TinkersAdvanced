package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.core.TiAcCrModule;
import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import com.c2h6s.tinkers_advanced.utilities.content.block.blockEntity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiAcBlockEntities {
    @SubscribeEvent
    public static void init(TiAcLoadRegistryClassEvent event){}
    public static final RegistryObject<BlockEntityType<ExchangerBlockEntity>> EXCHANGER_BLOCK_ENTITY = TiAcCrModule.BLOCK_ENTITIES.register("exchanger",()->BlockEntityType.Builder.of(ExchangerBlockEntity::new,TiAcBlocks.EXCHANGER.get()).build(null));

    public static final RegistryObject<BlockEntityType<CinderSlimeCastingBlockEntity.Table>> CINDER_SLIME_TABLE = TiAcCrModule.BLOCK_ENTITIES
            .register("cinderslime_casting_table_be",()->
                    BlockEntityType.Builder.of(CinderSlimeCastingBlockEntity.Table::new,TiAcBlocks.CINDERSLIME_TABLE.get())
                            .build(null));
    public static final RegistryObject<BlockEntityType<CinderSlimeCastingBlockEntity.Basin>> CINDER_SLIME_BASIN = TiAcCrModule.BLOCK_ENTITIES
            .register("cinderslime_casting_basin_be",()->
                    BlockEntityType.Builder.of(CinderSlimeCastingBlockEntity.Basin::new,TiAcBlocks.CINDERSLIME_BASIN.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<IridiumCastingBlockEntity.Table>> IRIDIUM_TABLE = TiAcCrModule.BLOCK_ENTITIES
            .register("iridium_casting_table_be",()->
                    BlockEntityType.Builder.of(IridiumCastingBlockEntity.Table::new,TiAcBlocks.IRIDIUM_TABLE.get())
                            .build(null));
    public static final RegistryObject<BlockEntityType<IridiumCastingBlockEntity.Basin>> IRIDIUM_BASIN = TiAcCrModule.BLOCK_ENTITIES
            .register("iridium_casting_basin_be",()->
                    BlockEntityType.Builder.of(IridiumCastingBlockEntity.Basin::new,TiAcBlocks.IRIDIUM_BASIN.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<CinderSlimeFaucetBlockEntity>> CINDER_SLIME_FAUCET = TiAcCrModule.BLOCK_ENTITIES
            .register("cinderslime_faucet_be",()->
                    BlockEntityType.Builder.of(CinderSlimeFaucetBlockEntity::new,TiAcBlocks.CINDERSILME_FAUCET.get()).build(null));
    public static final RegistryObject<BlockEntityType<IridiumFaucetBlockEntity>> IRIDIUM_FAUCET = TiAcCrModule.BLOCK_ENTITIES
            .register("iridium_faucet_be",()->
                    BlockEntityType.Builder.of(IridiumFaucetBlockEntity::new,TiAcBlocks.IRIDIUM_FAUCET.get()).build(null));
}
