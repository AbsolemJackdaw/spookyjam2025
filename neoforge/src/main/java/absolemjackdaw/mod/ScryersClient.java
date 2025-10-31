package absolemjackdaw.mod;

import absolemjackdaw.client.model.HatModelWrapper;
import absolemjackdaw.items.ScryerItems;
import com.google.common.base.Suppliers;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;

import java.util.function.Supplier;


@EventBusSubscriber(Dist.CLIENT)
public class ScryersClient {

    // FIXME cant call ScryersCommon because that initializes that item!
    private static final Supplier<ModelResourceLocation> WITCHHAT_HEAD_MODEL = Suppliers.memoize(() -> ModelResourceLocation.standalone(ScryersCommon.id("witchhat_model")));

    @SubscribeEvent
    public static void registerExtraModels(ModelEvent.RegisterAdditional event) {
        event.register(WITCHHAT_HEAD_MODEL.get());
    }

    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult event) {
        var headModel = event.getModels().get(WITCHHAT_HEAD_MODEL.get());
        event.getModels().computeIfPresent(ModelResourceLocation.inventory(ScryerItems.ITEM_HAT.getId()), (_location, bakedModel) -> new HatModelWrapper(bakedModel, headModel));
    }
}
