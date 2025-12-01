package com.c2h6s.tinkers_advanced.tools.init;

import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import com.c2h6s.tinkers_advanced.tools.content.tool.item.ElectronTunerItem;
import com.c2h6s.tinkers_advanced.tools.content.tool.item.IonizedCannonItem;
import com.c2h6s.tinkers_advanced.tools.content.tool.item.MatterManipulator;
import com.c2h6s.tinkers_advanced.tools.content.tool.tinkering.materialStat.FluxCoreMaterialStat;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;

import static com.c2h6s.tinkers_advanced.TinkersAdvanced.MODID;

public class TiAcTItems {
    public static final ItemDeferredRegisterExtension TOOL_ITEMS = new ItemDeferredRegisterExtension(MODID);

    public static final ItemObject<ModifiableItem> ELECTRON_TUNER = TOOL_ITEMS.register("electron_tuner",()->new ElectronTunerItem(new Item.Properties().stacksTo(1)));
    public static final ItemObject<ModifiableItem> MATTER_MANIPULATOR = TOOL_ITEMS.register("matter_manipulator",()->new MatterManipulator(new Item.Properties().stacksTo(1)));
    public static final ItemObject<ModifiableItem> IONIZED_CANNON = TOOL_ITEMS.register("ionized_cannon",()->new IonizedCannonItem(new Item.Properties().stacksTo(1)));
    public static final ItemObject<ToolPartItem> FLUX_CORE = TOOL_ITEMS.register("flux_core",()->new ToolPartItem(new Item.Properties(), FluxCoreMaterialStat.ID));
    public static final ItemObject<ToolPartItem> PARTICLE_CONTAINER = TOOL_ITEMS.register("particle_container",()->new ToolPartItem(new Item.Properties(), HandleMaterialStats.ID));
    public static final ItemObject<ToolPartItem> IONIZE_CHAMBER = TOOL_ITEMS.register("ionize_chamber",()->new ToolPartItem(new Item.Properties(), HandleMaterialStats.ID));

    @SubscribeEvent
    public static void init(TiAcLoadRegistryClassEvent event){}
}
