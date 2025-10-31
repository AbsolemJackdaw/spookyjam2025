package absolemjackdaw.blocks;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockCrystalBall extends Block {

    private static final VoxelShape BALL = Block.box(4, 5, 4, 12, 13, 12);
    private static final VoxelShape FULL = Util.make(() -> {
       var base = Block.box(3, 0, 3, 13, 2, 13);
       var base2 = Block.box(5, 2, 5, 11, 4, 11);
       var armNorth = Block.box(7, 3, 2, 9, 5, 6);
       var armEast = Block.box(2, 3, 7, 6, 5, 9);
       var armSouth = Block.box(7, 3, 10, 9, 5, 14);
       var armWest = Block.box(10, 3, 7, 14, 5, 9);

       return Shapes.or(BALL, base, base2, armNorth, armEast, armSouth, armWest);
    });

    public BlockCrystalBall(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return FULL;
    }

    @Override
    protected VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return BALL;
    }
}
