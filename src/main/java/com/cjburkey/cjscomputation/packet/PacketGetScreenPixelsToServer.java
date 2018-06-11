package com.cjburkey.cjscomputation.packet;

import java.nio.charset.Charset;
import com.cjburkey.cjscomputation.computer.ComputerCore;
import com.cjburkey.cjscomputation.computer.ComputerHandler;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGetScreenPixelsToServer implements IMessage {
    
    private short computer;
    private short typeLength;
    private String type;
    
    public PacketGetScreenPixelsToServer() {
    }
    
    public PacketGetScreenPixelsToServer(short computer, Class<? extends ComputerCore> type) {
        this.computer = computer;
        this.type = type.getName();
        typeLength = (short) this.type.length();
    }
    
    public void fromBytes(ByteBuf buf) {
        computer = buf.readShort();
        typeLength = buf.readShort();
        type = buf.readCharSequence(typeLength, Charset.forName("UTF-8")).toString();
    }
    
    public void toBytes(ByteBuf buf) {
        buf.writeShort(computer);
        buf.writeShort(typeLength);
        buf.writeCharSequence(type, Charset.forName("UTF-8"));
    }
    
    public static class Handler implements IMessageHandler<PacketGetScreenPixelsToServer, PacketGetScreenPixelsToClient> {
        
        public PacketGetScreenPixelsToClient onMessage(PacketGetScreenPixelsToServer message, MessageContext ctx) {
            ComputerCore computer = ComputerHandler.get().getComputer(message.computer, message.type);
            return new PacketGetScreenPixelsToClient(computer.screen.getPixelData());
        }
        
    }
    
}