package party.lemons.gubbins.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.gubbins.entity.CamelEntity;
import party.lemons.gubbins.init.GubbinsEntities;

import java.util.HashMap;
import java.util.Map;

@Mixin(DefaultAttributeRegistry.class)
public class DefaultAttributeRegistryMixin
{
	@Shadow @Final @Mutable
	private static Map<EntityType<? extends LivingEntity>, DefaultAttributeContainer> DEFAULT_ATTRIBUTE_REGISTRY;

	@Inject(at = @At("TAIL"), method = "<clinit>")
	private static void onStaticInit(CallbackInfo cbi)
	{
		DEFAULT_ATTRIBUTE_REGISTRY = new HashMap<>(DEFAULT_ATTRIBUTE_REGISTRY);
		DEFAULT_ATTRIBUTE_REGISTRY.put(GubbinsEntities.CAMEL, CamelEntity.createAttributes().build());
	}
}
