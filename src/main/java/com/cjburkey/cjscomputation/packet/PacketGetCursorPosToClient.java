package com.cjburkey.cjscomputation.packet;

import com.cjburkey.cjscomputation.Debug;
import com.cjburkey.cjscomputation.gui.GuiComputer;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGetCursorPosToClient implements IMessage {
    
    private short cursorX;
    private short cursorY;
    
    public PacketGetCursorPosToClient() {
    }
    
    public PacketGetCursorPosToClient(short cursorX, short cursorY) {
        this.cursorX = cursorX;
        this.cursorY = cursorY;
    }
    
    public void fromBytes(ByteBuf buf) {
        cursorX = buf.readShort();
        cursorY = buf.readShort();
    }
    
    public void toBytes(ByteBuf buf) {
        buf.writeShort(cursorX);
        buf.writeShort(cursorY);
    }
    
    public static class Handler implements IMessageHandler<PacketGetCursorPosToClient, IMessage> {
        
        public IMessage onMessage(PacketGetCursorPosToClient message, MessageContext ctx) {
            GuiComputer.cursorX = message.cursorX;
            GuiComputer.cursorY = message.cursorY;
            return null;
        }
        
    }
}