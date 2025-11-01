package absolemjackdaw.scryers.fabric.components;

import absolemjackdaw.scryers.fabric.init.ScryersComponents;
import absolemjackdaw.scryers.playerdata.ScryData;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.entity.player.Player;
import org.ladysnake.cca.api.v3.component.Component;
import org.slf4j.Logger;

public class ScryerComponent implements Component {

    private final Player player;

    private static final Logger LOGGER = LogUtils.getLogger();
    private ScryData data = ScryData.empty();

    public ScryerComponent(Player player) {
        this.player = player;
    }

    @Override
    public void readFromNbt(CompoundTag compoundTag, HolderLookup.Provider provider) {
        var ops = RegistryOps.create(NbtOps.INSTANCE, provider);
        if(compoundTag.contains("data", Tag.TAG_COMPOUND)) {
            data = ScryData.CODEC.decode(ops, compoundTag.getCompound("data"))
                    .resultOrPartial(err -> LOGGER.error("Unable to decode component data: {}", err))
                    .map(Pair::getFirst)
                    .orElseGet(ScryData::empty);
        }
    }

    @Override
    public void writeToNbt(CompoundTag compoundTag, HolderLookup.Provider provider) {
        var ops = RegistryOps.create(NbtOps.INSTANCE, provider);
        ScryData.CODEC.encodeStart(ops, data)
                .resultOrPartial(err -> LOGGER.error("Unable to encode component data: {}", err))
                .ifPresent(tag -> compoundTag.put("data", tag));
    }

    public ScryData getData() {
        return data;
    }

    public void setData(ScryData data) {
        this.data = data;
        this.sync();
    }

    public void sync() {
        ScryersComponents.SCRY_DATA.sync(player);
    }
}
