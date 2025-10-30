package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.content.block.CinderSlimeFaucetBlock;
import com.c2h6s.tinkers_advanced.content.block.ExchangerBlock;
import com.c2h6s.tinkers_advanced.content.block.IridiumFaucetBlock;
import com.c2h6s.tinkers_advanced.content.block.StibniteOreBlock;
import com.c2h6s.tinkers_advanced.content.block.blockEntity.CinderSlimeCastingBlockEntity;
import com.c2h6s.tinkers_advanced.content.block.blockEntity.IridiumCastingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.smeltery.block.CastingBasinBlock;
import slimeknights.tconstruct.smeltery.block.CastingTableBlock;

import java.util.List;

import static com.c2h6s.tinkers_advanced.TinkersAdvanced.MODID;

public class TiAcBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final RegistryObject<Block> BISMUTHINITE = BLOCKS.register("bismuthinite_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)));
    public static final RegistryObject<Block> IRIDIUM_LEAN_ORE = BLOCKS.register("iridium_lean_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.ANCIENT_DEBRIS).sound(SoundType.METAL).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> STIBNITE_ORE = BLOCKS.register("stibnite_ore", () -> new StibniteOreBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_QUARTZ_ORE).sound(SoundType.NETHER_ORE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> EXCHANGER = BLOCKS.register("exchanger", () -> new ExchangerBlock(BlockBehaviour.Properties.copy(TinkerSmeltery.searedBricks.get()).explosionResistance(10000).sound(SoundType.GLASS).noOcclusion()));

    public static final RegistryObject<Block> CINDERSILME_FAUCET = BLOCKS.register("cinderslime_faucet", () ->
            new CinderSlimeFaucetBlock(BlockBehaviour.Properties.copy(TinkerSmeltery.searedFaucet.get()).sound(SoundType.METAL)));
    public static final RegistryObject<Block> IRIDIUM_FAUCET = BLOCKS.register("iridium_faucet", () ->
            new IridiumFaucetBlock(BlockBehaviour.Properties.copy(TinkerSmeltery.searedFaucet.get()).sound(SoundType.NETHERITE_BLOCK)));

    public static final RegistryObject<Block> CINDERSLIME_TABLE = BLOCKS.register("cinderslime_casting_table", () ->
            new CastingTableBlock(BlockBehaviour.Properties.copy(TinkerSmeltery.searedTable.get())
                    .sound(SoundType.METAL),true) {
                @Override
                public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
                    return List.of(new ItemStack(this.asItem()));
                }

                @Override
                public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
                    return new CinderSlimeCastingBlockEntity.Table(pPos,pState);
                }

                @Override
                public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> check) {
                    return CinderSlimeCastingBlockEntity.getTicker(pLevel,check,TiAcBlockEntities.CINDER_SLIME_TABLE.get());
                }
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                    int temp = TiAcConfig.COMMON.CINDER_SLIME_TABLE_SEPARATION.get();
                    float low = TiAcConfig.COMMON.CINDER_SLIME_CASTING_INCREASE.get().floatValue();
                    float high = TiAcConfig.COMMON.CINDER_SLIME_CASTING_DECREASE.get().floatValue();
                    pTooltip.add(Component.translatable("tooltip.tinkers_advanced.cinderslime_casting",temp/20,String.format("%.1f",low),String.format("%.1f",high)));
                }
            });
    public static final RegistryObject<Block> CINDERSLIME_BASIN = BLOCKS.register("cinderslime_casting_basin", () ->
            new CastingBasinBlock(BlockBehaviour.Properties.copy(TinkerSmeltery.searedTable.get())
                    .sound(SoundType.METAL),true) {
                @Override
                public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
                    return List.of(new ItemStack(this.asItem()));
                }

                @Override
                public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
                    return new CinderSlimeCastingBlockEntity.Basin(pPos,pState);
                }

                @Override
                public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> check) {
                    return CinderSlimeCastingBlockEntity.getTicker(pLevel,check,TiAcBlockEntities.CINDER_SLIME_BASIN.get());
                }

                @Override
                public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                    int temp = TiAcConfig.COMMON.CINDER_SLIME_BASIN_SEPARATION.get();
                    float low = TiAcConfig.COMMON.CINDER_SLIME_CASTING_INCREASE.get().floatValue();
                    float high = TiAcConfig.COMMON.CINDER_SLIME_CASTING_DECREASE.get().floatValue();
                    pTooltip.add(Component.translatable("tooltip.tinkers_advanced.cinderslime_casting",temp/20,String.format("%.1f",low),String.format("%.1f",high)));
                }
            });

    public static final RegistryObject<Block> IRIDIUM_TABLE = BLOCKS.register("iridium_casting_table", () ->
            new CastingTableBlock(BlockBehaviour.Properties.copy(TinkerSmeltery.searedTable.get())
                    .sound(SoundType.NETHERITE_BLOCK),true) {
                @Override
                public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
                    return List.of(new ItemStack(this.asItem()));
                }

                @Override
                public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
                    return new IridiumCastingBlockEntity.Table(pPos,pState);
                }

                @Override
                public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> check) {
                    return IridiumCastingBlockEntity.getTicker(pLevel,check,TiAcBlockEntities.IRIDIUM_TABLE.get());
                }
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                    float high = TiAcConfig.COMMON.IRIDIUM_CASTING_SPEED.get().floatValue();
                    pTooltip.add(Component.translatable("tooltip.tinkers_advanced.iridium_casting",String.format("%.1f",high)));
                }
            });
    public static final RegistryObject<Block> IRIDIUM_BASIN = BLOCKS.register("iridium_casting_basin", () ->
            new CastingBasinBlock(BlockBehaviour.Properties.copy(TinkerSmeltery.searedTable.get())
                    .sound(SoundType.NETHERITE_BLOCK),true) {
                @Override
                public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
                    return List.of(new ItemStack(this.asItem()));
                }

                @Override
                public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
                    return new IridiumCastingBlockEntity.Basin(pPos,pState);
                }

                @Override
                public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> check) {
                    return IridiumCastingBlockEntity.getTicker(pLevel,check,TiAcBlockEntities.IRIDIUM_BASIN.get());
                }
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                    float high = TiAcConfig.COMMON.IRIDIUM_CASTING_SPEED.get().floatValue();
                    pTooltip.add(Component.translatable("tooltip.tinkers_advanced.iridium_casting",String.format("%.1f",high)));
                }
            });
}
