package party.lemons.gubbins.init;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import party.lemons.gubbins.effect.GubbinsStatusEffect;
import party.lemons.gubbins.util.registry.AutoReg;

@AutoReg(type = StatusEffect.class, registry = "mob_effect", priority = 99)
public class GubbinsStatusEffects
{
	public static final StatusEffect CLARITY = new GubbinsStatusEffect(StatusEffectType.BENEFICIAL, 0x8dff0a);
}
