package absolemjackdaw.scryers.playerdata;

import absolemjackdaw.scryers.Scryers;
import absolemjackdaw.scryers.events.VisitDimensionEvent;
import absolemjackdaw.scryers.service.ScryersPlatformService;
import com.google.common.collect.ImmutableMap;
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
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

public record ScryData(Map<ResourceKey<Level>, TeleportTarget> visitedDimensions) {

    public static final Codec<ScryData> CODEC = Codec.unboundedMap(Level.RESOURCE_KEY_CODEC, TeleportTarget.CODEC).xmap(ScryData::new, ScryData::visitedDimensions);
    private static final ScryData EMPTY = new ScryData(Map.of());

    public static ScryData empty() {
        return EMPTY;
    }

    public static ScryData getFor(ServerPlayer player) {
        return ScryersPlatformService.SERVICE.getScryData(player);
    }

    public static void onVisitDimension(ResourceKey<Level> dimension, ServerPlayer player) {
        onVisitDimension(player, new TeleportTarget(dimension, player.position(), player.getXRot(), player.getYHeadRot(), Instant.now()));
    }

    public static void onVisitDimension(ServerPlayer player, TeleportTarget newEntry) {
        var data = ScryData.getFor(player);
        @Nullable var modified = VisitDimensionEvent.EVENT.invoker().onVisitDimension(player, data, newEntry);
        if (modified != null) {
            Map<ResourceKey<Level>, TeleportTarget> dimensions = ImmutableMap.<ResourceKey<Level>, TeleportTarget>builder()
                    .putAll(data.visitedDimensions())
                    .put(modified.dimension(), modified)
                    .buildKeepingLast();

            var value = new ScryData(dimensions);
            value.save(player);
        }
    }

    public void save(ServerPlayer player) {
        ScryersPlatformService.SERVICE.setScryData(player, this);
    }

    public Optional<TeleportTarget> findTargetMatching(ResourceKey<Level> target) {
        return Optional.ofNullable(visitedDimensions().get(target));
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

        public Component dimensionName() {
            return formatDimension(dimension());
        }

        public void teleportPlayer(ServerPlayer player) {
            if (player.level().dimension() == this.dimension()) {
                Scryers.sendErrorMessage(player, Component.translatable("message.scryers.same_dimension", this.dimensionName()));
                return;
            }

            var targetLevel = player.server.getLevel(this.dimension());
            if (targetLevel == null) {
                Scryers.sendErrorMessage(player, Component.translatable("message.scryers.no_target_level", this.dimensionName()));
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

            public Component dimensionName() {
                return TeleportTarget.formatDimension(dimension());
            }

            public Component translationKey() {
                return Component.translatableWithFallback(dimension().location().toLanguageKey("dimension"), dimension().location().toString());
            }
        }

        private static Component formatDimension(ResourceKey<Level> dimension) {
            return Component.literal(dimension.location().toString());
        }
    }
}
