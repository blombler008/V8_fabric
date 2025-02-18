package com.tattyhost.fabric.v8.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tattyhost.fabric.v8.V8;
import com.tattyhost.fabric.v8.screens.GuenterScreenHandler;
import com.tattyhost.fabric.v8.screens.HighTempFurnaceScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class GuenterScreen extends HandledScreen<GuenterScreenHandler> {
    // A path to the gui texture. In this example we use the texture from the dispenser

    private static final Identifier TEXTURE = Identifier.of(V8.MOD_ID, "textures/gui/screen/guenter_gui.png");
    private static final Identifier ARROW = Identifier.of(V8.MOD_ID, "textures/gui/icons/guenter_arrow.png");
    // For versions before 1.21:
    // private static final Identifier TEXTURE = new Identifier("minecraft", "textures/gui/container/dispenser.png");

    public GuenterScreen(GuenterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, Text.translatable("container.v8.guenter"));
        playerInventoryTitleY = 48;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
//        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x, y, 0, 0, 176, 141, 176, 141);

        // Draw the progress arrow
        int progress = handler.getProgress();
        int maxProgress = handler.getMaxProgress();
        int arrowWidth = (int) ((float) progress / maxProgress * 16);
        context.drawTexture(RenderLayer::getGuiTextured, ARROW, x+74,y+24, 0, 0, arrowWidth, 9, 16, 9);
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
//        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}