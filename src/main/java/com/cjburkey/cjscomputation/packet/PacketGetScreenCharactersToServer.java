package com.cjburkey.cjscomputation.packet;

import java.nio.charset.Charset;
import com.cjburkey.cjscomputation.computer.ComputerCore;
import com.cjburkey.cjscomputation.computer.ComputerHandler;
import com.cjburkey.cjscomputation.computer.ComputerWrapper;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGetScreenCharactersToServer extends PacketComputerToServer {
    
    public PacketGetScreenCharactersToServer() {
    }
    
    public PacketGetScreenCharactersToServer(short computer, Class<? extends ComputerCore> type) {
        super(computer, type);
    }
    
    public static class Handler implements IMessageHandler<PacketGetScreenCharactersToServer, PacketGetScreenCharactersToClient> {

        // TODO: ERROR HANDLING
        public PacketGetScreenCharactersToClient onMessage(PacketGetScreenCharactersToServer message, MessageContext ctx) {
            ComputerCore computer = ComputerHandler.get().getComputer(message.computer, message.type);
            return new PacketGetScreenCharactersToClient(computer.screen.getCharacterData(), computer.screen.getCharacterColors());
        }
        
    }
    
}