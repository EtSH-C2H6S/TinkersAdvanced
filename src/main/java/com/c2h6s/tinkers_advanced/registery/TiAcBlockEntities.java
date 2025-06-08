package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.block.blockEntity.ExchangerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TiAcBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TinkersAdvanced.MODID);

    public static final RegistryObject<BlockEntityType<ExchangerBlockEntity>> EXCHANGER_BLOCK_ENTITY = BLOCK_ENTITIES.register("exchanger",()->BlockEntityType.Builder.of(ExchangerBlockEntity::new,TiAcBlocks.EXCHANGER.get()).build(null));
}
