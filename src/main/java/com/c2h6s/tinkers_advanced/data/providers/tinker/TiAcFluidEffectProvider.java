package com.c2h6s.tinkers_advanced.data.providers.tinker;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etstlib.tool.fluid.fluidEffect.AddEntityTickerFluidEffect;
import com.c2h6s.etstlib.tool.fluid.fluidEffect.ClearChunkRadiationFluidEffect;
import com.c2h6s.etstlib.tool.fluid.fluidEffect.RadiateEntityFluidEffect;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.registery.TiAcEffects;
import com.c2h6s.tinkers_advanced.registery.TiAcEntityTicker;
import com.c2h6s.tinkers_advanced.registery.TiAcFluids;
import mekanism.common.registries.MekanismDamageTypes;
import mekanism.common.tags.MekanismTags;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import slimeknights.mantle.recipe.condition.TagFilledCondition;
import slimeknights.tconstruct.common.json.ConfigEnabledCondition;
import slimeknights.tconstruct.library.data.tinkering.AbstractFluidEffectProvider;
import slimeknights.tconstruct.library.json.LevelingInt;
import slimeknights.tconstruct.library.json.LevelingValue;
import slimeknights.tconstruct.library.modifiers.fluid.FluidMobEffect;
import slimeknights.tconstruct.library.modifiers.fluid.TimeAction;
import slimeknights.tconstruct.library.modifiers.fluid.block.BreakBlockFluidEffect;
import slimeknights.tconstruct.library.modifiers.fluid.block.MobEffectCloudFluidEffect;
import slimeknights.tconstruct.library.modifiers.fluid.block.PlaceBlockFluidEffect;
import slimeknights.tconstruct.library.modifiers.fluid.entity.DamageFluidEffect;
import slimeknights.tconstruct.library.modifiers.fluid.general.ExplosionFluidEffect;
import slimeknights.tconstruct.shared.TinkerEffects;

public class TiAcFluidEffectProvider extends AbstractFluidEffectProvider {
    public TiAcFluidEffectProvider(PackOutput packOutput) {
        super(packOutput, TinkersAdvanced.MODID);
    }

    @Override
    protected void addFluids() {
        addFluid(TiAcFluids.MOLTEN_BISMUTH.get(),10)
                .fireDamage(2.5f)
                .addEntityEffects(FluidMobEffect.builder()
                        .effect(TiAcEffects.TETANUS.get(), 200,3)
                        .buildEntity(TimeAction.ADD))
                .addBlockEffect(new MobEffectCloudFluidEffect(FluidMobEffect.builder()
                        .effect(TiAcEffects.TETANUS.get(), 200,3)
                        .buildCloud()
                        .effects()));
        addFluid(TiAcFluids.MOLTEN_ANTIMONY.get(),10)
                .fireDamage(3f)
                .addEntityEffects(FluidMobEffect.builder()
                        .effect(TiAcEffects.PLAGUE.get(), 800,3)
                        .buildEntity(TimeAction.ADD))
                .addBlockEffect(new MobEffectCloudFluidEffect(FluidMobEffect.builder()
                        .effect(TiAcEffects.PLAGUE.get(), 800,3)
                        .buildCloud()
                        .effects()));
        addFluid(TiAcFluids.OVER_HEATED_LAVA.get(),100)
                .fireDamage(3.5f);
        addFluid(TiAcFluids.GASEOUS_LAVA.get(),100)
                .fireDamage(4.75f);
        addFluid(TiAcFluids.PLASMATIC_LAVA.get(),100)
                .fireDamage(6f);
        addFluid(TiAcFluids.MOLTEN_ANTIMATTER.get(),50)
                .addDamage(24,new DamageFluidEffect.DamageTypePair(DamageTypes.EXPLOSION,DamageTypes.EXPLOSION))
                .addBlockEffect(ExplosionFluidEffect
                        .radius(12,2)
                        .blockInteraction(Explosion.BlockInteraction.DESTROY)
                        .placeFire()
                        .damage(new LevelingValue(10,2)).build())
                .addCondition(tagFilled(MekanismTags.Items.PELLETS_ANTIMATTER));
        addFluid(TiAcFluids.MOLTEN_IRRADIUM.get(),10)
                .addDamage(9,new DamageFluidEffect.DamageTypePair(MekanismDamageTypes.RADIATION.key(),MekanismDamageTypes.RADIATION.key()))
                .addEntityEffect(new RadiateEntityFluidEffect(new LevelingValue(0.1f,0.1f)))
                .addBlockEffect(new ClearChunkRadiationFluidEffect(2))
                .addCondition(modLoaded("mekanism"));
        addFluid(TiAcFluids.MOLTEN_BASALZ_SIGNALUM.get(),10)
                .fireDamage(2.5f)
                .addEntityEffects(FluidMobEffect.builder()
                        .effect(CoreMobEffects.SUNDERED.get(), 200,3)
                        .buildEntity(TimeAction.ADD))
                .addBlockEffect(new BreakBlockFluidEffect(100, Enchantments.BLOCK_FORTUNE,5))
                .addCondition(modLoaded("thermal"));
        addFluid(TiAcFluids.MOLTEN_BILTZ_LUMIUM.get(),10)
                .fireDamage(3.5f)
                .addEntityEffects(FluidMobEffect.builder()
                        .effect(CoreMobEffects.SHOCKED.get(), 200,3)
                        .buildEntity(TimeAction.ADD))
                .addEntityEffects(FluidMobEffect.builder()
                        .effect(MobEffects.GLOWING, 200,1)
                        .buildEntity(TimeAction.ADD))
                .addBlockEffect(new MobEffectCloudFluidEffect(FluidMobEffect.builder()
                        .effect(CoreMobEffects.SHOCKED.get(), 200,3)
                        .buildCloud()
                        .effects()))
                .addBlockEffect(new MobEffectCloudFluidEffect(FluidMobEffect.builder()
                        .effect(MobEffects.GLOWING, 200,3)
                        .buildCloud()
                        .effects()))
                .addCondition(modLoaded("thermal"));
        addFluid(TiAcFluids.MOLTEN_BLIZZ_ENDERIUM.get(),10)
                .coldDamage(4.5f)
                .addEntityEffects(FluidMobEffect.builder()
                        .effect(CoreMobEffects.CHILLED.get(), 200,3)
                        .buildEntity(TimeAction.ADD))
                .addEntityEffects(FluidMobEffect.builder()
                        .effect(TinkerEffects.enderference.get(), 200,1)
                        .buildEntity(TimeAction.ADD))
                .addBlockEffect(new MobEffectCloudFluidEffect(FluidMobEffect.builder()
                        .effect(CoreMobEffects.CHILLED.get(), 200,3)
                        .buildCloud()
                        .effects()))
                .addBlockEffect(new MobEffectCloudFluidEffect(FluidMobEffect.builder()
                        .effect(TinkerEffects.enderference.get(), 200,3)
                        .buildCloud()
                        .effects()))
                .addCondition(modLoaded("thermal"));
        addFluid(TiAcFluids.MOLTEN_ACTIVATED_CHROMATIC_STEEL.get(),10)
                .fireDamage(4f).coldDamage(4f)
                .addEntityEffects(FluidMobEffect.builder()
                        .effect(CoreMobEffects.CHILLED.get(), 200,2)
                        .buildEntity(TimeAction.ADD))
                .addEntityEffects(FluidMobEffect.builder()
                        .effect(CoreMobEffects.SUNDERED.get(), 200,2)
                        .buildEntity(TimeAction.ADD))
                .addEntityEffects(FluidMobEffect.builder()
                        .effect(CoreMobEffects.SHOCKED.get(), 200,2)
                        .buildEntity(TimeAction.ADD))
                .addBlockEffect(new MobEffectCloudFluidEffect(FluidMobEffect.builder()
                        .effect(CoreMobEffects.CHILLED.get(), 200,2)
                        .buildCloud()
                        .effects()))
                .addBlockEffect(new MobEffectCloudFluidEffect(FluidMobEffect.builder()
                        .effect(CoreMobEffects.SHOCKED.get(), 200,2)
                        .buildCloud()
                        .effects()))
                .addBlockEffect(new MobEffectCloudFluidEffect(FluidMobEffect.builder()
                        .effect(CoreMobEffects.SUNDERED.get(), 200,2)
                        .buildCloud()
                        .effects()))
                .addBlockEffect(new BreakBlockFluidEffect(256,Enchantments.BLOCK_FORTUNE,10))
                .addCondition(modLoaded("thermal"));
        addFluid(TiAcFluids.PYROTHEUM.get(),50)
                .fireDamage(3f).addDamage(3f,new DamageFluidEffect.DamageTypePair(DamageTypes.EXPLOSION,DamageTypes.EXPLOSION))
                .addBlockEffect(new PlaceBlockFluidEffect(Blocks.FIRE, SoundEvents.FLINTANDSTEEL_USE))
                .addBlockEffect(ExplosionFluidEffect.radius(5,2.5f).ignoreBlocks().placeFire().build())
                .addCondition(modLoaded("thermal"));
        addFluid(TiAcFluids.MOLTEN_PROTOCITE.get(),10)
                .addDamage(6,new DamageFluidEffect.DamageTypePair(MekanismDamageTypes.RADIATION.key(),MekanismDamageTypes.RADIATION.key()))
                .addEntityEffects(FluidMobEffect.builder().effect(TiAcEffects.PROTO_POISON.get(),1200,5).buildEntity(TimeAction.ADD))
                .addCondition(modLoaded("mekanism"));
        addFluid(TiAcFluids.MOLTEN_NEUTRONITE.get(),10)
                .addDamage(16,new DamageFluidEffect.DamageTypePair(MekanismDamageTypes.RADIATION.key(),MekanismDamageTypes.RADIATION.key()))
                .addEntityEffect(new AddEntityTickerFluidEffect(TiAcEntityTicker.IONIZED.getId(), LevelingInt.eachLevel(400),LevelingInt.eachLevel(1)))
                .addCondition(modLoaded("mekanism"));
    }
    public static ICondition modLoaded(String modId){
        return new OrCondition(ConfigEnabledCondition.FORCE_INTEGRATION_MATERIALS,new ModLoadedCondition(modId));
    }
    public static ICondition tagFilled(TagKey<Item> tagKey){
        return new OrCondition(ConfigEnabledCondition.FORCE_INTEGRATION_MATERIALS,new TagFilledCondition<>(tagKey));
    }

    @Override
    public String getName() {
        return "Tinkers' Advanced Fluid Effect Provider.";
    }
}
