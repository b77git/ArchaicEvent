package com.archaic.archaicevent.Client;

import com.archaic.archaicevent.Gui.Teams;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import static org.lwjgl.input.Keyboard.KEY_Y;

public class ClientKeybinds {
    public static final String CATEGORY = "key.categories.archaicevents";

    public static final KeyBinding exampleKeybind = new KeyBinding("key.archaicevents.teams", KeyConflictContext.IN_GAME, KeyModifier.NONE,  KEY_Y, CATEGORY);

    public static void registerKeybinds() {
        ClientRegistry.registerKeyBinding(exampleKeybind);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (exampleKeybind.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new Teams());
        }
    }
}
