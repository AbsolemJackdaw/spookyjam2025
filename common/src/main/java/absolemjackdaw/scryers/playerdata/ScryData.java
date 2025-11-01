package absolemjackdaw.scryers.playerdata;

import absolemjackdaw.scryers.Scryers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.time.Instant;
import java.util.*;

public class ScryData {

    private final Map<ResourceKey<Level>, TeleportTarget> VISITED_DIMENSIONS = new HashMap<>();
    private final Map<ResourceKey<Level>, TeleportTarget> VISITED_DIMENSIONS_VIEW = Collections.unmodifiableMap(VISITED_DIMENSIONS);

    public static ScryData getFor(ServerPlayer player) {
        // TODO implement
        return null;
    }

    public Optional<TeleportTarget> findTargetMatching(ResourceKey<Level> target) {
        return Optional.ofNullable(VISITED_DIMENSIONS.get(target));
    }

    public void visitDimension(ServerPlayer player, Instant time) {
        var dimension = player.level().dimension();
        var pos = player.position();
        var pitch = player.getXRot();
        var yaw = player.getYHeadRot();
        VISITED_DIMENSIONS.put(dimension, new TeleportTarget(dimension, pos, pitch, yaw, time));
    }

    public Map<ResourceKey<Level>, TeleportTarget> getVisitedDimenions() {
        return VISITED_DIMENSIONS_VIEW;
    }

    public record TeleportTarget(ResourceKey<Level> dimension, Vec3 position, float pitch, float yaw,
                                 Instant timestamp) {
        public static final Codec<TeleportTarget> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Level.RESOURCE_KEY_CODEC.fieldOf("dimension").forGetter(TeleportTarget::dimension),
                Vec3.CODEC.fieldOf("position").forGetter(TeleportTarget::position),
                Codec.FLOAT.fieldOf("pitch").forGetter(TeleportTarget::pitch),
                Codec.FLOAT.fieldOf("yaw").forGetter(TeleportTarget::yaw),
                ExtraCodecs.INSTANT_ISO8601.fieldOf("timestamp").forGetter(TeleportTarget::timestamp)
        ).apply(instance, TeleportTarget::new));

        public Client toClientValue() {
            var pos = this.position().add(0.5D, 0.0D, 0.5D);
            var asBlockPos = new BlockPos((int) Math.round(pos.x()), Mth.floor(pos.y()), (int) Math.round(pos.z()));
            return new Client(this.dimension(), asBlockPos, this.timestamp());
        }

        public void teleportPlayer(ServerPlayer player) {
            if(player.level().dimension() == this.dimension()) {
                Scryers.sendErrorMessage(player, Component.translatable("message.scryers.same_dimension", this.dimension()));
                return;
            }

            var targetLevel = player.server.getLevel(this.dimension());
            if(targetLevel == null) {
                Scryers.sendErrorMessage(player, Component.translatable("message.scryers.no_target_level", this.dimension()));
                return;
            }

            var teleportSound = SoundEvents.ENDERMAN_TELEPORT;

            player.playSound(teleportSound, 1.0F, 1.0F);
            player.teleportTo(targetLevel, position().x(), position().y(), position().z(), yaw(), pitch());
            targetLevel.playSound(null, player.getX(), player.getY(), player.getZ(), teleportSound, player.getSoundSource(), 1.0F, 1.0F);
        }

        public record Client(ResourceKey<Level> dimension, BlockPos pos, Instant timestamp) {
            public static final StreamCodec<ByteBuf, ResourceKey<Level>> LEVEL_KEY_STREAM_CODEC = ResourceKey.streamCodec(Registries.DIMENSION);
            public static final StreamCodec<ByteBuf, Instant> ISO_INSTANT_STREAM_CODEC = ByteBufCodecs.STRING_UTF8.map(Instant::parse, Instant::toString);

            public static final StreamCodec<FriendlyByteBuf, Client> STREAM_CODEC = StreamCodec.composite(
                    LEVEL_KEY_STREAM_CODEC,
                    Client::dimension,

                    BlockPos.STREAM_CODEC,
                    Client::pos,

                    ISO_INSTANT_STREAM_CODEC,
                    Client::timestamp,

                    Client::new
            );
        }
    }
}
