package absolemjackdaw.scryers.neoforge.client;

import absolemjackdaw.scryers.Scryers;
import absolemjackdaw.scryers.init.ScryersItems;
import absolemjackdaw.scryers.neoforge.client.model.HatModelWrapper;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;


@EventBusSubscriber(Dist.CLIENT)
public class ScryersNeoForgeClient {

    private static final ModelResourceLocation WITCHHAT_HEAD_MODEL = ModelResourceLocation.standalone(Scryers.id("witchhat_model"));

    @SubscribeEvent
    public static void registerExtraModels(ModelEvent.RegisterAdditional event) {
        event.register(WITCHHAT_HEAD_MODEL);
    }

    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult event) {
        var headModel = event.getModels().get(WITCHHAT_HEAD_MODEL);
        event.getModels().computeIfPresent(ModelResourceLocation.inventory(ScryersItems.WITCH_HAT.getId()), (_location, bakedModel) -> new HatModelWrapper(bakedModel, headModel));
    }
}
