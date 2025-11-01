package absolemjackdaw.scryers.menus.screens;

import absolemjackdaw.scryers.Scryers;
import absolemjackdaw.scryers.menus.CrystalBallTeleportMenu;
import absolemjackdaw.scryers.network.c2s.SelectTeleportTargetPacket;
import com.ibm.icu.text.RelativeDateTimeFormatter;
import commonnetwork.api.Network;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import java.util.Random;

public class CrystalBallTeleportScreen extends AbstractContainerScreen<CrystalBallTeleportMenu> {

    private static final ResourceLocation BACKGROUND_TEXTURE = Scryers.id("textures/gui/container/crystal_ball.png");
    private static final Random RANDOM = new Random();
    private final Instant openingTime;
    private final long openingTimeMillis;

    private final int rightMargin = 5;
    private final int topMargin = 16;
    private final int leftMargin = 6;
    private final int bottomMargin = 8;

    public CrystalBallTeleportScreen(CrystalBallTeleportMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.openingTime = Instant.now();
        this.openingTimeMillis = this.openingTime.toEpochMilli();
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = 5;

        var x1 = leftPos + rightMargin;
        var x2 = leftPos + imageWidth - leftMargin;
        var y1 = topPos + topMargin;
        var y2 = topPos + imageHeight - bottomMargin;

        float centerX = x1 + (x2 - x1) / 2.0F;
        float centerY = y1 + (y2 - y1) / 2.0F;

        RANDOM.setSeed(openingTimeMillis);
        var targetsCount = menu.getTargets().size();
        var maxAngle = 360.0F / targetsCount;

        for (int i = 0; i < targetsCount; i++) {
            var target = menu.getTargets().get(i);
            var angle = Mth.DEG_TO_RAD * (maxAngle * i + RANDOM.nextFloat(maxAngle));
            var distance = RANDOM.nextFloat(8, 65);

            // TODO make this translatable
            var minutesDiff = Duration.between(target.timestamp(), openingTime).toMinutes();
            var formattedTimeSince = RelativeDateTimeFormatter.getInstance(Locale.ENGLISH).format(minutesDiff, RelativeDateTimeFormatter.Direction.LAST, RelativeDateTimeFormatter.RelativeUnit.MINUTES);

            var x0 = Mth.cos(angle) * distance;
            var y0 = Mth.sin(angle) * distance;

            addRenderableWidget(Button.builder(Component.empty(), button -> {
                Network.getNetworkHandler().sendToServer(new SelectTeleportTargetPacket(target.dimension()));
            }).tooltip(Tooltip.create(Component.translatable("gui.scryer.crystal_ball.teleport_target", target.dimensionName(), formattedTimeSince))).pos((int) (centerX + x0) - 2, (int) (centerY + y0) - 2).size(4, 4).build());
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        var x1 = rightMargin;
        var x2 = imageWidth - leftMargin;
        var y1 = topMargin;
        var y2 = this.imageHeight - bottomMargin;

        float centerX = (x2 - x1 + this.leftMargin) / 2.0F;
        float centerY = (y2 - y1 + this.topMargin) / 2.0F;

        guiGraphics.fill((int) (centerX - 2), (int) (centerY - 2), (int) (centerX + 2), (int) (centerY + 2), 0xFFFFFFFF);


        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0x404040, false);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(BACKGROUND_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        var x1 = this.leftPos + rightMargin;
        var x2 = this.leftPos + imageWidth - leftMargin;
        var y1 = this.topPos + topMargin;
        var y2 = this.topPos + this.imageHeight - bottomMargin;

        guiGraphics.fillRenderType(RenderType.endPortal(), x1, y1, x2, y2, 0);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        // TODO button hover style
//        Style style = this.getComponentStyleAt((double)mouseX, (double)mouseY);
//        if (style != null && style.getHoverEvent() != null) {
//            guiGraphics.renderComponentHoverEffect(this.font, style, mouseX, mouseY);
//        }
    }
}
