package com.c2h6s.tinkers_advanced.client.gui.components;

import com.c2h6s.tinkers_advanced.client.gui.screen.ElectronTunerScreen;
import com.c2h6s.tinkers_advanced.content.item.toolItem.ElectronTunerItem;
import com.c2h6s.tinkers_advanced.network.TiAcPacketHandler;
import com.c2h6s.tinkers_advanced.network.packets.PElectronTunerAdjustC2S;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class ElectronTunerSlider extends AbstractSliderButton {
    public final ItemStack toolItem;
    public final int slot;
    public ElectronTunerSlider(int pX, int pY, int pWidth, int pHeight, Component pMessage, double pValue, ItemStack toolItem,int slot) {
        super(pX, pY, pWidth, pHeight, pMessage, pValue);
        this.toolItem = toolItem;
        this.slot = slot;
    }

    @Override
    protected void updateMessage() {
        this.setMessage(Component.literal(String.format("%.0f",value*100)+"%"));
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (Screen.hasShiftDown()){
            this.setValue(this.value-pDelta*0.1);
            return true;
        }
        this.setValue(this.value-pDelta*0.01);
        return true;
    }

    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        pGuiGraphics.blitNineSliced(SLIDER_LOCATION, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, this.getTextureY());
        pGuiGraphics.blitNineSliced(SLIDER_LOCATION, this.getX() + (int)(this.value * (double)(this.width - 8)), this.getY(), 8, this.getHeight(), 20, 4, 200, 20, 0, this.getHandleTextureY());
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.active ? 16777215 : 10526880;
        this.renderScrollingString(pGuiGraphics, minecraft.font, 2, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    protected void applyValue() {
        ToolStack toolStack = ToolStack.from(this.toolItem);
        toolStack.getPersistentData().putFloat(ElectronTunerItem.KEY_ATTACK_DAMAGE, (float) this.value);
        toolStack.rebuildStats();
        ElectronTunerScreen.value = (float) value;
    }
}
