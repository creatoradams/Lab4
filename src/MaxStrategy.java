import java.util.List;

// finds the maxiumum inflation rate
public class MaxStrategy implements statStrategy
{
    @Override
    public String getName()
    {
        return "Maximum";
    }

    @Override
    public double compute(List<InflationCollection> collections)
    {
        // stream the rates and pick the max, say if empty
        return collections.stream().mapToDouble(InflationCollection::getInflationRate).max().orElse(Double.NaN);
    }
}
