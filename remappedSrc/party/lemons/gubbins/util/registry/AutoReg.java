package party.lemons.gubbins.util.registry;

import party.lemons.gubbins.Gubbins;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoReg
{
	Class type();
	String registry();
	String modid() default Gubbins.MODID;
	int priority() default 100;
}
