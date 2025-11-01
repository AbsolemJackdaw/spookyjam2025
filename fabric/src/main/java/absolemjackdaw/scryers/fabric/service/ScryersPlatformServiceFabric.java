package absolemjackdaw.scryers.fabric.service;

import absolemjackdaw.scryers.blocks.CrystalBallBlock;
import absolemjackdaw.scryers.menus.CrystalBallTeleportMenu;
import absolemjackdaw.scryers.service.ScryersPlatformService;
import com.google.auto.service.AutoService;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

@AutoService(ScryersPlatformService.class)
public class ScryersPlatformServiceFabric implements ScryersPlatformService {
    @Override
    public MenuType<CrystalBallTeleportMenu> createTeleportMenuType() {
        return new ExtendedScreenHandlerType<>((syncId, inventory, data) -> new CrystalBallTeleportMenu(syncId, inventory, ContainerLevelAccess.NULL, data), CrystalBallTeleportMenu.MenuData.STREAM_CODEC);
    }

    @Override
    public void openTeleportMenu(ServerPlayer serverPlayer, CrystalBallTeleportMenu.MenuData data, ContainerLevelAccess containerLevelAccess) {
        serverPlayer.openMenu(new ExtendedScreenHandlerFactory<CrystalBallTeleportMenu.MenuData>() {
            @Override
            public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
                if(ScryersPlatformService.SERVICE.isFakePlayer(player)) {
                    return null;
                }

                return new CrystalBallTeleportMenu(containerId, player.getInventory(), containerLevelAccess, data);
            }

            @Override
            public Component getDisplayName() {
                return CrystalBallBlock.CONTAINER_NAME;
            }

            @Override
            public CrystalBallTeleportMenu.MenuData getScreenOpeningData(ServerPlayer player) {
                return data;
            }
        });
    }

    @Override
    public boolean isFakePlayer(Player player) {
        return player instanceof FakePlayer;
    }
}
