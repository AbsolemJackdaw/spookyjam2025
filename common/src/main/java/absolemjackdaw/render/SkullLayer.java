package absolemjackdaw.render;

import absolemjackdaw.mod.ScryersCommon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class SkullLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    private static final Random RANDOM = new Random();

    public SkullLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing,
                       float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (Minecraft.getInstance().player != null && !ScryersCommon.isScryer(Minecraft.getInstance().player) || RANDOM.nextInt(20) > 0)
            return;

        var bodyRot = Math.toRadians((entity.yBodyRot % 360) + 360) % 360;
        double armOffset = entity.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;

        double baseX = (0.4 * armOffset) + (RANDOM.nextDouble(0.20) - 0.1);
        double baseZ = 0.4 + (RANDOM.nextDouble(0.20) - 0.1);

        double offsetX = baseX * Math.cos(bodyRot) - baseZ * Math.sin(bodyRot);
        double offsetZ = baseX * Math.sin(bodyRot) + baseZ * Math.cos(bodyRot);

        double rotX = entity.position().x() + offsetX;
        double rotZ = entity.position().z() + offsetZ;
        Minecraft.getInstance().level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, rotX, entity.position().y() + 0.8, rotZ, 0, 0, 0);
    }
}
