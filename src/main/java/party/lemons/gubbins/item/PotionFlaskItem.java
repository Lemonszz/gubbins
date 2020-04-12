package party.lemons.gubbins.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.world.World;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.init.GubbinsItems;

import java.util.Iterator;
import java.util.List;

public class PotionFlaskItem extends Item
{
	public PotionFlaskItem()
	{
		super(GubbinsItems.settings().maxCount(1));

		this.addPropertyGetter(new Identifier("full"), (stack, world, entity) ->(getUsages(stack) > 0 && getPotion(stack) != Potions.EMPTY) ? 1 : 0);
	}

	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;
		if (playerEntity instanceof ServerPlayerEntity) {
			Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);
		}

		if (!world.isClient) {
			List<StatusEffectInstance> list = PotionUtil.getPotionEffects(stack);
			Iterator var6 = list.iterator();

			while(var6.hasNext()) {
				StatusEffectInstance statusEffectInstance = (StatusEffectInstance)var6.next();
				if (statusEffectInstance.getEffectType().isInstant()) {
					statusEffectInstance.getEffectType().applyInstantEffect(playerEntity, playerEntity, user, statusEffectInstance.getAmplifier(), 1.0D);
				} else {
					user.addStatusEffect(new StatusEffectInstance(statusEffectInstance));
				}
			}
		}

		if (playerEntity != null) {
			playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
			if (!playerEntity.abilities.creativeMode) {
				setUsages(stack, getUsages(stack) - 1);

				if(getUsages(stack) <= 0)
				{
					PotionUtil.setPotion(stack, Potions.EMPTY);
				}
			}
		}

		return stack;
	}

	public int getUsages(ItemStack stack)
	{
		if(stack.getTag() == null || !stack.getTag().contains("usages"))
			return 0;

		return stack.getTag().getInt("usages");
	}

	public void setUsages(ItemStack stack, int usages)
	{
		CompoundTag tags = stack.getOrCreateTag();
		tags.putInt("usages", usages);
		stack.setTag(tags);
	}

	public Potion getPotion(ItemStack stack)
	{
		return PotionUtil.getPotion(stack);
	}

	public int getMaxUseTime(ItemStack stack) {
		return 32;
	}

	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack st = user.getStackInHand(hand);

		if(getPotion(st) != Potions.EMPTY && getUsages(st) > 0)
		{
			user.setCurrentHand(hand);
			return TypedActionResult.success(user.getStackInHand(hand));
		}

		return TypedActionResult.pass(user.getStackInHand(hand));
	}

	public Text getName(ItemStack stack) {
		Potion potion = getPotion(stack);
		if(potion == Potions.EMPTY)
			return super.getName(stack);

		StatusEffectInstance in = potion.getEffects().get(0);
		return new TranslatableText(in.getTranslationKey()).append(" ").append(super.getName(stack));
	}

	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		if(getUsages(stack) > 0)
		{
			tooltip.add(new TranslatableText("gubbins.potion_flask_doses", getUsages(stack), Gubbins.config.POTION_FLASK.maxSize).setStyle(new Style().setColor(Formatting.DARK_PURPLE)));
		}

		PotionUtil.buildTooltip(stack, tooltip, 1.0F);
	}

	public boolean hasEnchantmentGlint(ItemStack stack) {
		return super.hasEnchantmentGlint(stack) || !PotionUtil.getPotionEffects(stack).isEmpty();
	}
}
