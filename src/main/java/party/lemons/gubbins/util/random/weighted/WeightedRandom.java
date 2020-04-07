package party.lemons.gubbins.util.random.weighted;

import com.google.common.collect.Lists;

import java.util.List;

public class WeightedRandom<T>
{
	private List<WeightOption<T>> options = Lists.newArrayList();

	private float totalWeight = 0;

	public void add(T obj, float weight)
	{
		options.add(new WeightOption<>(obj, weight));
		totalWeight += weight;
	}

	public boolean remove(T obj)
	{
		WeightOption<T> toRemove = null;
		for(WeightOption<T> checkObj : options)
		{
			if(obj.equals(checkObj.obj))
			{
				toRemove = checkObj;
				break;
			}
		}
		if(toRemove != null)
		{
			totalWeight -= toRemove.weight;
			options.remove(toRemove);
			return true;
		}

		return false;
	}

	public T select()
	{
		float rnd = (float) (Math.random() * totalWeight);

		for(int i = 0; i < options.size(); i++)
		{
			WeightOption<T> option = options.get(i);
			rnd -= option.weight;

			if(rnd <= 0F)
			{
				return option.obj;
			}
		}

		return null;
	}
}
