package party.lemons.gubbins.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ChorusFruitItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.gubbins.util.EntityUtil;

@Mixin(ChorusFruitItem.class)
public abstract class ChorusFruitItemMixin extends Item
{
	public ChorusFruitItemMixin(Settings settings)
	{
		super(settings);
	}

	@Inject(at = @At("HEAD"), method = "finishUsing(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/item/ItemStack;", cancellable = true)
	public void onFinishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cbi)
	{
		ItemStack itemStack = super.finishUsing(stack, world, user);
		EntityUtil.chorusTeleport(user);

		if(user instanceof PlayerEntity)
			((PlayerEntity) user).getItemCooldownManager().set(this, 20);

		cbi.setReturnValue(itemStack);
	}
}
