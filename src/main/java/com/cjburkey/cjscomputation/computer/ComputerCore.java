package com.cjburkey.cjscomputation.computer;

import org.lwjgl.input.Keyboard;
import com.cjburkey.cjscomputation.Debug;
import com.cjburkey.cjscomputation.HexUtil;
import com.cjburkey.cjscomputation.commandline.CommandLineParser;
import com.cjburkey.cjscomputation.packet.ModPackets;
import com.cjburkey.cjscomputation.packet.PacketGetCursorPosToClient;
import com.cjburkey.cjscomputation.packet.PacketGetScreenCharactersToClient;
import com.cjburkey.cjscomputation.packet.PacketGetScreenPixelsToClient;
import com.cjburkey.cjscomputation.process.ProcessHost;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;

public abstract class ComputerCore extends ProcessHost {
    
    private short id;
    public final ComputerScreen screen;
    private short currentCursorX = 0;
    private short currentCursorY = 0;
    private boolean cursorMoved = false;
    public int currentFillColor = 0xFFFFFF;
    private boolean skip = false;
    
    public ComputerCore(ComputerHandler handler) {
        this ();
        id = handler.getNextId();
    }
    
    // Init or to be loaded with NBT
    public ComputerCore() {
        id = -1;
        screen = new ComputerScreen(this);
    }
    
    public final void update() {
        int i = 0;
        while (i < 500 && executeNext() && !skip) {  // Allows **up to** 500 processes
            i ++;
        }
        skip = false;
        if (screen.hasUpdated() || cursorMoved) {
            screen.resetUpdateDetection();
            cursorMoved = false;
            sendUpdateToViewers();
        }
    }
    
    // TODO: SEND A VISUAL UPDATE PACKET TO ALL PLAYERS WHO SEE THE COMPUTER'S GUI WHEN IT CHANGES
    public final void sendUpdateToViewers() {
        // TODO: ONLY SEND TO PLAYERS WITH GUI OPEN, NOT WHOLE WORLD
        for (EntityPlayer player : DimensionManager.getWorld(0).playerEntities) {
            if (screen.getIsPixelDrawMode()) {
                ModPackets.getNetwork().sendTo(new PacketGetScreenPixelsToClient(screen.getPixelData()), (EntityPlayerMP) player);
                continue;
            }
            ModPackets.getNetwork().sendTo(new PacketGetScreenCharactersToClient(screen.getCharacterData(), screen.getCharacterColors()), (EntityPlayerMP) player);
            ModPackets.getNetwork().sendTo(new PacketGetCursorPosToClient(currentCursorX, currentCursorY), (EntityPlayerMP) player);
        }
    }
    
    short r = 255;
    public void onKeyType(char character, int key) {
        
        // TODO: BASIC TEXT EDIT TEST PROGRAM USES ONLY 3 NATIVE COMMANDS
        //      "cur <x> <y>"       sets the cursor to (x, y)
        //      "sfl <r> <g> <b>"   sets the active color to r,g,b
        //      "fch <c> <m>"       sets the cursor to the character c, and moves the cursor right if m is true
        if (key == Keyboard.KEY_RIGHT) {
            CommandLineParser.execute(this, "native cur " + (currentCursorX + 1) + " " + currentCursorY);
            return;
        }
        if (key == Keyboard.KEY_LEFT) {
            CommandLineParser.execute(this, "native cur " + (currentCursorX - 1) + " " + currentCursorY);
            return;
        }
        if (key == Keyboard.KEY_UP) {
            CommandLineParser.execute(this, "native cur " + currentCursorX + " " + (currentCursorY - 1));
            return;
        }
        if (key == Keyboard.KEY_DOWN) {
            CommandLineParser.execute(this, "native cur " + currentCursorX + " " + (currentCursorY + 1));
            return;
        }
        CommandLineParser.execute(this, "native sfl " + r + " 255 255");
        CommandLineParser.execute(this, "native fch " + ((character != ' ') ? character : "\\s") + " true");
        r -= 10;
        if (r < 0) {
            r = (short) (255 + r);
        }
    }
    
    public final void _save(NBTTagCompound nbt) {
        nbt.setInteger("id", id);
        saveData(nbt);
    }
    
    public final boolean _load(NBTTagCompound nbt) {
        if (!nbt.hasKey("id")) {
            return false;
        }
        id = nbt.getShort("id");
        return loadData(nbt);
    }
    
    protected abstract void saveData(NBTTagCompound nbt);
    protected abstract boolean loadData(NBTTagCompound nbt);
    
    public short getId() {
        return id;
    }
    
    // Drawing utils
    
    public void setFillColor(byte r, byte g, byte b) {
        setFillColor(HexUtil.getHexColorNoAlpha(r, g, b));
    }
    
    public void setFillColor(int fillColor) {
        currentFillColor = fillColor;
    }
    
    public void fillCharacter(char character, boolean incrementCursor) {
        screen.setCharacter(currentCursorX, currentCursorY, character);
        screen.setCharacterColor(currentCursorX, currentCursorY, currentFillColor);
        if (incrementCursor) {
            currentCursorX ++;
            if (currentCursorX > ComputerScreen.SCREEN_CHARACTER_WIDTH - 1) {
                currentCursorX = 0;
                currentCursorY ++;
                if (currentCursorY > ComputerScreen.SCREEN_CHARACTER_HEIGHT - 1) {
                    currentCursorY = 0;
                }
            }
            setCursor(currentCursorX, currentCursorY);    // Wraps the cursor to the screen just in case
        }
    }
    
    public void fillString(String string, boolean incrementCursor) {
        if (string == null || (string = string.trim()).isEmpty()) {
            return;
        }
        short tmpCursorX = currentCursorX;
        short tmpCursorY = currentCursorY;
        char[] characters = string.toCharArray();
        for (int i = 0; i < characters.length; i ++) {
            fillCharacter(characters[i], true);
        }
        if (!incrementCursor) {
            setCursor(tmpCursorX, tmpCursorY);
        }
    }
    
    public void fillPixel(short x, short y) {
        screen.setPixel(x, y, currentFillColor);
    }
    
    public void setCursor(short cursorX, short cursorY) {
        currentCursorX = (short) Math.min(Math.max(0, cursorX), ComputerScreen.SCREEN_CHARACTER_WIDTH - 1);
        currentCursorY = (short) Math.min(Math.max(0, cursorY), ComputerScreen.SCREEN_CHARACTER_HEIGHT - 1);
        Debug.log(currentCursorX + " " + currentCursorY);
        cursorMoved = true;
    }
    
    public short getCursorX() {
        return currentCursorX;
    }
    
    public short getCursorY() {
        return currentCursorY;
    }
    
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + currentCursorX;
        result = prime * result + currentCursorY;
        result = prime * result + currentFillColor;
        result = prime * result + id;
        result = prime * result + ((screen == null) ? 0 : screen.hashCode());
        return result;
    }
    
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ComputerCore other = (ComputerCore) obj;
        if (currentCursorX != other.currentCursorX) {
            return false;
        }
        if (currentCursorY != other.currentCursorY) {
            return false;
        }
        if (currentFillColor != other.currentFillColor) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (screen == null) {
            if (other.screen != null) {
                return false;
            }
        } else if (!screen.equals(other.screen)) {
            return false;
        }
        return true;
    }
    
}