package com.archaic.archaicevent.Gui;

import com.archaic.archaicevent.ArchaicEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class Teams extends GuiScreen {

    private static final int BUTTON_ID = 0;
    private GuiButton exampleButton;

    private static final int GUI_WIDTH_PERCENT = 100;
    private static final int GUI_HEIGHT_PERCENT = 100;

    private int guiWidth;
    private int guiHeight;
    private int guiLeft;
    private int guiTop;

    @Override
    public void initGui() {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        guiWidth = scaledResolution.getScaledWidth() * GUI_WIDTH_PERCENT / 100;
        guiHeight = scaledResolution.getScaledHeight() * GUI_HEIGHT_PERCENT / 100;

        guiLeft = (width - guiWidth) / 2;
        guiTop = (height - guiHeight) / 2;

        buttonList.add(new GuiButton(0, guiLeft + 10, guiTop + 10, 80, 20, "Click me!"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        // Check if the clicked button is the exampleButton
        if (button.id == BUTTON_ID) {
//            // Handle button click action
//            EntityPlayer player = mc.player;
//            ForgePlayer p;
//            try {
//                p = CommandUtils.getForgePlayer(player);
//                ArchaicEvent.logger.info(p.team.getTitle());
//            } catch (CommandException e) {
//                e.printStackTrace();
//            }
            System.out.println("Button Clicked!");

        }
    }

    public void drawTeam(){
//        String playerName = "Brian729"; // Replace with the actual player's name
//        String skinUrl = "https://minotar.net/avatar/" + playerNam
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Load the background texture
        ResourceLocation backgroundTexture = new ResourceLocation(ArchaicEvent.MOD_NAME, "textures/gui/background_squares.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(backgroundTexture);

        // Set up the rendering to use the background texture
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, guiWidth, guiHeight, guiWidth, guiHeight);

        drawTeam();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}
