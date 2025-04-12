package com.c2h6s.tinkers_advanced.mixin.mekanismMixin;

import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.registery.TiAcItems;
import com.c2h6s.tinkers_advanced.util.BlockUtil;
import mekanism.api.Coord4D;
import mekanism.common.lib.radiation.RadiationManager;
import mekanism.common.registries.MekanismDamageTypes;
import mekanism.generators.common.content.fusion.FusionReactorMultiblockData;
import mekanism.generators.common.registries.GeneratorsBlocks;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.materials.definition.MaterialVariant;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.tools.data.material.MaterialIds;


@Mixin(value = FusionReactorMultiblockData.class,remap = false)
public class FusionReactorMultiblockDataMixin {

    @Shadow
    private AABB deathZone;
    @Shadow
    private boolean burning;

    @Inject(method = "tick",at = @At("TAIL"))
    private void blowUpIron(Level world, CallbackInfoReturnable<Boolean> cir){
        if (TiAcConfig.COMMON.EXPLODING_FUSION_REACTOR.get()&&burning) {
            int craftCount=0;
            for (ItemEntity itemEntity: world.getEntitiesOfClass(ItemEntity.class,deathZone)) {
                float countStack = 0;
                if (itemEntity.getItem().is(Tags.Items.INGOTS_IRON)) countStack = 1;
                if (itemEntity.getItem().is(Tags.Items.STORAGE_BLOCKS_IRON)) countStack = 9;
                if (itemEntity.getItem().is(Tags.Items.NUGGETS_IRON)) countStack = 0.1f;
                if (itemEntity.getItem().is(Items.ANVIL)) countStack = 31;
                if (itemEntity.getItem().getItem() instanceof ToolPartItem partItem &&partItem.getMaterial(itemEntity.getItem()).getId().getPath().equals(MaterialIds.iron.getPath())) countStack = TinkersAdvanced.RANDOM.nextInt(4);

                if (countStack>0) itemEntity.discard();
                if (itemEntity.getItem().getItem() instanceof IModifiable){
                    ToolStack tool = ToolStack.from(itemEntity.getItem());
                    tool.setDamage(Integer.MAX_VALUE);
                    for (MaterialVariant variant: tool.getMaterials()){
                        if (variant.getId().getPath().equals(MaterialIds.iron.getPath())){
                            countStack = 16;
                            break;
                        }
                    }
                }
                countStack *= itemEntity.getItem().getCount();
                craftCount+= (int) countStack;
            }
            if (craftCount > 0) {
                Vec3 posCenter = deathZone.getCenter();
                BlockUtil.getPosInRange(deathZone.inflate(1)).forEach((blockPos) -> {
                    BlockState blockState = world.getBlockState(blockPos);
                    if (blockState.is(GeneratorsBlocks.REACTOR_GLASS.getBlock()) || blockState.is(GeneratorsBlocks.FUSION_REACTOR_PORT.getBlock()) || blockState.is(GeneratorsBlocks.FUSION_REACTOR_FRAME.getBlock()) || blockState.is(GeneratorsBlocks.FUSION_REACTOR_LOGIC_ADAPTER.getBlock()) || blockState.is(GeneratorsBlocks.FUSION_REACTOR_CONTROLLER.getBlock())|| blockState.is(GeneratorsBlocks.LASER_FOCUS_MATRIX.getBlock())) {
                        world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                    }
                });
                var radiationManager = RadiationManager.get();
                radiationManager.radiate(new Coord4D(posCenter.x, posCenter.y, posCenter.z, world.dimension()), 1000);
                var explosion = world.explode(null, MekanismDamageTypes.RADIATION.source(world), null, posCenter, 24, true, Level.ExplosionInteraction.TNT);
                explosion.getHitPlayers().forEach(((player, vec3) -> {
                    player.setDeltaMovement(player.getDeltaMovement().add(vec3.scale(10)));
                    player.invulnerableTime = 0;
                    player.hurt(MekanismDamageTypes.RADIATION.source(world), 1024);
                    radiationManager.radiate(player, 1000);
                }));
                int entityCount = craftCount / 64;
                int leftOver = craftCount % 64;
                for (int i = 0; i < entityCount; i++) {
                    ItemEntity entity = new ItemEntity(world, posCenter.x, posCenter.y, posCenter.z, new ItemStack(TiAcItems.NEUTRONITE_INGOT.get(), 64));
                    world.addFreshEntity(entity);
                }
                if (leftOver > 0) {
                    ItemEntity entity = new ItemEntity(world, posCenter.x, posCenter.y, posCenter.z, new ItemStack(TiAcItems.NEUTRONITE_INGOT.get(), leftOver));
                    world.addFreshEntity(entity);
                }
            }
        }
    }
}


