package absolemjackdaw.playerdata;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ScryData {

    private final HashMap<ResourceLocation, Vec3> VISITED_DIMENIONS = new LinkedHashMap<>();

    public void visitDimension(ResourceLocation dimension, Vec3 coords) {
        if (VISITED_DIMENIONS.containsKey(dimension))
            VISITED_DIMENIONS.replace(dimension, coords);
        else
            VISITED_DIMENIONS.put(dimension, coords);
    }

    public Map<ResourceLocation, Vec3> getVisitedDimenions() {
        return new HashMap<>(VISITED_DIMENIONS);
    }
}
