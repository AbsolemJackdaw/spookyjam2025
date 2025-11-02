package absolemjackdaw.scryers.menus.screens;


import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class WorldButton extends Button {
    private static final WidgetSprites SPRITES = new WidgetSprites(ResourceLocation.withDefaultNamespace("icon/link"), ResourceLocation.withDefaultNamespace("icon/language"), ResourceLocation.withDefaultNamespace("icon/link_highlighted"));

    public WorldButton(int x, int y, int width, int height, Component message, OnPress onPress, CreateNarration createNarration) {
        super(x, y, width, height, message, onPress, createNarration);
    }

    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        guiGraphics.blitSprite(SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.active ? 16777215 : 10526880;
        //this.renderString(guiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    public static WorldButtonBuilder make(Component message, OnPress onPress) {
        return new WorldButtonBuilder(message, onPress);
    }

    @OnlyIn(Dist.CLIENT)
    public static class WorldButtonBuilder {

        private final Component message;
        private final OnPress onPress;
        @Nullable
        private Tooltip tooltip;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;
        private CreateNarration createNarration;

        public WorldButtonBuilder(Component message, OnPress onPress) {
            this.createNarration = Button.DEFAULT_NARRATION;
            this.message = message;
            this.onPress = onPress;
        }

        public WorldButtonBuilder pos(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public WorldButtonBuilder width(int width) {
            this.width = width;
            return this;
        }

        public WorldButtonBuilder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public WorldButtonBuilder bounds(int x, int y, int width, int height) {
            return this.pos(x, y).size(width, height);
        }

        public WorldButtonBuilder tooltip(@Nullable Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public WorldButtonBuilder createNarration(CreateNarration createNarration) {
            this.createNarration = createNarration;
            return this;
        }

        public WorldButton build() {
            var button = new WorldButton(this.x, this.y, this.width, this.height, this.message, this.onPress, this.createNarration);
            button.setTooltip(this.tooltip);
            return button;
        }
    }
}
