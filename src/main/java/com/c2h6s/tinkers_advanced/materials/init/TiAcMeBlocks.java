package com.c2h6s.tinkers_advanced.materials.init;

import com.c2h6s.tinkers_advanced.core.TiAcCrModule;
import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import com.c2h6s.tinkers_advanced.materials.content.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiAcMeBlocks {
    @SubscribeEvent
    public static void init(TiAcLoadRegistryClassEvent event){}
    public static final RegistryObject<Block> BISMUTHINITE = TiAcCrModule.BLOCKS.register("bismuthinite_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)));
    public static final RegistryObject<Block> IRIDIUM_LEAN_ORE = TiAcCrModule.BLOCKS.register("iridium_lean_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.ANCIENT_DEBRIS).sound(SoundType.METAL).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> STIBNITE_ORE = TiAcCrModule.BLOCKS.register("stibnite_ore", () -> new StibniteOreBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_QUARTZ_ORE).sound(SoundType.NETHER_ORE).requiresCorrectToolForDrops()));
}
