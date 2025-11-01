package absolemjackdaw.scryers.fabric.client.model;

import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

@SuppressWarnings("SwitchStatementWithTooFewBranches")
public class HatModelWrapper extends ForwardingBakedModel {

    private final BakedModel headModel;
    private final ItemTransforms transforms;

    public HatModelWrapper(BakedModel wrapped, BakedModel headModel) {
        super(wrapped);
        this.headModel = headModel;
        var wT = wrapped.getTransforms();
        this.transforms = new ItemTransforms(
                wT.thirdPersonLeftHand,
                wT.thirdPersonRightHand,
                wT.firstPersonLeftHand,
                wT.firstPersonRightHand,
                headModel.getTransforms().head,
                wT.gui,
                wT.ground,
                wT.fixed
        );
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<RandomSource> randomSupplier, RenderContext context) {
        switch (context.itemTransformationMode()) {
            case HEAD -> headModel.emitItemQuads(stack, randomSupplier, context);
            default -> wrapped.emitItemQuads(stack, randomSupplier, context);
        }
    }

    @Override
    public void emitBlockQuads(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context) {
        switch (context.itemTransformationMode()) {
            case HEAD -> headModel.emitBlockQuads(blockView, state, pos, randomSupplier, context);
            default -> wrapped.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        }
    }

    @Override
    public ItemTransforms getTransforms() {
        return this.transforms;
    }
}
