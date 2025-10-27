package absolemjackdaw.render;

import absolemjackdaw.mod.ScryersCommon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.HumanoidArm;

import java.util.Random;

public class SkullLayer extends RenderLayer<PlayerRenderState, PlayerModel> {

    private static final Random RANDOM = new Random();

    public SkullLayer(PlayerRenderer renderer, EntityModelSet entityModelSet) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, PlayerRenderState state, float v, float v1) {

        if (Minecraft.getInstance().player != null && !ScryersCommon.isScryer(Minecraft.getInstance().player) || RANDOM.nextInt(20) > 0)
            return;

        var bodyRot = Math.toRadians((state.bodyRot % 360) + 360) % 360;
        double armOffset = state.mainArm == HumanoidArm.RIGHT ? 1 : -1;

        double baseX = (0.4 * armOffset) + (RANDOM.nextDouble(0.20) - 0.1);
        double baseZ = 0.4 + (RANDOM.nextDouble(0.20) - 0.1);

        double offsetX = baseX * Math.cos(bodyRot) - baseZ * Math.sin(bodyRot);
        double offsetZ = baseX * Math.sin(bodyRot) + baseZ * Math.cos(bodyRot);

        double rotX = state.x + offsetX;
        double rotZ = state.z + offsetZ;
        Minecraft.getInstance().level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, rotX, state.y + 0.8, rotZ, 0, 0, 0);
    }
}
