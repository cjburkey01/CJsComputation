package com.cjburkey.cjscomputation.packet;

import com.cjburkey.cjscomputation.computer.ComputerScreen;
import com.cjburkey.cjscomputation.gui.GuiComputer;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGetScreenCharactersToClient implements IMessage {
    
    private char[][] characters;
    private byte[][][] colors;
    
    public PacketGetScreenCharactersToClient() {
    }
    
    public PacketGetScreenCharactersToClient(char[][] characters, byte[][][] colors) {
        this.characters = characters;
        this.colors = colors;
    }
    
    public void fromBytes(ByteBuf buf) {
        characters = new char[ComputerScreen.SCREEN_CHARACTER_WIDTH][ComputerScreen.SCREEN_CHARACTER_HEIGHT];
        for (int x = 0; x < characters.length; x ++) {
            for (int y = 0; y < characters[0].length; y ++) {
                characters[x][y] = buf.readChar();
            }
        }
        
        colors = new byte[ComputerScreen.SCREEN_CHARACTER_WIDTH][ComputerScreen.SCREEN_CHARACTER_HEIGHT][3];
        for (int x = 0; x < colors.length; x ++) {
            for (int y = 0; y < colors[0].length; y ++) {
                byte r = buf.readByte();
                byte g = buf.readByte();
                byte b = buf.readByte();
                colors[x][y][0] = r;
                colors[x][y][1] = g;
                colors[x][y][2] = b;
            }
        }
    }
    
    public void toBytes(ByteBuf buf) {
        for (int x = 0; x < characters.length; x ++) {
            for (int y = 0; y < characters.length; y ++) {
                buf.writeChar(characters[x][y]);
            }
        }
        
        for (int x = 0; x < colors.length; x ++) {
            for (int y = 0; y < colors[0].length; y ++) {
                for (int c = 0; c < colors[0][0].length; c ++) {
                    buf.writeByte(colors[x][y][c]);
                }
            }
        }
    }
    
    public static class Handler implements IMessageHandler<PacketGetScreenCharactersToClient, IMessage> {
        
        public IMessage onMessage(PacketGetScreenCharactersToClient message, MessageContext ctx) {
            GuiComputer.characterData = message.characters;
            GuiComputer.characterColorData = message.colors;
            GuiComputer.hasInit = true;
            return null;
        }
        
    }
}