package com.cjburkey.cjscomputation.gui;

import com.cjburkey.cjscomputation.ModInfo;
import com.cjburkey.cjscomputation.computer.ComputerCore;
import com.cjburkey.cjscomputation.computer.ComputerWrapper;
import com.cjburkey.cjscomputation.packet.ModPackets;
import com.cjburkey.cjscomputation.packet.PacketGetDrawTypeToServer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiComputer extends GuiContainer {
    
    private short computer = -1;
    private Class<? extends ComputerCore> type;
    public static boolean hasInit = false;
    private static boolean hasSentInitialRequest = false;
    public static boolean drawPixels = false;
    public static byte[][][] pixelData = new byte[0][0][0];
    public static char[][] characterData = new char[0][0];
    public static byte[][][] characterColorData = new byte[0][0][0];
    
    public GuiComputer(short computerId, Class<? extends ComputerCore> type) {
        super(new ContainerComputer(computerId));
        
        computer = computerId;
        this.type = type;
        xSize = 249;
        ySize = 209;
    }
    
    public void initGui() {
        super.initGui();
        
        ModPackets.getNetwork().sendToServer(new PacketGetDrawTypeToServer(computer, type));
    }
    
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        if (!hasSentInitialRequest && hasInit) {
            hasSentInitialRequest = true;
            if (drawPixels) {
                // TODO: Handle sending pixel request packet
            } else {
                // TODO: Handle sending character request packet
            }
        }
        
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(new ResourceLocation(ModInfo.modid, "textures/gui/gui_computer_o.png"));
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
    
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        
        draw();
    }
    
    private void draw() {
        if (drawPixels) {
            
            return;
        }
        
    }
    
}