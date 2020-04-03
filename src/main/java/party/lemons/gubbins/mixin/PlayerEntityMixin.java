package party.lemons.gubbins.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.gubbins.init.GubbinsItems;

import java.util.function.Predicate;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin
{
	@Inject(at = @At("HEAD"), method = "getArrowType(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;", cancellable = true)
	public void getArrowType(ItemStack stack, CallbackInfoReturnable<ItemStack> cbi)
	{
		if (!(stack.getItem() instanceof RangedWeaponItem)) {
			cbi.setReturnValue(ItemStack.EMPTY);
		}
		else
		{
			PlayerEntity playerEntity = (PlayerEntity)(Object)this;
			Predicate<ItemStack> predicate = ((RangedWeaponItem)stack.getItem()).getHeldProjectiles();
			ItemStack itemStack = RangedWeaponItem.getHeldProjectile(playerEntity, predicate);

			for(int i = 0; i < playerEntity.inventory.size(); ++i) {
				ItemStack checkStack = playerEntity.inventory.getStack(i);
				if(!checkStack.isEmpty() && checkStack.getItem() == GubbinsItems.QUIVER)
				{
					CompoundTag tags = checkStack.getTag();
					if(tags == null)
						continue;

					ItemStack arrowStack = ItemStack.EMPTY;
					String t = "";
					if(tags.contains("1"))
					{
						arrowStack = ItemStack.fromTag(tags.getCompound("1"));
						t = "1";
					}
					else if(tags.contains("2"))
					{
						arrowStack = ItemStack.fromTag(tags.getCompound("2"));
						t = "2";
					}
					else if(tags.contains("3"))
					{
						arrowStack = ItemStack.fromTag(tags.getCompound("3"));
						t = "3";
					}

					if(!arrowStack.isEmpty())
					{
						//ItemStack returnStack = arrowStack.copy();
						//returnStack.decrement(1);

						//tags.put(t, returnStack.toTag(new CompoundTag()));

						cbi.setReturnValue(arrowStack);
						return;
					}

				}
			}
		}
	}
}
