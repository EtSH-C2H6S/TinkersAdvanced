package com.c2h6s.tinkers_advanced.registery;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.c2h6s.tinkers_advanced.TinkersAdvanced.MODID;

public class TiAcBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final RegistryObject<Block> BISMUTHINITE = BLOCKS.register("bismuthinite_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Block> BISMUTHINITE_DEEPSLATE = BLOCKS.register("bismuthinite_ore_deepslate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)));
}
