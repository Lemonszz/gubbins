package party.lemons.gubbins.util.random.weighted;

public class WeightOption<T>
{
	public final T obj;
	public final float weight;

	public WeightOption(T obj, float weight)
	{
		this.obj = obj;
		this.weight = weight;
	}
}
