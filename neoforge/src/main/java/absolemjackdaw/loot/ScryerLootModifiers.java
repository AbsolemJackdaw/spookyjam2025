package absolemjackdaw.loot;

import absolemjackdaw.mod.ScryersCommon;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ScryerLootModifiers extends LootModifier {

    private final Item item;
    private final int count;
    public static final MapCodec<ScryerLootModifiers> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst).and(inst.group(
                            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(o -> o.item),
                            Codec.INT.fieldOf("count").forGetter(o -> o.count)))
                    .apply(inst, ScryerLootModifiers::new));

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOTMODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ScryersCommon.MODID);

    public static final Supplier<MapCodec<ScryerLootModifiers>> ADD_HAT_TO_WITCH =
            GLOBAL_LOOTMODIFIERS.register("add_witchhat_to_witches", () -> CODEC);

    public ScryerLootModifiers(LootItemCondition[] conditionsIn, Item item, int count) {
        super(conditionsIn);
        this.item = item;
        this.count = count;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext) {
        generatedLoot.add(new ItemStack(item, count));
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
