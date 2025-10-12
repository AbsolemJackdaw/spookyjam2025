package absolemjackdaw.events;

import absolemjackdaw.mod.ScryersCommon;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Random;

@EventBusSubscriber(modid = ScryersCommon.MODID)
public class SkullParticleEvent {

    @SubscribeEvent
    public static void holdSkullEvent(PlayerTickEvent.Post event) {
        var player = event.getEntity();
        var level = player.level();
        if (!ScryersCommon.isWitch(player))
            return;

        if (level.isClientSide() && new Random().nextInt(10) == 0) {
            // Base position: player eye height or mid-body
            double baseX = player.getX();
            double baseY = player.getY() + player.getBbHeight() * 0.7; // around chest height
            double baseZ = player.getZ();

            // Rotation data
            float yaw = player.yBodyRot; // horizontal rotation

            // Convert yaw to radians
            double yawRad = Math.toRadians(yaw - 90); // adjust so 0° faces +X in Minecraft coords

            var side = player.getMainArm().equals(HumanoidArm.RIGHT) ? -1 : 1;
            // Offset from player center to left (offhand)
            double armOffsetSide = -0.4 * side; // negative = left arm
            double armOffsetForward = -0.4; // slightly in front
            double armOffsetUp = -0.4; // vertical tweak

            // Rotate offsets by yaw
            double offsetX = Math.cos(yawRad) * armOffsetForward - Math.sin(yawRad) * armOffsetSide;
            double offsetZ = Math.sin(yawRad) * armOffsetForward + Math.cos(yawRad) * armOffsetSide;

            // Final world coordinates
            double px = baseX + offsetX + new Random().nextDouble(0.2) - 0.1;
            double py = baseY + armOffsetUp;
            double pz = baseZ + offsetZ + new Random().nextDouble(0.2) - 0.1;

            // Add particles
            level.addParticle(ParticleTypes.SMOKE, px, py, pz, 0.0, 0.0, 0.0);
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, px, py, pz, 0.0, 0.0, 0.0);
        }

    }
}
