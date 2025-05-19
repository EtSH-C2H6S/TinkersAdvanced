package com.c2h6s.tinkers_advanced.client.gui.screen;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.client.gui.components.ElectronTunerSlider;
import com.c2h6s.tinkers_advanced.content.item.toolItem.ElectronTunerItem;
import com.c2h6s.tinkers_advanced.content.menu.ElectronTunerMenu;
import com.c2h6s.tinkers_advanced.network.TiAcPacketHandler;
import com.c2h6s.tinkers_advanced.network.packets.PElectronTunerAdjustC2S;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ElectronTunerScreen extends AbstractContainerScreen<ElectronTunerMenu> {
    public final ItemStack toolItem;
    public final int slot;
    public static final ResourceLocation TEXTURE = TinkersAdvanced.getLocation("textures/gui/menu/electron_tuner_bg.png");
    public static float value;

    public ElectronTunerScreen(ElectronTunerMenu menu, Inventory inventory, Component title) {
        super(menu,inventory,title);
        this.toolItem = menu.getToolItem();
        this.slot = menu.slotIndex;
        this.imageWidth = 222;
    }

    @Override
    protected void init() {
        super.init();
        double value = ToolStack.from(this.toolItem).getPersistentData().getFloat(ElectronTunerItem.KEY_ATTACK_DAMAGE);
        int xSize = 222;
        int ySize = 176;
        int x = (this.width - xSize) / 2;
        int y = (this.height - ySize) / 2;
        ElectronTunerScreen.value = (float) value;
        this.addRenderableWidget(new ElectronTunerSlider(x+7,y+22,160,6,Component.literal(String.format("%.0f",value*100)+"%"),value,this.toolItem,this.slot));
    }

    @Override
    public void removed() {
        TiAcPacketHandler.sendToServer(new PElectronTunerAdjustC2S(this.slot, (float) value));
        value = 0;
        super.removed();
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics,pMouseX,pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int xSize = 222;
        int ySize = 176;
        int x = (this.width - xSize) / 2;
        int y = (this.height - ySize) / 2;
        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, xSize, ySize);
        y+=34;
        x+=7;
        List<Component> displays = ElectronTunerItem.getInfoList(ToolStack.from(this.toolItem));
        for (Component component:displays){
            pGuiGraphics.drawString(Minecraft.getInstance().font,component,x,y,component.getStyle().getColor()!=null?component.getStyle().getColor().getValue():5592405);
            y+=8;
        }
    }

}
