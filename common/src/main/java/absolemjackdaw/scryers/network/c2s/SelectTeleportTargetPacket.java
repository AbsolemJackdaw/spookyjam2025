package absolemjackdaw.scryers.network.c2s;

import absolemjackdaw.scryers.Scryers;
import absolemjackdaw.scryers.menus.CrystalBallTeleportMenu;
import absolemjackdaw.scryers.playerdata.ScryData;
import commonnetwork.networking.data.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public record SelectTeleportTargetPacket(ResourceKey<Level> dimension) implements CustomPacketPayload {

    public static final ResourceLocation ID = Scryers.id("c2s_select_teleport_target");
    public static final Type<SelectTeleportTargetPacket> TYPE = new Type<>(ID);
    public static final StreamCodec<FriendlyByteBuf, SelectTeleportTargetPacket> STREAM_CODEC = StreamCodec.composite(
            ScryData.TeleportTarget.Client.LEVEL_KEY_STREAM_CODEC,
            SelectTeleportTargetPacket::dimension,

            SelectTeleportTargetPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(PacketContext<SelectTeleportTargetPacket> ctx) {
        var player = ctx.sender();
        var target = ctx.message().dimension();
        if(!(player.containerMenu instanceof CrystalBallTeleportMenu)) {
            Scryers.sendErrorMessage(player, Component.translatable("message.scryers.invalid_screen"));
            return;
        }

        player.closeContainer();
        var scryData = ScryData.getFor(player);
        scryData.findTargetMatching(target).ifPresentOrElse(
                teleportTarget -> teleportTarget.teleportPlayer(player),
                () -> Scryers.sendErrorMessage(player, Component.translatable("message.scryers.invalid_target", target.location()))
        );
    }
}
