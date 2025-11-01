package absolemjackdaw.scryers.service;

import absolemjackdaw.scryers.menus.CrystalBallTeleportMenu;
import dev.upcraft.sparkweave.api.platform.Services;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;

public interface ScryersPlatformService {

    ScryersPlatformService SERVICE = Services.getService(ScryersPlatformService.class);

    MenuType<CrystalBallTeleportMenu> createTeleportMenuType();

    void openTeleportMenu(ServerPlayer serverPlayer, CrystalBallTeleportMenu.MenuData data, ContainerLevelAccess containerLevelAccess);

    boolean isFakePlayer(Player player);
}
