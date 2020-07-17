package party.lemons.gubbins.mixin.client;

import com.google.common.collect.Maps;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.gubbins.init.GubbinsItems;
import party.lemons.gubbins.item.PotionFlaskItem;

import java.util.Map;

//TODO: Change name of this class to new name
@Mixin(ModelPredicateProviderRegistry.class)
public class ItemPropertyGetterMixin
{
	@Shadow @Final private static Map<Item, Map<Identifier, ModelPredicateProvider>> ITEM_SPECIFIC;

	@Inject(at = @At("RETURN"), method = "<clinit>")
	private static void onStaticConstruct(CallbackInfo cbi)
	{
		Map<Identifier, ModelPredicateProvider> potion_flask = Maps.newHashMap();
		potion_flask.put(new Identifier("full"), (stack, world, entity) ->(PotionFlaskItem.getUsages(stack) > 0 && PotionFlaskItem.getPotion(stack) != Potions.EMPTY) ? 1 : 0);
		ITEM_SPECIFIC.put(GubbinsItems.POTION_FLASK, potion_flask);
	}
}
