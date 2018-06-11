package com.cjburkey.cjscomputation.gui;

import com.cjburkey.cjscomputation.computer.ComputerPlaced;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGuiHandler implements IGuiHandler {
    
    // The "x" is the computer id to be a hack-y son-of-a-b
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == 0) {
            return new ContainerComputer((short) x);
        }
        return null;
    }
    
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == 0) {  // 0 = Placed computer
            return new GuiComputer((short) x, ComputerPlaced.class);
        }
        return null;
    }
    
}