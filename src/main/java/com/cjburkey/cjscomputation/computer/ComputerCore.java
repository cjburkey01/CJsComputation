package com.cjburkey.cjscomputation.computer;

import com.cjburkey.cjscomputation.Debug;
import com.cjburkey.cjscomputation.process.IProcessHost;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ComputerCore implements IProcessHost {
    
    private short id;
    public final ComputerScreen screen;
    
    public ComputerCore(ComputerHandler handler) {
        this ();
        id = handler.getNextId();
    }
    
    // Init or to be loaded with NBT
    public ComputerCore() {
        id = -1;
        screen = new ComputerScreen(this);
        
        // TODO: DEBUG
//        screen.setCharacterColor((short) 0, (short) 0, 0xFFFFFF, false);
//        screen.setCharacterColor((short) 1, (short) 0, 0xFFFFFF, false);
//        screen.setCharacterColor((short) 2, (short) 0, 0xFFFFFF, false);
//        screen.setCharacter((short) 0, (short) 0, 'B', false);
//        screen.setCharacter((short) 1, (short) 0, 'o', false);
//        screen.setCharacter((short) 2, (short) 0, 'b', false);
        screen.setPixelDrawMode();
        screen.setPixel((short) 10, (short) 10, 0xFFFFFF, false);
        screen.setPixel((short) 10, (short) 11, 0xFFFFFF, false);
        screen.setPixel((short) 11, (short) 11, 0xFFFFFF, false);
        screen.setPixel((short) 11, (short) 10, 0xFFFFFF, false);
    }
    
    // TODO: SEND A VISUAL UPDATE PACKET TO ALL PLAYERS WHO SEE THE COMPUTER'S GUI WHEN IT CHANGES
    public final void sendUpdateToViewers() {
    }
    
    public void onKeyType(char character, int key) {
        Debug.log("Key pressed");
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
    
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
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
        if (id != other.id) {
            return false;
        }
        return true;
    }
    
}