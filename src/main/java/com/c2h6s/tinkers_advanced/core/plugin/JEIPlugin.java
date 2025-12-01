package com.c2h6s.tinkers_advanced.core.plugin;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.core.library.interfaces.IHiddenMaterial;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return TinkersAdvanced.getLocation("jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        for (var item : ForgeRegistries.ITEMS.getValues()) {
            if (item instanceof IHiddenMaterial) {
                registration.addIngredientInfo(item, Component.translatable("info.tinkers_advanced.hidden_material"));
            }
        }
    }
}
