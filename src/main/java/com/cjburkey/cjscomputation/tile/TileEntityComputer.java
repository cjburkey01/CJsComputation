package com.cjburkey.cjscomputation.tile;

import com.cjburkey.cjscomputation.computer.ComputerPlaced;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityComputer extends TileEntity implements ITickable {
    
    private short id = -1;
    private ComputerPlaced computer;
    
    public void update() {
        if (getWorld().isRemote) {
            return;
        }
    }
    
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        
        if (compound.hasKey("computer_id")) {
            id = compound.getShort("computer_id");
        }
    }
    
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        
        compound.setShort("computer_id", id);
        
        return compound;
    }
    
    public int getId() {
        return id;
    }
    
    public void setComputer(ComputerPlaced computer) {
        this.id = computer.id;
        this.computer = computer;
        markDirty();
    }
    
}