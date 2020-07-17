package party.lemons.gubbins.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ItemChorusPearl extends Item
{
	public ItemChorusPearl(Settings settings)
	{
		super(settings);

		//DispenserBlock.registerBehavior(this, new ProjectileDispenserBehavior() {
		//	protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
		//		return Util.make(new ChorusPearlEntity(world, position.getX(), position.getY(), position.getZ()), (eggEntity) ->eggEntity.setItem(stack));
		//	}
		//});
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
	{
		ItemStack itemStack = user.getStackInHand(hand);
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (RANDOM.nextFloat() * 0.4F + 0.8F));
		user.getItemCooldownManager().set(this, 20);

		if (!world.isClient)
		{
		//	ChorusPearlEntity pearl = new ChorusPearlEntity(world, user);
		//	pearl.setItem(itemStack);
		//	pearl.setProperties(user, user.pitch, user.yaw, 0.0F, 1.5F, 1.0F);
		//	world.spawnEntity(pearl);
		}
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		if (!user.abilities.creativeMode)
		{
			itemStack.decrement(1);
		}

		return TypedActionResult.success(itemStack);
	}
}
