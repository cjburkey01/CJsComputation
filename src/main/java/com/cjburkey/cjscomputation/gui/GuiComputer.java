package com.cjburkey.cjscomputation.gui;

import java.io.IOException;
import com.cjburkey.cjscomputation.HexUtil;
import com.cjburkey.cjscomputation.ModInfo;
import com.cjburkey.cjscomputation.computer.ComputerCore;
import com.cjburkey.cjscomputation.computer.ComputerScreen;
import com.cjburkey.cjscomputation.packet.ModPackets;
import com.cjburkey.cjscomputation.packet.PacketGetDrawTypeToServer;
import com.cjburkey.cjscomputation.packet.PacketGetScreenCharactersToServer;
import com.cjburkey.cjscomputation.packet.PacketGetScreenPixelsToServer;
import com.cjburkey.cjscomputation.packet.PacketKeyTypedToServer;
import net.minecraft.client.Minecraft;
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
    
    private static GuiComputer instance;
    
    public GuiComputer(short computerId, Class<? extends ComputerCore> type) {
        super(new ContainerComputer(computerId));
        
        computer = computerId;
        this.type = type;
        xSize = 249;
        ySize = 209;
        
        instance = this;
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks); // Overriding this fixed a bug for some reason
    }
    
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            mc.player.closeScreen();
        }
        if (typedChar >= 0x80) {
            return; // Invalid character as far as we are concerned
        }
        sendKeyTypedPacket(typedChar, keyCode);
    }
    
    public void initGui() {
        super.initGui();
        sendTypeRequestPacket();
    }
    
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        if (!hasSentInitialRequest && hasInit) {
            hasSentInitialRequest = true;
            
            if (drawPixels) {
                sendPixelRequestPacket();
            } else {
                sendCharacterRequestPacket();
            }
        }
        
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(new ResourceLocation(ModInfo.modid, "textures/gui/gui_computer_o.png"));
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
    
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        if (!hasInit || !hasSentInitialRequest) {
            return;
        }
        draw();
    }
    
    private void draw() {
        if (drawPixels) {
            drawPixels();
            return;
        }
        drawCharacters();
    }
    
    private void drawPixels() {
        if (pixelData.length <= 0 || pixelData[0].length <= 0 || pixelData[0][0].length != 3) {
            return;
        }
        int color = -1;
        for (int x = 0; x < pixelData.length; x ++) {
            for (int y = 0; y < pixelData[0].length; y ++) {
                if (!(pixelData[x][y][0] == 0 && pixelData[x][y][1] == 0 && pixelData[x][y][2] == 0)) {
                    color = HexUtil.getHex(pixelData, x, y);
                    drawRect(x + 4, y + 4, x + 5, y + 5, color);    // Gui bevel is 4 pixels wide
                }
            }
        }
    }
    
    private void drawCharacters() {
        if (characterColorData.length <= 0 || characterColorData[0].length <= 0 || characterColorData[0][0].length != 3 || characterData.length <= 0 || characterData[0].length <= 0 || characterColorData.length != characterData.length || characterColorData[0].length != characterData[0].length) {
            return;
        }
        int color = -1;
        int padding = ComputerScreen.CHARACTER_PADDING;
        for (int x = 0; x < characterData.length; x ++) {
            for (int y = 0; y < characterData[0].length; y ++) {
                color = HexUtil.getHexNoAlpha(characterColorData, x, y);
                char c = characterData[x][y];
                if (c <= 0x80) {
                    drawString(fontRenderer, c + "", (x * (ComputerScreen.CHARACTER_WIDTH + padding)) + 4 + padding, (y * (ComputerScreen.CHARACTER_HEIGHT + padding)) + 4 + padding, color);
                }
            }
        }
    }
    
    private void sendTypeRequestPacket() {
        ModPackets.getNetwork().sendToServer(new PacketGetDrawTypeToServer(computer, type));
    }
    
    private void sendPixelRequestPacket() {
        ModPackets.getNetwork().sendToServer(new PacketGetScreenPixelsToServer(computer, type));
    }
    
    private void sendCharacterRequestPacket() {
        ModPackets.getNetwork().sendToServer(new PacketGetScreenCharactersToServer(computer, type));
    }
    
    private void sendKeyTypedPacket(char typedChar, int keyCode) {
        ModPackets.getNetwork().sendToServer(new PacketKeyTypedToServer(computer, type, typedChar, keyCode));
    }
    
    public static boolean getIsOpen() {
        return Minecraft.getMinecraft().currentScreen.equals(instance); // This can only be run on the client
    }
    
}