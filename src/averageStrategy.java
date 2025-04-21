import java.util.List;

// calculates the average inflation rate
public class averageStrategy implements statStrategy
{
    @Override
    public String getName()
    {
        return "Average";
    }

    @Override
    public double compute(List<InflationCollection> collections)
    {
        // stream the rates and average them, say if empty
        return collections.stream().mapToDouble(InflationCollection::getInflationRate).average().orElse(Double.NaN);
    }
}
