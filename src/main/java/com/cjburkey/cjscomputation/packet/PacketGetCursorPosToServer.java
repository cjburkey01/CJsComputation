package com.cjburkey.cjscomputation.packet;

import java.nio.charset.Charset;
import com.cjburkey.cjscomputation.computer.ComputerCore;
import com.cjburkey.cjscomputation.computer.ComputerHandler;
import com.cjburkey.cjscomputation.computer.ComputerWrapper;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGetCursorPosToServer extends PacketComputerToServer {
    
    public PacketGetCursorPosToServer() {
    }
    
    public PacketGetCursorPosToServer(short computer, Class<? extends ComputerCore> type) {
        super(computer, type);
    }
    
    public static class Handler implements IMessageHandler<PacketGetCursorPosToServer, PacketGetCursorPosToClient> {
        
        // TODO: ERROR HANDLING
        public PacketGetCursorPosToClient onMessage(PacketGetCursorPosToServer message, MessageContext ctx) {
            ComputerCore computer = ComputerHandler.get().getComputer(message.computer, message.type);
            return new PacketGetCursorPosToClient(computer.getCursorX(), computer.getCursorY());
        }
        
    }
    
}