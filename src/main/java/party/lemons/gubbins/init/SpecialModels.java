package party.lemons.gubbins.init;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;
import party.lemons.gubbins.Gubbins;

public class SpecialModels
{
	public static final SpecialModel[] MODELS = new SpecialModel[]{
		new SpecialModel(new Identifier(Gubbins.MODID, "sticky_item_frame"), ((StateManager.Builder<Block, BlockState>)(new StateManager.Builder(Blocks.AIR)).add(BooleanProperty.of("map"))).build(BlockState::new))
	};

	public static class SpecialModel
	{
		public final Identifier ID;
		public final StateManager<Block, BlockState> stateStateManager;

		public SpecialModel(Identifier identifier, StateManager<Block, BlockState> stateManager)
		{
			this.ID = identifier;
			this.stateStateManager = stateManager;
		}
	}
}
