package com.cjburkey.cjscomputation.block;

import java.util.ArrayList;
import java.util.List;
import com.cjburkey.cjscomputation.Debug;
import com.cjburkey.cjscomputation.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;

public class ModBlocks {
    
    private static final List<Block> blocks = new ArrayList<>();
    
    public static BlockComputer blockComputer;
    
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        Debug.log("Registering blocks...");
        
        blockComputer = registerBlock(event, new BlockComputer(), "block_computer");
        
        Debug.log("Registered blocks");
    }
    
    private static <T extends Block> T registerBlock(RegistryEvent.Register<Block> e, T block, String name) {
        block.setUnlocalizedName(name);
        block.setRegistryName(ModInfo.modid, name);
        e.getRegistry().register(block);
        blocks.add(block);
        return block;
    }
    
    public static Block[] getRegisteredBlocks() {
        return blocks.toArray(new Block[blocks.size()]);
    }
    
}