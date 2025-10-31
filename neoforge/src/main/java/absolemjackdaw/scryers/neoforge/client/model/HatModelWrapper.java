package absolemjackdaw.scryers.neoforge.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.BakedModelWrapper;

public class HatModelWrapper extends BakedModelWrapper<BakedModel> {

    private final BakedModel headModel;

    public HatModelWrapper(BakedModel originalModel, BakedModel headModel) {
        super(originalModel);
        this.headModel = headModel;
    }

    @Override
    public BakedModel applyTransform(ItemDisplayContext cameraTransformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        if(cameraTransformType == ItemDisplayContext.HEAD) {
            return headModel.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
        }

        return super.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
    }
}
