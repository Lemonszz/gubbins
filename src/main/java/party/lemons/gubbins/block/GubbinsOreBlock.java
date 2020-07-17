package party.lemons.gubbins.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class GubbinsOreBlock extends TranslucentBlock
{
	private final int xpMin;
	private final int xpMax;

	public GubbinsOreBlock(Settings settings, int xpMin, int xpMax)
	{
		super(settings);

		this.xpMax = xpMax;
		this.xpMin = xpMin;

		if(FabricLoader.getInstance().getEnvironmentType() != EnvType.SERVER)
			BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getCutout());
	}

	public void onStacksDropped(BlockState state, World world, BlockPos pos, ItemStack stack) {
		super.onStacksDropped(state, world, pos, stack);
		if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) {
			int xpDropped = MathHelper.nextInt(world.random, xpMin, xpMax);
			if (xpDropped > 0) {
				this.dropExperience(world, pos, xpDropped);
			}
		}
	}
}
