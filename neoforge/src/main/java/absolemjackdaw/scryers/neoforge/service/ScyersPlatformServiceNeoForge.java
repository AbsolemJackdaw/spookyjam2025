package absolemjackdaw.scryers.neoforge.service;

import absolemjackdaw.scryers.blocks.CrystalBallBlock;
import absolemjackdaw.scryers.menus.CrystalBallTeleportMenu;
import absolemjackdaw.scryers.neoforge.init.ScryersAttachmentTypes;
import absolemjackdaw.scryers.playerdata.ScryData;
import absolemjackdaw.scryers.service.ScryersPlatformService;
import com.google.auto.service.AutoService;
import com.google.common.base.Preconditions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import org.jetbrains.annotations.Nullable;

@AutoService(ScryersPlatformService.class)
public class ScyersPlatformServiceNeoForge implements ScryersPlatformService {
    @Override
    public MenuType<CrystalBallTeleportMenu> createTeleportMenuType() {
        return IMenuTypeExtension.create((containerId, inventory, byteBuf) -> {
            // byteBuf is nullable here, but should never be!
            Preconditions.checkNotNull(byteBuf, "Someone tried to open a menu the wrong way :(");

            var data = CrystalBallTeleportMenu.MenuData.STREAM_CODEC.decode(byteBuf);
            return new CrystalBallTeleportMenu(containerId, inventory, ContainerLevelAccess.NULL, data);
        });
    }

    @Override
    public void openTeleportMenu(ServerPlayer serverPlayer, CrystalBallTeleportMenu.MenuData data, ContainerLevelAccess containerLevelAccess) {
        serverPlayer.openMenu(new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return CrystalBallBlock.CONTAINER_NAME;
            }

            @Override
            public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
                if(ScryersPlatformService.SERVICE.isFakePlayer(player)) {
                    return null;
                }

                return new CrystalBallTeleportMenu(containerId, inventory, containerLevelAccess, data);
            }
        }, byteBuf -> CrystalBallTeleportMenu.MenuData.STREAM_CODEC.encode(byteBuf, data));
    }

    @Override
    public boolean isFakePlayer(Player player) {
        return player.isFakePlayer();
    }

    @Override
    public ScryData getScryData(ServerPlayer player) {
        return player.getData(ScryersAttachmentTypes.SCRY_PLAYER_DATA.get());
    }

    @Override
    public void setScryData(ServerPlayer player, ScryData data) {
        player.setData(ScryersAttachmentTypes.SCRY_PLAYER_DATA.get(), data);
    }

}
