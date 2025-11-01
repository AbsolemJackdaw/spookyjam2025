package absolemjackdaw.scryers.client.render;

import absolemjackdaw.scryers.Scryers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class SkullLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    public SkullLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(!Scryers.hasWitchHat(entity)) {
            return;
        }

        double bodyRot = Math.toRadians((entity.yBodyRot % 360) + 360) % 360;

        addParticles(entity, bodyRot, InteractionHand.MAIN_HAND);
        addParticles(entity, bodyRot, InteractionHand.OFF_HAND);
    }

    private void addParticles(T entity, double bodyRot, InteractionHand hand) {
        if(entity.getRandom().nextFloat() < 0.05F && Scryers.hasSkull(entity, entity.getItemInHand(hand))) {
            double armOffset = (entity.getMainArm() == HumanoidArm.RIGHT) != (hand == InteractionHand.OFF_HAND) ? -1 : 1;
            double baseX = (0.4 * armOffset) + (entity.getRandom().nextDouble() * 0.20 - 0.1);
            double baseZ = 0.4 + (entity.getRandom().nextDouble() * 0.20 - 0.1);

            double offsetX = baseX * Math.cos(bodyRot) - baseZ * Math.sin(bodyRot);
            double offsetZ = baseX * Math.sin(bodyRot) + baseZ * Math.cos(bodyRot);

            double rotX = entity.position().x() + offsetX;
            double rotZ = entity.position().z() + offsetZ;
            entity.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, rotX, entity.position().y() + 0.8, rotZ, 0, 0, 0);
        }
    }
}
