package com.cjburkey.cjscomputation.item;

import java.util.ArrayList;
import java.util.List;
import com.cjburkey.cjscomputation.Debug;
import com.cjburkey.cjscomputation.ModInfo;
import com.cjburkey.cjscomputation.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

public class ModItems {
    
    private static final List<Item> items = new ArrayList<>();
    
    public static void registerItems(RegistryEvent.Register<Item> event) {
        Debug.log("Registering items...");
        registerBlockItems(event);
        Debug.log("Registered items");
    }
    
    public static void registerRenders(ModelRegistryEvent e) {
        Debug.log("Registering renders...");
        for (Item item : items) {
            registerRender(item);
        }
        Debug.log("Registered renders");
    }
    
    private static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
    
    private static void registerBlockItems(RegistryEvent.Register<Item> e) {
        for (Block block : ModBlocks.getRegisteredBlocks()) {
            registerItem(e, new ItemBlock(block), block.getRegistryName().getResourcePath());
        }
    }
    
    private static Item registerItem(RegistryEvent.Register<Item> e, Item item, String name) {
        item.setUnlocalizedName(name);
        item.setRegistryName(ModInfo.modid, name);
        e.getRegistry().register(item);
        items.add(item);
        return item;
    }
    
    public static Item[] getRegisteredItems() {
        return items.toArray(new Item[items.size()]);
    }
    
}