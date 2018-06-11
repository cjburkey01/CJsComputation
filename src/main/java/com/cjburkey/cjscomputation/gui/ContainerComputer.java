package com.cjburkey.cjscomputation.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerComputer extends Container {
    
    public final short computerId;
    
    public ContainerComputer(short computerId) {
        this.computerId = computerId;
    }
    
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
    
}