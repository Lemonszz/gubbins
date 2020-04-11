package party.lemons.gubbins.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DragonEggBlock.class)
public class DragonEggBlockMixin
{
	@Inject(at = @At("HEAD"), method = "onBlockBreakStart(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;)V", cancellable = true)
	public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player, CallbackInfo cbi) {
		ItemStack held = player.getMainHandStack();

		if(EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, held) >= 2)
		{
			cbi.cancel();
		}
	}
}
