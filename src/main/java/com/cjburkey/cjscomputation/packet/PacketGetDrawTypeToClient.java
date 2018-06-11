package com.cjburkey.cjscomputation.packet;

import com.cjburkey.cjscomputation.Debug;
import com.cjburkey.cjscomputation.gui.GuiComputer;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGetDrawTypeToClient implements IMessage {
    
    private boolean drawPixels;
    
    public PacketGetDrawTypeToClient() {
    }
    
    public PacketGetDrawTypeToClient(boolean drawPixels) {
        this.drawPixels = drawPixels;
    }
    
    public void fromBytes(ByteBuf buf) {
        drawPixels = buf.readBoolean();
    }
    
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(drawPixels);
    }
    
    public static class Handler implements IMessageHandler<PacketGetDrawTypeToClient, IMessage> {
        
        public IMessage onMessage(PacketGetDrawTypeToClient message, MessageContext ctx) {
            GuiComputer.drawPixels = message.drawPixels;
            GuiComputer.hasInit = true;
            return null;
        }
        
    }
}