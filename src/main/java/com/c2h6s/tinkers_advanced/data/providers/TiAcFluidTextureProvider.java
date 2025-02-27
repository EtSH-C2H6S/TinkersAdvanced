package com.c2h6s.tinkers_advanced.data.providers;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.registery.TiAcFluids;
import net.minecraft.data.PackOutput;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.fluid.texture.AbstractFluidTextureProvider;
import slimeknights.mantle.fluid.texture.FluidTexture;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.TConstruct;


public class TiAcFluidTextureProvider extends AbstractFluidTextureProvider {
    public TiAcFluidTextureProvider(PackOutput packOutput) {
        super(packOutput, TinkersAdvanced.MODID);
    }
    @Override
    public void addTextures() {
        for (FluidObject<ForgeFlowingFluid> object: TiAcFluids.getFluids()){
            this.commonFluid(object.getType());
        }
    }

    public FluidTexture.Builder commonFluid(FluidType fluid) {
        return super.texture(fluid).textures(TinkersAdvanced.getLocation("block/fluid/" + ForgeRegistries.FLUID_TYPES.get().getKey(fluid).getPath() + "/"), false, false);
    }

    @Override
    public String getName() {
        return "Tinkers' Advanced Fluid Texture Provider";
    }
}
