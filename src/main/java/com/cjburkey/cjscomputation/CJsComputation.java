package com.cjburkey.cjscomputation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cjburkey.cjscomputation.commandline.CommandLineParser;
import com.cjburkey.cjscomputation.computer.ComputerScreen;
import com.cjburkey.cjscomputation.proxy.CommonProxy;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = ModInfo.name, modid = ModInfo.modid, version = ModInfo.version, clientSideOnly = false, serverSideOnly = false, acceptedMinecraftVersions = ModInfo.mcVersions,
    acceptableRemoteVersions = "*", modLanguage = "java", canBeDeactivated = false, guiFactory = "", updateJSON = "", customProperties = {  })
public class CJsComputation {
    
    public static final Logger logger = LogManager.getLogger("cjscomputation");
    
    @Instance(owner = ModInfo.modid)
    private static CJsComputation instance = null;
    
    @SidedProxy(modId = ModInfo.modid, clientSide = ModInfo.clientProxy, serverSide = ModInfo.serverProxy)
    private static CommonProxy proxy = null;
    
    @EventHandler
    public void construct(FMLConstructionEvent e) {
        Debug.log("Constructing...");
        Debug.log("  DATA:");
        Debug.log("    Character size: {}x{}", ComputerScreen.CHARACTER_WIDTH, ComputerScreen.CHARACTER_HEIGHT);
        Debug.log("    Screen characters: {}x{}", ComputerScreen.SCREEN_CHARACTER_WIDTH, ComputerScreen.SCREEN_CHARACTER_HEIGHT);
        Debug.log("    Screen size: {}x{}", ComputerScreen.SCREEN_PIXEL_WIDTH, ComputerScreen.SCREEN_PIXEL_HEIGHT);
        proxy.construct(e);
        Debug.log("Constructed");
    }
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent e) {
        Debug.log("PreInitializing...");
        proxy.preinit(e);
        Debug.log("PreInitialized");
    }
    
    @EventHandler
    public void init(FMLInitializationEvent e) {
        Debug.log("Initializing...");
        proxy.init(e);
        Debug.log("Initialized");
    }
    
    @EventHandler
    public void postinit(FMLPostInitializationEvent e) {
        Debug.log("PostInitializing...");
        proxy.postinit(e);
        Debug.log("PostInitialized");
    }
    
    public static CJsComputation getInstance() {
        return instance;
    }
    
    public static CommonProxy getProxy() {
        return proxy;
    }
    
}