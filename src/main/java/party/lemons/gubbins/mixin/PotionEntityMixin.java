package party.lemons.gubbins.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.misc.WaterPotionConcrete;

import java.util.List;

@Mixin(PotionEntity.class)
public abstract class PotionEntityMixin extends ThrownItemEntity
{
	public PotionEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world)
	{
		super(entityType, world);
	}

	@Inject(at = @At("RETURN"), method = "onBlockHit(Lnet/minecraft/util/hit/BlockHitResult;)V")
	public void onPotionBreak(BlockHitResult blockHitResult, CallbackInfo cbi)
	{
		if(Gubbins.config.MISC.enableWaterBottleConcrete)
		{
			PotionEntity self = (PotionEntity) (Object) this;
			ItemStack itemStack = getItem();
			Potion potion = PotionUtil.getPotion(itemStack);
			List<StatusEffectInstance> list = PotionUtil.getPotionEffects(itemStack);
			boolean isWater = potion == Potions.WATER && list.isEmpty();

			PlayerEntity player = self.getOwner() instanceof PlayerEntity ? (PlayerEntity) self.getOwner() : null;

			if(isWater) WaterPotionConcrete.convert(self.world, player, blockHitResult);
		}
	}
}
