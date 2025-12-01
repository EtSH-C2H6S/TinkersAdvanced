package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import com.c2h6s.tinkers_advanced.core.content.item.HiddenMaterial;
import com.c2h6s.tinkers_advanced.core.init.TiAcCrItem;
import com.c2h6s.tinkers_advanced.materials.init.TiAcMeItems;
import com.c2h6s.tinkers_advanced.tools.init.TiAcTItems;
import com.c2h6s.tinkers_advanced.utilities.init.TiAcUItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.part.IMaterialItem;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.c2h6s.tinkers_advanced.core.init.TiAcCrTabs.CREATIVE_MODE_TABS;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiAcTabs {
    @SubscribeEvent
    public static void init(TiAcLoadRegistryClassEvent event){}
    private static void acceptTool(Consumer<ItemStack> output, Supplier<? extends IModifiable> tool) {
        ToolBuildHandler.addVariants(output, (IModifiable)tool.get(), "");
    }
    private static void acceptPart(Consumer<ItemStack> output, Supplier<? extends IMaterialItem> item) {
        item.get().addVariants(output, "");
    }
    private static void addToolItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output tab) {
        Objects.requireNonNull(tab);
        Consumer<ItemStack> output = tab::accept;
        acceptTool(output, TiAcTItems.IONIZED_CANNON);
        acceptPart(output, TiAcTItems.IONIZE_CHAMBER);
        acceptTool(output, TiAcTItems.MATTER_MANIPULATOR);
        acceptPart(output, TiAcTItems.PARTICLE_CONTAINER);
        acceptPart(output, TiAcTItems.FLUX_CORE);
        acceptTool(output, TiAcTItems.ELECTRON_TUNER);
    }

    public static final RegistryObject<CreativeModeTab> MATERIAL_TAB = CREATIVE_MODE_TABS.register("tiac_material", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tinkers_advanced.tiac_material"))
            .icon(() -> TiAcMeItems.BISMUTH_INGOT.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                for (RegistryObject<Item> object:TiAcCrItem.getListMaterial()){
                    if (object.isPresent()&&!(object.get() instanceof HiddenMaterial hiddenMaterial&&hiddenMaterial.config!=null&&!hiddenMaterial.config.get())) {
                        output.accept(object.get());
                    }
                }
            }).build());
    public static final RegistryObject<CreativeModeTab> BLOCK_TAB = CREATIVE_MODE_TABS.register("tiac_block", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tinkers_advanced.tiac_block"))
            .icon(() -> TiAcMeItems.BISMUTHINITE_ORE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                for (RegistryObject<BlockItem> object:TiAcCrItem.getListSimpleBlock()){
                    if (object.isPresent()) {
                        output.accept(object.get());
                    }
                }
                for (RegistryObject<BlockItem> object:TiAcCrItem.getListMiscBlock()){
                    if (object.isPresent()) {
                        output.accept(object.get());
                    }
                }
            })
            .build());

    public static final RegistryObject<CreativeModeTab> TOOL_TAB = CREATIVE_MODE_TABS.register("tiac_tool", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tinkers_advanced.tiac_tool"))
            .icon(() -> TiAcTItems.IONIZED_CANNON.get().getRenderTool())
            .displayItems(TiAcTabs::addToolItems).build());

}
