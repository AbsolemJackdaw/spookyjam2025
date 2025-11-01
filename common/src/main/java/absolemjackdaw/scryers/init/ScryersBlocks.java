package absolemjackdaw.scryers.init;

import absolemjackdaw.scryers.Scryers;
import absolemjackdaw.scryers.blocks.CrystalBallBlock;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ScryersBlocks {

    public static final RegistryHandler<Block> BLOCKS = RegistryHandler.create(Registries.BLOCK, Scryers.MODID);

    public static final RegistrySupplier<CrystalBallBlock> CRYSTAL_BALL = BLOCKS.register("crystal_ball", () -> new CrystalBallBlock(BlockBehaviour.Properties.of().noOcclusion().strength(1.0f).sound(SoundType.AMETHYST)));
}
