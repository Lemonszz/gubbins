package party.lemons.gubbins.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireworkItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworkItem.class)
public class FireworkItemMixin
{
	@Inject(at = @At("HEAD"), method = "use", cancellable = true)
	public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cbi)
	{
	/*	if (user.hasVehicle()) {
			ItemStack itemStack = user.getStackInHand(hand);
			if (!world.isClient) {
				world.spawnEntity(new FireworkRocketEntity(world, itemStack, user));
				if (!user.abilities.creativeMode) {
					itemStack.decrement(1);
				}
			}
			cbi.setReturnValue(TypedActionResult.success(user.getStackInHand(hand)));
		}*/
	}
}
