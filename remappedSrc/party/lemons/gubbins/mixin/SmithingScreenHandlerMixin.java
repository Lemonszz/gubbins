package party.lemons.gubbins.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SmithingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.adornment.Adornment;
import party.lemons.gubbins.adornment.Adornments;

@Mixin(SmithingScreenHandler.class)
public abstract class SmithingScreenHandlerMixin extends ForgingScreenHandler
{
	public SmithingScreenHandlerMixin(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context)
	{
		super(type, syncId, playerInventory, context);
	}

	@Inject(at = @At("RETURN"), method = "canTakeOutput(Lnet/minecraft/entity/player/PlayerEntity;Z)Z", cancellable = true)
	protected void canTakeOutput(PlayerEntity player, boolean present, CallbackInfoReturnable<Boolean> cbi)
	{
		if(Gubbins.config.ADORNMENTS.enabled)
		{
			ItemStack armourStack = this.input.getStack(0);
			ItemStack materialStack = this.input.getStack(1);
			Adornment adornment = Adornments.REGISTRY.getForMaterial(materialStack.getItem());
			if(adornment != null && !armourStack.isEmpty() && armourStack.getItem() instanceof ArmorItem)
			{
				ArmorItem armorItem = (ArmorItem) armourStack.getItem();
				if(armorItem.getMaterial().getRepairIngredient().test(materialStack)) return;
				if(armourStack.hasTag() && armourStack.getTag().contains("_adornment")) return;

				cbi.setReturnValue(true);
			}
		}
	}

	@Inject(at = @At("TAIL"), method = "updateResult()V")
	public void updateResult(CallbackInfo cbi)
	{
		if(!output.isEmpty() || !Gubbins.config.ADORNMENTS.enabled)
			return;

		ItemStack armourStack = this.input.getStack(0);
		ItemStack materialStack = this.input.getStack(1);

		Adornment adornment = Adornments.REGISTRY.getForMaterial(materialStack.getItem());
		if(adornment != null && !armourStack.isEmpty() && armourStack.getItem() instanceof ArmorItem)
		{
			ArmorItem armorItem = (ArmorItem) armourStack.getItem();
			if(armorItem.getMaterial().getRepairIngredient().test(materialStack))
				return;

			if(armourStack.hasTag() && armourStack.getTag().contains("_adornment"))
				return;

			ItemStack outputStack = new ItemStack(armorItem);
			CompoundTag origTag = armourStack.getTag();
			CompoundTag newTag;
			if(origTag == null)
				newTag = new CompoundTag();
			else
				newTag = origTag.copy();

			newTag.putString("_adornment", Adornments.REGISTRY.getId(adornment).toString());
			outputStack.setTag(newTag);

			output.setStack(0, outputStack);
		}
	}
}