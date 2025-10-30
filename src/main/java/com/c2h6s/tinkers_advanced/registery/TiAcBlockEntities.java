package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.etstlib.content.blockEntity.ConfigurableFaucetBlockEntity;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.block.blockEntity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.smeltery.block.entity.FaucetBlockEntity;

public class TiAcBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TinkersAdvanced.MODID);

    public static final RegistryObject<BlockEntityType<ExchangerBlockEntity>> EXCHANGER_BLOCK_ENTITY = BLOCK_ENTITIES.register("exchanger",()->BlockEntityType.Builder.of(ExchangerBlockEntity::new,TiAcBlocks.EXCHANGER.get()).build(null));

    public static final RegistryObject<BlockEntityType<CinderSlimeCastingBlockEntity.Table>> CINDER_SLIME_TABLE = BLOCK_ENTITIES
            .register("cinderslime_casting_table_be",()->
                    BlockEntityType.Builder.of(CinderSlimeCastingBlockEntity.Table::new,TiAcBlocks.CINDERSLIME_TABLE.get())
                            .build(null));
    public static final RegistryObject<BlockEntityType<CinderSlimeCastingBlockEntity.Basin>> CINDER_SLIME_BASIN = BLOCK_ENTITIES
            .register("cinderslime_casting_basin_be",()->
                    BlockEntityType.Builder.of(CinderSlimeCastingBlockEntity.Basin::new,TiAcBlocks.CINDERSLIME_BASIN.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<IridiumCastingBlockEntity.Table>> IRIDIUM_TABLE = BLOCK_ENTITIES
            .register("iridium_casting_table_be",()->
                    BlockEntityType.Builder.of(IridiumCastingBlockEntity.Table::new,TiAcBlocks.IRIDIUM_TABLE.get())
                            .build(null));
    public static final RegistryObject<BlockEntityType<IridiumCastingBlockEntity.Basin>> IRIDIUM_BASIN = BLOCK_ENTITIES
            .register("iridium_casting_basin_be",()->
                    BlockEntityType.Builder.of(IridiumCastingBlockEntity.Basin::new,TiAcBlocks.IRIDIUM_BASIN.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<CinderSlimeFaucetBlockEntity>> CINDER_SLIME_FAUCET = BLOCK_ENTITIES
            .register("cinderslime_faucet_be",()->
                    BlockEntityType.Builder.of(CinderSlimeFaucetBlockEntity::new,TiAcBlocks.CINDERSILME_FAUCET.get()).build(null));
    public static final RegistryObject<BlockEntityType<IridiumFaucetBlockEntity>> IRIDIUM_FAUCET = BLOCK_ENTITIES
            .register("iridium_faucet_be",()->
                    BlockEntityType.Builder.of(IridiumFaucetBlockEntity::new,TiAcBlocks.IRIDIUM_FAUCET.get()).build(null));
}
