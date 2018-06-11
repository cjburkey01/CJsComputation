package com.cjburkey.cjscomputation.computer;

import net.minecraft.nbt.NBTTagCompound;

public abstract class ComputerCore {
    
    public final short id;
    public final transient ComputerScreen screen;
    
    private ComputerCore() {
        id = -1;
        screen = new ComputerScreen();
    }
    
    public abstract void saveData(NBTTagCompound nbt);
    public abstract boolean loadData(NBTTagCompound nbt);
    
    public ComputerCore(ComputerHandler handler) {
        this (handler.getNextId());
    }
    
    // Loaded with NBT
    public ComputerCore(short id) {
        this.id = id;
        screen = new ComputerScreen();
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