package party.lemons.gubbins.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.gubbins.item.quiver.QuiverItem;

@Mixin(ArrowItem.class)
public class ArrowItemMixin
{
	@Inject(at = @At("HEAD"), method = "createArrow(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/entity/projectile/PersistentProjectileEntity;")
	public void initFromStack(World world, ItemStack stack, LivingEntity shooter, CallbackInfoReturnable<PersistentProjectileEntity> cbi)
	{
		QuiverItem.doQuiverCheck(shooter);
	}
}
