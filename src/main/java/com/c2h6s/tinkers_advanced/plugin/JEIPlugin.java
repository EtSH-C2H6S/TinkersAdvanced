package com.c2h6s.tinkers_advanced.plugin;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.item.HiddenMaterial;
import com.c2h6s.tinkers_advanced.registery.TiAcItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return TinkersAdvanced.getLocation("jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        for (var object : TiAcItems.getListSimpleMaterialModel()) {
            if (object.isPresent()&&object.get() instanceof HiddenMaterial) {
                registration.addIngredientInfo(object.get(), Component.translatable("info.tinkers_advanced.hidden_material"));
            }
        }
    }
}
