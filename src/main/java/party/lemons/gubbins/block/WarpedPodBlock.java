package party.lemons.gubbins.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import party.lemons.gubbins.init.GubbinsItems;

import java.util.Random;

public class WarpedPodBlock extends GubbinsBlock implements Fertilizable
{
	public static final IntProperty AGE = Properties.AGE_2;

	public WarpedPodBlock(Settings settings)
	{
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));

		if(FabricLoader.getInstance().getEnvironmentType() != EnvType.SERVER)
			BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getCutoutMipped());
	}

	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
	{
		if (world.getBaseLightLevel(pos, 0) <= 12) {
			int currentAge = state.get(AGE);
			if (currentAge < this.getMaxAge()) {
				if (random.nextInt(12) == 0)
				{
					world.setBlockState(pos, state.with(AGE, currentAge + 1), 2);
				}
			}
		}
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPES[state.get(AGE)];
	}

	private int getMaxAge()
	{
		return 2;
	}

	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return type == NavigationType.AIR;
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}

	@Override
	public boolean hasItem()
	{
		return false;
	}

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient)
	{
		return state.get(AGE) < 2;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state)
	{
		return isFertilizable(world, pos, state, world.isClient);
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state)
	{
		world.setBlockState(pos, state.with(AGE, state.get(AGE) + 1));
	}

	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return (world.getBaseLightLevel(pos, 0) <= 12) && world.getBlockState(pos.up()).isSideSolidFullSquare(world, pos, Direction.UP);
	}

	@Environment(EnvType.CLIENT)
	protected ItemConvertible getSeedsItem() {
		return GubbinsItems.WARPED_POD_SEEDS;
	}

	@Environment(EnvType.CLIENT)
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return new ItemStack(this.getSeedsItem());
	}

	public static final VoxelShape[] SHAPES = new VoxelShape[]{
					Block.createCuboidShape(5, 10, 5, 11, 16, 11),
					Block.createCuboidShape(4, 9, 4, 12, 16, 12),
					Block.createCuboidShape(0, 0, 0, 16, 16, 16),
			};
}
