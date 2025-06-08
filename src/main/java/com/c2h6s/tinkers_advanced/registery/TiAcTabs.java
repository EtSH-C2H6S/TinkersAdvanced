package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.content.item.HiddenMaterial;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.part.IMaterialItem;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.c2h6s.tinkers_advanced.TinkersAdvanced.MODID;

public class TiAcTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    private static void acceptTool(Consumer<ItemStack> output, Supplier<? extends IModifiable> tool) {
        ToolBuildHandler.addVariants(output, (IModifiable)tool.get(), "");
    }
    private static void acceptPart(Consumer<ItemStack> output, Supplier<? extends IMaterialItem> item) {
        item.get().addVariants(output, "");
    }
    private static void addToolItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output tab) {
        Objects.requireNonNull(tab);
        Consumer<ItemStack> output = tab::accept;
        acceptTool(output,TiAcItems.IONIZED_CANNON);
        acceptPart(output,TiAcItems.IONIZE_CHAMBER);
        acceptTool(output,TiAcItems.MATTER_MANIPULATOR);
        acceptPart(output,TiAcItems.PARTICLE_CONTAINER);
        acceptPart(output,TiAcItems.FLUX_CORE);
        acceptTool(output,TiAcItems.ELECTRON_TUNER);
    }

    public static final RegistryObject<CreativeModeTab> MATERIAL_TAB = CREATIVE_MODE_TABS.register("tiac_material", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tinkers_advanced.tiac_material"))
            .icon(() -> TiAcItems.BISMUTH_INGOT.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                for (RegistryObject<Item> object:TiAcItems.LIST_MATERIAL){
                    if (object.isPresent()&&!(object.get() instanceof HiddenMaterial hiddenMaterial&&hiddenMaterial.config!=null&&!hiddenMaterial.config.get())) {
                        output.accept(object.get());
                    }
                }
            }).build());
    public static final RegistryObject<CreativeModeTab> BLOCK_TAB = CREATIVE_MODE_TABS.register("tiac_block", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tinkers_advanced.tiac_block"))
            .icon(() -> TiAcItems.BISMUTHINITE_ORE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                for (RegistryObject<BlockItem> object:TiAcItems.LIST_SIMPLE_BLOCK){
                    if (object.isPresent()) {
                        output.accept(object.get());
                    }
                }
                for (RegistryObject<BlockItem> object:TiAcItems.LIST_MIXC_BLOCK){
                    if (object.isPresent()) {
                        output.accept(object.get());
                    }
                }
            })
            .build());
    public static final RegistryObject<CreativeModeTab> TOOL_TAB = CREATIVE_MODE_TABS.register("tiac_tool", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tinkers_advanced.tiac_tool"))
            .icon(() -> TiAcItems.IONIZED_CANNON.get().getRenderTool())
            .displayItems(TiAcTabs::addToolItems).build());
    public static final RegistryObject<CreativeModeTab> MIXC_TAG = CREATIVE_MODE_TABS.register("tiac_misc", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tinkers_advanced.tiac_misc"))
            .icon(() -> TiAcItems.ULTRA_DENSE_BOOK.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                for (RegistryObject<Item> object:TiAcItems.LIST_MIXC){
                    if (object.isPresent()) {
                        output.accept(object.get());
                    }
                }
            }).build());
}
