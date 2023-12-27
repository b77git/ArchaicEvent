package com.archaic.archaicevent;

import com.archaic.archaicevent.Client.ClientKeybinds;
import com.archaic.archaicevent.Gui.GuiHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(
        modid = ArchaicEvent.MOD_ID,
        name = ArchaicEvent.MOD_NAME,
        version = ArchaicEvent.VERSION
)
public class ArchaicEvent {

    public static final String MOD_ID = "archaicevent";
    public static final String MOD_NAME = "ArchaicEvent";
    public static final String VERSION = "1.0-SNAPSHOT";

    public static Logger logger;
    public static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    public static File datafolderDir;

    @Mod.Instance(MOD_ID)
    public static ArchaicEvent INSTANCE;
    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        if (event.getSide().isClient()) {
            clientSetup();
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {


    }

    @EventHandler
    public void postinit(FMLPostInitializationEvent event) {
    }

    @EventHandler
    public void serverSetup(FMLServerStartingEvent event) {
        logger.info("Joining multiplayer world doing server setup!");
        MinecraftForge.EVENT_BUS.register(new ServersideHandlers());
        // Get the server directory
        File serverDir = event.getServer().getDataDirectory();
        datafolderDir = new File(serverDir, "EventData");
        if (!datafolderDir.exists()) {
            if (datafolderDir.mkdir()) {
                logger.info("EventData folder created at: " + datafolderDir.getAbsolutePath());
            } else {
                logger.error("Failed to create EventData folder!");
            }
        }
    }

    public void clientSetup(){
        ClientKeybinds.registerKeybinds();
        MinecraftForge.EVENT_BUS.register(ClientKeybinds.class);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }
}
