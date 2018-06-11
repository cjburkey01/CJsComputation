package com.cjburkey.cjscomputation.packet;

import com.cjburkey.cjscomputation.computer.ComputerScreen;
import com.cjburkey.cjscomputation.gui.GuiComputer;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGetScreenPixelsToClient implements IMessage {
    
    private byte[][][] screenPixels;
    
    public PacketGetScreenPixelsToClient() {
    }
    
    public PacketGetScreenPixelsToClient(byte[][][] screenPixels) {
        this.screenPixels = screenPixels;
    }
    
    public void fromBytes(ByteBuf buf) {
        screenPixels = new byte[ComputerScreen.SCREEN_PIXEL_WIDTH][ComputerScreen.SCREEN_PIXEL_HEIGHT][3];
        for (int x = 0; x < ComputerScreen.SCREEN_PIXEL_WIDTH; x ++) {
            for (int y = 0; y < ComputerScreen.SCREEN_PIXEL_HEIGHT; y ++) {
                byte r = buf.readByte();
                byte g = buf.readByte();
                byte b = buf.readByte();
                screenPixels[x][y][0] = r;
                screenPixels[x][y][1] = g;
                screenPixels[x][y][2] = b;
            }
        }
    }
    
    public void toBytes(ByteBuf buf) {
        for (int x = 0; x < screenPixels.length; x ++) {
            for (int y = 0; y < screenPixels[0].length; y ++) {
                buf.writeByte(screenPixels[x][y][0]);
                buf.writeByte(screenPixels[x][y][1]);
                buf.writeByte(screenPixels[x][y][2]);
            }
        }
    }
    
    public static class Handler implements IMessageHandler<PacketGetScreenPixelsToClient, IMessage> {
        
        public IMessage onMessage(PacketGetScreenPixelsToClient message, MessageContext ctx) {
            GuiComputer.pixelData = message.screenPixels;
            return null;
        }
        
    }
}