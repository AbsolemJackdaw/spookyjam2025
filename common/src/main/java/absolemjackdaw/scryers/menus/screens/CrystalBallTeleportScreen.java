package absolemjackdaw.scryers.menus.screens;

import absolemjackdaw.scryers.Scryers;
import absolemjackdaw.scryers.menus.CrystalBallTeleportMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CrystalBallTeleportScreen extends AbstractContainerScreen<CrystalBallTeleportMenu> {


    private static final ResourceLocation BACKGROUND_TEXTURE = Scryers.id("textures/gui/container/crystal_ball.png");

    public CrystalBallTeleportScreen(CrystalBallTeleportMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        // TODO implement
    }
}
