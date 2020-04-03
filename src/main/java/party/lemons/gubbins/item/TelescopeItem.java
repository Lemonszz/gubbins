package party.lemons.gubbins.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class TelescopeItem extends Item
{
	public TelescopeItem(Settings settings)
	{
		super(settings);
	}

	@Override
	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks)
	{
		if(!world.isClient() && user instanceof PlayerEntity)
		{
			if(remainingUseTicks % 60 == 0)
			{
				stack.damage(1, user.getRandom(), (ServerPlayerEntity) user);
			}
		}

		super.usageTick(world, user, stack, remainingUseTicks);
	}

	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if(!(user instanceof PlayerEntity))
			return;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		user.setCurrentHand(hand);

		if(!world.isClient())
			user.getStackInHand(hand).damage(1, user.getRandom(), (ServerPlayerEntity) user);
		return TypedActionResult.success(user.getStackInHand(hand));
	}

	public int getMaxUseTime(ItemStack stack) {
		return 72000;
	}

	public UseAction getUseAction(ItemStack stack) {
		return UseAction.CROSSBOW;
	}
}
