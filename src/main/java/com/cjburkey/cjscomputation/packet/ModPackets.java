package com.cjburkey.cjscomputation.packet;

import com.cjburkey.cjscomputation.ModInfo;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ModPackets {
    
    private static SimpleNetworkWrapper network;
    private static int i = 0;
    
    public static void commonPreinit() {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.modid);
        
        network.registerMessage(PacketGetDrawTypeToServer.Handler.class, PacketGetDrawTypeToServer.class, i ++, Side.SERVER);
        network.registerMessage(PacketGetDrawTypeToClient.Handler.class, PacketGetDrawTypeToClient.class, i ++, Side.CLIENT);
        
        network.registerMessage(PacketGetScreenCharactersToServer.Handler.class, PacketGetScreenCharactersToServer.class, i ++, Side.SERVER);
        network.registerMessage(PacketGetScreenCharactersToClient.Handler.class, PacketGetScreenCharactersToClient.class, i ++, Side.CLIENT);
        
        network.registerMessage(PacketGetScreenPixelsToServer.Handler.class, PacketGetScreenPixelsToServer.class, i ++, Side.SERVER);
        network.registerMessage(PacketGetScreenPixelsToClient.Handler.class, PacketGetScreenPixelsToClient.class, i ++, Side.CLIENT);
        
        network.registerMessage(PacketKeyTypedToServer.Handler.class, PacketKeyTypedToServer.class, i ++, Side.SERVER);
    }
    
    public static SimpleNetworkWrapper getNetwork() {
        return network;
    }
    
}