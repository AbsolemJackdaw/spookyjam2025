package absolemjackdaw.blocks;

import absolemjackdaw.mod.ScryersCommon;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ScryerBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ScryersCommon.MODID);
    public static final DeferredBlock<Block> CRYSTAL_BALL = BLOCKS.register(ScryersCommon.CRYSTAL_BALL_NAME, BlockCrystalBall::new);
}
