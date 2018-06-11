package com.cjburkey.cjscomputation.packet;

import java.nio.charset.Charset;
import com.cjburkey.cjscomputation.computer.ComputerCore;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class PacketComputerToServer implements IMessage {
    
    protected short computer;
    protected short typeLength;
    protected String type;
    
    protected PacketComputerToServer() {
    }
    
    protected PacketComputerToServer(short computer, Class<? extends ComputerCore> type) {
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
    
}