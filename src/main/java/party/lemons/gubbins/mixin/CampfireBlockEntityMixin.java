package party.lemons.gubbins.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.DyeColor;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.gubbins.init.GubbinsColours;
import party.lemons.gubbins.misc.BlockProperties;
import party.lemons.gubbins.misc.CampfireDye;
import party.lemons.gubbins.particle.DyedSmokeEffect;

import java.util.Random;

@Mixin(CampfireBlockEntity.class)
public abstract class CampfireBlockEntityMixin extends BlockEntity implements CampfireDye
{
	private DyeColor color = null;

	@Shadow @Final
	private DefaultedList<ItemStack> itemsBeingCooked;
	@Shadow @Final
	private int[] cookingTimes;
	@Shadow @Final
	private int[] cookingTotalTimes;

	public CampfireBlockEntityMixin(BlockEntityType<?> type)
	{
		super(type);
	}

	@Override
	public DyeColor getColor()
	{
		return color;
	}

	@Override
	public void setColor(DyeColor color)
	{
		this.color = color;
		updateListeners();
		markDirty();
	}

	@Inject(at = @At("HEAD"), method = "spawnSmokeParticles()V", cancellable = true)
	public void spawnSmokeParicles(CallbackInfo cbi)
	{
		CampfireBlockEntity be = (CampfireBlockEntity)(Object)this;
		if(getColor() != null && be.getCachedState().get(BlockProperties.DYED))
		{
			World world = be.getWorld();
			if(world != null)
			{
				BlockPos blockPos = be.getPos();
				Random random = world.random;
				int j;
				if(random.nextFloat() < 0.11F)
				{
					for(j = 0; j < random.nextInt(2) + 2; ++j)
					{
						boolean isSignal = be.getCachedState().get(CampfireBlock.SIGNAL_FIRE);
						int color = GubbinsColours.brighten(getColor().getMaterialColor().color, 50);
						int red = (color & 0xff0000) >>> 16;
						int green = (color & 0x00ff00) >>> 8;
						int blue = (color & 0x00ff);

						world.addImportantParticle(new DyedSmokeEffect(red / 255F, green / 255F, blue / 255F, isSignal), true, blockPos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), blockPos.getY() + random.nextDouble() + random.nextDouble(), (double)blockPos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
					}
				}

				j = be.getCachedState().get(CampfireBlock.FACING).getHorizontal();

				for(int k = 0; k < this.itemsBeingCooked.size(); ++k)
				{
					if(!this.itemsBeingCooked.get(k).isEmpty() && random.nextFloat() < 0.2F)
					{
						Direction direction = Direction.fromHorizontal(Math.floorMod(k + j, 4));
						float f = 0.3125F;
						double d = (double) blockPos.getX() + 0.5D - (double) ((float) direction.getOffsetX() * 0.3125F) + (double) ((float) direction.rotateYClockwise().getOffsetX() * 0.3125F);
						double e = (double) blockPos.getY() + 0.5D;
						double g = (double) blockPos.getZ() + 0.5D - (double) ((float) direction.getOffsetZ() * 0.3125F) + (double) ((float) direction.rotateYClockwise().getOffsetZ() * 0.3125F);

						for(int l = 0; l < 4; ++l)
						{
							world.addParticle(ParticleTypes.SMOKE, d, e, g, 0.0D, 5.0E-4D, 0.0D);
						}
					}
				}

			}

			cbi.cancel();
		}
	}

	@Inject(at = @At("TAIL"), method = "fromTag(Lnet/minecraft/block/BlockState;Lnet/minecraft/nbt/CompoundTag;)V")
	public void fromTag(BlockState blockState, CompoundTag compoundTag, CallbackInfo cbi)
	{
		if(compoundTag.contains("dye"))
		{
			color = DyeColor.values()[compoundTag.getInt("dye")];
		}
	}

	@Inject(at = @At("RETURN"), method = "saveInitialChunkData(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;")
	public void saveInitialChunkData(CompoundTag tag, CallbackInfoReturnable<CompoundTag> cbi)
	{
		if(getColor() != null)
			tag.putInt("dye", getColor().ordinal());
	}

	@Shadow
	private void updateListeners()
	{

	}

}
