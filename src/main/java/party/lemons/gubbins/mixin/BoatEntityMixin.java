package party.lemons.gubbins.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.gubbins.boat.BoatType;
import party.lemons.gubbins.boat.BoatTypes;
import party.lemons.gubbins.entity.NewBoatEntity;
import party.lemons.gubbins.util.accessor.BoatAccessor;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin extends Entity implements BoatAccessor
{
	@Shadow private float yawVelocity;

	@Inject(at = @At("HEAD"), method = "interact(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Z", cancellable = true)
	public void interact(PlayerEntity player, Hand hand, CallbackInfoReturnable<Boolean> cbi)
	{
		ItemStack held = player.getStackInHand(hand);
		if(!(((BoatEntity) (Object) this) instanceof NewBoatEntity) && !player.world.isClient() && !held.isEmpty())
		{
			if(!hasPassengers() && held.getItem() == Blocks.CHEST.asItem())    //Chest
			{
				BoatType type = BoatTypes.getVanillaType(((BoatEntity) (Object) this).getBoatType());
				if(type != null)
				{
					held.decrement(1);
					NewBoatEntity boatEntity = new NewBoatEntity(((BoatEntity) (Object) this), type);
					this.remove();

					world.spawnEntity(boatEntity);
					cbi.setReturnValue(true);
				}
			}
		}
	}

	@Override
	public float getYawVelocity()
	{
		return yawVelocity;
	}

	public BoatEntityMixin(EntityType<?> type, World world)
	{
		super(type, world);
	}
}
