package party.lemons.gubbins.init;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockRenderView;
import party.lemons.gubbins.misc.CampfireDye;

public class GubbinsColours
{
	public static void init()
	{
		ColorProviderRegistry.ITEM.register(((stack, tintIndex) ->
		{
			if(tintIndex == 0 && PotionUtil.getPotion(stack) != Potions.EMPTY)
			{
				return PotionUtil.getColor(stack);
			}
			return 0xFFFFFF;
		}), GubbinsItems.POTION_FLASK);

		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex)->{
			if(tintIndex == 1 || tintIndex == 2)
			{
				BlockEntity te = world.getBlockEntity(pos);
				if(te != null && te instanceof CampfireBlockEntity)
				{
					CampfireDye dyer = (CampfireDye) te;
					if(dyer.getColor() != null)
					{
						if(tintIndex == 1)
							return dyer.getColor().getMaterialColor().color;

						return brighten(dyer.getColor().getMaterialColor().color, 60);
					}
				}
			}
			return 0xFFFFFF;
		}, Blocks.CAMPFIRE);
	}

	public static int brighten(int color, int amt){
		int red = (color & 0xff0000) >>> 16;
		int green = (color & 0x00ff00) >>> 8;
		int blue = (color & 0x00ff);

		red = MathHelper.clamp(red + amt, 0, 255);
		green = MathHelper.clamp(green + amt, 0, 255);
		blue = MathHelper.clamp(blue + amt, 0, 255);

		return (red << 16) + (green << 8) + (blue);
	}
}
