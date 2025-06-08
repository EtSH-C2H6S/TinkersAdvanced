package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.content.block.ExchangerBlock;
import com.c2h6s.tinkers_advanced.content.block.StibniteOreBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import static com.c2h6s.tinkers_advanced.TinkersAdvanced.MODID;

public class TiAcBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final RegistryObject<Block> BISMUTHINITE = BLOCKS.register("bismuthinite_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)));
    public static final RegistryObject<Block> IRIDIUM_LEAN_ORE = BLOCKS.register("iridium_lean_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.ANCIENT_DEBRIS).sound(SoundType.METAL).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> STIBNITE_ORE = BLOCKS.register("stibnite_ore", () -> new StibniteOreBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_QUARTZ_ORE).sound(SoundType.NETHER_ORE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> EXCHANGER = BLOCKS.register("exchanger", () -> new ExchangerBlock(BlockBehaviour.Properties.copy(TinkerSmeltery.searedBricks.get()).explosionResistance(10000).sound(SoundType.GLASS).noOcclusion()));
}
