package absolemjackdaw.scryers.menus;

import absolemjackdaw.scryers.init.ScryersBlocks;
import absolemjackdaw.scryers.init.ScryersMenus;
import absolemjackdaw.scryers.playerdata.ScryData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Comparator;
import java.util.List;

public class CrystalBallTeleportMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess access;
    private final MenuData data;

    public CrystalBallTeleportMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, MenuData data) {
        super(ScryersMenus.TELEPORT_MENU.get(), containerId);
        this.access = access;
        this.data = data;
    }

    public List<ScryData.TeleportTarget.Client> getTargets() {
        return this.data.targets();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.access.evaluate((level, blockPos) -> this.isValidBlock(level.getBlockState(blockPos)) && player.canInteractWithBlock(blockPos, 4.0 /* player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE) TODO do we want to diverge from vanilla here? */), true);
    }

    private boolean isValidBlock(BlockState state) {
        return state.is(ScryersBlocks.CRYSTAL_BALL.get());
    }

    public record MenuData(List<ScryData.TeleportTarget.Client> targets) {
        public static final StreamCodec<FriendlyByteBuf, MenuData> STREAM_CODEC = ScryData.TeleportTarget.Client.STREAM_CODEC
                .apply(ByteBufCodecs.list())
                .map(MenuData::new, MenuData::targets);
    }

    public static MenuData getDataFor(ServerPlayer player) {
        var targets = ScryData.getFor(player).visitedDimensions()
                .values()
                .stream()
                .filter(target -> target.dimension() != player.level().dimension())
                .sorted(Comparator.comparing(ScryData.TeleportTarget::timestamp))
                .map(ScryData.TeleportTarget::toClientValue)
                .toList();
        return new MenuData(targets);
    }
}
