package party.lemons.gubbins.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.gubbins.misc.BlockProperties;
import party.lemons.gubbins.misc.CampfireDye;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin extends Block
{
	public CampfireBlockMixin()
	{
		super(null);
	}

	@Inject(at = @At("HEAD"), method = "onUse(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;", cancellable = true)
	public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cbi)
	{
		ItemStack stack = player.getStackInHand(hand);
		if(!stack.isEmpty() && stack.getItem() instanceof DyeItem)
		{
			BlockEntity be = world.getBlockEntity(pos);
			if(be != null && be instanceof CampfireBlockEntity)
			{
				CampfireDye campfire = (CampfireDye) be;
				DyeItem item = (DyeItem) stack.getItem();
				campfire.setColor(item.getColor());

				world.setBlockState(pos, state.with(BlockProperties.DYED, true));
				stack.decrement(1);
				cbi.setReturnValue(ActionResult.SUCCESS);
			}
		}
	}


	@Inject(at = @At("TAIL"), method = "<init>")
	public void onConstruct(boolean isSoul, AbstractBlock.Settings settings, CallbackInfo cbi)
	{
		setDefaultState(this.getDefaultState().with(BlockProperties.DYED, false));
	}

	@Inject(at = @At("TAIL"), method = "appendProperties(Lnet/minecraft/state/StateManager$Builder;)V")
	public void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo cbi) {
		builder.add(BlockProperties.DYED);
	}
}
