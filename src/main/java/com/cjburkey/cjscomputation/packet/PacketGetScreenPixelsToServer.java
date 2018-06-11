package com.cjburkey.cjscomputation.packet;

import com.cjburkey.cjscomputation.computer.ComputerCore;
import com.cjburkey.cjscomputation.computer.ComputerHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGetScreenPixelsToServer extends PacketComputerToServer {
    
    public PacketGetScreenPixelsToServer() {
    }
    
    public PacketGetScreenPixelsToServer(short computer, Class<? extends ComputerCore> type) {
        super(computer, type);
    }
    
    public static class Handler implements IMessageHandler<PacketGetScreenPixelsToServer, PacketGetScreenPixelsToClient> {

        // TODO: ERROR HANDLING
        public PacketGetScreenPixelsToClient onMessage(PacketGetScreenPixelsToServer message, MessageContext ctx) {
            ComputerCore computer = ComputerHandler.get().getComputer(message.computer, message.type);
            return new PacketGetScreenPixelsToClient(computer.screen.getPixelData());
        }
        
    }
    
}