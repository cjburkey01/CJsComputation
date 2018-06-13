package com.cjburkey.cjscomputation.proxy;

import com.cjburkey.cjscomputation.CJsComputation;
import com.cjburkey.cjscomputation.Debug;
import com.cjburkey.cjscomputation.ModInfo;
import com.cjburkey.cjscomputation.commandline.CommandLineNativeCall;
import com.cjburkey.cjscomputation.commandline.CommandLineParser;
import com.cjburkey.cjscomputation.gui.ModGuiHandler;
import com.cjburkey.cjscomputation.natives.NativeCallFillChar;
import com.cjburkey.cjscomputation.natives.NativeCallSetCursor;
import com.cjburkey.cjscomputation.natives.NativeCallSetFill;
import com.cjburkey.cjscomputation.natives.Natives;
import com.cjburkey.cjscomputation.packet.ModPackets;
import com.cjburkey.cjscomputation.tile.TileEntityComputer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
    
    public void construct(FMLConstructionEvent e) {
        initNativeCalls();
        initCommandLineCalls();
    }
    
    public void preinit(FMLPreInitializationEvent e) {
        ModPackets.commonPreinit();
    }
    
    public void init(FMLInitializationEvent e) {
        GameRegistry.registerTileEntity(TileEntityComputer.class, new ResourceLocation(ModInfo.modid, "tile_computer"));
        NetworkRegistry.INSTANCE.registerGuiHandler(CJsComputation.getInstance(), new ModGuiHandler());
    }
    
    public void postinit(FMLPostInitializationEvent e) {
    }
    
    private static void initNativeCalls() {
        Natives.addNative(new NativeCallFillChar());
        Natives.addNative(new NativeCallSetCursor());
        Natives.addNative(new NativeCallSetFill());
        
        Debug.log("Initialized native calls");
    }
    
    private static void initCommandLineCalls() {
        CommandLineParser.addCommand(new CommandLineNativeCall());
        
        Debug.log("Initialized command line calls");
    }
    
}