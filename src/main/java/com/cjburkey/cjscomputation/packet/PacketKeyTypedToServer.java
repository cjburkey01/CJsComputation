package com.cjburkey.cjscomputation.packet;

import java.nio.charset.Charset;
import com.cjburkey.cjscomputation.computer.ComputerCore;
import com.cjburkey.cjscomputation.computer.ComputerHandler;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketKeyTypedToServer extends PacketComputerToServer {
    
    private char character;
    private int key;
    
    public PacketKeyTypedToServer() {
    }
    
    public PacketKeyTypedToServer(short computer, Class<? extends ComputerCore> type, char character, int key) {
        super(computer, type);
        this.character = character;
        this.key = key;
    }
    
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        character = buf.readChar();
        key = buf.readInt();
    }
    
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeChar(character);
        buf.writeInt(key);
    }
    
    public static class Handler implements IMessageHandler<PacketKeyTypedToServer, IMessage> {
        
        // TODO: ERROR HANDLING
        public IMessage onMessage(PacketKeyTypedToServer message, MessageContext ctx) {
            ComputerHandler.get().getComputer(message.computer, message.type).onKeyType(message.character, message.key);
            return null;
        }
        
    }
    
}