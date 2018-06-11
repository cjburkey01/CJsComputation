package com.cjburkey.cjscomputation.computer;

import com.cjburkey.cjscomputation.tile.TileEntityComputer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ComputerPlaced extends ComputerCore {
    
    private int dimensionId;
    private BlockPos blockPos;
    
    public ComputerPlaced(ComputerHandler computerHandler, TileEntityComputer computer) {
        this (computerHandler, computer.getWorld(), computer.getPos());
    }
    
    public ComputerPlaced(ComputerHandler computerHandler, World world, BlockPos blockPos) {
        this (computerHandler, world.provider.getDimension(), blockPos);
    }
    
    public ComputerPlaced(ComputerHandler computerHandler, int dimensionId, BlockPos blockPos) {
        super(computerHandler);
        
        this.dimensionId = dimensionId;
        this.blockPos = new BlockPos(blockPos);
    }
    
    // Loaded with NBT
    public ComputerPlaced(short id) {
        super (id);
    }
    
    public void saveData(NBTTagCompound nbt) {
        nbt.setInteger("dimension", dimensionId);
        NBTTagCompound pos = new NBTTagCompound();
        pos.setInteger("x", blockPos.getX());
        pos.setInteger("y", blockPos.getY());
        pos.setInteger("z", blockPos.getZ());
        nbt.setTag("position", pos);
    }
    
    public boolean loadData(NBTTagCompound nbt) {
        if (nbt.hasKey("dimension")) {
            dimensionId = nbt.getInteger("dimensionId");
        }
        if (nbt.hasKey("position")) {
            NBTTagCompound pos = nbt.getCompoundTag("position");
            int x = 0;
            int y = 0;
            int z = 0;
            if (pos.hasKey("x")) {
                x = pos.getInteger("x");
            }
            if (pos.hasKey("y")) {
                y = pos.getInteger("y");
            }
            if (pos.hasKey("z")) {
                z = pos.getInteger("z");
            }
            blockPos = new BlockPos(x, y, z);
        }
        return (nbt.hasKey("dimension") && nbt.hasKey("position"));
    }
    
    public int getDimensionId() {
        return dimensionId;
    }
    
    public BlockPos getPosition() {
        return blockPos;
    }
    
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((blockPos == null) ? 0 : blockPos.hashCode());
        result = prime * result + dimensionId;
        return result;
    }
    
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ComputerPlaced other = (ComputerPlaced) obj;
        if (blockPos == null) {
            if (other.blockPos != null) {
                return false;
            }
        } else if (!blockPos.equals(other.blockPos)) {
            return false;
        }
        if (dimensionId != other.dimensionId) {
            return false;
        }
        return true;
    }
    
}